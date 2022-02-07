public interface Subject
{
    public void RegisterObserver (Observer observer);
    public void RemoveObsever (Observer observer);
    public void NotifyObservers () throws InterruptedException;
}