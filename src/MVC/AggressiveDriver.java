package MVC;

import java.awt.Color;



/**
 *
 * @author carlr_000
 */
public class AggressiveDriver extends Driver{
    public AggressiveDriver(Road r, Car car, double timeStep) {
        super(r, car, timeStep);
        myColor = Color.RED;
        setPrefSpeed(Math.random()*10+ 75);

    }
    
}
