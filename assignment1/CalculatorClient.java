import java.rmi.Naming;

public class CalculatorClient {
  /**
   * Main method to run the Calculator RMI client.
   * Connects to the Calculator RMI server and tests the stack operations.
   * 
   * @param args Use: ./Client <ClientID>
   */
  public static void main(String[] args) {
    try {
      // Check if the correct number of arguments is provided
      if (args.length < 1) {
        System.err.println("Error: ClientID is required as the first argument.");
        System.err.println("Usage: ./Client <ClientID>");
        System.exit(1); // Exit the program with a non-zero status code indicating an error
      }

      // Assign client identifier
      String clientId = args[0];

      // Lookup the remote object from the registry using the name "Calculator".
      Calculator calculator = (Calculator) Naming.lookup("CalculatorServer");

      // *Testing */

      // Test 1: Pushes and pops a single value.
      System.out.println("Client " + clientId + " - Test 1: pushValue and pop single value");
      calculator.pushValue(clientId, 10);
      int popResult1 = calculator.pop(clientId);
      System.out.println("Expected: 10, Got: " + popResult1 + ", Test " + (popResult1 == 10 ? "PASSED" : "FAILED"));

      // Test 2: Attempts to pop from an empty stack (expecting an exception).
      System.out.println("Client " + clientId + " - Test 2: pop from empty stack");
      boolean caughtException2 = false;
      try {
        calculator.pop(clientId);
      } catch (Exception e) {
        caughtException2 = true;
      }
      System.out.println("Expected: Exception, Got: " + (caughtException2 ? "Exception" : "No Exception") + ", Test "
          + (caughtException2 ? "PASSED" : "FAILED"));

      // Test 3: GCD of two non-prime numbers (12 and 18).
      System.out.println("Client " + clientId + " - Test 3: pushOperation with GCD of two numbers");
      calculator.pushValue(clientId, 12);
      calculator.pushValue(clientId, 18);
      calculator.pushOperation(clientId, "gcd");
      int gcdResult3 = calculator.pop(clientId);
      System.out.println("Expected: 6, Got: " + gcdResult3 + ", Test " + (gcdResult3 == 6 ? "PASSED" : "FAILED"));

      // Test 4: GCD of two prime numbers (13 and 7).
      System.out.println("Client " + clientId + " - Test 4: pushOperation with GCD of prime numbers");
      calculator.pushValue(clientId, 13);
      calculator.pushValue(clientId, 7);
      calculator.pushOperation(clientId, "gcd");
      int gcdResult4 = calculator.pop(clientId);
      System.out.println("Expected: 1, Got: " + gcdResult4 + ", Test " + (gcdResult4 == 1 ? "PASSED" : "FAILED"));

      // Test 5: LCM of three different numbers (4, 6, and 8).
      System.out.println("Client " + clientId + " - Test 5: pushOperation with LCM of multiple numbers");
      calculator.pushValue(clientId, 4);
      calculator.pushValue(clientId, 6);
      calculator.pushValue(clientId, 8);
      calculator.pushOperation(clientId, "lcm");
      int lcmResult5 = calculator.pop(clientId);
      System.out.println("Expected: 24, Got: " + lcmResult5 + ", Test " + (lcmResult5 == 24 ? "PASSED" : "FAILED"));

      // Test 6: LCM of the same number repeated (5 and 5).
      System.out.println("Client " + clientId + " - Test 6: pushOperation with LCM of same numbers");
      calculator.pushValue(clientId, 5);
      calculator.pushValue(clientId, 5);
      calculator.pushOperation(clientId, "lcm");
      int lcmResult6 = calculator.pop(clientId);
      System.out.println("Expected: 5, Got: " + lcmResult6 + ", Test " + (lcmResult6 == 5 ? "PASSED" : "FAILED"));

      // Test 7: Minimum of mixed values (positive and negative).
      System.out.println("Client " + clientId + " - Test 7: pushOperation with Min of mixed values");
      calculator.pushValue(clientId, 9);
      calculator.pushValue(clientId, -3);
      calculator.pushValue(clientId, 7);
      calculator.pushOperation(clientId, "min");
      int minResult7 = calculator.pop(clientId);
      System.out.println("Expected: -3, Got: " + minResult7 + ", Test " + (minResult7 == -3 ? "PASSED" : "FAILED"));

      // Test 8: Minimum of all positive values.
      System.out.println("Client " + clientId + " - Test 8: pushOperation with Min of all positive values");
      calculator.pushValue(clientId, 9);
      calculator.pushValue(clientId, 3);
      calculator.pushValue(clientId, 7);
      calculator.pushOperation(clientId, "min");
      int minResult8 = calculator.pop(clientId);
      System.out.println("Expected: 3, Got: " + minResult8 + ", Test " + (minResult8 == 3 ? "PASSED" : "FAILED"));

      // Test 9: Maximum of all negative values.
      System.out.println("Client " + clientId + " - Test 9: pushOperation with Max of all negative values");
      calculator.pushValue(clientId, -9);
      calculator.pushValue(clientId, -3);
      calculator.pushValue(clientId, -7);
      calculator.pushOperation(clientId, "max");
      int maxResult9 = calculator.pop(clientId);
      System.out.println("Expected: -3, Got: " + maxResult9 + ", Test " + (maxResult9 == -3 ? "PASSED" : "FAILED"));

      // Test 10: Maximum of mixed values (positive and negative).
      System.out.println("Client " + clientId + " - Test 10: pushOperation with Max of mixed values");
      calculator.pushValue(clientId, -9);
      calculator.pushValue(clientId, 3);
      calculator.pushValue(clientId, -7);
      calculator.pushOperation(clientId, "max");
      int maxResult10 = calculator.pop(clientId);
      System.out.println("Expected: 3, Got: " + maxResult10 + ", Test " + (maxResult10 == 3 ? "PASSED" : "FAILED"));

      // Test 11: Check if the stack is empty after all pops.
      System.out.println("Client " + clientId + " - Test 11: isEmpty on empty stack");
      boolean isEmptyResult11 = calculator.isEmpty(clientId);
      System.out
          .println("Expected: true, Got: " + isEmptyResult11 + ", Test " + (isEmptyResult11 ? "PASSED" : "FAILED"));

      // Test 12: Check if the stack is not empty after pushing a value.
      System.out.println("Client " + clientId + " - Test 12: isEmpty on non-empty stack");
      calculator.pushValue(clientId, 5);
      boolean isEmptyResult12 = calculator.isEmpty(clientId);
      System.out
          .println("Expected: false, Got: " + isEmptyResult12 + ", Test " + (!isEmptyResult12 ? "PASSED" : "FAILED"));

      // Test 13: DelayPop with a delay of 2 seconds and pop the value.
      System.out.println("Client " + clientId + " - Test 13: delayPop with delay of 2 seconds");
      calculator.pushValue(clientId, 20);
      int delayPopResult13 = calculator.delayPop(clientId, 2000);
      System.out.println(
          "Expected: 20, Got: " + delayPopResult13 + ", Test " + (delayPopResult13 == 20 ? "PASSED" : "FAILED"));

      // Test 14: DelayPop with a delay of 0 seconds and pop the value immediately.
      System.out.println("Client " + clientId + " - Test 14: delayPop with delay of 0 seconds");
      calculator.pushValue(clientId, 15);
      int delayPopResult14 = calculator.delayPop(clientId, 0);
      System.out.println(
          "Expected: 15, Got: " + delayPopResult14 + ", Test " + (delayPopResult14 == 15 ? "PASSED" : "FAILED"));

    } catch (Exception e) {
      System.err.println("Calculator Client exception: " + e.toString());
      e.printStackTrace(); // Prints the stack trace for debugging.
    }
  }
}
