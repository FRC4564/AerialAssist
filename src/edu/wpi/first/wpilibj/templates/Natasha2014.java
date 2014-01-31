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
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

public class Natasha2014 extends SimpleRobot {
    
    Joystick leftstick = new Joystick(1);
    Joystick rightstick = new Joystick(2);
    
    
    DriverStation ds;
    // Can we get this long line to be split onto 2 lines, for readability?
    DriveTrain dt = new DriveTrain(Constants.frontLeft, Constants.rearLeft, Constants.frontRight, Constants.rearRight);
    Throweraterenator thrower = new Throweraterenator();
    
    protected void robotInit() {
       System.out.println("RobotInit...");
       ds = DriverStation.getInstance();
       dt.setMotorsInverted();
    }

    
    public void autonomous() {
        
    }


    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        System.out.println("Teleop...");
        dt.setSafetyEnabled(true);
        while(isOperatorControl() && isEnabled()){
            if (leftstick.getRawButton(Constants.JB_DRIVE_SLOW)) {
                dt.arcadeDrive(leftstick.getY() * .7, leftstick.getX() * .5);
            } else {
                dt.arcadeDrive(leftstick.getY(), leftstick.getX() * .7);
            }
            // *** Thrower
            System.out.print(thrower.position());
            System.out.println(", " + thrower.getStatus());
            thrower.setThrowSpeed(ds.getAnalogIn(1) / 5);
            thrower.setThrowArc((int)(ds.getAnalogIn(2) / 5 * 400 + 800) );
            thrower.setReturnSpeed(-0.3);
            // setup constants joystick buttons.  For safety, I'd like 2 buttons to be pressed to
            // initiate thrower. Use buttons at base on left and right hand side of stick so it
            // takes two hands to initiate a throw.  This will be temporary, while we test/tune.
            if (leftstick.getRawButton(Constants.JB_INIT_THROW_1) && leftstick.getRawButton(Constants.JB_INIT_THROW_2)) {
                // What is the proper case presentation for methods.  Should this be 'throw()'?
                thrower.Throw();
            }
            thrower.update();
            
            Timer.delay(Constants.TELEOP_LOOP_DELAY_SECS);
        }        

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
