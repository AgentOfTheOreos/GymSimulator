package gym.management;

import gym.Exception.*;
import gym.management.Sessions.*;
import gym.customers.*;
import gym.management.Strategy.BankManager;
import gym.management.Strategy.DateUtils;
import gym.management.Strategy.SalaryManager;
import gym.management.Strategy.Validation.*;
import gym.notification.*;

import java.util.*;

/**
 * Represents a secretary responsible for managing administrative tasks in the gym,
 * including client and instructor management, session coordination, notifications,
 * and salary payments.
 *
 * <p>The {@code Secretary} class includes methods for maintaining gym operations,
 * ensuring adherence to business rules, and keeping track of gym activities.</p>
 */
public class Secretary extends Person implements NotificationSubject {
	public static final int LEGAL_AGE = 18;

	private final List<NotificationObserver> observers = new ArrayList<>();
	private final int salary;
	private final Gym gym;
	private boolean isActive;
	private final BankManager bankManager;
	private final ValidationRuleSet<RegistrationContext> validationRuleSet;

	// I allow the constructor to accept a gym object, solely for the tests I have made in the "tests" folder,
	// apart from that, I recognize that typically a full singleton approach would be implemented here
	// rather than a semi-singleton approach.
	public Secretary(Person person, int salary, Gym gym) {
		super(person);
		this.salary = salary;
		if (gym == null) {
			this.gym = Gym.getInstance(); // Singleton
		} else {
			this.gym = gym;
		}
		this.isActive = true;
		this.bankManager = BankManager.getInstance();
		validationRuleSet = new ValidationRuleSet<>();
		implementGymRegistrationRules();
	}

	private void implementGymRegistrationRules() {
		validationRuleSet.addRule(
				context -> !context.getSession().isFull(),
				"No available spots for session"
		);

		validationRuleSet.addRule(
				context -> DateUtils.isDateInFuture(context.getSession().getDateTime()),
				"Session is not in the future"
		);

		validationRuleSet.addRule(
				context -> ForumValidator.clientMatchesSeniority(context.getSession(), context.getClient()),
				"Client doesn't meet the age requirements for this session (Seniors)"
		);

		validationRuleSet.addRule(
				context -> ForumValidator.clientMatchesGender(context.getSession(), context.getClient()),
				"Client's gender doesn't match the session's gender requirements"
		);

		validationRuleSet.addRule(
				context -> bankManager.isValidPayment(context.getClient().getId(), context.getSession().getType().getPrice()),
				"Client doesn't have enough balance"
		);
	}

	@Override
	public void addObserver(NotificationObserver observer) {
		checkActive();
		observers.add(observer);
	}

	@Override
	public void removeObserver(NotificationObserver observer) {
		checkActive();
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String message) {
		checkActive();
		for (NotificationObserver observer : observers) {
			observer.update(message);
		}
	}

	private void checkActive() {
		if (!isActive) {
			throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
		}
	}

	void deactivate() {
		isActive = false;
	}

	/**
	 * Registers a new client to the gym if they meet the eligibility criteria.
	 *
	 * @param person the person to register as a client.
	 * @return the newly registered {@code Client} object.
	 * @throws InvalidAgeException        if the client is under 18 years old.
	 * @throws DuplicateClientException   if the client is already registered.
	 */
	public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
		checkActive();

		Client client = new Client(person);

		if (DateUtils.getAge(client.getBirthDate()) < LEGAL_AGE) {
			throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
		}

		if (gym.getClients().contains(client)) {
			throw new DuplicateClientException("Error: The client is already registered");
		}

