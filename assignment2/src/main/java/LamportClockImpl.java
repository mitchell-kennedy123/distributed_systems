public class LamportClockImpl implements LamportClock {
  private int currentTime;

  public LamportClockImpl() {
    this.currentTime = 0; // Initialize the clock at time 0
  }

  @Override
  public int processEvent(int messageTimestamp) {
    // Assuming the message contains a timestamp to compare with the local clock
    currentTime = Math.max(currentTime, messageTimestamp) + 1;
    return currentTime;
  }

  @Override
  public int processEvent() {
    // For internal events not related to message passing
    currentTime += 1;
    return currentTime;
  }

  @Override
  public int getTime() {
    return currentTime;
  }

  @Override
  public void synchroniseClock(int timestamp) {
    currentTime = timestamp;
  }
}