package Master;

import MVC.Statistics;

/**
 * One scenario, all the params for a run, plus the Statistics from it
 * 
 * @author levenick Nov 30, 2015 5:40:15 PM
 */
public class Scenario {
    private Params params;
    private Statistics myStatistics;

    Scenario(Params params) {
        this.params = params;
    }
    
    public String toString() {
        String returnMe = "\nI am a Scenario:";
                
        returnMe += "\n\t" + getParams().toString();
                
        return returnMe;        
    }

    /**
     * @return the params
     */
    public Params getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Params params) {
        this.params = params;
    }

    /**
     * @return the myStatistics
     */
    public Statistics getMyStatistics() {
        return myStatistics;
    }

}
