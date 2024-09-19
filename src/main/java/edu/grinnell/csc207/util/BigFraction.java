package edu.grinnell.csc207.util;

import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * A simple implementation of arbitrary-precision Fractions.
 *
 * @author Samuel A. Rebelsky
 * @author Harrison Zhu, Sunjae Kim
 */
public class BigFraction {
    // +------------------+---------------------------------------------
    // | Design Decisions |
    // +------------------+

    /*
   * (1) Denominators are always positive. Therefore, negative fractions
   * are represented with a negative numerator. Similarly, if a fraction
   * has a negative numerator, it is negative.
   *
   * (2) Fractions are not necessarily stored in simplified form. To
   * obtain a fraction in simplified form, one must call the `simplify`
   * method.
     */
    // +-----------+---------------------------------------------------
    // | Constants |
    // +-----------+
    /**
     * The default numerator when creating fractions.
     */
    private static final BigInteger DEFAULT_NUMERATOR = BigInteger.valueOf(2);

    /**
     * The default denominator when creating fractions.
     */
    private static final BigInteger DEFAULT_DENOMINATOR = BigInteger.valueOf(7);

    // +--------+-------------------------------------------------------
    // | Fields |
    // +--------+
    /**
     * The numerator of the fraction. Can be positive, zero or negative.
     */
    BigInteger num;

    /**
     * The denominator of the fraction. Must be non-negative.
     */
    BigInteger denom;

    // +--------------+-------------------------------------------------
    // | Constructors |
    // +--------------+
    /**
     * Build a new fraction with numerator num and denominator denom.
     *
     * Warning! Not yet stable.
     *
     * @param numerator The numerator of the fraction.
     * @param denominator The denominator of the fraction.
     */
    public BigFraction(BigInteger numerator, BigInteger denominator) {
        this.num = numerator;
        this.denom = denominator;
    } // BigFraction(BigInteger, BigInteger)

    /**
     * Build a new fraction with numerator num and denominator denom.
     *
     * Warning! Not yet stable.
     *
     * @param numerator The numerator of the fraction.
     * @param denominator The denominator of the fraction.
     */
    public BigFraction(int numerator, int denominator) {
        this.num = BigInteger.valueOf(numerator);
        this.denom = BigInteger.valueOf(denominator);
    } // BigFraction(int, int)

    /**
     * Build a new fraction by parsing a string.
     *
     * Warning! Not yet implemented.
     *
     * @param str The fraction in string form
     */
    public BigFraction(String str) {
        this.num = DEFAULT_NUMERATOR;
        this.denom = DEFAULT_DENOMINATOR;
        String[] arr = str.split("/");
        this.num = BigInteger.valueOf(Integer.parseInt(arr[0]));
        this.denom = BigInteger.valueOf(Integer.parseInt(arr[1]));
    } // BigFraction

    // +---------+------------------------------------------------------
    // | Methods |
    // +---------+
    /**
     * Express this fraction as a double.
     *
     * @return the fraction approxiamted as a double.
     */
    public double doubleValue() {
        return this.num.doubleValue() / this.denom.doubleValue();
    } // doubleValue()

    /**
     * Add another faction to this fraction.
     *
     * @param addend The fraction to add.
     *
     * @return the result of the addition.
     */
    public BigFraction add(BigFraction addend) {
        BigInteger resultNumerator;
        BigInteger resultDenominator;

        // The denominator of the result is the product of this object's
        // denominator and addend's denominator
        resultDenominator = this.denom.multiply(addend.denom);
        // The numerator is more complicated
        resultNumerator
                = (this.num.multiply(addend.denom)).add(addend.num.multiply(this.denom));

        // Return the computed value
        return new BigFraction(resultNumerator, resultDenominator);
    } // add(BigFraction)

    public BigFraction subtract(BigFraction subtractend){
        BigInteger resultNumerator;
        BigInteger resultDenominator;

        // The denominator of the result is the product of this object's
        // denominator and addend's denominator
        resultDenominator = this.denom.multiply(subtractend.denom);
        // The numerator is more complicated
        resultNumerator
                = (this.num.multiply(subtractend.denom)).subtract(subtractend.num.multiply(this.denom));

        // Return the computed value
        return new BigFraction(resultNumerator, resultDenominator);
    } // subtract(BigFraction)

    /**
     * Get the denominator of this fraction.
     *
     * @return the denominator
     */
    public BigInteger denominator() {
        return this.denom;
    } // denominator()

    /**
     * Get the numerator of this fraction.
     *
     * @return the numerator
     */
    public BigInteger numerator() {
        return this.num;
    } // numerator()

    /**
     * Convert this fraction to a string for ease of printing.
     *
     * @return a string that represents the fraction.
     */
    public String toString() {
        // Special case: It's zero
        if (this.num.equals(BigInteger.ZERO)) {
            return "0";
        } // if it's zero

        // Lump together the string represention of the numerator,
        // a slash, and the string representation of the denominator
        // return this.num.toString().concat("/").concat(this.denom.toString());
        return this.num + "/" + this.denom;
    } // toString()

    public BigFraction multiply(BigFraction other) {
        return new BigFraction(this.numerator().multiply(other.numerator()), this.denominator().multiply(other.denominator()));
    }

    public BigFraction fractional() {
        return new BigFraction(this.numerator().mod(this.denominator()), this.denominator());
    }

    //TODO
    //subtraction and division
    //You should make sure to reduce your fractions to the simplest form.
    public BigFraction divide(BigFraction other) {
        return new BigFraction(this.numerator().divide(other.denominator()), this.denominator().divide(other.numerator()));
    }

    public static void main(String[] args) throws Exception {
        PrintWriter pen = new PrintWriter(System.out, true);
        BigFraction f1;
        f1 = new BigFraction(3, 10);
        BigFraction f2;
        f2 = new BigFraction(2, 5);
        pen.println("First fraction: " + f1);
        pen.println("Second fraction: " + f2);
        pen.println("Sum: " + (f1.add(f2)));
        pen.println("Product: " + (f1.multiply(f2)));
        pen.println("fractional "+ (f1.fractional()));
        BigFraction f3;
        f3 = new BigFraction("123/456");
        pen.println("Fraction from string: " + f3);
    
        pen.close();
      } // main(String[])

} //end