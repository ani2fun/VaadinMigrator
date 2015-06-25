public class ConversionException extends Exception {


    public static final String NOT_IMPLEMENTED = "Conversion mode not implemented yet";

    public ConversionException(String message) {
        super(message);
    }


    public void print() {
        if (getMessage() != null) {
            System.out.println(getMessage());
        }
    }

}
