/*
 * NotSupportedAlignmentException.java
 *
 * Created on 26 July 2006, 17:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.unam.iimas.alignment;

/**
 *
 * @author xaltonalli
 */
public class NonSupportedAlignmentException extends Exception {

    private int nLine = 0;
    private int position = 0;
    private char character = 0;

    /** Creates a new instance of NotSupportedAlignmentException */
    public NonSupportedAlignmentException() {
    }

    public NonSupportedAlignmentException(char character, int position) {
        super();
        this.character = character;
        this.position = position;
    }

    public NonSupportedAlignmentException(char character, int position, int nLine) {
        super();
        this.character = character;
        this.position = position;
        this.nLine = nLine;
    }

    public NonSupportedAlignmentException(NonSupportedAlignmentException ex, int nLine) {
        super();
        this.character = ex.getCharacter();
        this.position = ex.getPosition();
        this.nLine = nLine;
    }

    @Override
    public String getMessage() {
        return "It is not a Protein or DNA Sequence Alignment. Invalid character at position " + position + " in line " + nLine + " : " + character;
    }

    /**
     * @return the nLine
     */
    public int getnLine() {
        return nLine;
    }

    /**
     * @param nLine the nLine to set
     */
    public void setnLine(int nLine) {
        this.nLine = nLine;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the character
     */
    public char getCharacter() {
        return character;
    }

    /**
     * @param character the character to set
     */
    public void setCharacter(char character) {
        this.character = character;
    }
}
