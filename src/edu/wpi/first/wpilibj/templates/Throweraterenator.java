/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;





    

        

/**
 *
 * @author TheGreenBox
 */
public class Throweraterenator {
    private Jaguar motorRight = new Jaguar(Constants.PWM_THROWER_RIGHT);
    private Jaguar motorLeft = new Jaguar(Constants.PWM_THROWER_LEFT);
    private double throwSpeed = 0;
    private double stowSpeed = 0;
    private int status = 0;
    private int arc = 0;
    private Encoder encoder = new Encoder(Constants.DIO_THROWER_ENCODER_A, 
            Constants.DIO_THROWER_ENCODER_B, false, EncodingType.k4X);

    java.util.Timer timer;
    class stopThrowerTask extends java.util.TimerTask {
        public void run() {
            if (encoder.get() >= arc) {
                setMotors(0);
                status = Constants.THROWER_STATUS_STOW;
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
    
    
    /** Initiate a throw at currently set speed and arc.
       Throw will only initiate if in home position.
    **/
    public void startThrow() {
        if (status == Constants.THROWER_STATUS_HOME) {
            status = Constants.THROWER_STATUS_THROW;
        //Launch task to stop thrower when target pos is reached
 //       timer = new java.util.Timer();    
 //       timer.schedule(new stopThrowerTask(), 0, 200);  
        
        }
    }
    
    /** Based on thrower status and position, move thrower.
     *  Call this routine on a regular basis to service the thrower.
     */
    public void update() {
        if (getStatus() == Constants.THROWER_STATUS_THROW) {
            updateThrow();
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
