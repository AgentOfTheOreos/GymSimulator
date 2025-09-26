package tests.management;

import gym.Exception.*;
import gym.customers.*;
import gym.management.*;
import gym.management.Sessions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GymTest {
	private Gym gym;
	private Person personClient;
	private Person personInstructor;
	private Person personSecretary;
	private Client client;
	private Instructor instructor;

	@BeforeEach
	void setUp() {
		gym = new Gym();
		personClient = new Person("John Client", 1000.0, Gender.Male, "01-01-1990");
		personInstructor = new Person("Jane Instructor", 2000.0, Gender.Female, "01-01-1985");
		personSecretary = new Person("Sam Secretary", 1500.0, Gender.Male, "01-01-1988");
		gym.setSecretary(personSecretary, 5000);
		client = new Client(personClient);
		instructor = new Instructor(personInstructor, 1500, new ArrayList<>(
				Arrays.asList(SessionType.Pilates, SessionType.Ninja)));
	}

	@Test
	void testSetSecretary() {
		assertNotNull(gym.getSecretary());
		assertEquals(personSecretary.getName(), gym.getSecretary().getName());

		Person newSecretary = new Person("New Secretary", 1500.0, Gender.Female, "01-01-1989");
		gym.setSecretary(newSecretary, 3500);
		assertEquals(newSecretary.getName(), gym.getSecretary().getName());
	}

	@Test
	void testClientManagement() {
		try {
			gym.getSecretary().registerClient(client);
			assertTrue(gym.getClients().contains(client));
			gym.getSecretary().unregisterClient(client);
			assertFalse(gym.getClients().contains(client));
		} catch (ClientNotRegisteredException | InvalidAgeException | DuplicateClientException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testInstructorManagement() throws  InvalidAgeException, DuplicateClientException{
		gym.getSecretary().hireInstructor(instructor, 1500,new ArrayList<>(
				Arrays.asList(SessionType.Pilates, SessionType.Ninja)));
		assertTrue(gym.getInstructors().contains(instructor));
	}

	@Test
	void testSessionManagement() {
		Session session = new PilatesSession("21-12-2024 10:00", ForumType.All, instructor);
		try {
			gym.getSecretary().addSession(session);
			assertTrue(gym.getSessions().contains(session));
		} catch (InstructorNotQualifiedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testBalanceOperations() {
		double initialBalance = 0;
		gym.deposit(1000);
		assertEquals(initialBalance + 1000, Double.parseDouble(gym.toString().split("Gym Balance: ")[1].split("\n")[0]));

		gym.withdraw(500);
		assertEquals(initialBalance + 500, Double.parseDouble(gym.toString().split("Gym Balance: ")[1].split("\n")[0]));
	}

	@Test
	void testActionHistory() {
		String testAction = "Test action";
		gym.addToHistory(testAction);
		assertTrue(gym.getActionHistory().contains(testAction));
	}
}