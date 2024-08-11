import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

  // Map to associate each client with their own stack.
  private Map<String, Stack<Integer>> clientStacks;

  /**
   * Constructor for the CalculatorImplementation class.
   * Initializes the clientStacks map and prepares the object for remote access.
   * 
   * @throws RemoteException if there is a communication-related issue during the
   *                         remote method call.
   */
  protected CalculatorImplementation() throws RemoteException {
    super(); // Calls the superclass constructor, necessary for remote objects.
    clientStacks = new HashMap<>(); // Initializes the map to store stacks for each client.
  }

  /**
   * Retrieves the stack for a specific client. If the client does not have a
   * stack yet,
   * a new stack is created and associated with the client.
   * 
   * @param clientId The unique identifier for the client.
   * @return The stack associated with the client.
   */
  private synchronized Stack<Integer> getClientStack(String clientId) {
    return clientStacks.computeIfAbsent(clientId, k -> new Stack<>());
  }

  /**
   * Pushes an integer value onto the client's stack.
   * 
   * @param clientId The unique identifier for the client.
   * @param val      The integer value to be pushed onto the stack.
   * @throws RemoteException if there is a communication-related issue during the
   *                         remote method call.
   */
  @Override
  public synchronized void pushValue(String clientId, int val) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId); // Retrieves the client's stack.
    stack.push(val); // Adds the provided integer value to the top of the stack.
  }

  /**
   * Performs an operation on the client's stack by popping all values, applying
   * the specified operator,
   * and pushing the result back onto the stack.
   * 
   * @param clientId The unique identifier for the client.
   * @param operator A string representing the operation ("min", "max", "lcm",
   *                 "gcd").
   * @throws RemoteException          if there is a communication-related issue
   *                                  during the remote method call.
   * @throws IllegalStateException    if the client's stack is empty and no
   *                                  operation can be performed.
   * @throws IllegalArgumentException if an invalid operator is provided.
   */
  @Override
  public synchronized void pushOperation(String clientId, String operator) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId); // Retrieves the client's stack.

    System.out.println("Client: " + clientId + ", Operator: " + operator);
    System.out.println("Stack: ");
    System.out.println(stack);

    if (stack.isEmpty()) { // Checks if the stack is empty before proceeding.
      throw new IllegalStateException("Stack is empty, cannot perform operation.");
    }

    int result = 0;
    switch (operator.toLowerCase()) { // Converts the operator to lowercase for consistency.
      case "min":
        result = stack.stream().min(Integer::compare).orElseThrow();
        break;
      case "max":
        result = stack.stream().max(Integer::compare).orElseThrow();
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

    stack.clear(); // Clears the stack after the operation is performed.
    stack.push(result); // Pushes the result of the operation onto the stack.
    System.out.println(stack);
  }

  /**
   * Pops the top value from the client's stack and returns it.
   * 
   * @param clientId The unique identifier for the client.
   * @return The integer value that was popped from the stack.
   * @throws RemoteException       if there is a communication-related issue
   *                               during the remote method call.
   * @throws IllegalStateException if the client's stack is empty and no value can
   *                               be popped.
   */
  @Override
  public synchronized int pop(String clientId) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId); // Retrieves the client's stack.

    if (stack.isEmpty()) {
      throw new IllegalStateException("Stack is empty, cannot pop.");
    }
    return stack.pop(); // Pops the top value off the stack.
  }

  /**
   * Checks whether the client's stack is empty.
   * 
   * @param clientId The unique identifier for the client.
   * @return true if the client's stack is empty, false otherwise.
   * @throws RemoteException if there is a communication-related issue during the
   *                         remote method call.
   */
  @Override
  public boolean isEmpty(String clientId) throws RemoteException {
    Stack<Integer> stack = getClientStack(clientId); // Retrieves the client's stack.
    return stack.isEmpty();
  }

  /**
   * Delays the pop operation on the client's stack by a specified number of
   * milliseconds before performing the pop.
   * 
   * @param clientId The unique identifier for the client.
   * @param millis   The number of milliseconds to wait before performing the pop
   *                 operation.
   * @return The integer value that was popped from the stack after the delay.
   * @throws RemoteException      if there is a communication-related issue during
   *                              the remote method call.
   * @throws InterruptedException if the thread performing the delay is
   *                              interrupted.
   */
  @Override
  public int delayPop(String clientId, int millis) throws RemoteException {
    try {
      Thread.sleep(millis); // Causes the current thread to sleep (pause execution) for the specified time.
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Restores the interrupted status of the thread.
      throw new RemoteException("Thread interrupted", e);
    }
    return pop(clientId); // Pops the value after the delay.
  }

  private int gcd(Stack<Integer> stack) {
    return stack.stream().reduce(this::gcd).orElseThrow();
  }

  private int lcm(Stack<Integer> stack) {
    return stack.stream().reduce(1, this::lcm);
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
