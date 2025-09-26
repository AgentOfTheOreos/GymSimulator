package gym.management.Sessions;

import gym.management.Instructor;

public class MachinePilatesSession extends  Session{
	public MachinePilatesSession(String dateTime, ForumType forum, Instructor instructor) {
		super(SessionType.MachinePilates, dateTime, forum, instructor);
	}

}
