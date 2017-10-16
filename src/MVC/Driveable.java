/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import java.awt.Color;

/**
 *
 * @author carlr_000
 */
public interface Driveable {
    double getSpeed();
    double getLoc();
    double getSize();
    Color getColor();
    
    double mergeLanes(double MergeAmount);
    double accel(double amount);
    
    
}
