/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Steven
 */
public class Constants {
    
    // PWMs
    public static final int PWM_DRIVE_FRONT_LEFT = 1;
    public static final int PWM_DRIVE_REAR_LEFT = 2;
    public static final int PWM_DRIVE_FRONT_RIGHT = 3;
    public static final int PWM_DRIVE_REAR_RIGHT = 4;
    public static final int PWM_THROWER_RIGHT = 5;
    public static final int PWM_THROWER_LEFT = 6;
    public static final int PWM_TAIL_BASE = 7;
    public static final int PWM_TAIL_STINGER = 8;
    
    // DIOs
    public static final int DIO_THROWER_ENCODER_A = 1;
    public static final int DIO_THROWER_ENCODER_B = 2;
    
    // Analog Inputs
    public static final int ANA_SONIC_RIGHT = 1;
    public static final int ANA_SONIC_LEFT = 2;
    public static final int ANA_TAIL_POT = 3;
    
    // Relays
    
    
    // Drive Motors
    static Talon frontLeft = new Talon(PWM_DRIVE_FRONT_LEFT);
    static Talon rearLeft = new Talon(PWM_DRIVE_REAR_LEFT);
    static Talon frontRight = new Talon(PWM_DRIVE_FRONT_RIGHT);
    static Talon rearRight = new Talon(PWM_DRIVE_REAR_RIGHT);
        
    // Joysticks
    public static final int JB_DRIVE_SLOW = 1;
    public static final int JB_THROWER_ENCODER_RESET = 2;
    public static final int JB_INIT_THROW_1 = 7;
    public static final int JB_INIT_THROW_2 = 10;
    public static final int JB_TAIL_EXTEND = 4;
    public static final int JB_TAIL_RETRACT = 5;
    public static final int JB_TAIL_EJECT = 1;

    
    
    // Miscellaneous
    public static final double TELEOP_LOOP_DELAY_SECS = .01;
    
    // Thrower parameters
    static double[] throwerSpeedVals = new double[10];
    static int[] throwerArcVals = new int[10];
    int[] throwerDistanceVals = new int[10];
    public static final int THROWER_STATUS_HOME = 0;
    public static final int THROWER_STATUS_THROW = 1;
    public static final int THROWER_STATUS_STOW = 2;
    public static final int THROWER_STATUS_INIT = 3;
    public static final double COUNTDOWN_TIME = 0.25;

    //Sonic constants
    public static final int SONIC_BALANCE_EQUAL = 0;
    public static final int SONIC_BALANCE_RIGHT = 1;
    public static final int SONIC_BALANCE_LEFT = 2;

    //Scorpion Tail
    public static final int TAIL_STATUS_EXTENDED = 0;
    public static final int TAIL_STATUS_RETRACTED = 1;
    public static final int TAIL_STATUS_EXTENDING = 2;
    public static final int TAIL_STATUS_RETRACTING = 3;
    public static final int TAIL_STATUS_EJECTING = 4;
    
}
