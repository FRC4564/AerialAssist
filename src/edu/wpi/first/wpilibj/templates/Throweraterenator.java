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
    
    private Encoder encoder = new Encoder(Constants.DIO_THROWER_ENCODER_A, 
            Constants.DIO_THROWER_ENCODER_B, false, EncodingType.k4X);
     
    private double motorsSpeed = 0; // Current thrower motors speed
    private double throwSpeed = 0;
    private double stowSpeed = 0;
    private int status = 0;
    private int arc = 0;
    
    // Initialization variables
    private double initTime = 0;
    private int initPosition = 0;
    
    // Trace statistics for tuning
    private Trace trace = new Trace();

    // This timer task is dedicated to stopping the thrower at the target arc position
    // 'timer' will be scheduled to run this at a highspeed rate that doesn't overtax
    // the cRIO.
    // Once started timer schedule will remain running unless programatically stopped. 
    java.util.Timer timer;
    class stopThrowerTask extends java.util.TimerTask {
        public void run() {
            if (status == Constants.THROWER_STATUS_THROW && encoder.get() >= arc) {
                setMotors(stowSpeed);
                status = Constants.THROWER_STATUS_STOW;
                System.out.println("Thrower task stopped throw");
            }
            if (trace.count() > 1) {
                //trace.add(position(), getThrowArc(), getStatus());
            } 
        }
    }
    
    public Throweraterenator() {
        encoder.start();
        encoder.reset();
    }
    
    /** Set motors based on 'speed' and preserve in 'motorsSpeed'.
     *  Motors must run counter to one another.
     */
    private void setMotors(double speed) {
        motorsSpeed = speed;
        motorRight.set(-motorsSpeed);
        motorLeft.set(motorsSpeed);
    }
    
    /**
     *  Set speed at which throw will occur.
     * 
     * @param speed
     */
    public void setThrowSpeed(double speed) {
        throwSpeed = speed;
    }
    
    /** 
     *  Speed at which thrower motors will throw 
     * 
     * @return throwSpeed
     */
    public double getThrowSpeed() {
        return throwSpeed;
    }
    
    /** 
     * Set speed at which arm will be stowed.
     */
    public void setStowSpeed(double speed) {
        stowSpeed = speed;
    }
    
    /** Speed at which are will be stowed.
     * 
     * @return stowSpeed 
     */
    public double getStowSpeed() {
        return stowSpeed;
    }
    
    /** Set the throw arc based on targeted encoder count.
     * 
     * @param value - Encoder target count
     */
    public void setThrowArc(int value) {
        arc = value;
    }
    
    public int getThrowArc() {
        return arc;
    }
    
    
    public int getStatus() {
        return status;
    }
        
    /**
     *  Current arm position based on encoder.
     * @return Encoder count
     */
    public int position() {
        return encoder.get();
    }
    
    /** Reset encoder to zero.
     * 
     */
    public void resetEncoder() {
        encoder.reset();
    }

    /** Call once, when robot initializes.
     *  Sets thrower into initialization mode, which will locate Home position.
     *  Also launches scheduled timer task to control throwing arc.
     */
    public void initThrower() {
        encoder.start();
        //Start finding Home position
        status = Constants.THROWER_STATUS_INIT;
        //Schedule timer task to stop thrower when target pos is reached       
        timer = new java.util.Timer();    
        timer.schedule(new stopThrowerTask(), 0, 2);  // set to run every 2ms  
    }
    
    /** Initiate a throw at currently set speed and arc.
     *  Throw will only initiate if Thrower and Scorpion Tail are
     *  at Home position.
     */
    public void startThrow() {
        if (status == Constants.THROWER_STATUS_HOME) {
             status = Constants.THROWER_STATUS_THROW;
             //trace.start();
             //trace.add(position(), getThrowArc(), getStatus());
        }
    }
    
    /** Based on thrower status and position, move thrower.
     *  Call this routine on a regular basis to service the thrower.
     */
    public void update() {
        // Capture trace
        //if (trace.count() > 50) {
        //    trace.print();
        //}
        // Process Thrower status
        if (status == Constants.THROWER_STATUS_INIT) {
            updateInit();
        } else if (status == Constants.THROWER_STATUS_THROW) {
            updateThrow();
        } else if (position() != 0) {
            updateStow();
        }

    }

    
    /**
     *  During initialization, find the thrower arm home position by slowly
     *  moving downward until it stops moving.  Ever 1/4 second, check to
     *  see if arm position has changed.  Once it stops, motors will be
     *  stopped and another 1/4 second delay will let the arm settle.  The
     *  encoder is then zeroed and arm status is changed to Home.
     */
    private void updateInit() {
        // Is initialization just starting?
        if (initTime == 0 ) {   // let's get started.
            initTime = Timer.getFPGATimestamp();
            initPosition = position();
            setMotors(stowSpeed);
        } else {
            // Every 1/4 second, recheck initialization progress.
            if (Timer.getFPGATimestamp() - initTime >= 0.25) {
                // If the arm hasn't moved
                if (position() == initPosition) {
                    // Stop the motors, if not already stopped.
                    if (motorsSpeed != 0) {
                        setMotors(0);
                    // Arm is stopped and settled -- we're home
                    } else {
                        resetEncoder();
                        status = Constants.THROWER_STATUS_HOME;
                    }
                }
                initTime = Timer.getFPGATimestamp();
                initPosition = position();
            }
        }     
    }
  
    /**
     * Update throwing arm and switch to Stow once target arc reached.
     * The timerTask is also watching for target arc, and stopping throw.
     */
    private void updateThrow() {
        if (position() < arc) {
            setMotors(throwSpeed);
        } else {
            setMotors(0);
            status = Constants.THROWER_STATUS_STOW;
        }
    }
    
    
    /**
     * Return thrower to home position.  
     */ 
    private void updateStow() {
        if (position() > 1) {
            setMotors(stowSpeed);
        } else if (position() < -1) {
            setMotors(-stowSpeed / 2);
        } else {
            setMotors(0);
            status = Constants.THROWER_STATUS_HOME;
        }
    }


    
    private boolean inRange(){
        return true;
    }
    
    /** Sets speed and arc parameters given target distance in feet.
     *  If the distance is out of throwing range then
     *  no changes will be made and 'false' will be returned.
     * 
     * @param distance decimal feet to target
     * @return true if distance is within thrower range
     */
    public boolean setTargetDistance(double distance) {
        return false;
    }
}
