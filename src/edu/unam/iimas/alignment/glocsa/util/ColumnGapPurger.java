/*
 * ColumnGapPurger.java
 *
 * Created on May 15, 2007, 6:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.util;

import edu.unam.iimas.alignment.Alignment;

/**
 *
 * @author xaltonalli
 */
public class ColumnGapPurger {
    
    /** Creates a new instance of ColumnGapPurger */
    public ColumnGapPurger() {
    }
    
    public static Alignment purgeColumnsGap(Alignment alignment) {
        
        char[][] matrix = alignment.getAlignedMatrix();
        
        int maxPositions = alignment.getMaxPositions();
        int numberOfSequences = alignment.getNumberOfSequences();
        boolean[] validColumns = new boolean[maxPositions];
        int inValidColumnCount = maxPositions;
        
        for (int j = 0 ; j < maxPositions ; j ++) {
            validColumns[j] = false; //Assuming all positions in the column i are gaps
            for (int i = 0 ; i < numberOfSequences ; i++) {
                if (matrix[i][j] != Alignment.GAP) {
                    validColumns[j] = true;
                    inValidColumnCount--;
                    break;
                }
            }
        }
        
        char[][] purgedMatrix = new char[numberOfSequences][maxPositions-inValidColumnCount];
        
        
        for (int j = 0 , newColumnIndex = 0; j < maxPositions ; j ++) {
            if (validColumns[j]) {
                for (int i = 0 ; i < numberOfSequences ; i++) {
                    purgedMatrix[i][newColumnIndex] = matrix[i][j];
                }
                newColumnIndex++;
            }
        }
        
        Alignment purgedAlignment = new Alignment(purgedMatrix);
        
        purgedAlignment.setSequenceNames(alignment.getSequenceNames());
        
        
        
        return purgedAlignment;
        
    }
    
}
