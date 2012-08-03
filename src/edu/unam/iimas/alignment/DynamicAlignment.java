/*
/*
 * Alignment.java
 *
 * Created on 20 July 2006, 18:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment;

import edu.unam.iimas.xt.commons.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author xaltonalli
 */
public class DynamicAlignment {

    //Symbols can be Aminoacids or Nucleotides
    private boolean aminoacids = false;
    private boolean nucleotides = true;
    private int numberOfSequences;
    private int maxPositions;
    private int minNumberOfSymbolsInSequence;
    private int maxNumberSymbolsUnAligned;
    private int totalSymbols;
    private int totalGaps;
    private ArrayList<String> sequenceNames; //sequenceNames
    private ArrayList<ArrayList<Character>> alignedMatrix; //All the taxas have the same length in this alignedMatrix
    private ArrayList<ArrayList<Character>> unAlignedMatrix; //Taxas may have differnt lenght (according to the number of symbols of each)
    private ArrayList<ArrayList<Integer>> gapOffsetMatrix; //symIndex -> position
    private ArrayList<ArrayList<Integer>> symIndexMatrix; //position -> symIndex
    //private ArrayList<ArrayList<Boolean>> gapMatrix; //marks where are de gaps
    private ArrayList<Integer> numberSymbolsPerSequence; //numer of symbols per taxa
    //private ArrayList<Invertion> invertionsList;

    /** Creates a new instance of Alignment */
    public DynamicAlignment(Alignment minimalAlignmet) {

        char[][] staticAlignedMatrix = minimalAlignmet.getAlignedMatrix();
        String[] staticSequenceNames = minimalAlignmet.getSequenceNames();

        numberOfSequences = minimalAlignmet.getNumberOfSequences();
        maxPositions = minimalAlignmet.getMaxPositions();

        sequenceNames = new ArrayList<String>(numberOfSequences);
        alignedMatrix = new ArrayList<ArrayList<Character>>(numberOfSequences);
        unAlignedMatrix = new ArrayList<ArrayList<Character>>(numberOfSequences);
        gapOffsetMatrix = new ArrayList<ArrayList<Integer>>(numberOfSequences);
        symIndexMatrix = new ArrayList<ArrayList<Integer>>(numberOfSequences);
        //gapMatrix = new ArrayList<ArrayList<Boolean>>(numberOfSequences);
        numberSymbolsPerSequence = new ArrayList<Integer>(numberOfSequences);
        //invertionsList = new ArrayList<Invertion>();

        int numberSymbols = 0;
        int numberGaps = 0;
        maxNumberSymbolsUnAligned = 0;
        minNumberOfSymbolsInSequence = maxPositions;

        ArrayList<Character> alignedMatrixLine;
        ArrayList<Character> unAlignedMatrixLine;
        ArrayList<Integer> gapOffsetMatrixLine;
        ArrayList<Integer> symIndexMatrixLine;
        //ArrayList<Boolean> gapMatrixLine;

        for (int i = 0; i < numberOfSequences; i++) {

            sequenceNames.add(staticSequenceNames[i]);

            alignedMatrixLine = new ArrayList<Character>(maxPositions);
            unAlignedMatrixLine = new ArrayList<Character>(maxPositions);
            gapOffsetMatrixLine = new ArrayList<Integer>(maxPositions);
            symIndexMatrixLine = new ArrayList<Integer>(maxPositions);
            //gapMatrixLine = new ArrayList<Boolean>(maxPositions);

            numberSymbols = 0;
            numberGaps = 0;
            for (int j = 0; j < maxPositions; j++) {

                alignedMatrixLine.add(staticAlignedMatrix[i][j]);

                if ((staticAlignedMatrix[i][j] != Alignment.GAP)) {
                    unAlignedMatrixLine.add(staticAlignedMatrix[i][j]);
                    gapOffsetMatrixLine.add(numberGaps);
                    numberSymbols++;
                } else {
                    numberGaps++;
                }

                symIndexMatrixLine.add(numberSymbols - 1);

            }

            alignedMatrix.add(alignedMatrixLine);
            unAlignedMatrix.add(unAlignedMatrixLine);
            gapOffsetMatrix.add(gapOffsetMatrixLine);
            symIndexMatrix.add(symIndexMatrixLine);

            numberSymbolsPerSequence.add(numberSymbols);

            totalSymbols += numberSymbols;
            totalGaps += numberGaps;

            if (numberSymbols > maxNumberSymbolsUnAligned) {
                maxNumberSymbolsUnAligned = numberSymbols;
            }

            if (numberSymbols < minNumberOfSymbolsInSequence) {
                minNumberOfSymbolsInSequence = numberSymbols;
            }

        }

        //System.out.println(toString());

    }

