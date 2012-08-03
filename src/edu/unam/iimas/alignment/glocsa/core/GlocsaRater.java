/*
 * GlocsaRater.java
 *
 * Created on 26 July 2006, 13:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment.glocsa.core;

import edu.unam.iimas.alignment.Alignment;
import java.util.ArrayList;

/**
 *
 * @author xaltonalli
 */
public class GlocsaRater {

    private double wMeanColumnValue;
    private double wGapConcentration;
    private double wColumnRatio;
    //private double affineGapOpenningCost;
    //private double affineGapExtensionCost;

    //Auxiliares
    private int maxVTerminalsSequence;

    /** Creates a new instance of GlocsaRater */
    public GlocsaRater(GlocsaParameters parameters) {
        wMeanColumnValue = parameters.getWMeanColumnValue();
        wGapConcentration = parameters.getWGapConcentration();
        wColumnRatio = parameters.getWColumnIncrementRatio();

        //affineGapOpenningCost = parameters.getAffineGapOpenningCost();
        //affineGapExtensionCost = parameters.getAffineGapExtensionCost();
    }

    public GlocsaRater() {
        wMeanColumnValue = GlocsaParameters.wMeanColumnValueDefault;
        wGapConcentration = GlocsaParameters.wGapConcentrationDefault;
        wColumnRatio = GlocsaParameters.wColumnIncrementRatioDefault;

        //affineGapOpenningCost = GlocsaParameters.affineGapOpenningCostDefault;
        //affineGapExtensionCost = GlocsaParameters.affineGapExtensionCostDefault;
    }

    public GlocsaRating rate(Alignment alignment) {

        GlocsaRating glocsaRating = new GlocsaRating();
        boolean[][] valuableDataMask = createValuableDataMask(alignment);
        boolean[][] nINFGapDataMask = createNINFGapDataMask(alignment);

        double[] columnValues = rateAlignmentColumns(alignment, valuableDataMask);
        glocsaRating.setColumnValues(columnValues);

        glocsaRating.setColumnsAligned(columnValues.length);

        glocsaRating.setMeanColumnValue(calculateMeanColumnValue(columnValues));

        /* makeGapCalculations sets:
         *
         *  meanGapBlockSize
         *  totalGapPositions
         *  columnsNotAligned
         */
        makeGapCalculations(alignment, valuableDataMask, nINFGapDataMask, glocsaRating);

        //glocsaRating.setGapConcentration(calculateGapConcentration(glocsaRating.getMeanGapBlockSize(), glocsaRating.getTotalGapPositions()));
        glocsaRating.setGapConcentration(calculateGapConcentration(glocsaRating.getNumberGapBlocks()));
        glocsaRating.setColumnIncrementRatio(calculateColumnIncrementRatio(glocsaRating.getColumnsAligned(), glocsaRating.getColumnsNotAligned()));

        double combinedValue = combineResults(glocsaRating.getMeanColumnValue(), glocsaRating.getGapConcentration(), glocsaRating.getColumnIncrementRatio());

        glocsaRating.setRatingValue(combinedValue);


        return glocsaRating;
    }

    public double combineResults(double meanColumnValue, double gapConcentration, double columnIncrementRatio) {
        double value = 0;

        value = wMeanColumnValue * meanColumnValue + wGapConcentration * gapConcentration + wColumnRatio * columnIncrementRatio;

        return value;
    }

    /*
     *  No se toman en cuenta los signos de interrogacion
     */
    public boolean[][] createValuableDataMask(Alignment alignment) {

        int numberOfSequences = alignment.getNumberOfSequences();
        int maxPositions = alignment.getMaxPositions();
        char[][] matrix = alignment.getAlignedMatrix();

        boolean[][] valuableDataMask = new boolean[numberOfSequences][maxPositions];

        for (int i = 0; i < numberOfSequences; i++) {
            for (int j = 0; j < maxPositions; j++) {
                valuableDataMask[i][j] = true;
            }
        }


        for (int i = 0; i < numberOfSequences; i++) {
            for (int j = maxPositions - 1; j >= 0; j--) {

                if (matrix[i][j] == '?') {
                    valuableDataMask[i][j] = false;
                }

            }
        }

        return valuableDataMask;

    }

