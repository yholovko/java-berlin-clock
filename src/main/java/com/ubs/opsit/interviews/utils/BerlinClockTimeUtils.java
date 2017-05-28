package com.ubs.opsit.interviews.utils;


import com.ubs.opsit.interviews.Time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A suite of utilities that help to work with Berlin clock
 */
public class BerlinClockTimeUtils {

	private static final Pattern pattern;

	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]";

	static {
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
	}

	/**
	 * Validate time in 24 hours format with regular expression
	 * Applicable range for hours is [0-24]
	 * Applicable range for minutes and seconds is [0-60)
	 *
	 * @param timeToValidate - time in 24 hours format for validation
	 * @return 'true' in case time format is valid, 'false' in case time format is invalid
	 */
	private static boolean isValidTime(String timeToValidate) {
		Matcher matcher = pattern.matcher(timeToValidate);
		if (matcher.matches()) {
			// according to a test-scenario, 24:00:00 is a valid time.
			// this block is necessary to prevent a case where 24:00:01 could be a valid time too
			try {
				Time time = convertToTime(timeToValidate);
				if (time.getHours() == 24 && (time.getMinutes() > 0 || time.getSeconds() > 0)) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * A method converts String to Time object
	 *
	 * @param time - input String in HH:mm:ss format
	 * @return Time object initialized with the hour, minute, second values
	 */
	private static Time convertToTime(String time) throws IllegalArgumentException {
		String[] parsedTime = time.split(":");

		if (parsedTime.length != 3) {
			throw new IllegalArgumentException("Incorrect format: " + time + ". The correct format is HH:mm:ss");
		}

		int hours = Integer.valueOf(parsedTime[0].trim());
		int minutes = Integer.valueOf(parsedTime[1].trim());
		int seconds = Integer.valueOf(parsedTime[2].trim());

		return new Time(hours, minutes, seconds);
	}

	/**
	 * Parses text from the beginning of the given String to produce a Time object.
	 *
	 * @param time - time in 24 hours format
	 * @return Time object initialized with the hour, minute, second values
	 * @throws IllegalArgumentException in case time format is invalid or does not fit HH:mm:ss format
	 */
	public static Time parse(String time) throws IllegalArgumentException {
		if (!isValidTime(time)) {
			throw new IllegalArgumentException(
					String.format("Incorrect time value: '%s'. The correct format is HH:mm:ss. Make sure that a value for hours is in [0-24] range, for minutes and seconds is in [0-60) range", time)
			);
		}

		return convertToTime(time);
	}

}
