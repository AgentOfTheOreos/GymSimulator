package gym.management.Strategy.Validation;

/**
 * A class representing a validation payload that encapsulates a validation rule
 * and a corresponding message.
 *
 * <p>This class is designed to hold a {@link ValidationRule} and an associated
 * message that can be used to describe the validation outcome. It provides a
 * method to validate an input using the rule and retrieve the message.</p>
 *
 * @param <T> the type of the input to be validated
 */
public class ValidationPayload<T> {
	private final ValidationRule<T> rule;
	private final String message;


	public ValidationPayload(ValidationRule<T> rule, String message) {
		this.rule = rule;
		this.message = message;
	}

	/**
	 * Validates the given input using the encapsulated validation rule.
	 *
	 * @param input the input to be validated
	 * @return {@code true} if the input satisfies the validation rule, {@code false} otherwise
	 */
	public boolean validate(T input) {
		return rule.validate(input);
	}

	public String getMessage() {
		return message;
	}
}