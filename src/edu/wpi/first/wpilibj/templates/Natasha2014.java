/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Natasha2014 extends SimpleRobot {
    
    Joystick leftstick = new Joystick(1);
    Joystick rightstick = new Joystick(2);
    
    
    DriverStation ds;
    // Can we get this long line to be split onto 2 lines, for readability?
    DriveTrain dt = new DriveTrain(Constants.frontLeft, Constants.rearLeft,
                                   Constants.frontRight, Constants.rearRight);
    Throweraterenator thrower = new Throweraterenator();
    Tail tail = new Tail();
    SinisterSonar sonar = new SinisterSonar();

    /** 
     * Robot Initialization upon boot
     */
    protected void robotInit() {
        System.out.println("RobotInit...");
        ds = DriverStation.getInstance();
        dt.setMotorsInverted();

        thrower.setStowSpeed(-0.3);
        thrower.initThrower();
    }

    /**
     * This function is called once, when Autonomous mode is enabled.
     */
    public void autonomous() {
        
    }


    /**
     * This function is called while Teleop mode is enabled.
     */
    public void operatorControl() {
        System.out.println("Teleop...");
        dt.setSafetyEnabled(true);
        while(isOperatorControl() && isEnabled()){
            
            // DRIVETRAIN
            dt.arcadeDrive(leftstick.getY() * -1, leftstick.getX() * .7);
            
            // THROWER
            thrower.setThrowSpeed(ds.getAnalogIn(1)/5);
            thrower.setThrowArc((int)(ds.getAnalogIn(2)/5 * 130));
            // manual encoder reset
            if (leftstick.getRawButton(Constants.JB_THROWER_ENCODER_RESET)) {
                thrower.resetEncoder();
            }
            // A throw requires two JB buttons and the tail to be retracted.
            if (leftstick.getRawButton(Constants.JB_INIT_THROW_1) &&
                  leftstick.getRawButton(Constants.JB_INIT_THROW_2) &&
                  tail.getStatus() == Constants.TAIL_STATUS_RETRACTED) {
                thrower.startThrow();
            }
            thrower.update();
                      
            // SCORPION TAIL
            if (leftstick.getRawButton(Constants.JB_TAIL_EXTEND)) {
                tail.startExtend();
            }
            if (leftstick.getRawButton(Constants.JB_TAIL_RETRACT)) {
                tail.startRetract();
            }
            if (leftstick.getRawButton(Constants.JB_TAIL_EJECT)) {
                tail.startEject();
            }
            tail.update();

            // SONAR

            
            // DEBUG
            System.out.println(tail.getTheta());
            /*System.out.print(Timer.getFPGATimestamp() );
            System.out.print(" pos:" + thrower.position() );
            System.out.print(" arc: " + thrower.getThrowArc() );
            System.out.print(" sonar: " + sonar.getDistance() );
            System.out.println(" status: " + thrower.getStatus() );
            */
 
            Timer.delay(Constants.TELEOP_LOOP_DELAY_SECS);
        }        

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}
