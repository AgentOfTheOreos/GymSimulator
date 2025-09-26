package gym.customers;

import gym.notification.NotificationObserver;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person implements NotificationObserver {
	private final List<String> notifications;

	public Client(Person person) {
		super(person);
		this.notifications = new ArrayList<>();
	}

	public Client(String name, double Balance, Gender gender, String birthDate) {
		super(name, Balance, gender, birthDate);
		this.notifications = new ArrayList<>();
	}

	@Override
	public void update(String message) {
		notifications.add(message);
	}

	public void addNotification(String message) {
		notifications.add(message);
	}

	public List<String> getNotifications() {
		return new ArrayList<>(notifications);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Client other = (Client) obj;

		return super.equals(other);
	}

	@Override
	public int hashCode() {
		return super.hashCode() * 31 + Integer.hashCode(getId());
	}
}