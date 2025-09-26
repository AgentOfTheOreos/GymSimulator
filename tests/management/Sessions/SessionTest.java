package tests.management.Sessions;

import static org.junit.jupiter.api.Assertions.*;

import gym.management.Sessions.*;
import gym.management.Strategy.Validation.ForumValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gym.customers.*;
import gym.management.Instructor;

import java.util.ArrayList;
import java.util.Arrays;

public class SessionTest {
	private Session session;
	private Client client;

	@BeforeEach
	void setUp() {
		Person person = new Person("John Smith", 100.0, Gender.Male, "01-01-1990");
		client = new Client(person);
		Person personInstructor = new Person("Shachar", 300, Gender.Female, "09-04-1958");
		Instructor instructor = new Instructor(personInstructor, 70, new ArrayList<>(Arrays.asList(SessionType.ThaiBoxing, SessionType.MachinePilates)));
		session = new PilatesSession("21-12-2024 10:00", ForumType.All, instructor);
	}

	@Test
	void testAddClient() {
		session.addClient(client);
		assertTrue(session.hasClient(client));
		assertEquals(1, session.getClients().size());
	}

	@Test
	void testIsFull() {
		for (int i = 0; i < 30; i++) {
			Person person = new Person("Person" + i, 100.0, Gender.Male, "01-01-1990");
			Client client = new Client(person);
			session.addClient(client);
		}
		assertTrue(session.isFull());
	}

	@Test
	void testSeniorsForum() {
		Session seniorSession = new PilatesSession(
				"21-12-2024 10:00",
				ForumType.Seniors,
				new Instructor(new Person("Yuval", 650, Gender.Female, "23-05-1976")
						, 90, new ArrayList<>(
								Arrays.asList(SessionType.ThaiBoxing, SessionType.Pilates, SessionType.MachinePilates))
				)
		);

		Person youngPerson = new Person("Young", 100.0, Gender.Male, "01-01-1990");
		Client youngClient = new Client(youngPerson);
		assertFalse(ForumValidator.clientMatchesSeniority(seniorSession, youngClient));

		Person seniorPerson = new Person("Senior", 100.0, Gender.Male, "01-01-1950");
		Client seniorClient = new Client(seniorPerson);
		assertTrue(ForumValidator.clientMatchesSeniority(seniorSession, seniorClient));
	}

	@Test
	void testGenderRestriction() {
		Session maleSession = new PilatesSession(
				"21-12-2024 10:00",
				ForumType.Male,
				new Instructor(	new Person("Noam", 70, Gender.Male, "20-12-1984")
						,50, new ArrayList<>(Arrays.asList(SessionType.Pilates, SessionType.Ninja)))

		);

		Person femalePerson = new Person("Female", 100.0, Gender.Female, "01-01-1990");
		Client femaleClient = new Client(femalePerson);
		assertFalse(ForumValidator.clientMatchesGender(maleSession, femaleClient));

		Person malePerson = new Person("Male", 100.0, Gender.Male, "01-01-1990");
		Client maleClient = new Client(malePerson);
		assertFalse(ForumValidator.clientMatchesGender(maleSession, maleClient));
	}
}