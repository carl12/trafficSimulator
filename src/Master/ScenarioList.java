/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Master;

import java.util.ArrayList;

/**
 *
 * @author levenick
 */
public class ScenarioList extends ArrayList<Scenario> {
    
    public String toString() {
        String returnMe = "ScenarioList";
        
        for (Scenario nextScenario: this) {
            returnMe += "\n\t" + nextScenario;
        }
        
        return returnMe;
    }
}
