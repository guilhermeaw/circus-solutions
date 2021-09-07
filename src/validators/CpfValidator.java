package validators;

public class CpfValidator {
  private static final int[] cpfWeight = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

  public static boolean validate(String cpf) {
      if ((cpf == null) || (cpf.length() != 11)) {
          return false;
      }

      Integer digit1 = calculateDigit(cpf.substring(0, 9), cpfWeight);
      Integer digit2 = calculateDigit(cpf.substring(0, 9) + digit1, cpfWeight);

      return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
  }

  private static int calculateDigit(String str, int[] weight) {
      int sum = 0;

      for (int i = str.length() - 1, digit; i >= 0; i--) {
          digit = Integer.parseInt(str.substring(i, i + 1));
          sum += digit * weight[weight.length - str.length() + i];
      }

      sum = 11 - sum % 11;
      return sum > 9 ? 0 : sum;
  }
}
