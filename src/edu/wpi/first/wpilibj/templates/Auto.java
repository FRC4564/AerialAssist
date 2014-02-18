/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author TheGreenBox
 */
public class Auto {
    //objects used by autonomous
    Throweraterenator thrower;
    DriveTrain dt;
    DriverStation ds;
    Solenoid light;
    
    Vision vision = new Vision();
    private int hotCounter = 0;
    double startTime;
    int status = Constants.AUTO_STATUS_INIT;
    double driveSpeed = 0;
    
    public Auto(Throweraterenator thrower, DriveTrain dt, DriverStation ds,
                Solenoid light) {
        this.thrower = thrower;
        this.dt = dt;
        this.ds = ds;
        this.light = light;
    }
        
    public void updateAuto() {
        if (status == Constants.AUTO_STATUS_INIT) {
            System.out.println("INIT Autonomous");
            dt.setSafetyEnabled(false);
            startTime = Timer.getFPGATimestamp();
            thrower.initThrower();
            thrower.setThrowSpeed(1.0);
            thrower.setThrowArc(Constants.THROWER_NOMINAL_ARC);
            vision.init();
            status = Constants.AUTO_STATUS_MOVING;
        // Moving
        } else if (status == Constants.AUTO_STATUS_MOVING) {
            System.out.println("Moving");
            if (Timer.getFPGATimestamp() < startTime + 2.9) {
               driveSpeed = -0.7;
            } else {
               driveSpeed = 0.0;
                status = Constants.AUTO_STATUS_LOOKING;
            }
        // Looking for Hot or Cold
        } else if (status == Constants.AUTO_STATUS_LOOKING) {
            System.out.println("Looking");
            light.set(true);
            if (Timer.getFPGATimestamp() < startTime + 4) {
                if (vision.hot()) {
                    hotCounter ++;
                } else {
                    hotCounter --;
                }
            } else {
                light.set(false);
                vision.close();
                status = Constants.AUTO_STATUS_THROWCHECK;
            }
        // Test to see if it is time to throw or wait
        } else if (status == Constants.AUTO_STATUS_THROWCHECK) {
            if (hotCounter > 0) {
                System.out.println("Shooting");
                status = Constants.AUTO_STATUS_THROW;
            } else if (Timer.getFPGATimestamp() < startTime + 6) {
                System.out.println("Shooting");
                status = Constants.AUTO_STATUS_THROW;
            }
        // Initiate throw
        } else if (status == Constants.AUTO_STATUS_THROW) {
            thrower.startThrow();
            status = Constants.AUTO_STATUS_THROWING;
        // Allow throw to complete
        } else if (status == Constants.AUTO_STATUS_THROWING) {
            if (thrower.getStatus() == Constants.THROWER_STATUS_HOME) {
                status = Constants.AUTO_STATUS_DONE;
            }
        }
        // Thrower must be updated every loop
        thrower.update();
        dt.arcadeDrive(driveSpeed, 0);
    }
    
}


