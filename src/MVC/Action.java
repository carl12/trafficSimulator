
package MVC;

/**
 *
 * @author carlr_000
 */
public class Action {
    double speed;
    double lane;
    double loc;
    double acceleration;
    double laneChange;
    double time;
    public Action(double speedIn, double locIn, double laneIn, 
            double accelerationIn, double laneChangeIn, double time)
    {
        speed = speedIn;
        loc = locIn;
        lane = laneIn;
        acceleration = accelerationIn;
        laneChange = laneChangeIn;
    }
    
    public String toString()
    {
        String a = "Tim: "+time+" Spd: " + speed+" lan: " + lane + " loc: " + loc;
        if(acceleration != 0)
        {
            a += " acc: " + acceleration;
        } 
        if(laneChange != 0)
        {
            a += " lnChng: " + laneChange;
        }
        return a;
    }
    
}
