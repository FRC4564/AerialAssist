/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author TheGreenBox
 */
public class Auto {
    Throweraterenator thrower;
    DriveTrain dt;
    DriverStation ds;
    double startTime;
    Vision vision = new Vision();
    private int hotCounter = 0;
    int status = Constants.AUTO_STATUS_INIT;
    
    public Auto(Throweraterenator thrower, DriveTrain dt, DriverStation ds) {
        thrower = this.thrower;
        dt = this.dt;
        ds = this.ds;
    }
    
    public int checkStatus() {
        if (Timer.getFPGATimestamp() < startTime + 2.9) {
            return Constants.AUTO_STATUS_MOVING;
        } else if (Timer.getFPGATimestamp() < startTime + 4) {
            return Constants.AUTO_STATUS_LOOKING;
        } else {
            return Constants.AUTO_STATUS_THROW;
            
        }
    }
    
    public void autoThrow() {
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
        if (thrower.getStatus() != Constants.THROWER_STATUS_HOME) {
            thrower.update();
            System.out.println(thrower.getStatus());
            }
        status = Constants.AUTO_STATUS_DONE;
    }
    
    public void updateAuto() {
        if (status == Constants.AUTO_STATUS_INIT) {
            startTime = Timer.getFPGATimestamp();
            thrower.initThrower();
            status = Constants.AUTO_STATUS_MOVING;
        } else if (status == Constants.AUTO_STATUS_MOVING) {
            dt.setSafetyEnabled(true);
            if (Timer.getFPGATimestamp() < startTime + 2.9) {
                dt.arcadeDrive(-0.7, 0);
            } else {
                status = Constants.AUTO_STATUS_LOOKING;
            }
        } else if (checkStatus() == Constants.AUTO_STATUS_LOOKING) {
            dt.arcadeDrive(0, 0);
            if (Timer.getFPGATimestamp() < startTime + 4) {
                if (vision.hot()) {
                    hotCounter ++;
                }
                else {
                    hotCounter --;
                }
            } else {
                status = Constants.AUTO_STATUS_THROW;
            }
        } else if (checkStatus() == Constants.AUTO_STATUS_THROW) {
            autoThrow();
        }
        thrower.update();
    }
    
}
