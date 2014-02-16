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
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Natasha2014 extends SimpleRobot {
    
    Joystick leftstick = new Joystick(1);
    Joystick rightstick = new Joystick(2);
    DriveTrain dt = new DriveTrain(Constants.frontLeft, Constants.rearLeft,
                                   Constants.frontRight, Constants.rearRight);
    Throweraterenator thrower = new Throweraterenator();
    Tail tail = new Tail();
    SinisterSonar sonar = new SinisterSonar();
    DriverStation ds;
    SmartDashboard DashData = new SmartDashboard();
    Auto auto = new Auto(thrower, dt, ds);
    Solenoid leftLight = new Solenoid(1);
    Solenoid rightLight = new Solenoid(2);

    /** 
     * Robot Initialization upon boot
     */
    protected void robotInit() {
        System.out.println("RobotInit...");
        ds = DriverStation.getInstance();
        dt.setMotorsInverted();

        thrower.setStowSpeed(-0.32);
        thrower.initThrower();
    }

    /**
     * This function is called once, when Autonomous mode is enabled.
     */
    public void autonomous() {
        while (auto.status != Constants.AUTO_STATUS_DONE) {
            auto.updateAuto();
            Timer.delay(Constants.TELEOP_LOOP_DELAY_SECS);
        }
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
            //thrower.setThrowSpeed(ds.getAnalogIn(1)/5);
            //thrower.setThrowArc((int)(ds.getAnalogIn(2)/5 * 160));
            thrower.setTargetDistance(sonar.getDistance());
            // manual encoder reset
            if (leftstick.getRawButton(Constants.JB_THROWER_ENCODER_RESET)) {
                thrower.resetEncoder();
            }
            // A throw requires two JB buttons and the tail to be retracted.
            if (leftstick.getRawButton(Constants.JB_INIT_THROW_1) &&
                  leftstick.getRawButton(Constants.JB_INIT_THROW_2) &&
                  tail.getStatus() == Constants.TAIL_STATUS_RETRACTED) {
                thrower.setTargetDistance(sonar.getDistance());
                thrower.startThrow();
            }
            thrower.update();
                      
            // SCORPION TAIL
            if (thrower.getStatus() == Constants.THROWER_STATUS_HOME) {
                if (leftstick.getRawButton(Constants.JB_TAIL_EXTEND) ) {
                    tail.startExtend();
                }
                if (leftstick.getRawButton(Constants.JB_TAIL_RETRACT) ) {
                    tail.startRetract();
                }
                if (leftstick.getRawButton(Constants.JB_TAIL_EJECT) ) {
                    tail.startEject();
                }
            }
            tail.update();

            // SONAR
            SmartDashboard.putNumber("Left dist",sonar.getLeftDistance());
            SmartDashboard.putNumber("Right dist",sonar.getRightDistance());
            SmartDashboard.putNumber("Thrower Status",thrower.getStatus());
            SmartDashboard.putNumber("Target Arc", thrower.getThrowArc());
            
            sonar.update();
            // lights
            if (thrower.inRange() ) {
                if (sonar.getBalance() == Constants.SONIC_BALANCE_LEFT
                    || sonar.getBalance() == Constants.SONIC_BALANCE_EQUAL) {
                    leftLight.set(true);
                } else { 
                    leftLight.set(false);
                }
                if (sonar.getBalance() == Constants.SONIC_BALANCE_RIGHT
                    || sonar.getBalance() == Constants.SONIC_BALANCE_EQUAL) {
                    rightLight.set(true);
                } else { 
                    rightLight.set(false);
                }
            } else {
                rightLight.set(false);
                leftLight.set(false);
            }
                
            // DEBUG
            //System.out.print("pot: " + tail.getTheta());
            //System.out.print(Timer.getFPGATimestamp() );
            System.out.println(" pos:" + thrower.position() );
            //System.out.println(", arc: " + thrower.getThrowArc() );
            /*System.out.print(" sonar: " + sonar.getDistance() );
            //System.out.println(" status: " + thrower.getStatus() );
            
            System.out.print("L: " + sonar.getLeftDistance());
            System.out.print(", R: " + sonar.getRightDistance());
            System.out.print(", Dist: " + sonar.getDistance());
            System.out.println(", Bal: " + sonar.getBalance());*/
 
            Timer.delay(Constants.TELEOP_LOOP_DELAY_SECS);
        }        

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
        Solenoid left = new Solenoid(1);
        Solenoid right = new Solenoid(2);
        Solenoid comm = new Solenoid(3);
        while (isEnabled()) {
            sonar.sonarLeftEnable.set(true);
            sonar.sonarRightEnable.set(true);
            System.out.print("L: " + sonar.getLeftDistance());
            System.out.print(", R: " + sonar.getRightDistance());
            System.out.print(", Dist: " + sonar.getDistance());
            System.out.println(", Bal: " + sonar.getBalance());
            
            if (sonar.getBalance() == Constants.SONIC_BALANCE_LEFT
                || sonar.getBalance() == Constants.SONIC_BALANCE_EQUAL) {
                left.set(true);
            } else { 
                left.set(false);
            }
            if (sonar.getBalance() == Constants.SONIC_BALANCE_RIGHT
                || sonar.getBalance() == Constants.SONIC_BALANCE_EQUAL) {
                right.set(true);
            } else { 
                right.set(false);
            }
            
            Timer.delay(.1);
        }
        
        /*Vision vision = new Vision();
        double startTime = Timer.getFPGATimestamp();
        thrower.initThrower();
        // drive forward
        dt.setSafetyEnabled(true);
        dt.arcadeDrive(-0.7,0);
        while (Timer.getFPGATimestamp() < startTime + 2.9) {  //2.9 secs run time
            thrower.update();
        }
        dt.arcadeDrive(0,0);
        // Hot test
        int hotCounter = 0;
        while (Timer.getFPGATimestamp()< startTime + 4) {
            if (vision.hot()) {
                hotCounter ++;
            }
            else {
                hotCounter --;
            }
            thrower.update();
            Timer.delay(0.1);
        }
        //Throw test
        thrower.setThrowSpeed(1.0);
        thrower.setThrowArc((int)(ds.getAnalogIn(2)/5 * 130));
        if (hotCounter > 0) {
            System.out.println("Shooting");
          } else {
            System.out.println("Not hot, waiting");
            Timer.delay(2);
            System.out.println("Shooting");
        }
        thrower.startThrow();
        while (thrower.getStatus() != Constants.THROWER_STATUS_HOME) {
            thrower.update();
            //System.out.println(thrower.getStatus());
            Timer.delay(Constants.TELEOP_LOOP_DELAY_SECS);
        }
        //Turn around*/
        
    }
}
