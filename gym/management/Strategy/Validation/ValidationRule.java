package gym.management.Strategy.Validation;

/**
 * Functional interface representing a validation rule for a generic input type.
 *
 * <p>This interface defines a single method, {@code validate}, that can be implemented
 * to provide validation logic for any type of input. It is annotated with
 * {@link FunctionalInterface}, meaning it is intended to be implemented with a single
 * abstract method, making it compatible with lambda expressions and method references.</p>
 *
 * @param <T> the type of the input to be validated
 */
@FunctionalInterface
public interface ValidationRule<T> {
	/**
	 * Validates the given input based on specific criteria.
	 *
	 * <p>The implementation of this method should define the logic for validating the input.
	 * It should return {@code true} if the input satisfies the validation criteria,
	 * or {@code false} otherwise.</p>
	 *
	 * @param input the input to be validated
	 * @return {@code true} if the input is valid, {@code false} otherwise
	 */
	boolean validate(T input);
}