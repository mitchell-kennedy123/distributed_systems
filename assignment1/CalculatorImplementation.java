import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
  // ** Bonus Marks **
  // Map to associate each client with their own stack.
  private Map<String, Stack<Integer>> clientStacks;

  /**
   * Constructor for the CalculatorImplementation class.
   * Initialises the clientStacks map and prepares the RMI
   */
  protected CalculatorImplementation() throws RemoteException {
    super(); // Calls the superclass constructor to prepare RMI
    clientStacks = new HashMap<>(); // Initializes the map to store stacks for each client.
  }

  /**
   * Retrieves the stack for a specific client or creates
   * a new stack if the client does not have one yet.
   * 
   * @param clientId Identifier for the client
   * @return The clients private stack
   */
  private synchronized Stack<Integer> getClientStack(String clientId) {
    return clientStacks.computeIfAbsent(clientId, k -> new Stack<>());
  }

  /**
   * Pushes an integer value onto the client's stack.
   * 
   * @param clientId Identifier for the client
   * @param val      The integer value to be pushed onto the stack.
   */
  @Override
  public synchronized void pushValue(String clientId, int val) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId);
    stack.push(val);
  }

  /**
   * Performs an operation on the client's stack by popping all values, and
   * applying
   * the operator, then pushes the result back onto the stack.
   * 
   * @param clientId Identifier for the client
   * @param operator String for the operation
   */
  @Override
  public synchronized void pushOperation(String clientId, String operator) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId);

    // Checks if the stack is empty
    if (stack.isEmpty()) {
      throw new IllegalStateException("Stack is empty, cannot perform operation.");
    }

    int result = 0;
    // Converts the operator to lowercase for more robustness
    switch (operator.toLowerCase()) {
      case "min":
        result = min(stack);
        break;
      case "max":
        result = max(stack);
        break;
      case "lcm":
        result = lcm(stack);
        break;
      case "gcd":
        result = gcd(stack);
        break;
      default:
        throw new IllegalArgumentException("Invalid operation: " + operator);
    }

    stack.clear();
    stack.push(result);
  }

  /**
   * Pops the top value from the client's stack and returns it.
   * 
   * @param clientId Identifier for the client
   * @return Value from the top of the stack
   */
  @Override
  public synchronized int pop(String clientId) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId);

    if (stack.isEmpty()) {
      throw new IllegalStateException("Stack is empty, cannot pop.");
    }
    return stack.pop();
  }

  /**
   * Checks whether the client's stack is empty.
   * 
   * @param clientId Identifier for the client
   * @return true if the client's stack is empty, false otherwise.
   */
  @Override
  public boolean isEmpty(String clientId) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId);
    return stack.isEmpty();
  }

  /**
   * Delays the pop operation on the client's stack
   * 
   * @param clientId Identifier for the client
   * @param millis   Milliseconds to wait before performing the pop
   * @return Value from the top of the stack
   */
  @Override
  public int delayPop(String clientId, int millis) throws RemoteException {
    try {
      Thread.sleep(millis); // Causes the current thread to sleep for millis
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RemoteException("Thread interrupted", e);
    }
    return pop(clientId); // Pops the value after the delay
  }

  // Helper functions that perform the maths on the stack

  private int min(Stack<Integer> stack) {
    int minValue = stack.peek();
    for (int value : stack) {
      if (value < minValue) {
        minValue = value;
      }
    }
    return minValue;
  }

  private int max(Stack<Integer> stack) {
    int maxValue = stack.peek();
    for (int value : stack) {
      if (value > maxValue) {
        maxValue = value;
      }
    }
    return maxValue;
  }

  private int gcd(Stack<Integer> stack) {
    if (stack.size() < 2) {
      throw new IllegalArgumentException("Stack must contain at least two elements to calculate GCD.");
    }

    int result = stack.get(0);
    for (int i = 1; i < stack.size(); i++) {
      result = gcd(result, stack.get(i));
    }
    return result;
  }

  private int lcm(Stack<Integer> stack) {
    if (stack.size() < 2) {
      throw new IllegalArgumentException("Stack must contain at least two elements to calculate LCM.");
    }

    int result = stack.get(0);
    for (int i = 1; i < stack.size(); i++) {
      result = lcm(result, stack.get(i));
    }
    return result;
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int t = b;
      b = a % b;
      a = t;
    }
    return a;
  }

  private int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
  }
}
