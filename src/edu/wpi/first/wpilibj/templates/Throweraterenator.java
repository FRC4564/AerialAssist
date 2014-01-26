/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author TheGreenBox
 */
public class Throweraterenator {
    private Jaguar arm1 = new Jaguar(Constants.PWM_THROWER_1);
    private Jaguar arm2 = new Jaguar(Constants.PWM_THROWER_2);
    private double throwSpeed = 0;
    private double returnSpeed = 0;
    
    
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
    
    
}
