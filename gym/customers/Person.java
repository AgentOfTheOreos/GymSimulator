package gym.customers;

import gym.management.Strategy.BankManager;

public class Person {
	private static int nextID = 1111;
	private final int id;
	private final BankManager bankManager;
	private final String name;
	private final Gender gender;
	private final String birthDate;

	public Person(String name, double balance, Gender gender, String birthDate) {
		this.id = nextID++;
		this.name = name;
		bankManager = BankManager.getInstance();
		bankManager.createAccount(id, balance);
		this.gender = gender;
		this.birthDate = birthDate;
	}

	public Person(Person person) {
		this.id = person.getId();
		this.name = person.getName();
		bankManager = BankManager.getInstance();
		this.gender = person.getGender();
		this.birthDate = person.getBirthDate();
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return bankManager.getBalance(id);
	}

	public void deposit(double amount) {
			bankManager.deposit(id, amount);
	}

	public void withdraw(double amount) {
			bankManager.withdraw(id, amount);
	}

	public Gender getGender() {
		return gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Person other = (Person) obj;
		return id == other.id && name.equals(other.name) && gender == other.gender && birthDate.equals(other.birthDate) &&
				Double.compare(getBalance(), other.getBalance()) == 0;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
}