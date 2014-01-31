/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 *
 * @author TheGreenBox
 */
public class Throweraterenator {
    private Jaguar armRight = new Jaguar(Constants.PWM_THROWER_RIGHT);
    private Jaguar armLeft = new Jaguar(Constants.PWM_THROWER_LEFT);
    private double throwSpeed = 0;
    private double returnSpeed = 0;
    private int status = 0;
    private int arc = 0;
    private Encoder angle = new Encoder(Constants.DIO_THROWER_ENCODER_A, Constants.DIO_THROWER_ENCODER_B, true, EncodingType.k4X);
    
    public Throweraterenator() {
        angle.start();
        angle.reset();
    }
    
    private void setMotors(double speed) {
        armRight.set(-speed);
        armLeft.set(speed);
    }
    
    public void setThrowSpeed(double speed) {
        throwSpeed = speed;
    }
    
    public double getThrowSpeed() {
        return throwSpeed;
    }
    public void setReturnSpeed(double speed) {
        returnSpeed = speed;
    }
    
    public double getReturnSpeed() {
        return returnSpeed;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setThrowArc(int value) {
        arc = value;
    }
    
    public int getThrowArc() {
        return arc;
    }
    
    public int position() {
        return angle.get();
    }
    
    public void zeroEncoder() {
        angle.reset();
        angle.start();
    }
    
    public void stopEncoder() {
        angle.stop();
    }
    
    public void Throw() {
        status = 1;
    }
    
    public void update() {
        if (getStatus() == Constants.THROWER_STATUS_THROW) {
            updateThrow();
        } else if (getStatus() == Constants.THROWER_STATUS_STOW) {
            updateStow();
        }
    }
    
    public void updateThrow() {
        if (angle.get() < arc) {
            setMotors(throwSpeed);
        } else {
            setMotors(0);
            status = Constants.THROWER_STATUS_STOW;
        }
    }
    
    public void updateStow() {
        if (angle.get() > 0) {
            setMotors(returnSpeed);
        } else {
            setMotors(0);
            status = Constants.THROWER_STATUS_IDLE;
        }
    }
    
    public boolean checkDistance() {
        return true;
    }
    
    public double setDistance(double distance) {
        return 0;
    }
}
