import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CalculatorServer {
  /**
   * Main method to start the Calculator RMI server.
   * Sets up the RMI registry, creates the implementation instance, and binds it
   * to a name.
   * 
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    try {
      // Create the registry on port 1099 (default RMI port).
      LocateRegistry.createRegistry(1099);

      // Create the implementation object that will handle the remote method calls.
      Calculator stub = new CalculatorImplementation();

      // Bind the remote object's stub (reference) in the registry with the name
      // "CalculatorService".
      Naming.rebind("rmi://localhost/CalculatorService", stub);

      System.out.println("Calculator Server is ready.");

    } catch (Exception e) {
      System.err.println("Calculator Server exception: " + e.toString());
      e.printStackTrace(); // Prints the stack trace for debugging.
    }
  }
}
