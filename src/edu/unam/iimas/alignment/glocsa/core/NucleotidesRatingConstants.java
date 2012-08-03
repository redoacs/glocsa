/*
 * NucleotidesRatingConstants.java
 *
 * Created on May 31, 2007, 1:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public abstract class NucleotidesRatingConstants {
    
    
    static public char[] supportedCharacters = {'A','C','G','T','-', 'N', 'M', 'R', 'W', 'S', 'Y', 'K', 'V', 'H', 'D', 'B' , '?'}; //If you change this, change also supportedCharactersGapPosition and supportedCharactersAnyPosition accordingly
    //static public char[] supportedPolymorphisms = {'N', 'M', 'R', 'W', 'S', 'Y', 'K', 'V', 'H', 'D', 'B' };
    static public int supportedCharactersGapPosition = 4;
    static public int supportedCharactersAnyPosition = 5;
    static public int basicCharacters = 4;
    static public double[][] charactersWeightsMatrix =  {   
    //    'A',  'C',  'G' , 'T',  '-',  'N',  'M',  'R',  'W',  'S',  'Y',  'K',    'V',      'H',      'D',    'B'
        { 1.0 , 0.0 , 0.0 , 0.0 , 0.0 , .25 , 0.5 , 0.5 , 0.5 , 0.0 , 0.0 , 0.0 , 1.0/3.0 , 1.0/3.0 , 1.0/3.0 , 0.0} ,         //A
        { 0.0 , 1.0 , 0.0 , 0.0 , 0.0 , .25 , 0.5 , 0.0 , 0.0 , 0.5 , 0.5 , 0.0 , 1.0/3.0 , 1.0/3.0 , 0.0     , 1.0/3.0} ,     //C
        { 0.0 , 0.0 , 1.0 , 0.0 , 0.0 , .25 , 0.0 , 0.5 , 0.0 , 0.5 , 0.0 , 0.5 , 1.0/3.0 , 0.0     , 1.0/3.0 , 1.0/3.0} ,     //G
        { 0.0 , 0.0 , 0.0 , 1.0 , 0.0 , .25 , 0.0 , 0.0 , 0.5 , 0.0 , 0.5 , 0.5 , 0.0     , 1.0/3.0 , 1.0/3.0 , 1.0/3.0} ,     //T
        { 0.0 , 0.0 , 0.0 , 0.0 , 1.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0     , 0.0     , 0.0     , 0.0}           //-
    
    };    
    
}
