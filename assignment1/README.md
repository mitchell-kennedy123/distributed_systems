# Assignment 1 - Introduction to Java RMI

This project is a simple RMI (Remote Method Invocation) calculator service in Java. It allows clients to remotely perform basic arithmetic operations.

## Bonus marks

I have completed the bonus marks, see the `CalculatorImplementation.java` for implementation of the individual client stacks and step 5 of compilation for verification.

## Project Structure

- **Calculator.java**: The interface that defines the remote operations.
- **CalculatorImplementation.java**: The implementation class for the remote operations.
- **CalculatorServer.java**: The server class that registers the calculator service.
- **CalculatorClient.java**: A test client that connects to the server and tests its operations.

## Compilation Instructions

1. **Navigate to the project directory**:

 Open your terminal or command prompt and navigate to the directory where the Java files are located.

   ```bash
   cd path/to/your/project
   ```

2. **Step 2: Compile the Java Files**: 

 Compile all the Java files using the `javac` command.
  
   ```bash
   javac Calculator.java CalculatorImplementation.java CalculatorServer.java CalculatorClient.java 
   ```

3. **Run the Server**

Start the RMI server:
   ```bash
   java CalculatorServer
   ```
Ensure the server outputs `"Calculator Server is ready."` to confirm it has started successfully.

4. **Run a Client Test**

In a new terminal, run the client, where `clientID_1` is a command line prompt that sets the clientID:
   ```bash
   java CalculatorClient clientID_1
   ```

5. **Multiple Clients & Bonus Marks Testing**

To test the individual client stacks feature, run multiple clients concurrently with different client identifiers. This demonstrates that each client operates independently with its own stack:
   ```bash
   java CalculatorClient clientID_1 &
   java CalculatorClient clientID_2 &
   java CalculatorClient clientID_3 &
   java CalculatorClient clientID_4 &
   java CalculatorClient clientID_5 &
   ```


   





