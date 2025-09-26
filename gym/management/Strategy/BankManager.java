package gym.management.Strategy;

import java.util.*;

/**
 * The {@code BankManager} class provides functionality to manage financial accounts
 * for individuals (e.g., clients, staff) and institutions (e.g., gyms).
 *
 * <p>This class follows a singleton pattern, ensuring there is only one instance
 * of the {@code BankManager} that manages all financial transactions. It allows
 * creating accounts, performing deposits and withdrawals, checking balances, and
 * validating payments.</p>
 *
 * <p>Usage Example:</p>
 * <pre>
 * BankManager bankManager = BankManager.getInstance();
 * bankManager.createAccount(personId, initialBalance);
 * bankManager.deposit(personId, 500.0);
 * double balance = bankManager.getBalance(personId);
 * </pre>
 */
public class BankManager {
	private static final Random rand = new Random();
	private final Set<Integer> usedIds = new HashSet<>();
	private static BankManager instance = null;
	private final Map<Integer, Double> accountBalances;

	/**
	 * Private constructor to enforce the singleton pattern.
	 * Initializes the internal accountBalances map.
	 */
	private BankManager() {
		accountBalances = new HashMap<>();
	}

	/**
	 * Retrieves the singleton instance of the {@code BankManager}.
	 *
	 * @return the singleton instance of the {@code BankManager}.
	 */
	public static BankManager getInstance() {
		if (instance == null) {
			instance = new BankManager();
		}
		return instance;
	}

	/**
	 * Creates a new account for the given person ID with an initial balance.
	 *
	 * @param personId       the unique ID of the person.
	 * @param accountBalance the initial balance of the account.
	 */
	public void createAccount(int personId, double accountBalance) {
		accountBalances.put(personId, accountBalance);
	}

	/**
	 * Deposits a specified amount into the account associated with the given person ID.
	 *
	 * @param personId the unique ID of the person.
	 * @param amount   the amount to deposit (must be positive).
	 */
	public void deposit(int personId, double amount) {
		accountBalances.put(personId, accountBalances.get(personId) + amount);
	}

	/**
	 * Withdraws a specified amount from the account associated with the given person ID.
	 *
	 * @param personId the unique ID of the person.
	 * @param amount   the amount to withdraw (must be positive and not exceed the current balance).
	 */
	public void withdraw(int personId, double amount) {
		accountBalances.put(personId, accountBalances.get(personId) - amount);
	}

	/**
	 * Retrieves the balance of the account associated with the given person ID.
	 *
	 * @param personId the unique ID of the person.
	 * @return the current balance of the account.
	 */
	public double getBalance(int personId) {
		return accountBalances.get(personId);
	}

	/**
	 * Generates a unique ID that can be used for creating new accounts.
	 * The generated ID is guaranteed to be unique across all existing accounts.
	 *
	 * @return a unique integer ID.
	 */
	public int uniqueIdGenerator() {
		int uniqueId;

		do {
			uniqueId = rand.nextInt(1000, 9999);
		} while (usedIds.contains(uniqueId));

		usedIds.add(uniqueId);
		return uniqueId;
	}

	/**
	 * Validates whether a payment of the specified amount can be made from the account
	 * associated with the given person ID.
	 *
	 * @param personId the unique ID of the person.
	 * @param amount   the amount to validate.
	 * @return {@code true} if the account has sufficient funds, {@code false} otherwise.
	 */
	public boolean isValidPayment(int personId, double amount) {
		return getBalance(personId) >= amount;
	}
}