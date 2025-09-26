package gym.management.Strategy.Validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing a set of validation rules, each associated with a message,
 * that can be applied to an input.
 *
 * <p>The {@code ValidationRuleSet} class allows adding multiple validation rules
 * with messages, validating an input against all rules, and retrieving the
 * failure messages for any validations that fail.</p>
 *
 * @param <T> the type of the input to be validated
 */
public class ValidationRuleSet<T> implements Iterable<ValidationPayload<T>> {
	/**
	 * The list of validation rules and their associated messages.
	 */
	private final List<ValidationPayload<T>> rules = new ArrayList<>();
	/**
	 * The list of failure messages for validations that did not pass.
	 */
	private final List<String> failureMessages = new ArrayList<>();

	/**
	 * Adds a validation rule with its associated message to the rule set.
	 *
	 * @param rule the validation rule to be added
	 * @param message the message to be associated with the rule
	 */
	public void addRule(ValidationRule<T> rule, String message) {
		rules.add(new ValidationPayload<>(rule, message));
	}

	/**
	 * Validates the given input against all rules in the set.
	 *
	 * <p>If any validation rule fails, its associated message is added to the
	 * failure messages list. The method clears the failure messages list before
	 * performing validations.</p>
	 *
	 * @param input the input to be validated
	 * @return {@code true} if all validation rules pass, {@code false} otherwise
	 */
	public boolean validateAll(T input) {
		failureMessages.clear();
		boolean allValid = true;

		for (ValidationPayload<T> ruleWithMessage : this) { // Using Iterable
			if (!ruleWithMessage.validate(input)) {
				allValid = false;
				failureMessages.add(ruleWithMessage.getMessage());
			}
		}
		return allValid;
	}

	public List<String> getFailureMessages() {
		return new ArrayList<>(failureMessages);
	}

	/**
	 * Returns an iterator over the validation rules in the set.
	 *
	 * @return an iterator over the {@link ValidationPayload} instances
	 */
	@Override
	public Iterator<ValidationPayload<T>> iterator() {
		return rules.iterator();
	}
}
