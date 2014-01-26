/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Natasha2014 extends SimpleRobot {
    
    Joystick leftstick = new Joystick(1);
    Joystick rightstick = new Joystick(2);
    
    DriveTrain dt = new DriveTrain(Constants.leftForward, Constants.leftBackward, Constants.rightForward, Constants.rightBackward);
    Throweraterenator cat = new Throweraterenator();
    
    protected void robotInit(){
       dt.setMotorsInverted();
    }
    
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        dt.setSafetyEnabled(true);
        while(isOperatorControl() && isEnabled()){
            if (leftstick.getRawButton(Constants.JB_DRIVE_SLOW)) {
                dt.arcadeDrive(leftstick.getY() * .7, leftstick.getX() * .5);
            } else {
                dt.arcadeDrive(leftstick.getY(), leftstick.getX() * .7);
            }
            Timer.delay(Constants.TIMER_DELAY_SECS);
        }        

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
