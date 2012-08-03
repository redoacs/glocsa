/*
 * EventCounter.java
 *
 * Created on May 3, 2007, 3:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment.glocsa.core;

import edu.unam.iimas.alignment.Alignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author xaltonalli
 */
public abstract class EventCounter {

    public static EventCount countEvents(Alignment alignment) {
        EventCount eventCount = new EventCount();

        int[] substitutionEventsPerColumn = countSubstitutionEventsPerColumn(alignment);

        eventCount.setSubstitutionEventsPerColumn(substitutionEventsPerColumn);
        eventCount.setSubstitutionEvents(concentrateSubstitutionEventsTotal(substitutionEventsPerColumn));
        eventCount.setInDelEvents(countInDelEvents(alignment));
        eventCount.setTotalEvents(eventCount.getInDelEvents() + eventCount.getSubstitutionEvents());

        return eventCount;
    }

    private static int concentrateSubstitutionEventsTotal(int[] substitutionEventsPerColumn) {

        int nEvents = 0;
        for (int j = 0; j < substitutionEventsPerColumn.length; j++) {
            nEvents += substitutionEventsPerColumn[j];
        }

        return nEvents;

    }

    private static int[] countSubstitutionEventsPerColumn(Alignment alignment) {

        char[][] matrix = alignment.getAlignedMatrix();
        int numberOfSequences = alignment.getNumberOfSequences();
        int maxPositions = alignment.getMaxPositions();

        int nCharacters;
        int basicCharacters;
        char[] supportedCharacters;
        int supportedCharactersGapPosition;
        double[][] charactersWeightsMatrix;

        if (alignment.isNucleotides()) {
            nCharacters = NucleotidesRatingConstants.charactersWeightsMatrix[0].length - 1;
            basicCharacters = NucleotidesRatingConstants.basicCharacters;
            supportedCharacters = NucleotidesRatingConstants.supportedCharacters;
            supportedCharactersGapPosition = NucleotidesRatingConstants.supportedCharactersGapPosition;
            charactersWeightsMatrix = NucleotidesRatingConstants.charactersWeightsMatrix;
        } else {
            nCharacters = AminoacidsRatingConstants.charactersWeightsMatrix[0].length - 1;
            basicCharacters = AminoacidsRatingConstants.basicCharacters;
            supportedCharacters = AminoacidsRatingConstants.supportedCharacters;
            supportedCharactersGapPosition = AminoacidsRatingConstants.supportedCharactersGapPosition;
            charactersWeightsMatrix = AminoacidsRatingConstants.charactersWeightsMatrix;
        }


        int[] substitutionEventsPerColumn = new int[maxPositions];

        double[] probabilityOfBase = new double[basicCharacters]; // 'A'  ,'C'  ,'G'  ,'T'
        //boolean[] assumedBase = new boolean[basicCharacters];

        boolean[][] isBasePosible = new boolean[numberOfSequences][basicCharacters];

        boolean[] considerTaxa = new boolean[numberOfSequences];


        for (int j = 0; j < maxPositions; j++) {

            for (int k = 0; k < basicCharacters; k++) {
                probabilityOfBase[k] = 0.0;
            }

            for (int i = 0; i < numberOfSequences; i++) {

                considerTaxa[i] = false;
                for (int k = 0; k < basicCharacters; k++) {
                    isBasePosible[i][k] = false;
                }

                for (int l = 0; l < nCharacters; l++) {

                    if ((matrix[i][j] == supportedCharacters[l]) && (matrix[i][j] != supportedCharacters[supportedCharactersGapPosition])) {  //We are going to use the matrix

                        //System.out.println("l["+j+"]: " + l + " - " + supportedCharacters[l]);
                        considerTaxa[i] = true;

                        for (int k = 0; k < basicCharacters; k++) { //We know what it is, mark the bases
                            probabilityOfBase[k] = probabilityOfBase[k] += charactersWeightsMatrix[k][l]; //revisa esto
                            isBasePosible[i][k] = isBasePosible[i][k] || (charactersWeightsMatrix[k][l] > 0);
                        //System.out.println("probabilityOfBase["+k+"]["+j+"]: " + probabilityOfBase[k] + " - " + charactersWeightsMatrix[l][k]);

                        }

                        break;

                    }

                }

            }

            substitutionEventsPerColumn[j] = 0;

            int[] order = calculateOrderOfProbs(probabilityOfBase);
            int p = 0;

            boolean taxasUnconsidered = false;

            for (int i = 0; i < numberOfSequences; i++) {
                taxasUnconsidered = taxasUnconsidered || considerTaxa[i];
            }

            while (taxasUnconsidered && (p < basicCharacters)) {

                substitutionEventsPerColumn[j]++;

                for (int i = 0; i < numberOfSequences; i++) {
                    if (isBasePosible[i][order[p]]) {
                        considerTaxa[i] = false;
                    }
                }

                taxasUnconsidered = false;
                for (int i = 0; i < numberOfSequences; i++) {
                    taxasUnconsidered = taxasUnconsidered || considerTaxa[i];
                }

                p++;

            }



            if (substitutionEventsPerColumn[j] > 0) {
                substitutionEventsPerColumn[j]--;
            }

        //System.out.println("mutationEventsColumn[j]: " + substitutionEventsPerColumn[j]);

        }

        return substitutionEventsPerColumn;
    }

    private static int[] calculateOrderOfProbs(double[] probabilityOfBase) {

        int[] order = new int[probabilityOfBase.length];
        boolean[] used = new boolean[probabilityOfBase.length];
        int max = 0;

        for (int i = 0; i < probabilityOfBase.length; i++) {
            max = -1;
            for (int j = 0; j < probabilityOfBase.length; j++) {
                if (!used[j]) {
                    if (max == -1) {
                        max = j;
                    } else if (probabilityOfBase[j] > probabilityOfBase[max]) {
                        max = j;
                    }
                }
            }
            used[max] = true;
            order[i] = max;
        }

        return order;

    }

    private static int countInDelEvents(Alignment alignment) {

        ArrayList<HashMap<Integer, Integer>> gapRepresentation = GapCounter.getGapExistenceOfAlignment(alignment);

        Set<Map.Entry<Integer, Integer>> gapConcentration = GapCounter.integrateGapExistance(gapRepresentation);

        return gapConcentration.size();

    }
}
