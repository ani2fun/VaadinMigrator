package beanRule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Rule")
public class JRule {
    private String OldPattern;
    private String NewPattern;
    private String Comments;
    private String AdditionalPattern;

    public JRule(String oldPattern, String newPattern, String comments, String additionalPattern) {
        super();
        OldPattern = oldPattern;
        NewPattern = newPattern;
        Comments = comments;
        AdditionalPattern = additionalPattern;
    }

    public JRule() {
        super();
    }

    public String getOldPattern() {
        return OldPattern;
    }

    @XmlElement(name = "OldPattern")
    public void setOldPattern(String oldPattern) {
        OldPattern = oldPattern;
    }

    public String getNewPattern() {
        return NewPattern;
    }

    @XmlElement(name = "NewPattern")
    public void setNewPattern(String newPattern) {
        NewPattern = newPattern;
    }

    public String getComments() {
        return Comments;
    }

    @XmlElement(name = "Comments")
    public void setComments(String comments) {
        Comments = comments;
    }

    public String getAdditionalPattern() { return AdditionalPattern;}

    @XmlElement(name = "additionalPattern")
    public void setadditionalPattern(String additionalPattern) {
        AdditionalPattern = additionalPattern;
    }
}