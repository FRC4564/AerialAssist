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
    
    static Joystick leftstick = new Joystick(1);
    Joystick rightstick = new Joystick(2);
    
    
//    DriveTrain dt = new DriveTrain(Constants.leftForward, Constants.leftBackward, Constants.rightForward, Constants.rightBackward);
    Throweraterenator cat = new Throweraterenator();
    DriverStation ds = DriverStation.getInstance();
    
    protected void robotInit(){
       System.out.println("RobotInit...");
//       dt.setMotorsInverted();
    }
    
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        System.out.println("Teleop...");
//        dt.setSafetyEnabled(false);
        while(isOperatorControl() && isEnabled()){
            if (leftstick.getRawButton(Constants.JB_DRIVE_SLOW)) {
//                dt.arcadeDrive(leftstick.getY() * .7, leftstick.getX() * .5);
            } else {
//                dt.arcadeDrive(leftstick.getY(), leftstick.getX() * .7);
            }
            // Catapult
            System.out.println(cat.encoderCount());
            cat.setThrowSpeed(ds.getAnalogIn(1) / 5);
            cat.setThrowArc((int)(ds.getAnalogIn(2) / 5 * 400 + 800) );
            cat.setReturnSpeed(-0.2);
            if (leftstick.getRawButton(1)) {
                cat.Throw();
            }
            cat.update();
            
            Timer.delay(Constants.TIMER_DELAY_SECS);
        }        

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
