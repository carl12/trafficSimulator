package MVC;

import java.awt.Color;

/**
 *
 * @author carlr_000
 */
public class PassiveDriver extends Driver{
    public PassiveDriver(Road r, Car car, double timeStep) {
        super(r, car, timeStep);
        setPrefSpeed(Math.random()*10+ 55);
        myColor = Color.GRAY;

    }
    @Override
    public boolean wantToMerge()
    {
        return false;
    }
    
}