    public int countAllGapColumns() {
        boolean[] allGaps = new boolean[maxPositions];
        int allGapColumns = 0;

        for (int i = 0; i < maxPositions; i++) {

            allGaps[i] = true;

            for (int j = 0; j < numberOfSequences; j++) {

                if (alignedMatrix.get(j).get(i) != Alignment.GAP) {
                    allGaps[i] = false;
                    break;
                }

            }

            if (allGaps[i] == true) {
                allGapColumns++;
            }

        }

        return allGapColumns;
    }

    @Override
    public String toString() {

        StringBuilder stringb = new StringBuilder();

        if (aminoacids) {
            stringb.append("Is Aminoacids");
        }
        if (nucleotides) {
            stringb.append("Is Nucleotides");
        }

        stringb.append(Constants.newline);

        stringb.append("numberOfSequences: ").append(numberOfSequences).append(Constants.newline);
        stringb.append("maxPositions: ").append(maxPositions).append(Constants.newline);
        stringb.append("minNumberOfSymbolsInTaxa: ").append(minNumberOfSymbolsInSequence).append(Constants.newline);
        stringb.append("maxNumberSymbolsUnAligned: ").append(maxNumberSymbolsUnAligned).append(Constants.newline);
        stringb.append("totalSymbols: ").append(totalSymbols).append(Constants.newline);
        stringb.append("totalGaps: ").append(totalGaps).append(Constants.newline);

        stringb.append(Constants.newline);
        stringb.append(Constants.newline);
        stringb.append("Alignment Matrices:").append(Constants.newline);

        stringb.append(Constants.newline);
        stringb.append("Sequence Names:").append(Constants.newline);
        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|").append(sequenceNames.get(i)).append(Constants.newline);
        }

