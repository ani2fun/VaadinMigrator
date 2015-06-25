package beanRule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="RuleSet")
public class JAllRules {

    private List<JRule> AllRules = new ArrayList<JRule>();


    public List<JRule> getAllRules() {
        return AllRules;
    }

    @XmlElement(name="Rule")
    public void setAllRules(List<JRule> rules) {
        this.AllRules = rules;
    }

}