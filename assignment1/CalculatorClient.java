import java.rmi.Naming;

public class CalculatorClient {
  /**
   * Main method to run the Calculator RMI client.
   * Connects to the Calculator RMI server and tests the stack operations for a
   * specific client.
   * 
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    try {
      // Lookup the remote object from the registry using the name
      // "CalculatorService".
      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");

      String clientId = args[0]; // Unique identifier for this client

      // Test the stack operations by pushing values and performing operations.
      calculator.pushValue(clientId, 12);
      calculator.pushValue(clientId, 18);
      calculator.pushOperation(clientId, "gcd"); // Calculates the GCD of 12 and 18.

      System.out.println("Result after GCD operation: " + calculator.pop(clientId));

      calculator.pushValue(clientId, 4);
      calculator.pushValue(clientId, 6);
      calculator.pushValue(clientId, 8);
      calculator.pushOperation(clientId, "lcm"); // Calculates the LCM of 4, 6, and 8.

      System.out.println("Result after LCM operation: " + calculator.pop(clientId));

      System.out.println("Stack is empty: " + calculator.isEmpty(clientId));

      calculator.pushValue(clientId, 20);
      System.out.println("Popped value after delay: " + calculator.delayPop(clientId, 2000)); // 2 second delay

    } catch (Exception e) {
      System.err.println("Calculator Client exception: " + e.toString());
      e.printStackTrace(); // Prints the stack trace for debugging.
    }
  }
}
