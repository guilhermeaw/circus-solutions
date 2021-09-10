package validators;

public class NameValidator {
    public static boolean validate(String value) {
        boolean isCompleteName = value != null && validateFullName(value);

        return isCompleteName;
    }

    private static boolean validateFullName(String value) {
        String[] nameParts = value.trim().split(" ");

        boolean result = nameParts.length > 1;

        for (int i = 0; i < nameParts.length; i++) {
            String namePart = nameParts[i];

            if (namePart.trim().length() <= 1) {
                result = false;
                break;
            }
        }

        return result;
    }
}
