/*
 * GlocsaParameters.java
 *
 * Created on February 23, 2007, 8:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public class GlocsaParameters {
    
    private double wMeanColumnValue;
    private double wGapConcentration;
    private double wColumnIncrementRatio;

    public static double wMeanColumnValueDefault = 1000.0;
    public static double wGapConcentrationDefault = 20.0;
    public static double wColumnIncrementRatioDefault = -20.0;


    //private double affineGapOpenningCost;
    //private double affineGapExtensionCost;

    //public static double affineGapOpenningCostDefault = 2.0;
    //public static double affineGapExtensionCostDefault = 1.0;


    /** Creates a new instance of GlocsaParameters */
    public GlocsaParameters() {
        wMeanColumnValue = wMeanColumnValueDefault;
        wGapConcentration = wGapConcentrationDefault;
        wColumnIncrementRatio = wColumnIncrementRatioDefault;

        //affineGapOpenningCost = affineGapOpenningCostDefault;
        //affineGapExtensionCost = affineGapExtensionCostDefault;
    }
    
    public double getWMeanColumnValue() {
        return wMeanColumnValue;
    }
    
    public void setWMeanColumnValue(double wMeanColumnValue) {
        this.wMeanColumnValue = wMeanColumnValue;
    }
    
    public void setWGapConcentration(double wMeanGapBlockSize) {
        this.wGapConcentration = wMeanGapBlockSize;
    }
    
    public double getWColumnIncrementRatio() {
        return wColumnIncrementRatio;
    }
    
    public void setWColumnIncrementRatio(double wColumnRatio) {
        this.wColumnIncrementRatio = wColumnRatio;
    }

    public double getWGapConcentration() {
        return wGapConcentration;
    }
//
//    /**
//     * @return the affineGapOpenningCost
//     */
//    public double getAffineGapOpenningCost() {
//        return affineGapOpenningCost;
//    }
//
//    /**
//     * @param affineGapOpenningCost the affineGapOpenningCost to set
//     */
//    public void setAffineGapOpenningCost(double affineGapOpenningCost) {
//        this.affineGapOpenningCost = affineGapOpenningCost;
//    }
//
//    /**
//     * @return the affineGapExtensionCost
//     */
//    public double getAffineGapExtensionCost() {
//        return affineGapExtensionCost;
//    }
//
//    /**
//     * @param affineGapExtensionCost the affineGapExtensionCost to set
//     */
//    public void setAffineGapExtensionCost(double affineGapExtensionCost) {
//        this.affineGapExtensionCost = affineGapExtensionCost;
//    }
    
    
    
}
