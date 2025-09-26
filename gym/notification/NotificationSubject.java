package gym.notification;

// Subject interface
public interface NotificationSubject {
	void addObserver(NotificationObserver observer);
	void removeObserver(NotificationObserver observer);
	void notifyObservers(String message);
}