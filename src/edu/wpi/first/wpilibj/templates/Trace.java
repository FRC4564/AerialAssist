/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Ben
 */
public class Trace {
    
    double[] traceTime = new double[500];
    double[] tracePos = new double[500];
    double[] traceArc = new double[500];
    double[] traceStatus = new double[500];
    double[][] traceArray = new double[][] {traceTime, tracePos, traceArc, traceStatus};
    private int traceNumber = 0;
    private int traceCount = 0;
    private double traceStartTime;
    
    public void start() {
        if (traceCount > 0) {
            traceCount = 0;
            traceNumber++;
            traceStartTime = Timer.getFPGATimestamp();
        }
    }
    
    public void add(int pos, int arc, int status) {
        traceArray[0][traceCount] = Timer.getFPGATimestamp() - traceStartTime;
        traceArray[1][traceCount] = pos;
        traceArray[2][traceCount] = arc;
        traceArray[3][traceCount] = status;
        traceCount++;
    }
    
    public int count() {
        return traceCount + 1;
    }
    
    public void print() {
        for (int i = 0; i <= traceCount; i++) {
            System.out.print(traceArray[0][i] + ", ");
            System.out.print(traceArray[1][i] + ", ");
            System.out.print(traceArray[2][i] + ", ");
            System.out.println(traceArray[3][i]);
        }
        traceCount = 0;
    }
}
