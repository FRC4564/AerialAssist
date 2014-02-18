/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import java.lang.*; 

/**
 *
 * @author Oisin
 */
public class Vision {
    public void init() {
        System.out.println("Vision Initialized");
    }
    
    public boolean hot(){
        return true;
    }
    
    public void close() {
        System.out.println("Vision Closed");
    }
    
}
