/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Steven
 */
public class Constants {
    public static final int PWM_DRIVE_FL = 4;
    public static final int PWM_DRIVE_RL = 3;
    public static final int PWM_DRIVE_FR = 2;
    public static final int PWM_DRIVE_RR = 1;
    public static final int PWM_THROWER_1 = 5;
    public static final int PWM_THROWER_2 = 6;
    
    
    static Talon leftForward = new Talon(PWM_DRIVE_FL);
    static Talon leftBackward = new Talon(PWM_DRIVE_RL);
    static Talon rightForward = new Talon(PWM_DRIVE_FR);
    static Talon rightBackward = new Talon(PWM_DRIVE_RR);
    
    public static final int JB_DRIVE_SLOW = 2;
    
    public static final double TIMER_DELAY_SECS = .01;
}
