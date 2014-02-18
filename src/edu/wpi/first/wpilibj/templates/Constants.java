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
    public static final int DIO_SONIC_LEFT_ENABLE = 3;
    public static final int DIO_SONIC_RIGHT_ENABLE = 4;
    
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
    // rightstick
    public static final int JB_THROW_SAFETY = 1;          //Throw enable
    public static final int JB_THROW_AUTO_DIST = 2;       //Throw based on sonar
    public static final int JB_THROW_TRUSS_TOSS = 3;      //Throw a truss toss
    public static final int JB_THROW_ROBOT_PASS = 4;      //Lob to another robot
    public static final int JB_THROW_MANUAL = 5;          //Throw static params, no sonar
    public static final int JB_THROW_ANALOG = 7;          //Throw using analog sliders
    public static final int JB_THROW_CATCH = 10;          //Keeps thrower at top of throw
    //Throw using analog inputs
    // leftstick
    public static final int JB_TAIL_EXTEND = 1;           //Extend tail-eject ball
    public static final int JB_TAIL_RETRACT = 2;          //Retract tail-pickup ball
    
    public static final int JB_LIGHT_CENTER_1 = 8;
    public static final int JB_LIGHT_CENTER_2 = 9;
 
    
    // Miscellaneous
    public static final double TELEOP_LOOP_DELAY_SECS = .05;  //Main loop speed (.05 = 20hz)

    
    // Thrower parameters
    static double[] throwerSpeedVals = new double[10];
    static int[] throwerArcVals = new int[10];
    int[] throwerDistanceVals = new int[10];
    public static final int THROWER_STATUS_HOME = 0;
    public static final int THROWER_STATUS_THROW = 1;
    public static final int THROWER_STATUS_STOW = 2;
    public static final int THROWER_STATUS_INIT = 3;
    public static final int THROWER_STATUS_BRAKE = 4;
    //Standard arc used for normal throw and autonomous (roughly 4' to 7')
    public static final int THROWER_NOMINAL_ARC = 125;

    //Sonic constants
    public static final int SONIC_BALANCE_EQUAL = 0;
    public static final int SONIC_BALANCE_RIGHT = 1;
    public static final int SONIC_BALANCE_LEFT = 2;
    public static final double SONIC_ALT_LOOP_DELAY = .2;  //delay time between sonar pings

    //Scorpion Tail
    public static final int TAIL_STATUS_INIT = 0;
    public static final int TAIL_STATUS_EXTENDED = 1;
    public static final int TAIL_STATUS_RETRACTED = 2;
    public static final int TAIL_STATUS_EXTENDING = 3;
    public static final int TAIL_STATUS_RETRACTING = 4;
    
    //Autonomous
    public static final int AUTO_STATUS_INIT = 0;
    public static final int AUTO_STATUS_MOVING = 1;
    public static final int AUTO_STATUS_LOOKING = 2;
    public static final int AUTO_STATUS_THROWCHECK = 3;
    public static final int AUTO_STATUS_THROW = 4;
    public static final int AUTO_STATUS_THROWING = 5;
    public static final int AUTO_STATUS_DONE = 6;
}
