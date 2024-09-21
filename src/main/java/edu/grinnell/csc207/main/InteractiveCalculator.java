package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BigFraction;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * The InteractiveCalculator class provides a REPL (Read-Eval-Print Loop) interface to perform
 * calculations with fractions using BigFraction and BFCalculator. It supports addition,
 * subtraction, multiplication, division, and storing values in registers.
 */
public class InteractiveCalculator {

  /**
   * The main method runs the interactive calculator, accepting input from the user, processing
   * commands or expressions, and displaying results.
   *
   * @param args command-line arguments (not used in this implementation)
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    PrintWriter output = new PrintWriter(System.out, true); // PrintWriter for output

    BFCalculator calculator = new BFCalculator();
    BFRegisterSet registers = new BFRegisterSet();
    String input;

    while (true) {
      output.print("> ");
      output.flush();
      input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("QUIT")) {
        break;
      } else if (input.startsWith("STORE ")) {
        handleStoreCommand(input, registers, calculator, output);
      } else {
        try {
          BigFraction result = evaluateExpression(input, calculator, registers);
          output.println(formatOutput(result));
        } catch (IllegalArgumentException e) {
          output.println(e.getMessage());
        } // end of catch
      } // end of else
    } // end of while loop
    scanner.close();
    output.close();
  } // end of method

  /**
   * Handles the STORE command to store the calculator's current value in a register.
   *
   * @param input the full command line input (e.g., "STORE a")
   * @param registers the BFRegisterSet to store the value in
   * @param calculator the BFCalculator providing the current value
   * @param output the PrintWriter for output
   */
  private static void handleStoreCommand(
      String input, BFRegisterSet registers, BFCalculator calculator, PrintWriter output) {
    String[] parts = input.split(" ");
    if (parts.length != 2 || parts[1].length() != 1 || !Character.isLowerCase(parts[1].charAt(0))) {
      // Register must be a lowercase letter (a-z)
      output.println("*** ERROR [STORE command received invalid register] ***");
    } else {
      char register = parts[1].charAt(0);
      registers.store(register, calculator.get());
      output.println("STORED");
    } // end of else case
  } // end of method

  /**
   * Evaluates an expression involving fractions and operators.
   *
   * @param input the expression to evaluate (e.g., "1/2 + 1/3")
   * @param calculator the BFCalculator to perform the calculations
   * @param registers the BFRegisterSet to retrieve stored values
   * @return the result of the evaluated expression as a BigFraction
   * @throws IllegalArgumentException if the expression is invalid
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
      } // end of if

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
      } // end of switch case
    } // end of for loop

    return calculator.get();
  } // end of method

  /**
   * Parses a token (either a number or a register) into a BigFraction value.
   *
   * @param token the token to parse
   * @param registers the BFRegisterSet to retrieve register values
   * @return the BigFraction value corresponding to the token
   * @throws IllegalArgumentException if the token is invalid
   */
  private static BigFraction parseValue(String token, BFRegisterSet registers)
      throws IllegalArgumentException {
    if (token.length() == 1 && Character.isLetter(token.charAt(0))) {
      char register = token.charAt(0);
      // Only allow lowercase letters as valid registers (a-z)
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
  } // end of method

  /**
   * Formats the output of a BigFraction. If the result is a whole number, it returns only the
   * numerator. Otherwise, it returns the fraction in the numerator/denominator form.
   *
   * @param result the BigFraction to format
   * @return the formatted result as a string
   */
  private static String formatOutput(BigFraction result) {
    // Check if the denominator is 1, meaning the result is a whole number
    if (result.denominator().equals(BigInteger.ONE)) {
      return result.numerator().toString(); // Display as a whole number
    } // end of if
    return result.toString(); // Otherwise, display as a fraction
  } // end of method
} // end of class
