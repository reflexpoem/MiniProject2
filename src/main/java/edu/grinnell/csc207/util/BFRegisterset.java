package edu.grinnell.csc207.util;

import java.util.Arrays;

/**
 * A class that represents a set of registers for storing BigFraction values. Each register is
 * associated with a letter from 'a' to 'z' and stores a BigFraction.
 */
public class BFRegisterSet {

  /** Array of registers to hold BigFraction values, one for each letter from 'a' to 'z'. */
  private final BigFraction[] registers;

  /** Number of registers, corresponding to the 26 letters of the alphabet. */
  private static final int REGISTER_COUNT = 26;

  /** Initializes an array of 26 registers, each starting with a value of 0/1. */
  public BFRegisterSet() {
    registers = new BigFraction[REGISTER_COUNT];
    // Initialize each register to 0/1 (representing a fraction of 0)
    Arrays.fill(registers, new BigFraction(0, 1));
  } // End of constructor BFRegisterSet()

  /**
   * Stores a BigFraction value in a specified register.
   *
   * @param register the register ('a' to 'z') to store the value in.
   * @param value the BigFraction value to store.
   * @throws IllegalArgumentException if the register is not a letter between 'a' and 'z'.
   */
  public void store(char register, BigFraction value) {
    if (register < 'a' || register > 'z') {
      throw new IllegalArgumentException("Register must be a letter from 'a' to 'z'.");
    } // End of if block
    registers[register - 'a'] = value; // Store the value in the corresponding register
  } // End of store method

  /**
   * Retrieves the BigFraction value stored in a specified register.
   *
   * @param register the register ('a' to 'z') from which to retrieve the value.
   * @return the BigFraction value stored in the register.
   * @throws IllegalArgumentException if the register is not a letter between 'a' and 'z'.
   */
  public BigFraction get(char register) {
    if (register < 'a' || register > 'z') {
      throw new IllegalArgumentException("Register must be a letter from 'a' to 'z'.");
    } // End of if block
    return registers[register - 'a']; // Return the value stored in the corresponding register
  } // End of get method
} // End of BFRegisterSet class
