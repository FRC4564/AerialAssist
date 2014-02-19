/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Steve
 */
public class Tail {
    
    private int status = Constants.TAIL_STATUS_INIT;
    private double theta;
    private double stingerSpeed;
    private double baseSpeed;
    // potentiometer values
    private double volts = 0;
    private double voltsMin = 1.94;             // Minimum pot reading
    private double voltsMax = 3.47;             // Maximum pot reading
    private double voltsExtended = voltsMax - 0.03;  // safe extend
    private double voltsRetracted = voltsMin + 0.02; // safe retract
    // stinger motor rotation varies by mode and tail position
    //private double voltsEjectStinger = voltsRetracted * 1.10; // stinger eject start 
    //private double voltsStingerStart = voltsRetracted * 1.40; // stinger pickup start
    private double extendBeginEject = voltsRetracted + 0.1;    
    private double extendBeginPickup = voltsRetracted + 0.9;    
    private double retractBeginPickup = voltsRetracted + 0.56;   
    // tail base motor speeds at end points and direction
    private double beginRetractSpeed = -0.75;
    private double endRetractSpeed = 0.15;
    private double beginExtendSpeed = 0.8;
    private double endExtendSpeed = -0.22;
     
    Talon base = new Talon(Constants.PWM_TAIL_BASE);
    Talon stinger = new Talon(Constants.PWM_TAIL_STINGER);
    
    AnalogChannel potentiometer = new AnalogChannel(Constants.ANA_TAIL_POT);
    
    public Tail() {
        
    }
    
    /**
     *    Set stinger motor speed
     */
    public void setStingerSpeed(double speed) {
        stingerSpeed = speed;
        stinger.set(stingerSpeed);
    }
    
    /**
     *    Current speed of stinger motor
     */
    public double getStingerSpeed() {
        return stingerSpeed;
    }

    /**
     *     Set base motor speed
     */   
    public void setBaseSpeed(double speed) {
        baseSpeed = speed;
        base.set(baseSpeed);
    }

    /**
     *      Current speed set on base motor
     */
    public double getBaseSpeed() {
        return baseSpeed;
    }

    /**
     *     Angle of tail measured in volts from potentiometer
     */
    public double getTheta() {
        theta = potentiometer.getVoltage();
        return theta;
    }
    
    public int getStatus() {
        return status;
    }
    
    /**
     *    Initiate tail extension
     */
    public void startExtend() {
        status = Constants.TAIL_STATUS_EXTENDING;
    }

    /**
     *     Initiate tail retraction
     */
    public void startRetract() {
        status = Constants.TAIL_STATUS_RETRACTING;
    }
    
   
    /**
     *   Update tail and stinger movement
     *   Call every robot loop cycle.
     */
    public void update() {
        volts = getTheta();
        // tail movement
        if (status == Constants.TAIL_STATUS_RETRACTING) {
            updateRetract();
        } else if (status == Constants.TAIL_STATUS_EXTENDING) {
            updateExtend();
        } else if (status == Constants.TAIL_STATUS_INIT) {
            if (volts <= voltsRetracted) {
                status = Constants.TAIL_STATUS_RETRACTED;
                setBaseSpeed(0);
            } else if (volts >= voltsExtended) {
                status = Constants.TAIL_STATUS_EXTENDED;
                setBaseSpeed(0);
            }
        } else {
            setBaseSpeed(0);
        }
        // stinger rotation when extending
        if (status == Constants.TAIL_STATUS_EXTENDING || 
            status == Constants.TAIL_STATUS_EXTENDED ) {
            if (volts > extendBeginPickup) {
                setStingerSpeed(1.0);
            } else if (volts > extendBeginEject) {
                setStingerSpeed(-1.0);
            } else {
                setStingerSpeed(0.0);
            }
        // stinger rotation when retracting
        } else if (status == Constants.TAIL_STATUS_RETRACTING ||
                   status == Constants.TAIL_STATUS_RETRACTED ) {
            if (volts > retractBeginPickup) {
                setStingerSpeed(1.0);
            } else {
                setStingerSpeed(0.0);
            }
        }
        /*
        if (getTheta() > voltsStingerStart     //rotate inward for pickup
                && status != Constants.TAIL_STATUS_EJECTING) {
            setStingerSpeed(1.0);
        } else if (getTheta() > voltsEjectStinger  //rotate outward to eject
                && status == Constants.TAIL_STATUS_EJECTING) {
            setStingerSpeed(-1.0);
        } else {
                setStingerSpeed(0.0);
        }
        */
    }
    
    private void updateRetract() {
        double m = (endRetractSpeed - beginRetractSpeed) / (voltsRetracted - voltsExtended);
        setBaseSpeed(m * (getTheta() - voltsRetracted) + endRetractSpeed);
        if (getTheta() <= voltsRetracted) {
            setBaseSpeed(0);
            status = Constants.TAIL_STATUS_RETRACTED;
        }
    }
    
    private void updateExtend() {
        double m = (endExtendSpeed - beginExtendSpeed) / (voltsExtended - voltsRetracted);
        setBaseSpeed(m * (getTheta() - voltsExtended) + endExtendSpeed);
        if (getTheta() >= voltsExtended) {
            setBaseSpeed(0);
            status = Constants.TAIL_STATUS_EXTENDED;
        }
    }
}
