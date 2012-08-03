/*
 * Alignment.java
 *
 * Created on 20 July 2006, 18:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Alignment that also calculates de unalignedMatrix and related indicators.
 *
 * @author xaltonalli
 */
public class AlignmentUM extends Alignment {

    private char[][] unAlignedMatrix; //Taxas may have different lengths in this unAlignedMatrix
    private int minNumberOfSymbolsInSequence;
    private int[] numberSymbolsPerSequence;
    private int maxNumberSymbolsUnAligned;
    private int totalBases;

    /** Creates a new instance of Alignment */
    public AlignmentUM() {
    }

    public AlignmentUM(BufferedReader bufferedReader) throws IOException, NonSupportedAlignmentException {
        super(bufferedReader);
        extractUnAlignedMatrix();
    }

    public AlignmentUM(InputStream inputStream) throws IOException, NonSupportedAlignmentException {
        super(inputStream);
        extractUnAlignedMatrix();
    }

    public AlignmentUM(InputStreamReader inputStreamReader) throws IOException, NonSupportedAlignmentException {
        super(inputStreamReader);
        extractUnAlignedMatrix();
    }

    public AlignmentUM(File file) throws IOException, NonSupportedAlignmentException {
        super(file);
        extractUnAlignedMatrix();
    }

    public AlignmentUM(char charMatrix[][]) {
        super(charMatrix);
        extractUnAlignedMatrix();
    }

    public AlignmentUM(char charMatrix[][], String[] taxaNames) {
        super(charMatrix, taxaNames);
        extractUnAlignedMatrix();
    }

    //The UnAligned matrix will have a constant number of lines, terminals or taxas, and each line will have a variable number of positions
    private void extractUnAlignedMatrix() {

        char tmpMatrix[][] = new char[numberOfSequences][maxPositions];

        numberSymbolsPerSequence = new int[numberOfSequences];

        int numberSymbols = 0;
        maxNumberSymbolsUnAligned = 0;
        minNumberOfSymbolsInSequence = maxPositions;

        totalBases = 0;

        for (int i = 0; i < numberOfSequences; i++) {
            numberSymbols = 0;
            for (int j = 0; j < maxPositions; j++) {
                if ((alignedMatrix[i][j] != Alignment.GAP)) {
                    tmpMatrix[i][numberSymbols] = alignedMatrix[i][j];
                    numberSymbols++;
                }
            }
            numberSymbolsPerSequence[i] = numberSymbols;
            totalBases += numberSymbols;

            if (numberSymbols > maxNumberSymbolsUnAligned) {
                maxNumberSymbolsUnAligned = numberSymbols;
            }

            if (numberSymbols < minNumberOfSymbolsInSequence) {
                minNumberOfSymbolsInSequence = numberSymbols;
            }

        }

        unAlignedMatrix = new char[numberOfSequences][];

        for (int i = 0; i < numberOfSequences; i++) {
            unAlignedMatrix[i] = new char[numberSymbolsPerSequence[i]];

//            for (int j = 0; j < numberSymbolsPerTaxa[i]; j++) {
//                unAlignedMatrix[i][j] = tmpMatrix[i][j];
//            }

            System.arraycopy(tmpMatrix[i], 0, unAlignedMatrix[i], 0, numberSymbolsPerSequence[i]);
        }

    }

    /**
     * @return the unAlignedMatrix
     */
    public char[][] getUnAlignedMatrix() {
        return unAlignedMatrix;
    }

    /**
     * @param unAlignedMatrix the unAlignedMatrix to set
     */
    public void setUnAlignedMatrix(char[][] unAlignedMatrix) {
        this.unAlignedMatrix = unAlignedMatrix;
    }

    /**
     * @return the minNumberOfSymbolsInTaxa
     */
    public int getMinNumberOfSymbolsInSequence() {
        return minNumberOfSymbolsInSequence;
    }

    /**
     * @param minNumberOfSymbolsInTaxa the minNumberOfSymbolsInTaxa to set
     */
    public void setMinNumberOfSymbolsInSequence(int minNumberOfSymbolsInSequence) {
        this.minNumberOfSymbolsInSequence = minNumberOfSymbolsInSequence;
    }

    /**
     * @return the numberSymbolsPerTaxa
     */
    public int[] getNumberSymbolsPerSequence() {
        return numberSymbolsPerSequence;
    }

    /**
     * @param numberSymbolsPerTaxa the numberSymbolsPerTaxa to set
     */
    public void setNumberSymbolsPerSequence(int[] numberSymbolsPerSequence) {
        this.numberSymbolsPerSequence = numberSymbolsPerSequence;
    }

    /**
     * @return the maxNumberSymbolsUnAligned
     */
    public int getMaxNumberSymbolsUnAligned() {
        return maxNumberSymbolsUnAligned;
    }

    /**
     * @param maxNumberSymbolsUnAligned the maxNumberSymbolsUnAligned to set
     */
    public void setMaxNumberSymbolsUnAligned(int maxNumberSymbolsUnAligned) {
        this.maxNumberSymbolsUnAligned = maxNumberSymbolsUnAligned;
    }

    /**
     * @return the totalBases
     */
    public int getTotalBases() {
        return totalBases;
    }

    /**
     * @param totalBases the totalBases to set
     */
    public void setTotalBases(int totalBases) {
        this.totalBases = totalBases;
    }
}
