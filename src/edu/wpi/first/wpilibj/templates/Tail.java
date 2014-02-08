/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author Steve
 */
public class Tail {
    
    private double theta;
    private double stingerSpeed;
    private double baseSpeed;
    private double voltsExtended = 4.45;
    private double voltsRetracted = 3.35;
    private double voltsEjectStinger = 3.57;
    private double voltsStingerStart = 3.8;
    private double beginRetractSpeed = -0.8;
    private double endRetractSpeed = 0.3;
    private double beginExtendSpeed = 0.8;
    private double endExtendSpeed = -0.22;
    private double status = 0;
    
    Jaguar base = new Jaguar(Constants.PWM_TAIL_BASE);
    Jaguar stinger = new Jaguar(Constants.PWM_TAIL_STINGER);
    
    AnalogChannel potentiometer = new AnalogChannel(Constants.ANA_TAIL_POT);
    
    public Tail() {
        
    }
    
    public void setStingerSpeed(double speed) {
        stingerSpeed = speed;
        stinger.set(stingerSpeed);
    }
    
    public double getStingerSpeed() {
        return stingerSpeed;
    }
    
    public void setBaseSpeed(double speed) {
        baseSpeed = speed;
        base.set(baseSpeed);
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

   
    public double getTheta() {
        theta = potentiometer.getVoltage();
        return theta;
    }
    
    
    public void startExtend() {
        status = Constants.TAIL_STATUS_EXTENDING;
    }

    public void startRetract() {
        status = Constants.TAIL_STATUS_RETRACTING;
    }
    
    public void startEject() {
        status = Constants.TAIL_STATUS_EJECTING;
    }
    
    public void update() {
        if (status == Constants.TAIL_STATUS_RETRACTING) {
            updateRetract();
        } else if (status == Constants.TAIL_STATUS_EXTENDING
                || status == Constants.TAIL_STATUS_EJECTING) {
            updateExtend();
        } else {
            setBaseSpeed(0);
        }
        if (getTheta() > voltsStingerStart 
                && status != Constants.TAIL_STATUS_EJECTING) {
            setStingerSpeed(1.0);
        } else if (getTheta() > voltsEjectStinger
                && status == Constants.TAIL_STATUS_EJECTING) {
            setStingerSpeed(-1.0);
        } else {
                setStingerSpeed(0.0);
        }
    }
    
    public void updateRetract() {
        double m = (endRetractSpeed - beginRetractSpeed) / (voltsRetracted - voltsExtended);
        setBaseSpeed(m * (getTheta() - voltsRetracted) + endRetractSpeed);
        if (getTheta() <= voltsRetracted) {
            setBaseSpeed(0);
            status = Constants.TAIL_STATUS_RETRACTED;
        }
    }
    
    public void updateExtend() {
        double m = (endExtendSpeed - beginExtendSpeed) / (voltsExtended - voltsRetracted);
        setBaseSpeed(m * (getTheta() - voltsExtended) + endExtendSpeed);
        if (getTheta() >= voltsExtended) {
            setBaseSpeed(0);
            status = Constants.TAIL_STATUS_EXTENDED;
        }
    }
}
