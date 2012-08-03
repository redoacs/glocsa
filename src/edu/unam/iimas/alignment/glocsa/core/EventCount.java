/*
 * EventCount.java
 *
 * Created on June 1, 2007, 2:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.unam.iimas.alignment.glocsa.core;

/**
 *
 * @author xaltonalli
 */
public class EventCount {
    
    private int substitutionEvents;
    private int[] substitutionEventsPerColumn;
    private int inDelEvents;
    private int totalEvents;
    
    /** Creates a new instance of EventCount */
    public EventCount() {
    }

    public int getSubstitutionEvents() {
        return substitutionEvents;
    }

    public void setSubstitutionEvents(int substitutionEvents) {
        this.substitutionEvents = substitutionEvents;
    }

    public int getInDelEvents() {
        return inDelEvents;
    }

    public void setInDelEvents(int inDelEvents) {
        this.inDelEvents = inDelEvents;
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public int[] getSubstitutionEventsPerColumn() {
        return substitutionEventsPerColumn;
    }

    public void setSubstitutionEventsPerColumn(int[] substitutionEventsPerColumn) {
        this.substitutionEventsPerColumn = substitutionEventsPerColumn;
    }
    
    @Override
    public String toString(){
        String lineSeparator = System.getProperty("line.separator");
        return new String(
                "estimatedEvents: \t\t\t" + totalEvents + lineSeparator +
                lineSeparator +
                "substitutionEvents: \t\t\t" + substitutionEvents + lineSeparator +
                "inDelEvents: \t\t\t\t" + inDelEvents + lineSeparator
                );
    }
    
}
