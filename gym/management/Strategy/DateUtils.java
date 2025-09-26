package gym.management.Strategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The {@code DateUtils} class provides utility methods for handling and validating dates.
 *
 * <p>This class includes functionality for calculating age, checking if a date is in the future,
 * and formatting dates into specific output formats. All date-related operations assume that
 * dates are in specific formats as outlined in each method's documentation.</p>
 *
 * <p>Usage Example:</p>
 * <pre>
 * int age = DateUtils.getAge("15-04-1990");
 * boolean isFuture = DateUtils.isDateInFuture("25-12-2025 15:00");
 * String formattedDate = DateUtils.formatDateTimeForOutput("25-12-2025 15:00");
 * </pre>
 */
public class DateUtils {
	private static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	/**
	 * Calculates and returns the age of a person based on their birthdate.
	 *
	 * <p>The method parses the {@code birthDate} string, which must be in the format "dd-MM-yyyy",
	 * and computes the age by finding the difference in years between the birthdate and a fixed
	 * reference date (21st December 2024).</p>
	 *
	 * @param birthDate the birthdate of the person in "dd-MM-yyyy" format.
	 * @return the age of the person as an integer.
	 * @throws DateTimeParseException if the {@code birthDate} is not in the expected format.
	 */
	public static int getAge(String birthDate) {
		LocalDate birth = LocalDate.parse(birthDate, DATE_ONLY_FORMATTER);
		LocalDate now = LocalDate.of(2024, 12, 21);
		return Period.between(birth, now).getYears();
	}


	/**
	 * Checks if a given date and time string represents a date in the future.
	 *
	 * <p>The method parses the {@code dateTime} string, which must be in the format "dd-MM-yyyy HH:mm",
	 * and compares it to a fixed reference date and time (30th December 2024, 00:00).
	 * If the parsed date is after the reference date, it returns {@code true}, otherwise {@code false}.
	 * If the input string is not in the expected format, the method will return {@code false}.</p>
	 *
	 * @param dateTime the date and time string to validate in "dd-MM-yyyy HH:mm" format.
	 * @return {@code true} if the input date is in the future, {@code false} otherwise.
	 */
	public static boolean isDateInFuture(String dateTime) {
		try {
			LocalDateTime inputDate = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
			LocalDateTime referenceDate = LocalDateTime.of(2024, 12, 30, 0, 0);
			return inputDate.isAfter(referenceDate);
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	/**
	 * Attempts to parse the given input string as "dd-MM-yyyy HH:mm" first;
	 * if that fails, tries to parse it as "dd-MM-yyyy" instead.
	 *
	 * <p>If parsing as date-time is successful, it returns an ISO 8601 style date-time
	 * (yyyy-MM-dd'T'HH:mm). If it's only a date (no time), it returns a date
	 * in yyyy-MM-dd format. Else, throw a DateTimeParseException</p>
	 *
	 * @param input the date/datetime string (either "dd-MM-yyyy" or "dd-MM-yyyy HH:mm")
	 * @return a formatted string in either "yyyy-MM-dd'T'HH:mm" or "yyyy-MM-dd"
	 * @throws DateTimeParseException if the input does not match either pattern
	 */
	public static String formatDate(String input) {
		try {
			LocalDateTime dateTime = LocalDateTime.parse(input, DATE_TIME_FORMATTER);
			return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
		} catch (DateTimeParseException e) {
			LocalDate date = LocalDate.parse(input, DATE_ONLY_FORMATTER);
			return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
	}

}