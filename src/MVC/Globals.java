/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

/**
 *
 * @author carlr_000
 */
public class Globals {

    public static double removeExtraDecimals(double in)
    {
        if(in%1 < 0.000001 || in%1 > 0.99999)
            return Math.round(in);
        else return in;
    }
}
