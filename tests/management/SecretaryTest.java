package tests.management;

import gym.customers.*;
import gym.Exception.*;
import gym.management.*;
import gym.management.Sessions.*;
import gym.notification.NotificationObserver;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class SecretaryTest {
	private Secretary secretary;
	private Gym gym;
	private Person person;
	private Person clientPerson;
	private Person instructorPerson;

	@BeforeEach
	void setUp() {
		gym = Gym.getInstance();
		person = new Person("Secretary", 1000.0, Gender.Female, "01-01-1990");
		secretary = new Secretary(person, 3000, gym);
		clientPerson = new Person("Client", 2000.0, Gender.Male, "01-01-1990");
		instructorPerson = new Person("Instructor", 1500.0, Gender.Female, "01-01-1985");
	}

	@Test
	void testRegisterClient() throws InvalidAgeException, DuplicateClientException {
		Client client = secretary.registerClient(clientPerson);
		assertTrue(gym.getClients().contains(client));

		assertThrows(DuplicateClientException.class, () ->
				secretary.registerClient(clientPerson));
	}

	@Test
	void testRegisterUnderageClient() {
		Person underagePerson = new Person("Minor", 100.0, Gender.Male, "01-01-2010");
		assertThrows(InvalidAgeException.class, () ->
				secretary.registerClient(underagePerson));
	}

	@Test
	void testUnregisterClient() throws InvalidAgeException, DuplicateClientException, ClientNotRegisteredException {
		Client client = secretary.registerClient(clientPerson);
		secretary.unregisterClient(client);
		assertFalse(gym.getClients().contains(client));
	}

	@Test
	void testHireInstructor()  throws InvalidAgeException, DuplicateClientException{
		Instructor instructor = secretary.hireInstructor(instructorPerson, 50,
				Arrays.asList(SessionType.Pilates, SessionType.MachinePilates));
		assertTrue(gym.getInstructors().contains(instructor));
	}

	@Test
	void testAddSession() throws InstructorNotQualifiedException, InvalidAgeException, DuplicateClientException {
		Instructor instructor = secretary.hireInstructor(instructorPerson, 50,
				List.of(SessionType.Pilates));

		Session session = secretary.addSession(SessionType.Pilates, "31-12-2024 10:00",
				ForumType.All, instructor);
		assertTrue(gym.getSessions().contains(session));
	}

	@Test
	void testRegisterClientToLesson() throws Exception {
		Client client = secretary.registerClient(clientPerson);
		Instructor instructor = secretary.hireInstructor(instructorPerson, 50,
				List.of(SessionType.Pilates));
		Session session = secretary.addSession(SessionType.Pilates, "31-12-2024 10:00",
				ForumType.All, instructor);

		secretary.registerClientToLesson(client, session);
		assertTrue(session.hasClient(client));
	}

	@Test
	void testNotification() {
		TestObserver observer = new TestObserver();
		secretary.addObserver(observer);
		secretary.notifyObservers("Test message");
		assertEquals("Test message", observer.getLastMessage());
	}

	private static class TestObserver implements NotificationObserver {
		private String lastMessage;

		@Override
		public void update(String message) {
			this.lastMessage = message;
		}

		public String getLastMessage() {
			return lastMessage;
		}
	}
}