		gym.addClient(client);
		observers.add(client);
		gym.addToHistory(String.format("Registered new client: %s", person.getName()));
		return client;
	}

	/**
	 * Unregisters a client from the gym.
	 *
	 * @param client the client to be unregistered.
	 * @throws ClientNotRegisteredException if the client is not registered.
	 */
	public void unregisterClient(Client client) throws ClientNotRegisteredException {
		checkActive();

		if (!gym.getClients().contains(client)) {
			throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
		}

		gym.removeClient(client);
		removeObserver(client);
		gym.addToHistory(String.format("Unregistered client: %s", client.getName()));
	}

	/**
	 * Hires a new instructor and adds them to the gym.
	 *
	 * @param person         the person to hire as an instructor.
	 * @param hourlyRate     the hourly rate of the instructor.
	 * @param qualifications the list of session types the instructor is qualified for.
	 * @return the newly hired {@code Instructor}.
	 */
	public Instructor hireInstructor(Person person, int hourlyRate, List<SessionType> qualifications)
			throws  InvalidAgeException, DuplicateClientException {
		checkActive();

		if (DateUtils.getAge(person.getBirthDate()) < LEGAL_AGE) {
			throw new InvalidAgeException("Error: Instructor must be at least 18 years old to register");
		}

		for (Instructor instructor : gym.getInstructors()) {
			if (instructor.equals(person)) {
				throw new DuplicateClientException("Error: The Instructor is already registered");
			}
		}

		Instructor instructor = new Instructor(person, hourlyRate, qualifications);
		gym.addInstructor(instructor);
		gym.addToHistory(String.format("Hired new instructor: %s with salary per hour: %d", person.getName(), hourlyRate));
		return instructor;
	}

	/**
	 * Creates and adds a session to the gym if the instructor is qualified.
	 *
	 * @param type       the type of the session.
	 * @param datetime   the date and time of the session in "dd-MM-yyyy HH:mm" format.
	 * @param forum      the forum type for the session.
	 * @param instructor the instructor conducting the session.
	 * @return the created {@code Session}.
	 * @throws InstructorNotQualifiedException if the instructor is not qualified for the session type.
	 */
	public Session addSession(SessionType type, String datetime, ForumType forum, Instructor instructor)
			throws InstructorNotQualifiedException {
		checkActive();

		Session session = SessionFactory.createSession(type, datetime, forum, instructor);
		if(gym.getSessions().contains(session)) {
			System.out.println("Error: Cannot add duplicate session");
			return null;
		}
		gym.addSession(session);
		gym.addToHistory(String.format("Created new session: %s on %s with instructor: %s",
				type, DateUtils.formatDate(datetime), instructor.getName()));
		return session;
	}

	/**
	 * Adds an existing session to the gym.
	 *
	 * @param session the session to be added.
	 */
	public void addSession(Session session)
		throws InstructorNotQualifiedException {
			checkActive();
			gym.addSession(session);
			gym.addToHistory(String.format("Created new session: %s on %s with instructor: %s",
					session.getType(), DateUtils.formatDate(session.getDateTime()), session.getInstructor().getName()));
	}

	/**
	 * Registers a client to a session if they meet all eligibility criteria and the session is valid.
	 *
	 * @param client  the client to register.
	 * @param session the session to register the client in.
	 * @throws ClientNotRegisteredException if the client is not registered with the gym.
	 * @throws DuplicateClientException     if the client is already registered for the session.
	 */
	public void registerClientToLesson(Client client, Session session)
			throws ClientNotRegisteredException, DuplicateClientException {
		checkActive();

		RegistrationContext context = new RegistrationContext(client, session);
		double sessionCost = session.getType().getPrice();

		if (!gym.getClients().contains(client)) {
			throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
		}

		if (session.hasClient(client)) {
			throw new DuplicateClientException("Error: The client is already registered for this lesson");
		}

		if (!validationRuleSet.validateAll(context)) {
			for (String message : validationRuleSet.getFailureMessages()) {
				gym.addToHistory(String.format("Failed registration: %s", message));
			}
			return;
		}

		client.withdraw(sessionCost);
		gym.deposit(sessionCost);
		session.addClient(client);

		gym.addToHistory(String.format(
				"Registered client: %s to session: %s on %s for price: %d",
				client.getName(), session.getType(), DateUtils.formatDate(session.getDateTime()), (int)sessionCost
		));
	}



	/**
	 * Notifies all clients registered for a specific session with a message.
	 *
	 * @param session the session for which clients will be notified.
	 * @param message the message to notify clients about.
	 */
	public void notify(Session session, String message) {
		checkActive();
		for (Client client : session.getClients()) {
			client.addNotification(message);
		}
		gym.addToHistory(String.format("A message was sent to everyone registered for session %s on %s : %s",
				session.getType(), DateUtils.formatDate(session.getDateTime()), message));
	}

	/**
	 * Notifies all clients registered for any session on a specific date with a message.
	 *
	 * @param date    the date in "dd-MM-yyyy" format.
	 * @param message the message to notify clients about.
	 */
	public void notify(String date, String message) {
		checkActive();
		boolean messageSent = false;
		for (Session session : gym.getSessions()) {
			if (session.getDateTime().startsWith(date)) {
				for (Client client : session.getClients()) {
					client.addNotification(message);
				}
				messageSent = true;
			}
		}
		if (messageSent) {
			gym.addToHistory(String.format("A message was sent to everyone registered for a session on %s : %s",
					DateUtils.formatDate(date), message));
		}
	}

	/**
	 * Sends a notification to all registered gym clients.
	 *
	 * @param message the message to send.
	 */
	public void notify(String message) {
		checkActive();
		notifyObservers(message);
		gym.addToHistory(String.format("A message was sent to all gym clients: %s", message));
	}

	/**
	 * Pays salaries to the secretary and all instructors.
	 */
	public void paySalaries() {
		checkActive();
		List<Person> staff = new ArrayList<>();
		staff.add(this);
		staff.addAll(gym.getInstructors());

		if (SalaryManager.paySalaries(gym, staff)) {
			gym.addToHistory("Salaries have been paid to all employees");
		} else {
			gym.addToHistory("Failed to pay salaries to all employees");
		}
	}

	/**
	 * Prints the gym's action history to the console.
	 */
	public void printActions() {
		checkActive();
		for (String action : gym.getActionHistory()) {
			System.out.println(action);
		}
	}

	public int getSalary() {
		return salary;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Secretary other = (Secretary) obj;

		return super.equals(other) && salary == other.salary;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * 31 + Integer.hashCode(getId());
	}
}