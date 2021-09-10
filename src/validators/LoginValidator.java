package validators;

public class LoginValidator {
    public static boolean validate(String value) {
        boolean isValidLogin = value != null && value.trim().length() > 3;
        return isValidLogin;
    }
}
