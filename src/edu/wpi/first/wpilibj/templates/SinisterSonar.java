/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;



/**
 *
 * @author goffigus
 */
public class SinisterSonar {
    
    AnalogChannel sonicLeft = new AnalogChannel(Constants.ANA_SONIC_LEFT);
    AnalogChannel sonicRight = new AnalogChannel(Constants.ANA_SONIC_RIGHT);
    DigitalOutput sonarLeftEnable = new DigitalOutput(Constants.DIO_SONIC_LEFT_ENABLE);
    DigitalOutput sonarRightEnable = new DigitalOutput(Constants.DIO_SONIC_RIGHT_ENABLE);

    //constants
    private static final double VOLTS_PER_INCH = 5.0 / 1024 * 2.54; //factor for volts per inch. (see Maxbotic docs)
    private static final double variance = 1.0;   // Variance, in feet, for Balanced
    //local variables
    private int balance;  // Balance is either Equal or favored Left-side or Right-side
    private double altTime = 0;
    private int whichSonar = 1;
    
    public SinisterSonar () {
        
    }
    
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
        return distance;  //conversion to feet from inches
    }
    
    /**
     * Get left sonar distance in feet
     */
    public double getLeftDistance() {
        return sonicLeft.getVoltage() / VOLTS_PER_INCH / 12;
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
    
    public void update () {
    //    if (altTime == 0) {
    //        altTime = Timer.getFPGATimestamp();
            
            
    //    } else {
            if (Timer.getFPGATimestamp() - altTime >= Constants.SONIC_ALT_LOOP_DELAY){
                altTime = Timer.getFPGATimestamp();
                if (whichSonar == 1){                
                    sonarLeftEnable.set(true);
                    sonarLeftEnable.set(false);
                    whichSonar = whichSonar * -1;
                } else {
                   sonarRightEnable.set(true);
                   sonarRightEnable.set(false);
                   whichSonar = whichSonar * -1;
                }
            }
    //    }
    }    
}



