import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * This class represents a conversion rule It uses a Pattern and defines for
 * each capturing group of this Pattern a replacement text
 */
public class SingleConversionRule implements ConversionRule {

    final private Pattern pattern;
    final private String replacementText;

    final private String comments;
    final private String additionalPattern;
    final private String additionalLine;


    public SingleConversionRule(Pattern pattern, String replacementText, String comments, String additionalPattern) {
        this(pattern, replacementText, comments, additionalPattern, null);
    }

    public SingleConversionRule(Pattern pattern, String replacementText, String comments, String additionalPattern, String additionalLine) {
        this.pattern = pattern;
        this.replacementText = replacementText;
        this.comments = comments;
        this.additionalPattern = additionalPattern;
        this.additionalLine = additionalLine;

    }

    public Pattern getPattern() {
        return pattern;
    }


    public String replace(Matcher matcher) {
        return replacementText;
    }

    public String getComments(String text) {
        String commentsReplacement = comments + "\n" + text;
        return commentsReplacement;
    }

    public String getAdditionalPattern() {
        return additionalPattern;
    }

    public String getAdditionalLine() {
        return additionalLine;
    }

}