    /*
     *  No se toman en cuenta los gaps iniciales, ni finales
     */
    public boolean[][] createNINFGapDataMask(Alignment alignment) {

        int numberOfSequences = alignment.getNumberOfSequences();
        int maxPositions = alignment.getMaxPositions();
        char[][] matrix = alignment.getAlignedMatrix();

        boolean[][] nINFGapDataMask = new boolean[numberOfSequences][maxPositions];

        for (int i = 0; i < numberOfSequences; i++) {
            for (int j = 0; j < maxPositions; j++) {
                nINFGapDataMask[i][j] = true;
            }
        }

        boolean initialBlock = false;
        boolean finalBlock = false;

        for (int i = 0; i < numberOfSequences; i++) {
            initialBlock = true;
            for (int j = 0; j < maxPositions; j++) {

                if (initialBlock && matrix[i][j] != Alignment.GAP) {
                    initialBlock = false;
                }

                if (initialBlock) {
                    nINFGapDataMask[i][j] = false;
                }

            }
        }

        for (int i = 0; i < numberOfSequences; i++) {
            finalBlock = true;
            for (int j = maxPositions - 1; j >= 0; j--) {

                if (finalBlock && matrix[i][j] != Alignment.GAP) {
                    finalBlock = false;
                }

                if (finalBlock) {
                    nINFGapDataMask[i][j] = false;
                }
            }
        }



        return nINFGapDataMask;

    }

    private double[] rateAlignmentColumns(Alignment alignment, boolean[][] valuableDataMask) {

        char[][] matrix = alignment.getAlignedMatrix();
        int numberOfSequences = alignment.getNumberOfSequences();
        int maxPositions = alignment.getMaxPositions();
        int nCharacters;
        int nBasicCharacters;
        int lines;
        double[][] charactersWeightsMatrix;
        char[] supportedCharacters;
        int supportedCharactersGapPosition;

        if (alignment.isNucleotides()) {
            nCharacters = NucleotidesRatingConstants.charactersWeightsMatrix[0].length;
            nBasicCharacters = NucleotidesRatingConstants.charactersWeightsMatrix.length;
            lines = NucleotidesRatingConstants.charactersWeightsMatrix[0].length;
            charactersWeightsMatrix = NucleotidesRatingConstants.charactersWeightsMatrix;
            supportedCharacters = NucleotidesRatingConstants.supportedCharacters;
            supportedCharactersGapPosition = NucleotidesRatingConstants.supportedCharactersGapPosition;
        } else {
            nCharacters = AminoacidsRatingConstants.charactersWeightsMatrix[0].length;
            nBasicCharacters = AminoacidsRatingConstants.charactersWeightsMatrix.length;
            lines = AminoacidsRatingConstants.charactersWeightsMatrix[0].length;
            charactersWeightsMatrix = AminoacidsRatingConstants.charactersWeightsMatrix;
            supportedCharacters = AminoacidsRatingConstants.supportedCharacters;
            supportedCharactersGapPosition = AminoacidsRatingConstants.supportedCharactersGapPosition;
        }

        double[] columnValues = new double[maxPositions];
        int[] count = new int[nCharacters];
        //int intCount = 0;
        int[] numSeqsConsidered = new int[maxPositions];


        for (int j = 0; j < maxPositions; j++) {
            count = new int[nCharacters];
            for (int i = 0; i < numberOfSequences; i++) {
                if (valuableDataMask[i][j]) {
                    for (int k = 0; k < nCharacters; k++) {

                        if (matrix[i][j] == supportedCharacters[k]) {  //We are going to use the matrix
                            count[k]++;
                            break;
                        }

                    }
                    numSeqsConsidered[j]++;
                }
            }
            //intCount = 0;

            //double dtCount = 0;

            double lowerTerm = 0;
            double product;

            //for (int k = 0 ; k < nCharacters ; k++) {
            //Here is where we use the matrix
            for (int l = 0; l < nBasicCharacters; l++) {

                product = 0;
                for (int k = 0; k < nCharacters; k++) {
                    product += count[k] * charactersWeightsMatrix[l][k];
                }
                if (l != supportedCharactersGapPosition) {
                    columnValues[j] += Math.pow(product, 2);
                }

                lowerTerm += product;

            }

//                columnValues[j] += Math.pow(count[k]*charactersWeights[k],2);
//                dtCount += (double)count[k]*charactersWeights[k];
//                intCount += count[k];
            //}
            if ((count[supportedCharactersGapPosition] == numSeqsConsidered[j])) { // If it is all gaps or it did not considered any character, columnValue is zero
                columnValues[j] = 0;
            } else {
                columnValues[j] /= Math.pow(lowerTerm, 2);
            //columnValues[j] /= Math.pow(intCount,2);
            }

        }

        return columnValues;

    }

    private double calculateMeanColumnValue(double[] columnValues) {

        double meanColumnValue = 0;

        int nColumns = columnValues.length;

        for (int i = 0; i < nColumns; i++) {
            meanColumnValue += columnValues[i];
        }

        meanColumnValue /= nColumns;

        return meanColumnValue;

    }

//    private double calculateGapConcentration(double meanGapBlockSize, int totalGapPositions) {
//
//        double gapConcentration = 0;
//
//        if (totalGapPositions != 0) {
//            gapConcentration = meanGapBlockSize / (double) totalGapPositions;
//        }
//        return gapConcentration;
//
//    }

//    private double calculateGapConcentration(double bestCaseScenarioAffineGapModelCost, double affineGapModelCost, int totalGapPositions) {
//
//        double gapConcentration = 0;
//
//        if (totalGapPositions > 0) {
//            gapConcentration = bestCaseScenarioAffineGapModelCost / affineGapModelCost;
//        }
//
//        return gapConcentration;
//
//    }

