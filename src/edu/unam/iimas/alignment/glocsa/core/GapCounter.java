/*
 * GapCounter.java
 *
 * Created on August 9, 2007, 3:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

import edu.unam.iimas.alignment.Alignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author xaltonalli
 */
public abstract class GapCounter {
    
    public static SGCMatrix createSGCMatrix(Alignment alignment) {
        
        ArrayList<HashMap<Integer, Integer>> gapExistence = GapCounter.getGapExistenceOfAlignment(alignment);
        
        Set<Map.Entry<Integer,Integer>> integratedGapExistence = GapCounter.integrateGapExistance(gapExistence);
        
        return createSGCMatrix( integratedGapExistence, gapExistence, alignment.getSequenceNames() );
    }
    
    public static SGCMatrix createSGCMatrix(Set<Map.Entry<Integer,Integer>> gapConcentration, ArrayList<HashMap<Integer,Integer>> gapExistence, String[] names) {
        
        int totalDifferentGaps = gapConcentration.size();
        int nTaxas = gapExistence.size();
        ConcurrentSkipListSet<Gap> cslsGaps = new ConcurrentSkipListSet<Gap>(); //TODO This is for ordering, possibly just a luxury which adds complexity
        
        Iterator<Map.Entry<Integer,Integer>> entriesIterator = gapConcentration.iterator();
        
        Map.Entry<Integer,Integer> anEntry = null;
        
        while ( entriesIterator.hasNext() ) {
            anEntry = entriesIterator.next();
            
            cslsGaps.add( new Gap(anEntry.getKey().intValue(), anEntry.getValue().intValue()) );
        }
        
        Gap[] gaps = new Gap[totalDifferentGaps];
        
        gaps = cslsGaps.toArray(gaps);
        
        char[][] exist = new char[nTaxas][totalDifferentGaps];
        
        HashMap<Integer,Integer> gapsInTaxa = null;
        int nGapsInTaxa = 0;
        
        for ( int i = 0 ; i < nTaxas ; i ++ ) {
            
            gapsInTaxa = gapExistence.get(i);
            
            for ( int j = 0 ; j < totalDifferentGaps ; j ++ ) {
                
//                exist[i][j] = '0';
//                if ( gapsInTaxa.containsKey(new Integer( gaps[j].getPosition() ) ) )  {
//                    if ( gapsInTaxa.get(gaps[j].getPosition()).intValue() == gaps[j].getLength() ) {
//                        exist[i][j] = '1';
//                    }
//                }
                
                exist[i][j] = determineExistence(gaps[j], gapsInTaxa);
                
                
            }
            
        }
        
        SGCMatrix sgcMatrix = new SGCMatrix( gaps, names, exist );
        
        
        return sgcMatrix;
        
    }
    
    private static char determineExistence(Gap aGap, HashMap<Integer,Integer> gapsInTaxa) {
        
        char exist = '0';
        
        if ( gapsInTaxa.containsKey(new Integer( aGap.getPosition() ) ) )  {
            if ( gapsInTaxa.get(aGap.getPosition()).intValue() == aGap.getLength() ) {
                exist = '1';
            }
        } else {
            Iterator<Map.Entry<Integer,Integer>> gapsInTaxaIterator = gapsInTaxa.entrySet().iterator();
            Map.Entry<Integer,Integer> anEntry = null;
            int gapITStartPosition = 0;
            int gapITLenght = 0;
            
            while (gapsInTaxaIterator.hasNext()) {
                
                anEntry = gapsInTaxaIterator.next();
                gapITStartPosition = anEntry.getKey().intValue();
                gapITLenght = anEntry.getValue().intValue();
                
                if ( gapITStartPosition <= aGap.getPosition() && ( (gapITStartPosition + gapITLenght) >= (aGap.getPosition() + aGap.getLength()) ) ) {
                    exist = '?';
                }
                
            }
        }
        
        
        
        return exist;
    }
     
    public static Set<Map.Entry<Integer,Integer>> integrateGapExistance(ArrayList<HashMap<Integer,Integer>> gapExistence) {
        
        
        Set<Map.Entry<Integer,Integer>> integratedGapExistence = new HashSet<Map.Entry<Integer,Integer>>();
        
        int numberOfSequences = gapExistence.size();
        
        HashMap<Integer,Integer> gapsTaxa = null;
        Iterator<Map.Entry<Integer,Integer>> entriesIterator = null;
        Map.Entry<Integer,Integer> entry = null;
        
        for (int i = 0 ; i < numberOfSequences ; i++) {
            
            gapsTaxa = gapExistence.get(i);
            
            entriesIterator = gapsTaxa.entrySet().iterator();
            
            while ( entriesIterator.hasNext() ) {
                
                entry = entriesIterator.next();
                
                if (!integratedGapExistence.contains(entry)) {
                    integratedGapExistence.add(entry);
                }
                
            }
            
        }
        
        return integratedGapExistence;
    }
        
    public static ArrayList<HashMap<Integer,Integer>> getGapExistenceOfAlignment(Alignment alignment) {
        
        char alignedMatrix[][] = alignment.getAlignedMatrix();
        
        int lines = alignment.getNumberOfSequences();
        int columnsAligned = alignment.getMaxPositions();
        
        int[][] intArrayGapCount = new int[lines][columnsAligned];
        
        
        
        //int position = 0;
        int gapsCurrentPosition = 0;
        
        for (int i = 0 ; i < lines ; i ++) {
            
            gapsCurrentPosition = 0;
            
            for (int j = 0 ; j < columnsAligned ; j ++) {
                
                if (alignedMatrix[i][j] != Alignment.GAP) {
                    
                    intArrayGapCount[i][j-gapsCurrentPosition] = gapsCurrentPosition;
                    gapsCurrentPosition = 0;
                    
                    
                } else {
                    
                    gapsCurrentPosition ++;
                    
                }
                
            }
            
        }
        
        
        ArrayList<HashMap<Integer,Integer>> representation = new ArrayList<HashMap<Integer,Integer>>(lines);
        
        HashMap<Integer,Integer> aTaxa ;
        
        for (int i = 0 ; i < lines ; i ++) {
            
            aTaxa = new HashMap<Integer,Integer>();
            
            for (int j = 1 ; j < columnsAligned ; j ++) {
                
                if (intArrayGapCount[i][j] != 0) {
                    aTaxa.put(new Integer(j), new Integer(intArrayGapCount[i][j]));
                }
                
            }
            
            representation.add(i,aTaxa);
            
        }
        
        return representation;
        
    }
    
    
}
