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
public class Semi extends Car{
    public Semi(double timeStep, Road r)
    {
        super(timeStep,r);
        carLength = 53;
        carWidth = 8;
        carColor = Color.GRAY;
    }
    public Semi(double timeStep, Road r, double loc)
    {
        super(timeStep,r,loc);
        carLength = 53;
        carWidth = 8;
        
        carColor = Color.GRAY;
    }
}
