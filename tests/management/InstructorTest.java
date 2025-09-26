package tests.management;

import gym.customers.*;
import gym.management.Instructor;
import gym.management.Sessions.SessionType;
import gym.management.Strategy.SalaryManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class InstructorTest {
	private Instructor instructor;
	private Person person;
	private List<SessionType> qualifications;

	@BeforeEach
	void setUp() {
		person = new Person("John Doe", 1000.0, Gender.Male, "01-01-1990");
		qualifications = Arrays.asList(SessionType.Pilates, SessionType.MachinePilates);
		instructor = new Instructor(person, 50, qualifications);
	}

	@Test
	void testIsQualified() {
		assertTrue(instructor.isQualified(SessionType.Pilates));
		assertFalse(instructor.isQualified(SessionType.ThaiBoxing));
	}

	@Test
	void testCalculatePay() {
		instructor.addSession();
		instructor.addSession();
		assertEquals(100, SalaryManager.calculatePay(instructor));
		assertEquals(0, SalaryManager.calculatePay(instructor));
	}

	@Test
	void testGetters() {
		assertEquals("John Doe", instructor.getName());
		assertEquals(50, instructor.getHourlyRate());
		assertEquals(qualifications, instructor.getQualifications());
	}
}