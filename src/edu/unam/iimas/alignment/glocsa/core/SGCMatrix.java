/*
 * SGCMatrix.java
 *
 * Created on August 9, 2007, 4:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public class SGCMatrix {
    
    private Gap[] gaps;
    private String[] names;
    private char[][] exist;
    
    /**
     * Creates a new instance of SGCMatrix
     */
    public SGCMatrix(Gap[] _gaps, String[] _names, char[][] _exist) {
        this.setGaps(_gaps);
        this.setNames(_names);
        this.setExist(_exist);
    }
    
    public Gap[] getGaps() {
        return gaps;
    }
    
    public void setGaps(Gap[] gaps) {
        this.gaps = gaps;
    }
    
    public String[] getNames() {
        return names;
    }
    
    public void setNames(String[] names) {
        this.names = names;
    }
    
    public char[][] getExist() {
        return exist;
    }
    
    public void setExist(char[][] exist) {
        this.exist = exist;
    }
    
    public String toString() {
        
        StringBuffer stringBuffer = new StringBuffer();
        
        
        //stringBuffer.append();
        
        stringBuffer.append("          \t"); // 10 spaces
        
        for ( int j = 0 ; j < gaps.length ; j ++ ) {
            stringBuffer.append(gaps[j]);
            stringBuffer.append("\t");
            
        }
        stringBuffer.append("\n");
        
        for ( int i = 0 ; i < exist.length ; i ++ ) {
            stringBuffer.append(names[i].substring(0,Math.min(9,names[i].length())));
            stringBuffer.append("\t");
            for ( int j = 0 ; j < gaps.length ; j ++ ) {
                stringBuffer.append(exist[i][j]);
                stringBuffer.append("\t");
            }
            stringBuffer.append("\n");
        }
        
        return stringBuffer.toString();
        
    }
    
    
}
