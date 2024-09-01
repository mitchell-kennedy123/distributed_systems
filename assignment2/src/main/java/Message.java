/**
 * Note use of Java record.
 * 
 * This automatically creates the constructor and all the getters and setters for the elements in the class.
 * Very useful and saves both time and avoids silly mistakes.
 * 
 */

public record Message(int to, int from, int time, Payload payload)  {

}