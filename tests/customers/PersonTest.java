package tests.customers;

import gym.customers.*;
import gym.management.Strategy.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
	private Person person;

	@BeforeEach
	void setUp() {
		person = new Client("John Doe", 100.0, Gender.Male, "01-01-1990");
	}

	@Test
	void testGetAge() {
		assertEquals(34, DateUtils.getAge(person.getBirthDate()));
	}

	@Test
	void testGetAgeForRecentBirthday() {
		Person recentBirthday = new Client("Jane Doe", 100.0, Gender.Female, "20-12-1995");
		assertEquals(29, DateUtils.getAge(recentBirthday.getBirthDate()));
	}

	@Test
	void testGetAgeForUpcomingBirthday() {
		Person upcomingBirthday = new Client("Jim Doe", 100.0, Gender.Male, "22-12-1990");
		assertEquals(33, DateUtils.getAge(upcomingBirthday.getBirthDate()));
	}

	@Test
	void testGetAgeForNewborn() {
		Person newborn = new Client("Baby Doe", 0.0, Gender.Female, "21-12-2024");
		assertEquals(0, DateUtils.getAge(newborn.getBirthDate()));
	}
}