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
    
    public void setMotorSpeed(double speed) {
        arm1.set(speed);
        arm2.set(speed);
    }
    
    public double[] geMotorSpeed() {
        double[] speeds = new double[2];
        speeds[1] = arm1.get();
        speeds[2] = arm2.get();
        return speeds;
    }
    
    
}
