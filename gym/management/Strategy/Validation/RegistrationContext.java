package gym.management.Strategy.Validation;

import gym.customers.Client;
import gym.management.Sessions.Session;

public class RegistrationContext {
	private final Client client;
	private final Session session;

	public RegistrationContext(Client client, Session session) {
		this.client = client;
		this.session = session;
	}

	public Client getClient() {
		return client;
	}

	public Session getSession() {
		return session;
	}
}