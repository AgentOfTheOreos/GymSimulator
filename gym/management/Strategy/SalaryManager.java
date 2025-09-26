package gym.management.Strategy;

import gym.customers.Person;
import gym.management.Gym;
import gym.management.Instructor;
import gym.management.Secretary;

import java.util.List;

public class SalaryManager {

	/**
	 * Calculates the total pay for the instructor based on their hourly rate and the number of sessions conducted.
	 *
	 * <p>The method multiplies the instructor's hourly rate by the number of sessions conducted,
	 * providing the total pay. This method does not reset the session count, leaving that responsibility
	 * to the caller or the Instructor class itself.</p>
	 *
	 * @param instructor the instructor whose salary is to be calculated.
	 * @return the total pay for the instructor as a {@code double}.
	 * @throws NullPointerException if the instructor is null.
	 */
	public static double calculatePay(Instructor instructor) {
		if (instructor == null) {
			throw new NullPointerException("Instructor cannot be null");
		}

		return instructor.getHourlyRate() * instructor.getSessionCount();
	}

	/**
	 * Pays salaries to the secretary and all instructors.
	 *
	 * @param gym the gym instance to manage financial transactions.
	 * @param staff the list of staff members (secretary and instructors) to pay.
	 * @return true if all payments were successful, false otherwise.
	 */
	public static boolean paySalaries(Gym gym, List<Person> staff) {
		boolean paid = true;
		for (Person employee : staff) {
			if (employee instanceof Secretary secretary) {
				int secretarySalary = secretary.getSalary();
				gym.withdraw(secretarySalary);
				secretary.deposit(secretarySalary);
			} else if (employee instanceof Instructor instructor) {
				double instructorSalary = calculatePay(instructor);
				gym.withdraw(instructorSalary);
				instructor.deposit(instructorSalary);
			} else {
				paid = false;
			}
		}
		return paid;
	}

}