        stringb.append(Constants.newline);
        stringb.append("Alignmed Matrix:").append(Constants.newline);
        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|");
            for (int j = 0; j < maxPositions; j++) {
                stringb.append(alignedMatrix.get(i).get(j));
            }
            stringb.append(Constants.newline);
        }

        stringb.append(Constants.newline);
        stringb.append("UnAligned Matrix:").append(Constants.newline);
        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|");
            for (int j = 0; j < unAlignedMatrix.get(i).size(); j++) {
                stringb.append(unAlignedMatrix.get(i).get(j));
            }
            stringb.append(Constants.newline);
        }

        stringb.append(Constants.newline);
        stringb.append("Gap Offset Matrix:").append(Constants.newline);
        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|");
            for (int j = 0; j < numberSymbolsPerSequence.get(i); j++) {
                stringb.append(gapOffsetMatrix.get(i).get(j)).append(" ");
            }
            stringb.append(Constants.newline);
        }

        stringb.append(Constants.newline);
        stringb.append("Symbol Index Matrix:").append(Constants.newline);
        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|");
            for (int j = 0; j < maxPositions; j++) {
                stringb.append(symIndexMatrix.get(i).get(j)).append(" ");
            }
            stringb.append(Constants.newline);
        }

        stringb.append(Constants.newline);
        stringb.append("Number of Symbols per Taxa:").append(Constants.newline);
        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|").append(numberSymbolsPerSequence.get(i)).append(Constants.newline);
        }


        return stringb.toString();
    }

    public String toShortString() {
        StringBuilder sbout = new StringBuilder();
        sbout.append("<");
        sbout.append("numberOfSequences: ").append(numberOfSequences).append(" ");
        sbout.append("maxPositions: ").append(maxPositions).append(" ");
        sbout.append("taxaNames: ").append(Arrays.toString(sequenceNames.toArray()));
        sbout.append(">");
        return sbout.toString();
    }

    public void degap() {

        /*
         *
        int numberOfSequences; <- remains the same
        int maxPositions; <- maxNumberSymbolsUnAligned
        int minNumberOfSymbolsInTaxa; <- remains the same
        int maxNumberSymbolsUnAligned; <- remains the same
        int totalSymbols; <- remains the same
        int totalGaps; <- 0
        ArrayList<String> taxaNames; //taxaNames <- remains the same
        ArrayList<ArrayList<Character>> alignedMatrix; //All the taxas have the same length in this alignedMatrix <- unAlignedMatrix
        ArrayList<ArrayList<Character>> unAlignedMatrix; //Taxas may have differnt lenght (according to the number of symbols of each) <- remains the same
        ArrayList<ArrayList<Integer>> gapOffsetMatrix; //symIndex -> position <- all zeros
        ArrayList<ArrayList<Integer>> symIndexMatrix; //position -> symIndex <- transparent relationship
        ArrayList<Integer> numberSymbolsPerTaxa; //numer of symbols per taxa <- remains the same
         *
         */

        maxPositions = maxNumberSymbolsUnAligned;
        totalGaps = 0;

        alignedMatrix = new ArrayList<ArrayList<Character>>(numberOfSequences);
        gapOffsetMatrix = new ArrayList<ArrayList<Integer>>(numberOfSequences);
        symIndexMatrix = new ArrayList<ArrayList<Integer>>(numberOfSequences);

        ArrayList<Character> unAlignedMatrixLine;
        ArrayList<Character> alignedMatrixLine;
        ArrayList<Integer> gapOffsetMatrixLine;
        ArrayList<Integer> symIndexMatrixLine;

        for (int i = 0; i < numberOfSequences; i++) {

            unAlignedMatrixLine = unAlignedMatrix.get(i);
            alignedMatrixLine = new ArrayList<Character>(maxPositions);
            gapOffsetMatrixLine = new ArrayList<Integer>(maxPositions);
            symIndexMatrixLine = new ArrayList<Integer>(maxPositions);

            for (int j = 0; j < unAlignedMatrix.get(i).size(); j++) {
                alignedMatrixLine.add(unAlignedMatrixLine.get(j));
                gapOffsetMatrixLine.add(0);
                symIndexMatrixLine.add(j);
            }

            for (int j = unAlignedMatrix.get(i).size(); j < maxPositions; j++) {
                alignedMatrixLine.add(Alignment.GAP);
                symIndexMatrixLine.add(unAlignedMatrix.get(i).size() - 1);
            }

            alignedMatrix.add(alignedMatrixLine);
            gapOffsetMatrix.add(gapOffsetMatrixLine);
            symIndexMatrix.add(symIndexMatrixLine);

        }

    }

    public int getPositionOfSymbolInAlignment(int nSequence, int unAlignedBasePosition) {

        return (gapOffsetMatrix.get(nSequence).get(unAlignedBasePosition) + unAlignedBasePosition);

    }

    public int getSymbolofPositionInAlignment(int nSequence, int position) {

        return (symIndexMatrix.get(nSequence).get(position));

    }

    public Alignment getMinimalAlignment() {

        String[] taxaNamesArray = new String[numberOfSequences];
        taxaNamesArray = sequenceNames.toArray(taxaNamesArray);

        char[][] staticAlignedMatrix = new char[numberOfSequences][maxPositions];
        ArrayList<Character> alignedMatrixLine = null;

        for (int i = 0; i < numberOfSequences; i++) {
            alignedMatrixLine = alignedMatrix.get(i);
            for (int j = 0; j < maxPositions; j++) {
                staticAlignedMatrix[i][j] = alignedMatrixLine.get(j);
            }
        }

        return new Alignment(staticAlignedMatrix, taxaNamesArray);

    }

        public AlignmentUM getMinimalAlignmentUM() {

        String[] taxaNamesArray = new String[numberOfSequences];
        taxaNamesArray = sequenceNames.toArray(taxaNamesArray);

        char[][] staticAlignedMatrix = new char[numberOfSequences][maxPositions];
        ArrayList<Character> alignedMatrixLine = null;

        for (int i = 0; i < numberOfSequences; i++) {
            alignedMatrixLine = alignedMatrix.get(i);
            for (int j = 0; j < maxPositions; j++) {
                staticAlignedMatrix[i][j] = alignedMatrixLine.get(j);
            }
        }

        return new AlignmentUM(staticAlignedMatrix, taxaNamesArray);

    }

    /**
     * @return the aminoacids
     */
    public boolean isAminoacids() {
        return aminoacids;
    }

    /**
     * @return the nucleotides
     */
    public boolean isNucleotides() {
        return nucleotides;
    }

    /**
     * @return the numberOfSequences
     */
    public int getNumberOfSequences() {
        return numberOfSequences;
    }

    /**
     * @return the maxPositions
     */
    public int getMaxPositions() {
        return maxPositions;
    }

    /**
     * @return the minNumberOfSymbolsInTaxa
     */
    public int getMinNumberOfSymbolsInTaxa() {
        return minNumberOfSymbolsInSequence;
    }

    /**
     * @return the maxNumberSymbolsUnAligned
     */
    public int getMaxNumberSymbolsUnAligned() {
        return maxNumberSymbolsUnAligned;
    }

    /**
     * @return the totalSymbols
     */
    public int getTotalSymbols() {
        return totalSymbols;
    }

    /**
     * @return the totalGaps
     */
    public int getTotalGaps() {
        return totalGaps;
    }

    /**
     * @return the sequenceNames
     */
    public ArrayList<String> getSequenceNames() {
        return sequenceNames;
    }

    /**
     * @return the alignedMatrix
     */
    public ArrayList<ArrayList<Character>> getAlignedMatrix() {
        return alignedMatrix;
    }

    /**
     * @return the unAlignedMatrix
     */
    public ArrayList<ArrayList<Character>> getUnAlignedMatrix() {
        return unAlignedMatrix;
    }

    /**
     * @return the gapOffsetMatrix
     */
    public ArrayList<ArrayList<Integer>> getGapOffsetMatrix() {
        return gapOffsetMatrix;
    }

    /**
     * @return the symIndexMatrix
     */
    public ArrayList<ArrayList<Integer>> getSymIndexMatrix() {
        return symIndexMatrix;
    }

    /**
     * @return the numberSymbolsPerTaxa
     */
    public ArrayList<Integer> getNumberSymbolsPerSequence() {
        return numberSymbolsPerSequence;
    }

    /**
     * @param maxPositions the maxPositions to set
     */
    public void setMaxPositions(int maxPositions) {
        this.maxPositions = maxPositions;
    }

    /**
     * @param totalGaps the totalGaps to set
     */
    public void setTotalGaps(int totalGaps) {
        this.totalGaps = totalGaps;
    }

    /**
     * @param numberSymbolsPerTaxa the numberSymbolsPerTaxa to set
     */
    public void setNumberSymbolsPerSequence(ArrayList<Integer> numberSymbolsPerSequence) {
        this.numberSymbolsPerSequence = numberSymbolsPerSequence;
    }
}
