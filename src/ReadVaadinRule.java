import beanRule.JAllRules;
import beanRule.JRule;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.regex.Pattern;
import java.util.*;


public class ReadVaadinRule implements RuleSet {

    private ArrayList<ConversionRule> conversionRuleList;
    private JAllRules rules = null;

    public ReadVaadinRule() {
        // matching : import org.apache.commons.logging.LogFactory;

        // Provide path to XML file for the RuleSet Data

        File file = new File("/Users/aniket/IdeaProjects/VaadinMigrator/RuleSetData/RuleSetJava.xml");


        try {
            JAXBContext jc = JAXBContext.newInstance(JAllRules.class);
            Unmarshaller ums = jc.createUnmarshaller();

            rules = (JAllRules) ums.unmarshal(file);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        conversionRuleList = new ArrayList<ConversionRule>();
        for (JRule rule : rules.getAllRules()) {
            SingleConversionRule cr = new SingleConversionRule(Pattern
                    .compile(rule.getOldPattern()),
                    rule.getNewPattern(), rule.getComments(), rule.getAdditionalPattern());
            conversionRuleList.add(cr);
        }

    }


    public Iterator<ConversionRule> iterator() {
        return conversionRuleList.iterator();
    }
}
