package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * A class representing fractions with arbitrary precision using {@link BigInteger}. This class
 * supports operations such as addition, subtraction, multiplication, and division.
 */
public class BigFraction {

  /** The numerator of the fraction. */
  private final BigInteger num;

  /** The denominator of the fraction. */
  private final BigInteger denom;

  /**
   * Constructs a BigFraction with the specified numerator and denominator. The fraction is
   * automatically simplified.
   *
   * @param numerator the numerator of the fraction
   * @param denominator the denominator of the fraction (must not be zero)
   * @throws ArithmeticException if the denominator is zero
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    if (denominator.equals(BigInteger.ZERO)) {
      throw new ArithmeticException("Denominator cannot be zero");
    } // End if block to send the error when denominator is zero

    // Ensure the denominator is positive
    if (denominator.signum() < 0) {
      numerator = numerator.negate();
      denominator = denominator.negate();
    } // End if block to ensure the denominator is positive

    // Simplify the fraction by dividing by the GCD
    BigInteger gcd = numerator.gcd(denominator);
    this.num = numerator.divide(gcd); // Using 'this' to reference class field
    this.denom = denominator.divide(gcd); // Using 'this' to reference class field
  } // End constructor BigFraction(BigInteger, BigInteger)

  /**
   * Constructs a BigFraction using integer values for the numerator and denominator.
   *
   * @param numerator the numerator of the fraction
   * @param denominator the denominator of the fraction (must not be zero)
   */
  public BigFraction(int numerator, int denominator) {
    this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
  } // End constructor BigFraction(int, int)

  /**
   * Constructs a BigFraction from a string representation. The string can be in the form
   * "numerator/denominator" or just a whole number.
   *
   * @param str the string representing the fraction
   */
  public BigFraction(String str) {
    if (str.contains("/")) {
      String[] parts = str.split("/");
      this.num =
          new BigFraction(new BigInteger(parts[0].trim()), new BigInteger(parts[1].trim()))
              .numerator();
      this.denom =
          new BigFraction(new BigInteger(parts[0].trim()), new BigInteger(parts[1].trim()))
              .denominator();
    } else {
      // Handle whole numbers like "42"
      this.num = new BigInteger(str.trim());
      this.denom = BigInteger.ONE;
    } // End if block to initialize the fields based on string input
  } // End constructor BigFraction(String)

  /**
   * Adds the current fraction to another fraction.
   *
   * @param other the fraction to add
   * @return a new BigFraction representing the sum
   */
  public BigFraction add(BigFraction other) {
    BigInteger newNumerator =
        this.num.multiply(other.denom).add(other.num.multiply(this.denom));
    BigInteger newDenominator = this.denom.multiply(other.denom);
    return new BigFraction(newNumerator, newDenominator);
  } // End method add

  /**
   * Subtracts another fraction from the current fraction.
   *
   * @param other the fraction to subtract
   * @return a new BigFraction representing the result
   */
  public BigFraction subtract(BigFraction other) {
    BigInteger newNumerator =
        this.num
            .multiply(other.denom)
            .subtract(other.num.multiply(this.denom));
    BigInteger newDenominator = this.denom.multiply(other.denom);
    return new BigFraction(newNumerator, newDenominator);
  } // End method subtract

  /**
   * Multiplies the current fraction by another fraction.
   *
   * @param other the fraction to multiply by
   * @return a new BigFraction representing the product
   */
  public BigFraction multiply(BigFraction other) {
    BigInteger newNumerator = this.num.multiply(other.num);
    BigInteger newDenominator = this.denom.multiply(other.denom);
    return new BigFraction(newNumerator, newDenominator);
  } // End method multiply

  /**
   * Divides the current fraction by another fraction.
   *
   * @param other the fraction to divide by
   * @return a new BigFraction representing the quotient
   * @throws ArithmeticException if dividing by zero
   */
  public BigFraction divide(BigFraction other) {
    if (other.num.equals(BigInteger.ZERO)) {
      throw new ArithmeticException("Cannot divide by zero.");
    } // End if block to send the error when numerator is zero
    BigInteger newNumerator = this.num.multiply(other.denom);
    BigInteger newDenominator = this.denom.multiply(other.num);
    return new BigFraction(newNumerator, newDenominator);
  } // End method divide

  /**
   * Returns the numerator of the fraction.
   *
   * @return the numerator as a BigInteger
   */
  public BigInteger numerator() {
    return this.num;
  } // End method numerator

  /**
   * Returns the denominator of the fraction.
   *
   * @return the denominator as a BigInteger
   */
  public BigInteger denominator() {
    return this.denom;
  } // End method denominator

  /**
   * Returns a string representation of the fraction.
   *
   * @return the string representation of the fraction
   */
  @Override
  public String toString() {
    if (denom.equals(BigInteger.ONE)) {
      return num.toString(); // Return whole numbers without "/1"
    } // End if block to return whole numbers without "/1"
    return num + "/" + denom;
  } // End method toString
} // End class BigFraction
