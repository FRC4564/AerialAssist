/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
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
    SinisterSonar sonar = new SinisterSonar();
    
    private Jaguar motorTail = new Jaguar(Constants.PWM_TAIL);

    
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
            // *** Drive
            if (leftstick.getRawButton(Constants.JB_DRIVE_SLOW)) {
                dt.arcadeDrive(leftstick.getY() * .7, leftstick.getX() * .5);
            } else {
                dt.arcadeDrive(leftstick.getY(), leftstick.getX() * .7);
            }
            // *** Thrower
            thrower.setThrowSpeed(ds.getAnalogIn(1)/5);
            thrower.setThrowArc((int)(ds.getAnalogIn(2)/5 * 300));
            thrower.setStowSpeed(-0.2);

            System.out.print(Timer.getFPGATimestamp() );
            System.out.print(" pos:" + thrower.position() );
            System.out.print(" arc: " + thrower.getThrowArc() );
            System.out.print(" sonar: " + sonar.getDistance() );
            System.out.println(" status: " + thrower.getStatus() );
            
            if (leftstick.getRawButton(Constants.JB_THROWER_ENCODER_RESET)) {
                thrower.resetEncoder();
            }
            
            if (leftstick.getRawButton(Constants.JB_INIT_THROW_1) &&
                  leftstick.getRawButton(Constants.JB_INIT_THROW_2) ) {
                thrower.startThrow();
            }
            thrower.update();
            // *** Sonar
            
            // *** Scorpion Tail
            if (leftstick.getRawButton(Constants.JB_TAIL_EXTEND)){
                motorTail.set(-(ds.getAnalogIn(3)/5));
            } else if (leftstick.getRawButton(Constants.JB_TAIL_RETRACT)) {
                motorTail.set((ds.getAnalogIn(3)/5));
            } else {
                motorTail.set(0);
            }
            
            
            Timer.delay(Constants.TELEOP_LOOP_DELAY_SECS);
        }        

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
