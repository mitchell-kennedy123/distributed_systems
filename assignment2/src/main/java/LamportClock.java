public interface  LamportClock {
	/**
	 * Maintain the internal Lamport Clock when a message is received
	 * 
	 * @param message a received Message
	 * @return the current Lamport Clock Value
	 */
	public int processEvent(int messageTimestamp);
	
	/**
	 * Update the internal Lamport Clock for events other than message receive
	 * 
	 * @return the current Lamport Clock value
	 */
	public int processEvent();
	
	/**
	 * Get the current Lamport Time
	 * 
	 * @return the current Lamport clock value 
	 */
	public int getTime();

  /**
   * Synchronizes the clock to a specified time
    */
  public void synchroniseClock(int timestamp);

}