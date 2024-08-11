# Assignment 1 - Introduction to Java RMI

This project is a simple RMI (Remote Method Invocation) calculator service in Java. It allows clients to remotely perform basic arithmetic operations like addition, subtraction, multiplication, and division.

## Bonus marks

I have completed the bonus marks, see the `calculator class` for implementation.

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

3. **Run the Server:**

Run:
   ```bash
   java CalculatorServer:
   ```

4. **In another terminal run the Client:**

Run:
   ```bash
   java CalculatorServer
   ```

## Testing


   





