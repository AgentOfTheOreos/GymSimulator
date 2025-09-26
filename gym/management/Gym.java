package gym.management;

import gym.customers.*;
import gym.management.Sessions.*;
import gym.management.Strategy.BankManager;
import gym.management.Strategy.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class Gym {
	private static Gym instance = null;
	private String name;
	private Secretary secretary;
	private final BankManager bankManager;
	private final List<Client> clients;
	private final List<Instructor> instructors;
	private final List<Session> sessions;
	private final List<String> actionHistory;
	private final int id;

	public Gym() {
		this.clients = new ArrayList<>();
		this.instructors = new ArrayList<>();
		this.sessions = new ArrayList<>();
		this.actionHistory = new ArrayList<>();
		this.bankManager = BankManager.getInstance();
		id = bankManager.uniqueIdGenerator();
		bankManager.createAccount(id, 0);
	}

	/**
	 * Retrieves the singleton instance of the Gym. If no instance exists, a new one is created.
	 *
	 * @return the singleton {@code Gym} instance.
	 */
	public static Gym getInstance() {
		if (instance == null) {
			instance = new Gym();
		}
		return instance;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Appoints a new secretary for the gym. Deactivates the previous secretary if one exists.
	 *
	 * @param person the person to be appointed as secretary.
	 * @param salary the monthly salary of the secretary.
	 */
	public void setSecretary(Person person, int salary) {
		if (this.secretary != null) {
			this.secretary.deactivate();  // Deactivate the previous secretary
		}
		this.secretary = new Secretary(person, salary, null);
		actionHistory.add(String.format("A new secretary has started working at the gym: %s", person.getName()).strip());
	}

	public Secretary getSecretary() {
		return this.secretary;
	}

	void addClient(Client client) {
		clients.add(client);
	}

	void removeClient(Client client) {
		clients.remove(client);
	}

	void addInstructor(Instructor instructor) {
		instructors.add(instructor);
	}

	void addSession(Session session) {
		sessions.add(session);
	}

	public void deposit(double amount) {
		bankManager.deposit(id, amount);
	}

	public void withdraw(double amount) {
		bankManager.withdraw(id, amount);
	}

	public void addToHistory(String action) {
		actionHistory.add(action);
	}

	public List<String> getActionHistory() {
		return new ArrayList<>(actionHistory);
	}

	public ArrayList<Client> getClients() {
		return new ArrayList<>(clients);
	}

	public List<Instructor> getInstructors() {
		return new ArrayList<>(instructors);
	}

	public List<Session> getSessions() {
		return new ArrayList<>(sessions);
	}

	/**
	 * Provides a string representation of the gym's current state, including details about
	 * its name, balance, secretary, clients, instructors, and sessions.
	 *
	 * @return a formatted string representation of the gym.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Gym basic info
		sb.append(String.format("Gym Name: %s\n", name));
		if (secretary != null) {
			sb.append(String.format("Gym Secretary: ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %.0f | Role: Secretary | Salary per Month: %d\n",
					secretary.getId(), secretary.getName(), secretary.getGender(), secretary.getBirthDate(),
					DateUtils.getAge(secretary.getBirthDate()), secretary.getBalance(), secretary.getSalary()));
		}
		sb.append(String.format("Gym Balance: %.0f\n\n", bankManager.getBalance(id)));

		// Clients data
		sb.append("Clients Data:\n");
		for (Client client : clients) {
			sb.append(String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %.0f\n",
					client.getId(), client.getName(), client.getGender(), client.getBirthDate(),
					DateUtils.getAge(client.getBirthDate()),
					client.getBalance()));
		}
		sb.append("\n");

		// Employees data
		sb.append("Employees Data:\n");
		for (Instructor instructor : instructors) {
			sb.append(String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %.0f | Role: Instructor | Salary per Hour: %d | Certified Classes: %s\n",
					instructor.getId(), instructor.getName(), instructor.getGender(), instructor.getBirthDate(),
					DateUtils.getAge(instructor.getBirthDate()), instructor.getBalance(), instructor.getHourlyRate(),
					String.join(", ", instructor.getQualifications().stream().map(SessionType::toString).toList())));
		}
		if (secretary != null) {
			sb.append(String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %.0f | Role: Secretary | Salary per Month: %d\n",
					secretary.getId(), secretary.getName(), secretary.getGender(), secretary.getBirthDate(),
					DateUtils.getAge(secretary.getBirthDate()), secretary.getBalance(), secretary.getSalary()));
		}
		sb.append("\n");

		// Sessions data
		sb.append("Sessions Data:\n");
		for (Session session : sessions) {
			sb.append(String.format("Session Type: %s | Date: %s | Forum: %s | Instructor: %s | Participants: %d/%d\n",
					session.getType(), session.getDateTime(), session.getForum(), session.getInstructor().getName(),
					session.getClients().size(), session.getType().getCapacity()));
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}
}