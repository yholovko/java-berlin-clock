package com.ubs.opsit.interviews;

public interface TimeConverter {
	/**
	 * Converts time passed as a String to a specific clock type
	 *
	 * @param aTime time value
	 * @return converted time to a specific clock type
	 * @throws IllegalArgumentException in case input time value is incorrect
	 */
	String convertTime(String aTime) throws IllegalArgumentException;

}
