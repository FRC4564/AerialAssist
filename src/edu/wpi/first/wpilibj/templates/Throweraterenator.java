/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author TheGreenBox
 */
public class Throweraterenator {
    private Victor motorRight = new Victor(Constants.PWM_THROWER_RIGHT);
    private Victor motorLeft = new Victor(Constants.PWM_THROWER_LEFT);
    private double throwSpeed = 0;
    private double stowSpeed = 0;
    private int status = 0;
    private int arc = 0;
    private double initTime = 0;
    private int prevPosition = 0;
    private Encoder encoder = new Encoder(Constants.DIO_THROWER_ENCODER_A, 
            Constants.DIO_THROWER_ENCODER_B, false, EncodingType.k4X);
    private Trace trace = new Trace();

    // This timer task is dedicated to stopping the thrower at the target arc position
    // 'timer' will be scheduled to run this at a highspeed rate that doesn't overtax
    // the cRIO.
    // Once started timer schedule will remain running unless programatically stopped. 
    java.util.Timer timer;
    class stopThrowerTask extends java.util.TimerTask {
        public void run() {
            if (status == Constants.THROWER_STATUS_THROW && encoder.get() >= arc) {
                setMotors(0);
                status = Constants.THROWER_STATUS_STOW;
                System.out.println("Thrower task stopped throw");
            }
        }
    }
    
    public Throweraterenator() {
        encoder.start();
        encoder.reset();
    }
    
    private void setMotors(double speed) {
        motorRight.set(-speed);
        motorLeft.set(speed);
    }
    
    public void setThrowSpeed(double speed) {
        throwSpeed = speed;
    }
    
    /** Speed at which thrower motors will throw 
     * 
     * @return 0.0 to 1.0
     */
    public double getThrowSpeed() {
        return throwSpeed;
    }
    
    public void setStowSpeed(double speed) {
        stowSpeed = speed;
    }
    
    public double getStowSpeed() {
        return stowSpeed;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setThrowArc(int value) {
        arc = value;
    }
    
    public int getThrowArc() {
        return arc;
    }
    
    public int position() {
        return encoder.get();
    }
    
    /** Reset encoder to zero.
     * 
     */
    public void resetEncoder() {
        encoder.reset();
        encoder.start();
    }

    public double updateInit() {
        if (Timer.getFPGATimestamp() - initTime >= Constants.COUNTDOWN_TIME) {
            initTime = Timer.getFPGATimestamp();
            if (position() == prevPosition) {
                setMotors(0);
                encoder.reset();
                status = Constants.THROWER_STATUS_HOME;
            } else { 
                prevPosition = position();
            }
        }
        
        return 0;
    }

    public void initThrower() {
        //Schedule timer task to stop thrower when target pos is reached
        status = Constants.THROWER_STATUS_INIT;
        initTime = Timer.getFPGATimestamp();
        setMotors(stowSpeed);
        prevPosition = position();
        timer = new java.util.Timer();    
        timer.schedule(new stopThrowerTask(), 0, 2);  // set to run every 2ms  
        //
    }
    
    /** Initiate a throw at currently set speed and arc.
       Throw will only initiate if in home position.
    **/
    public void startThrow() {
        if (status == Constants.THROWER_STATUS_HOME) {
            status = Constants.THROWER_STATUS_THROW;
            trace.start();
            trace.add(position(), getThrowArc(), getStatus());
        }
    }
    
    /** Based on thrower status and position, move thrower.
     *  Call this routine on a regular basis to service the thrower.
     */
    public void update() {
        // Capture trace
        if (trace.count() > 1) {
            trace.add(position(), getThrowArc(), getStatus());
        } 
        if (trace.count() > 50) {
            trace.print();
        }
        // Process status
        if (getStatus() == Constants.THROWER_STATUS_THROW) {
            updateThrow();
        } else if (getStatus() == Constants.THROWER_STATUS_INIT) {
            updateInit();
        } else if (position() != 0) {
            updateStow();
        }

    }
    
    private void updateThrow() {
        if (position() < arc) {
            setMotors(throwSpeed);
        } else {
            setMotors(0);
            status = Constants.THROWER_STATUS_STOW;
        }
    }
    
    /** Return thrower to home position (+/- 1).  
     *  Values either above or below zero will cause the arm to move
     */ 
    private void updateStow() {
        if (position() > 1) {
            setMotors(stowSpeed);
        } else if (position() < -1) {
            setMotors(-stowSpeed);
        } else {
            setMotors(0);
            status = Constants.THROWER_STATUS_HOME;
        }
    }
    
    private boolean inRange(){
        return true;
    }
    
    /** Sets speed and arc parameters given target distance in feet.
     *  If the distance is out of range of the thrower then
     *  no changes will be made and 'false' will be returned.
     * 
     * @param distance decimal feet to target
     * @return true if distance is within thrower range
     */
    public boolean setTargetDistance(double distance) {
        return false;
    }
}
