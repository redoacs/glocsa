/*
 * Alignment.java
 *
 * Created on 20 July 2006, 18:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment;

import edu.unam.iimas.alignment.glocsa.core.AminoacidsRatingConstants;
import edu.unam.iimas.alignment.glocsa.core.NucleotidesRatingConstants;
import edu.unam.iimas.xt.commons.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author xaltonalli
 */
public class Alignment {

    public static boolean NUCLEOTIDES = true;
    public static boolean AMINOACIDS = false;
    //Symbols can be Aminoacids or Nucleotides we do not know yet, while reading lineIsValidSequence() we will determine it
    private boolean dataType = NUCLEOTIDES;
    protected String[] sequenceNames; //sequenceNames
    protected char[][] alignedMatrix; //All the taxas have the same length in this alignedMatrix
    protected int numberOfSequences;
    protected int maxPositions;
    static public char GAP = '-';
    static public char NI = '?';
    protected String aminoacidsPermitedChars = new String(AminoacidsRatingConstants.supportedCharacters);
    protected String nucleotidesPermitedChars = new String(NucleotidesRatingConstants.supportedCharacters);

    /** Creates a new instance of Alignment */
    public Alignment() {
    }

    public Alignment(BufferedReader bufferedReader) throws IOException, NonSupportedAlignmentException {
        readFilefromBufferdStream(bufferedReader);
    }

