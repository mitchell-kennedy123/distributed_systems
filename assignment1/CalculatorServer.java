import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class CalculatorServer {
  /**
   * Main method to start the Calculator RMI server.
   */
  public static void main(String[] args) {
    try {
      // Create the registry on port 1099
      LocateRegistry.createRegistry(1099);

      // Creates and instance of the Calculator that can be invoked remotely
      Calculator stub = new CalculatorImplementation();

      // Bind the remote object (stub) to a name
      Naming.rebind("CalculatorServer", stub);

      System.out.println("Calculator Server is ready.");

    } catch (Exception e) {
      System.err.println("Calculator Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
