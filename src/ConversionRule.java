import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ConversionRule {

    Pattern getPattern();


    // Given replacement rules, replace each capturing group in matcher's pattern

    String replace(Matcher matcher);

    /**
     * Returns a non-null value if there should be an additional line
     * following a match of this rule. In most cases this method
     * returns null.
     */

    String getAdditionalLine();

    /**
     * This method will find the comments for the particular rule
     */
    String getComments(String text);

    String getAdditionalPattern();
}