    public Alignment(InputStream inputStream) throws IOException, NonSupportedAlignmentException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        readFilefromBufferdStream(bufferedReader);
    }

    public Alignment(InputStreamReader inputStreamReader) throws IOException, NonSupportedAlignmentException {
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        readFilefromBufferdStream(bufferedReader);
    }

    public Alignment(File file) throws IOException, NonSupportedAlignmentException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        readFilefromBufferdStream(bufferedReader);
    }

    // It does not validate data type
    public Alignment(char charMatrix[][]) {
        alignedMatrix = charMatrix;
        sequenceNames = new String[alignedMatrix.length];
        numberOfSequences = alignedMatrix.length;
        maxPositions = alignedMatrix[0].length;

        trimAlignment();
    }

    // It does not validate data type
    public Alignment(char charMatrix[][], String[] taxaNames) {
        alignedMatrix = charMatrix;
        if (alignedMatrix.length != taxaNames.length) {
            throw new RuntimeException("Names' array is of different size than expected from matrix");
        }
        this.sequenceNames = taxaNames;
        numberOfSequences = alignedMatrix.length;
        maxPositions = alignedMatrix[0].length;

        trimAlignment();
    }

    public Alignment(char charMatrix[][], String[] taxaNames, boolean dataType) {
        this.dataType = dataType;
        alignedMatrix = charMatrix;
        if (alignedMatrix.length != taxaNames.length) {
            throw new RuntimeException("Names' array is of different size than expected from matrix");
        }
        this.sequenceNames = taxaNames;
        numberOfSequences = alignedMatrix.length;
        maxPositions = alignedMatrix[0].length;

        trimAlignment();
    }

    // It does not validate data type
    private void reInitializeFromCharMatrix(char charMatrix[][]) {

        alignedMatrix = charMatrix;
        numberOfSequences = alignedMatrix.length;
        maxPositions = alignedMatrix[0].length;

        trimAlignment();

    }

    private void readFilefromBufferdStream(BufferedReader bufferedReader) throws IOException, NonSupportedAlignmentException {

        this.dataType = Alignment.NUCLEOTIDES;

        ArrayList<String> sequenceNamesList = new ArrayList<String>();
        ArrayList<String> sequences = new ArrayList<String>();

        String line;
        numberOfSequences = 0;
        maxPositions = 0;
        StringBuffer aSequence = new StringBuffer("");
        boolean readingASequence = false;
        int nLine = 0;

        while ((line = bufferedReader.readLine()) != null) {
            nLine++;
            line = line.trim();
            //line = line.toUpperCase();
            if (line.length() != 0) { //It's not an empty line
                if (line.charAt(0) != ';') { //It's not a comment
                    if (line.charAt(0) == '>') { //A sequence starts
                        if (numberOfSequences != 0) { //It is not the first sequence to be read, previous sequence finished and needs to be stored
                            if (aSequence.length() > maxPositions) {
                                maxPositions = aSequence.length();  //Getting the maximun length of a sequence
                            }

                            sequences.add(aSequence.toString());
                            aSequence = new StringBuffer("");
                        }
                        readingASequence = true;
                        sequenceNamesList.add(line.substring(1));
                        numberOfSequences++;
                    } else { //Reading a sequence
                        line = line.toUpperCase();
                        if (readingASequence) {
                            try {
                                validateSequenceLine(line);
                                aSequence.append(line);
                            } catch (NonSupportedAlignmentException nsaException) {
                                throw new NonSupportedAlignmentException(nsaException, nLine);
                            }

                        } else {
                            //Not good, probably not the correct file
                            throw new IOException("This file does not seem to be a fasta format file, there are lines before a sequence starts");
                        }
                    }
                }
            }
        }
        if (numberOfSequences != 0) { //One ore more sequences have been read, previous sequence finished and needs to be stored
            if (aSequence.length() > maxPositions) {
                maxPositions = aSequence.length(); //Getting the maximun length of a sequence
            }
            sequences.add(aSequence.toString());
        }

        bufferedReader.close();

        this.sequenceNames = new String[numberOfSequences];

        for (int i = 0; i < getNumberOfSequences(); i++) {
            this.sequenceNames[i] = sequenceNamesList.get(i);
        }

        this.alignedMatrix = new char[numberOfSequences][maxPositions]; //Initialize the matix with gaps

        for (int i = 0; i < getNumberOfSequences(); i++) {
            for (int j = 0; j < maxPositions; j++) {
                this.alignedMatrix[i][j] = GAP;
            }
        }

        char[] srcCharArry;

        for (int i = 0; i < getNumberOfSequences(); i++) {
            srcCharArry = sequences.get(i).toCharArray();
            System.arraycopy(srcCharArry, 0, this.alignedMatrix[i], 0, srcCharArry.length);
        }

        trimAlignment();

    }

    public void readFilefromStream(InputStream inputStream) throws IOException, NonSupportedAlignmentException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        readFilefromBufferdStream(bufferedReader);
    }

    public void writeAlignment(File toFile) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFile));

        for (int i = 0; i < this.numberOfSequences; i++) {
            bufferedWriter.write(">" + this.getSequenceNames()[i]);
            bufferedWriter.newLine();
            for (int j = 0; j < alignedMatrix[i].length; j++) {
                if (((j + 1) % 80) != 0) {
                    bufferedWriter.write(this.alignedMatrix[i][j]);
                } else {
                    bufferedWriter.write(this.alignedMatrix[i][j]);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

    }

    public String getAlignmentFileString() {

        StringBuilder alignmentStringBuffer = new StringBuilder();

        for (int i = 0; i < this.numberOfSequences; i++) {
            alignmentStringBuffer.append(">").append(this.getSequenceNames()[i]);
            alignmentStringBuffer.append(Constants.newline);
            for (int j = 0; j < alignedMatrix[i].length; j++) {
                if (((j + 1) % 80) != 0) {
                    alignmentStringBuffer.append(this.alignedMatrix[i][j]);
                } else {
                    alignmentStringBuffer.append(this.alignedMatrix[i][j]);
                    alignmentStringBuffer.append(Constants.newline);
                }
            }
            alignmentStringBuffer.append(Constants.newline);
        }

        return alignmentStringBuffer.toString();

    }

    private void validateSequenceLine(String line) throws NonSupportedAlignmentException {

        int linelength = line.length();
        boolean nucleotides = this.dataType;
        for (int i = 0; i < linelength; i++) {

            if (nucleotides) {
                if (!nucleotidesPermitedChars.contains(line.subSequence(i, i + 1))) {
                    nucleotides = false;
                    if (!aminoacidsPermitedChars.contains(line.subSequence(i, i + 1))) {
                        throw new NonSupportedAlignmentException(line.charAt(i), i + 1);
                    }
                }
            } else {
                if (!aminoacidsPermitedChars.contains(line.subSequence(i, i + 1))) {
                    throw new NonSupportedAlignmentException(line.charAt(i), i + 1);
                }
            }
        }

        this.dataType = nucleotides;

        return;
    }

    private void trimAlignment() {

        int initialColumn;
        int finalColumn;

        boolean stillLooking;

        stillLooking = true;
        initialColumn = 0;
        for (int j = 0; j < maxPositions; j++) {

            for (int i = 0; i < numberOfSequences; i++) {
                if (this.alignedMatrix[i][j] != Alignment.GAP) {
                    stillLooking = false;
                }


            }

            if (!stillLooking) {
                break;
            }

            initialColumn++;

        }


        stillLooking = true;
        finalColumn = maxPositions - 1;
        for (int j = maxPositions - 1; j >= 0; j--) {

            for (int i = 0; i < numberOfSequences; i++) {
                if (this.alignedMatrix[i][j] != Alignment.GAP) {
                    stillLooking = false;
                }


            }

            if (!stillLooking) {
                break;
            }

            finalColumn--;

        }

        int trimedColumns = finalColumn - initialColumn + 1;


        char[][] trimedMatrix = new char[numberOfSequences][trimedColumns];

        for (int j = initialColumn, k = 0; j <= finalColumn; j++, k++) {

            for (int i = 0; i < numberOfSequences; i++) {

                trimedMatrix[i][k] = alignedMatrix[i][j];

            }

        }

        alignedMatrix = trimedMatrix;
        maxPositions = trimedColumns;


    }

    public int countAllGApColumns() {

        boolean[] allGaps = new boolean[maxPositions];
        int allGapColumns = 0;

        for (int i = 0; i < maxPositions; i++) {

            allGaps[i] = true;

            for (int j = 0; j < numberOfSequences; j++) {

                if (alignedMatrix[j][i] != Alignment.GAP) {
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

    public void purgeAlignment() {

        boolean[] allGaps = new boolean[maxPositions];
        int allGapColumns = 0;

        for (int i = 0; i < maxPositions; i++) {

            allGaps[i] = true;

            for (int j = 0; j < numberOfSequences; j++) {

                if (alignedMatrix[j][i] != Alignment.GAP) {
                    allGaps[i] = false;
                    break;
                }

            }

            if (allGaps[i] == true) {
                allGapColumns++;
            }

        }

        int newMaxPositions = maxPositions - allGapColumns;
        char[][] newCharMatrix = new char[numberOfSequences][newMaxPositions];
        int newColumnCounter = 0;

        for (int i = 0; i < maxPositions; i++) {

            if (!allGaps[i]) {

                for (int j = 0; j < numberOfSequences; j++) {

                    newCharMatrix[j][newColumnCounter] = alignedMatrix[j][i];

                }

                newColumnCounter++;

            }

        }

        reInitializeFromCharMatrix(newCharMatrix);

    }

    @Override
    public String toString() {

        StringBuilder stringb = new StringBuilder();

        stringb.append("numberOfSequences: ").append(numberOfSequences).append(Constants.newline);
        stringb.append("maxPositions: ").append(maxPositions).append(Constants.newline);
        stringb.append("names: ").append(Arrays.toString(sequenceNames)).append(Constants.newline);

        for (int i = 0; i < numberOfSequences; i++) {
            stringb.append(i).append("|");
            for (int j = 0; j < maxPositions; j++) {
                stringb.append(alignedMatrix[i][j]);
            }
            stringb.append(Constants.newline);
        }

        return stringb.toString();
    }

    public String toShortString() {
        StringBuilder sbout = new StringBuilder();
        sbout.append("<");
        sbout.append("numberOfSequences: ").append(numberOfSequences).append(" ");
        sbout.append("maxPositions: ").append(maxPositions).append(" ");
        sbout.append("names: ").append(Arrays.toString(sequenceNames));
        sbout.append(">");
        return sbout.toString();
    }

    public void replaceDotsWithDashes() {

        for (int i = 0; i < numberOfSequences; i++) {
            for (int j = 0; j < maxPositions; j++) {
                if (alignedMatrix[i][j] == '.') {
                    alignedMatrix[i][j] = '-';
                }
            }
        }

        reInitializeFromCharMatrix(alignedMatrix);

    }

    public void replaceQMWithDashes() {

        for (int i = 0; i < numberOfSequences; i++) {
            for (int j = 0; j < maxPositions; j++) {
                if (alignedMatrix[i][j] == '?') {
                    alignedMatrix[i][j] = '-';
                }
            }
        }

        reInitializeFromCharMatrix(alignedMatrix);

    }

    public void replaceIFQMWithDashes() {

        boolean stillContiguous;

        for (int i = 0; i < numberOfSequences; i++) {

            stillContiguous = true;
            for (int j = 0; (j < maxPositions) && stillContiguous; j++) {
                if (alignedMatrix[i][j] == '?') {
                    alignedMatrix[i][j] = '-';
                } else {
                    stillContiguous = false;
                }
            }

            stillContiguous = true;
            for (int j = maxPositions - 1; (j >= 0) && stillContiguous; j--) {
                if (alignedMatrix[i][j] == '?') {
                    alignedMatrix[i][j] = '-';
                } else {
                    stillContiguous = false;
                }
            }

        }

        reInitializeFromCharMatrix(alignedMatrix);

    }

    public char getGAP() {
        return GAP;
    }

    public char[][] getAlignedMatrix() {
        return alignedMatrix;
    }

    public String[] getSequenceNames() {
        return sequenceNames;
    }

    public void setSequenceNames(String[] sequenceNames) {
        this.sequenceNames = sequenceNames;
    }

    public int getNumberOfSequences() {
        return numberOfSequences;
    }

    public int getMaxPositions() {
        return maxPositions;
    }

    /**
     * @return the dataType
     */
    public boolean isDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(boolean dataType) {
        this.dataType = dataType;
    }

    /*
     * Convinience method for dataType comparisons
     */
    public boolean isNucleotides() {
        return dataType;
    }
}
