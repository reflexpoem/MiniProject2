package edu.grinnell.csc207.util;

/**
 * A calculator for operations on fractions using BigFraction. It maintains a running total and
 * supports addition, subtraction, multiplication, and division operations.
 */
public class BFCalculator {

  /** The last computed value of the calculator. */
  private BigFraction lastValue;

  /** Constant representing the value 0, used for initializing BigFraction. */
  private static final int ZERO = 0;

  /** Constant representing the value 1, used for initializing BigFraction. */
  private static final int ONE = 1;

  /**
   * Constructor initializes the calculator with a value of 0/1. Initializes the last computed value
   * of the calculator to 0 (represented as a fraction 0/1).
   */
  public BFCalculator() {
    lastValue = new BigFraction(ZERO, ONE); // Start with 0
  } // End constructor BFCalculator

  /**
   * Gets the last computed value of the calculator.
   *
   * @return the last computed value as a BigFraction.
   */
  public BigFraction get() {
    return lastValue;
  } // End method get

  /**
   * Adds a fraction to the last computed value.
   *
   * @param val the fraction to add.
   */
  public void add(BigFraction val) {
    lastValue = lastValue.add(val);
  } // End method add

  /**
   * Subtracts a fraction from the last computed value.
   *
   * @param val the fraction to subtract.
   */
  public void subtract(BigFraction val) {
    lastValue = lastValue.subtract(val);
  } // End method subtract

  /**
   * Multiplies the last computed value by a fraction.
   *
   * @param val the fraction to multiply by.
   */
  public void multiply(BigFraction val) {
    lastValue = lastValue.multiply(val);
  } // End method multiply

  /**
   * Divides the last computed value by a fraction.
   *
   * @param val the fraction to divide by.
   * @throws ArithmeticException if the fraction to divide by is zero.
   */
  public void divide(BigFraction val) {
    lastValue = lastValue.divide(val);
  } // End method divide

  /**
   * Resets the calculator's value to 0/1. Clears the calculator by resetting the last computed
   * value to 0 (represented as 0/1).
   */
  public void clear() {
    lastValue = new BigFraction(ZERO, ONE);
  } // End method clear
} // End class BFCalculator
