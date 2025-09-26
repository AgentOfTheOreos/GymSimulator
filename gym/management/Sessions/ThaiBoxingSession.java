package gym.management.Sessions;

import gym.management.Instructor;

public class ThaiBoxingSession extends Session {
	public ThaiBoxingSession(String dateTime, ForumType forum, Instructor instructor) {
		super(SessionType.ThaiBoxing, dateTime, forum, instructor);
	}
}
