package gym.management.Sessions;

import gym.management.Instructor;

public class PilatesSession extends Session {
	public PilatesSession(String dateTime, ForumType forum, Instructor instructor) {
		super(SessionType.Pilates, dateTime, forum, instructor);
	}
}
