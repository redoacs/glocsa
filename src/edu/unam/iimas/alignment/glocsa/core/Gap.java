/*
 * Gap.java
 *
 * Created on August 9, 2007, 4:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public class Gap implements Comparable<Gap> {
    
    /** Creates a new instance of Gap */
    
    private int position;
    private int length;
    
//    public Gap() {
//        this.position = 0;
//        this.length = 0;
//    }
    
    public Gap(int _position, int _length) {
        this.setPosition(_position);
        this.setLength(_length);
    }
    
    public int compareTo(Gap o) {
        int result = 0;
        if (this.position < o.getPosition()) {
            result = -1;
        } else if (this.position > o.getPosition()) {
            result = 1;
        } else { // position is the same
            if (this.length < o.getLength()) {
                result = -1;
            }
            else if (this.length > o.getLength()) {
                result = 1;
            } else {
                result = 0;
            }
                
        }
        
        return result;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    public String toString() {
        return new String( position + "," + length );
    }
    
}
