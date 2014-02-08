/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author goffigus
 */
public class SinisterSonar {
    
    AnalogChannel sonicLeft = new AnalogChannel(Constants.ANA_SONIC_LEFT);
    AnalogChannel sonicRight = new AnalogChannel(Constants.ANA_SONIC_RIGHT);
    //constants
    private static final double VOLTS_PER_INCH = 5.0 / 1024 * 2.54; //factor for volts per inch. (see Maxbotic docs)
    private static final double variance = 1.0;   // Variance, in feet, for Balanced
    //local variables
    private int balance;  // Balance is either Equal or favored Left-side or Right-side

    /**
     * Returns distance in feet and sets 'balance'.
     * If out of balance, distance will be to favored side (most distant).
     * 
     * @return Distance in feet
     */ 
    public double getDistance (){
        double distanceRight = getRightDistance(); 
        double distanceLeft = getLeftDistance(); 
        double distance;

        double difference = Math.abs(distanceLeft-distanceRight);
        if (difference <= variance){
            distance = (distanceLeft+distanceRight)/2; //average of distances
            balance = Constants.SONIC_BALANCE_EQUAL;
        }
        else if (distanceLeft > distanceRight){
            distance = distanceLeft;
            balance = Constants.SONIC_BALANCE_LEFT;
        }
        else {
            distance = distanceRight;
            balance = Constants.SONIC_BALANCE_RIGHT;
        } 
        return distance / 12.0;  //conversion to feet from inches
    }
    
    /**
     * Get left sonar distance in feet
     */
    public double getLeftDistance() {
        return sonicRight.getVoltage() / VOLTS_PER_INCH / 12;
    }
    
    /**
     * Get right sonar distance in feet
     */
    public double getRightDistance() {
        return sonicRight.getVoltage() / VOLTS_PER_INCH / 12;
    }
    
    /**
     * Returns which sensor is favored or balanced.
     * 
     * @return Constants.SONIC_BALANCE_EQUAL,Constants.SONIC_BALANCE_LEFT,
     * or Constants.SONIC_BALANCE_RIGHT
     */
    public int getBalance () {
        return balance;
        //int balance; // Returns which sensor is being used
        //double distanceRight = sonicRight.getVoltage()/VI; //distance for right sensor in inches
        //double distanceLeft = sonicLeft.getVoltage()/VI; //distance for left sensor in inches
        //double difference = 12;
        //double range = Math.abs(distanceLeft-distanceRight);
        //if (range<difference){
        //    balance = Constants.SONIC_BALANCE_EQUAL; //average of distances
        //}
        //else if (distanceLeft>distanceRight){
        //    balance = Constants.SONIC_BALANCE_LEFT;
        //}
        //else {
        //    balance = Constants.SONIC_BALANCE_RIGHT;
        //}
        
        //return balance;
    }
}

