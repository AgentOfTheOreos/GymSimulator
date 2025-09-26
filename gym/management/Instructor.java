package gym.management;

import gym.customers.Gender;
import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person{
	private final int hourlyRate;
	private final List<SessionType> qualifications;
	private int sessionCount;

	public Instructor(Person person, int hourlyRate, List<SessionType> qualifications) {
		super(person);
		this.hourlyRate = hourlyRate;
		this.qualifications = new ArrayList<>(qualifications);
		this.sessionCount = 0;
	}

	public Instructor(String name, double Balance, Gender gender, String birthDate, int hourlyRate, List<SessionType> qualifications) {
		super(name, Balance, gender, birthDate);
		this.hourlyRate = hourlyRate;
		this.qualifications = new ArrayList<>(qualifications);
		this.sessionCount = 0;
	}

	public boolean isQualified(SessionType type) {
		return qualifications.contains(type);
	}

	public void addSession() {
		sessionCount++;
	}

	public int getHourlyRate() {
		return hourlyRate;
	}

	public int getSessionCount() { return sessionCount; }

	public List<SessionType> getQualifications() {
		return qualifications;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Instructor other = (Instructor) obj;

		return super.equals(other) && hourlyRate == other.hourlyRate && sessionCount == other.sessionCount;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * 31 + Integer.hashCode(getId());
	}
}