package gym.management.Sessions;

import gym.customers.*;
import gym.management.Instructor;

import java.util.*;

public abstract class Session {
	private final SessionType type;
	private final String dateTime;
	private final ForumType forum;
	private final Instructor instructor;
	private final List<Client> clients;

	public Session(SessionType type, String dateTime, ForumType forum, Instructor instructor) {
		this.type = type;
		this.dateTime = dateTime;
		this.forum = forum;
		this.instructor = instructor;
		this.clients = new ArrayList<>();
		instructor.addSession();
	}

	public SessionType getType() {
		return type;
	}

	public String getDateTime() {
		return dateTime;
	}

	public boolean isFull() {
		return clients.size() >= type.getCapacity();
	}

	public boolean hasClient(Client client) {
		return clients.contains(client);
	}

	public void addClient(Client client) {
		if (!isFull()) {
			clients.add(client);
		}
	}

	public List<Client> getClients() {
		return new ArrayList<>(clients);
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public ForumType getForum() {
		return forum;
	}
}