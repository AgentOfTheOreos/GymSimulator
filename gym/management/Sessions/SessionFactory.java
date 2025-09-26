package gym.management.Sessions;

import gym.Exception.InstructorNotQualifiedException;
import gym.management.Instructor;

/**
 * A factory class responsible for creating {@link Session} objects.
 *
 * <p>The {@code SessionFactory} ensures that the provided instructor is
 * qualified to conduct the specified session type before creating the session.
 * If the instructor is not qualified, an exception is thrown.</p>
 */
public class SessionFactory {

	/**
	 * Creates a new {@link Session} with the specified type, date/time, forum, and instructor.
	 *
	 * <p>Before creating the session, this method verifies that the instructor
	 * is qualified to conduct the specified session type. If the instructor is
	 * not qualified, an {@link InstructorNotQualifiedException} is thrown.</p>
	 *
	 * @param type      the type of session to be created (e.g., Pilates, MachinePilates).
	 * @param dateTime  the date and time of the session as a string.
	 * @param forum     the forum type of the session (e.g., Seniors, Male, Female).
	 * @param instructor the instructor assigned to conduct the session.
	 * @return a new {@link Session} object initialized with the provided parameters.
	 * @throws InstructorNotQualifiedException if the instructor is not qualified
	 *                                         to conduct the specified session type.
	 */
	public static Session createSession(SessionType type, String dateTime, ForumType forum, Instructor instructor)
			throws InstructorNotQualifiedException {
		if (!instructor.isQualified(type)) {
			throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
		}

		return switch (type) {
			case Pilates -> new PilatesSession(dateTime, forum, instructor);
			case MachinePilates -> new MachinePilatesSession(dateTime, forum, instructor);
			case ThaiBoxing -> new ThaiBoxingSession(dateTime, forum, instructor);
			case Ninja -> new NinjaSession(dateTime, forum, instructor);
			//noinspection UnnecessaryDefault
			default -> throw new IllegalArgumentException("Unsupported session type: " + type);
		};
	}
}