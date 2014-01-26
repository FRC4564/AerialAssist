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
    private Encoder angle = new Encoder(Constants.ANGLE_CHANNEL_A, Constants.ANGLE_CHANNEL_B, true, EncodingType.k4X);
    
    public Throweraterenator() {
        angle.start();
        angle.reset();
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
    
    public void setStatus(int value) {
        status = value;
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
    
    public int encoderCount() {
        return angle.get();
    }
    
    public void zeroEncoder() {
        angle.reset();
    }
    
    public void update() {
        if (getStatus() == 1) {
            updateThrow();
        } else if (getStatus() == 2) {
            updateStow();
        }
    }
    
    public void updateThrow() {
        
    }
    
    public void updateStow() {
        
    }
    
    public boolean checkDistance() {
        return true;
    }
    
    public double setDistance(double distance) {
        return 0;
    }
}