        private double calculateGapConcentration(int nGapBlocks) {

        double gapConcentration = 1;

        if (nGapBlocks > 0) {
            gapConcentration = 1.0 / (double )nGapBlocks;
        }

        return gapConcentration;

    }

    private double calculateColumnIncrementRatio(int columnsAligned, int columnsNotAligned) {
        return (double) columnsAligned / (double) columnsNotAligned - 1.0;
    }

    private void makeGapCalculations(Alignment alignment, boolean[][] valuableDataMask, boolean[][] nINFGapDataMask, GlocsaRating glocsaRating) {

        char[][] matrix = alignment.getAlignedMatrix();
        int numberOfSequences = alignment.getNumberOfSequences();
        int maxPositions = alignment.getMaxPositions();
        boolean readingGapBlock = false;
        int nGapBlocks = 0;
        int gapBlockSize = 0;
        ArrayList<Integer> gapBlockSizes = new ArrayList<Integer>(); //TODO Consider using better constructor parameters
        int nGapPositions = 0;
        int nVTerminalsSequence = 0;
        maxVTerminalsSequence = 0;

        for (int i = 0; i < numberOfSequences; i++) {
            nVTerminalsSequence = 0;                                    //We are going to count the number of terminals than are not gaps in each sequence
            readingGapBlock = false;
            for (int j = 0; j < maxPositions; j++) {
                if (valuableDataMask[i][j] && nINFGapDataMask[i][j]) {
                    if (matrix[i][j] == Alignment.GAP) {                //It is a GAP
                        nGapPositions++;                                        // Counting total gaps
                        if (!readingGapBlock) {
                            readingGapBlock = true;
                            nGapBlocks++;
                            gapBlockSize = 0;                           //TODO This changed from the prototype please check
                        }
                        gapBlockSize++;
                    } else {                                            //It is not a GAP
                        nVTerminalsSequence++;
                        if (readingGapBlock) {
                            readingGapBlock = false;
                            gapBlockSizes.add(new Integer(gapBlockSize));
                        }
                    }
                }
            }

            //The next code should only work when counting final gap blocks
            if (readingGapBlock) {
                readingGapBlock = false;
                gapBlockSizes.add(new Integer(gapBlockSize));
            }

            if (nVTerminalsSequence > maxVTerminalsSequence) {
                maxVTerminalsSequence = nVTerminalsSequence;            //Keep the maximun value
            }
        }

        //We have the size of every gap block

        //int nGapBlocks = gapBlockSizes.size();
        double meanGapBlockSize = 0;

        for (int i = 0; i < nGapBlocks; i++) {
            //meanGapBlockSize += (double) ((Integer) gapBlockSizes.get(i)).intValue();
            meanGapBlockSize += (double) gapBlockSizes.get(i);
        }

        if (nGapBlocks != 0) {
            meanGapBlockSize /= (double) nGapBlocks;
        } else {
            meanGapBlockSize = 0;
        }

        //double affineGapModelCost = calculateAffineGapModelCost(gapBlockSizes);
        //double bestCaseScenarioAffineGapModelCost = calculateBestCaseScenarioAffineGapModelCost(nGapPositions);

        glocsaRating.setMeanGapBlockSize(meanGapBlockSize); // Original, not divided
        glocsaRating.setTotalGapPositions(nGapPositions);
        glocsaRating.setColumnsNotAligned(maxVTerminalsSequence);
        glocsaRating.setNumberGapBlocks(nGapBlocks);
        //glocsaRating.setAffineGapModelCost(affineGapModelCost);
        //glocsaRating.setBestCaseScenarioAffineGapModelCost(bestCaseScenarioAffineGapModelCost);

    }

//    private double calculateAffineGapModelCost(ArrayList<Integer> gapBlockSizes) {
//        double cost = 0;
//        int nGapBlocks = gapBlockSizes.size();
//        int gapBlockSize;
//        for (int i = 0; i < nGapBlocks; i++) {
//            gapBlockSize = gapBlockSizes.get(i);
//            cost += (double) affineGapOpenningCost + affineGapExtensionCost * (double) (gapBlockSize - 1);
//        }
//        return cost;
//    }
//
//    private double calculateBestCaseScenarioAffineGapModelCost(int nGapPositions) {
//        double bcsCost = 0;
//        if (nGapPositions > 0) {
//            bcsCost = affineGapOpenningCost + affineGapExtensionCost * (double) (nGapPositions - 1);
//        }
//        return bcsCost;
//    }


}
