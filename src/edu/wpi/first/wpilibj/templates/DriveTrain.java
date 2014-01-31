/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;


/**
 *
 * @author Steven
 */
public class DriveTrain extends RobotDrive{
    
    public DriveTrain(SpeedController frontLeft, SpeedController rearLeft, SpeedController frontRight, SpeedController rearRight) {
        super(frontLeft, rearLeft, frontRight, rearRight);
    }
    
    public void setMotorsInverted() {
       setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
       setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
       setInvertedMotor(RobotDrive.MotorType.kFrontRight,true);
       setInvertedMotor(RobotDrive.MotorType.kRearRight,true);
    }
}
