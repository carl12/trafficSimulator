
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
