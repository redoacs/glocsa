/*
 * GlocsaRating.java
 *
 * Created on 26 July 2006, 18:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public class GlocsaRating {
    
    private double[] columnValues;
    private double meanGapBlockSize;
    private int columnsNotAligned;
    private int columnsAligned;
    private double ratingValue;
    private int totalGapPositions;
    private int numberGapBlocks;
    //private double affineGapModelCost;
    //private double bestCaseScenarioAffineGapModelCost;
    
    //These are going to factors of the termns in the glocsa polynomial
    private double meanColumnValue;
    private double gapConcentration;
    private double columnIncrementRatio;
    
    
    /** Creates a new instance of GlocsaRating */
    public GlocsaRating() {
    }

    public double[] getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(double[] columnValues) {
        this.columnValues = columnValues;
    }

    public double getMeanColumnValue() {
        return meanColumnValue;
    }

    public void setMeanColumnValue(double meanColumnValue) {
        this.meanColumnValue = meanColumnValue;
    }

    public double getMeanGapBlockSize() {
        return meanGapBlockSize;
    }

    public void setMeanGapBlockSize(double meanGapBlockSize) {
        this.meanGapBlockSize = meanGapBlockSize;
    }

    public int getColumnsNotAligned() {
        return columnsNotAligned;
    }

    public void setColumnsNotAligned(int columnsNotAligned) {
        this.columnsNotAligned = columnsNotAligned;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getTotalGapPositions() {
        return totalGapPositions;
    }

    public void setTotalGapPositions(int totalIndivdualGaps) {
        this.totalGapPositions = totalIndivdualGaps;
    }

    public int getColumnsAligned() {
        return columnsAligned;
    }

    public void setColumnsAligned(int columnsAligned) {
        this.columnsAligned = columnsAligned;
    }

    public double getGapConcentration() {
        return gapConcentration;
    }

    public void setGapConcentration(double gapConcentration) {
        this.gapConcentration = gapConcentration;
    }

    public double getColumnIncrementRatio() {
        return columnIncrementRatio;
    }

    public void setColumnIncrementRatio(double columnIncrementRatio) {
        this.columnIncrementRatio = columnIncrementRatio;
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");
        return new String(
                "GLOCSA: \t\t\t\t" + ratingValue + lineSeparator +
                lineSeparator +
                "meanColumnValue: \t\t\t" + meanColumnValue + lineSeparator +
                "gapConcentration: \t\t\t" + gapConcentration + lineSeparator +
                "columnIncrementRatio: \t\t\t" + columnIncrementRatio + lineSeparator +
                lineSeparator +
                "meanGapBlockSize: \t\t\t" + meanGapBlockSize + lineSeparator +
                "totalGapPositions: \t\t\t" + totalGapPositions + lineSeparator +
                "numberGapBlocks: \t\t\t" + numberGapBlocks + lineSeparator +
                //"affineGapModelCost: \t\t\t" + affineGapModelCost + lineSeparator +
                //"bestCaseScenarioAffineGapModelCost: \t" + bestCaseScenarioAffineGapModelCost + lineSeparator +
                "columnsNotAligned: \t\t\t" + columnsNotAligned + lineSeparator +
                "columnsAligned: \t\t\t" + columnsAligned + lineSeparator
                );
    }

    public String toShortString() {
        return (new Double(ratingValue)).toString();
    }

//    /**
//     * @return the affineGapModelCost
//     */
//    public double getAffineGapModelCost() {
//        return affineGapModelCost;
//    }
//
//    /**
//     * @param affineGapModelCost the affineGapModelCost to set
//     */
//    public void setAffineGapModelCost(double affineGapModelCost) {
//        this.affineGapModelCost = affineGapModelCost;
//    }
//
//    /**
//     * @return the bestCaseScenarioAffineGapModelCost
//     */
//    public double getBestCaseScenarioAffineGapModelCost() {
//        return bestCaseScenarioAffineGapModelCost;
//    }
//
//    /**
//     * @param bestCaseScenarioAffineGapModelCost the bestCaseScenarioAffineGapModelCost to set
//     */
//    public void setBestCaseScenarioAffineGapModelCost(double bestCaseScenarioAffineGapModelCost) {
//        this.bestCaseScenarioAffineGapModelCost = bestCaseScenarioAffineGapModelCost;
//    }

    /**
     * @return the numberGapBlocks
     */
    public int getNumberGapBlocks() {
        return numberGapBlocks;
    }

    /**
     * @param numberGapBlocks the numberGapBlocks to set
     */
    public void setNumberGapBlocks(int numberGapBlocks) {
        this.numberGapBlocks = numberGapBlocks;
    }
    
}
