package validators;

public class PasswordValidator {
    public static boolean validate(String value) {
        boolean isValidPassword = value != null && value.trim().length() > 4;
        return isValidPassword;
    }
}
