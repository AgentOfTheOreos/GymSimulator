package gym.management.Strategy.Validation;

import gym.customers.Client;
import gym.customers.Gender;
import gym.management.Sessions.*;
import gym.management.Strategy.DateUtils;

public class ForumValidator {
	/**
	 * Checks if a client matches the seniority requirement of the session's forum.
	 *
	 * <p>If the session's forum is for seniors, this method ensures the client's
	 * age is 65 or older. For other forums, no age restriction is applied.</p>
	 *
	 * @param client the client to be checked for seniority compatibility.
	 * @return {@code true} if the client meets the seniority requirements, {@code false} otherwise.
	 */
	public static boolean clientMatchesSeniority(Session session, Client client) {
		return !(session.getForum() == ForumType.Seniors && DateUtils.getAge(client.getBirthDate()) < 65);
	}

	/**
	 * Checks if a client matches the gender requirement of the session's forum.
	 *
	 * <p>If the session is restricted to a specific gender, this method verifies
	 * the client's gender matches the forum's restriction. For forums without gender
	 * restrictions, the method always returns {@code true}.</p>
	 *
	 * @param client the client to be checked for gender compatibility.
	 * @return {@code true} if the client meets the gender requirements, {@code false} otherwise.
	 */
	public static boolean clientMatchesGender(Session session, Client client) {
		return !((session.getForum() == ForumType.Male && client.getGender() == Gender.Female) ||
				(session.getForum() == ForumType.Female && client.getGender() == Gender.Male));
	}
}