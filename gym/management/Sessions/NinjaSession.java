package gym.management.Sessions;

import gym.management.Instructor;

public class NinjaSession extends Session {
	public NinjaSession(String dateTime, ForumType forum, Instructor instructor) {
		super(SessionType.Ninja, dateTime, forum, instructor);
	}

}
