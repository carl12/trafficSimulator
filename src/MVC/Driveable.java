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
