

/*
  This class runs Pattern matching with java.util.regex using Patterns defined
  in concrete implementations
 */
public abstract class RuleSetFactory {

    /*
     Return matcher implementation depending on the conversion mode
    */
    public static RuleSet getMatcherImpl(int conversionType) {
        switch (conversionType) {
            case Constant.Vaadin7Standart:
                return new ReadVaadinRule();
            case Constant.NOP_TO_Vaadin7:
                return new EmptyRuleSet();
            default:
                return null;
        }
    }
}