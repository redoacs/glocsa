/*
 * BaseGapReplacer.java
 *
 * Created on November 28, 2007, 11:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment.glocsa.util;

import edu.unam.iimas.alignment.Alignment;
import edu.unam.iimas.alignment.glocsa.core.AminoacidsRatingConstants;
import edu.unam.iimas.alignment.glocsa.core.NucleotidesRatingConstants;

/**
 *
 * @author xaltonalli
 */
public class BaseGapReplacer {

    /** Creates a new instance of BaseGapReplacer */
    private BaseGapReplacer() {
    }

    public static Alignment replaceNwithGap(Alignment alignment) {
        Alignment newAlignment = null;
        char[][] matrix = alignment.getAlignedMatrix();

        int numberOfSequences = alignment.getNumberOfSequences();
        int maxPositions = alignment.getMaxPositions();

        char[][] newMatrix = new char[numberOfSequences][maxPositions];

        char[] supportedCharacters;
        int supportedCharactersGapPosition;
        int supportedCharactersAnyPosition;

        if (alignment.isNucleotides()) {
            supportedCharacters = NucleotidesRatingConstants.supportedCharacters;
            supportedCharactersGapPosition = NucleotidesRatingConstants.supportedCharactersGapPosition;
            supportedCharactersAnyPosition = NucleotidesRatingConstants.supportedCharactersAnyPosition;
        } else {
            supportedCharacters = AminoacidsRatingConstants.supportedCharacters;
            supportedCharactersGapPosition = AminoacidsRatingConstants.supportedCharactersGapPosition;
            supportedCharactersAnyPosition = AminoacidsRatingConstants.supportedCharactersAnyPosition;
        }

        for (int i = 0; i < numberOfSequences; i++) {
            for (int j = 0; j < maxPositions; j++) {

                if (matrix[i][j] == supportedCharacters[supportedCharactersAnyPosition]) {
                    newMatrix[i][j] = supportedCharacters[supportedCharactersGapPosition];
                } else {
                    newMatrix[i][j] = matrix[i][j];
                }


            }
        }

        newAlignment = new Alignment(newMatrix);
        newAlignment.setSequenceNames(alignment.getSequenceNames());

        return newAlignment;

    }
}
