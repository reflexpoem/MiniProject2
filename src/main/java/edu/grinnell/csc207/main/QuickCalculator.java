package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * The QuickCalculator class evaluates fractional expressions and executes store commands from the
 * command line. It takes expressions as arguments and evaluates them in sequence.
 */
public class QuickCalculator {

  /**
   * The main method takes command-line arguments as input, processes each argument as an expression
   * or store command, and outputs the result.
   *
   * @param args The command-line arguments representing expressions or commands.
   */
  public static void main(String[] args) {
    BFCalculator calculator = new BFCalculator();
    BFRegisterSet registers = new BFRegisterSet();
    PrintWriter output =
        new PrintWriter(System.out, true); // Use PrintWriter instead of System.out.println

    for (String arg : args) {
      if (arg.startsWith("STORE ")) {
        handleStoreCommand(arg, registers, calculator, output);
      } else {
        try {
          BigFraction result = evaluateExpression(arg, calculator, registers);
          output.println(arg + " -> " + formatOutput(result));
        } catch (IllegalArgumentException e) {
          output.println(arg + ": FAILED [Invalid expression]");
        } // end of catch
      } // end of else
    } // end of for loop
  } // End of main method

  /**
   * Handles the STORE command by storing the calculator's current value in the specified register.
   *
   * @param arg The STORE command.
   * @param registers The BFRegisterSet object to store the value.
   * @param calculator The BFCalculator object that holds the current value.
   * @param output The PrintWriter for output.
   */
  private static void handleStoreCommand(
      String arg, BFRegisterSet registers, BFCalculator calculator, PrintWriter output) {
    String[] parts = arg.split(" ");
    if (parts.length == 2 && parts[1].length() == 1 && Character.isLowerCase(parts[1].charAt(0))) {
      char register = parts[1].charAt(0);
      registers.store(register, calculator.get());
      output.println(arg + " -> STORED");
    } else {
      output.println(arg + ": FAILED [Invalid expression]");
    } // end of else case
  } // End of handleStoreCommand method

  /**
   * Evaluates a mathematical expression and returns the result.
   *
   * @param input The expression as a string.
   * @param calculator The BFCalculator to perform calculations.
   * @param registers The BFRegisterSet that holds register values.
   * @return The result of the expression as a BigFraction.
   * @throws IllegalArgumentException If the expression is invalid.
   */
  private static BigFraction evaluateExpression(
      String input, BFCalculator calculator, BFRegisterSet registers)
      throws IllegalArgumentException {
    if (input.isEmpty() || input.matches(".*[+\\-*/]$")) {
      throw new IllegalArgumentException("*** ERROR [Invalid expression] ***");
    } // end of if

    String[] tokens = input.split(" ");
    BigFraction result = parseValue(tokens[0], registers);
    calculator.clear(); // Clear the calculator for each new expression
    calculator.add(result);

    for (int i = 1; i < tokens.length; i += 2) {
      if (i + 1 >= tokens.length) {
        throw new IllegalArgumentException("*** ERROR [Invalid expression] ***");
      } // end of if case

      String operator = tokens[i];
      BigFraction nextValue = parseValue(tokens[i + 1], registers);

      switch (operator) {
        case "+":
          calculator.add(nextValue);
          break;
        case "-":
          calculator.subtract(nextValue);
          break;
        case "*":
          calculator.multiply(nextValue);
          break;
        case "/":
          calculator.divide(nextValue);
          break;
        default:
          throw new IllegalArgumentException("*** ERROR [Invalid expression] ***");
      } // End of switch block
    } // end of for loop

    return calculator.get();
  } // End of evaluateExpression method

  /**
   * Parses a token to determine whether it is a number or a register and returns the corresponding
   * BigFraction value.
   *
   * @param token The token to parse.
   * @param registers The BFRegisterSet that holds register values.
   * @return The parsed BigFraction value.
   * @throws IllegalArgumentException If the token is not a valid number or register.
   */
  private static BigFraction parseValue(String token, BFRegisterSet registers)
      throws IllegalArgumentException {
    if (token.length() == 1 && Character.isLetter(token.charAt(0))) {
      char register = token.charAt(0);
      if (!Character.isLowerCase(register)) {
        throw new IllegalArgumentException("*** ERROR [Invalid expression] ***");
      } // end of if
      BigFraction value = registers.get(register);
      if (value == null) {
        throw new IllegalArgumentException("*** ERROR [Invalid expression] ***");
      } // end of if
      return value;
    } else if (token.matches("-?[0-9]+(/[0-9]+)?")) {
      return new BigFraction(token);
    } else {
      throw new IllegalArgumentException("*** ERROR [Invalid expression] ***");
    } // end of else
  } // End of parseValue method

  /**
   * Formats the output of a BigFraction. If the result is a whole number, it returns the numerator
   * only. Otherwise, it returns the fraction as a string.
   *
   * @param result The BigFraction to format.
   * @return The formatted string representation of the result.
   */
  private static String formatOutput(BigFraction result) {
    // Directly compare the denominator to BigInteger.ONE
    if (result.denominator().equals(BigInteger.ONE)) {
      return result.numerator().toString(); // Return the numerator as a whole number
    } // end of if
    return result.toString(); // Otherwise, return the fraction as a string
  } // End of formatOutput method
} // End of QuickCalculator class
