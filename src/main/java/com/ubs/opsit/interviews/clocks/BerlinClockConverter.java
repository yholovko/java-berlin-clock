package com.ubs.opsit.interviews.clocks;


import com.ubs.opsit.interviews.Time;
import com.ubs.opsit.interviews.TimeConverter;
import com.ubs.opsit.interviews.utils.BerlinClockTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A class that converts input time value in HH:mm:ss format to Berlin Clock format.
 *
 *
 * The Berlin Clock result is read from the top row to the bottom.
 * The top row of four red fields denote five full hours each, alongside the second row, also of four red fields,
 * which denote one full hour each, displaying the hour value in 24-hour format.
 * The third row consists of eleven yellow-and-red fields,
 * which denote five full minutes each (the red ones also denoting 15, 30 and 45 minutes past),
 * and the bottom row has another four yellow fields, which mark one full minute each.
 * The round yellow light on top blinks to denote even- (when lit) or odd-numbered (when unlit) seconds.
 */
public class BerlinClockConverter implements TimeConverter {

	private final static Logger log = LoggerFactory.getLogger(BerlinClockConverter.class);

	private static final char RED_LIGHT = 'R';

	private static final char YELLOW_LIGHT = 'Y';

	private static final char DISABLED_LIGHT = 'O';

	@Override
	public String convertTime(String aTime) throws IllegalArgumentException {
		log.info(String.format("Trying to convert '%s' to Berlin clock output", aTime));

		Time time = BerlinClockTimeUtils.parse(aTime);

		StringBuilder berlinClockOutput = new StringBuilder();
		berlinClockOutput.append(getBlinkerValue(time)).append("\n");
		berlinClockOutput.append(getTopHoursRow(time)).append("\n");
		berlinClockOutput.append(getLowerHoursRow(time)).append("\n");
		berlinClockOutput.append(getTopMinutesRow(time)).append("\n");
		berlinClockOutput.append(getLowerMinutesRow(time));

		return berlinClockOutput.toString();
	}

	private char getBlinkerValue(Time time) {
		return (time.getSeconds() % 2 == 0) ? YELLOW_LIGHT : DISABLED_LIGHT;
	}

	private String getTopHoursRow(Time time) {
		StringBuilder bigHoursRow = new StringBuilder();
		int numberOfRedLights = time.getHours() / 5;

		for (int i = 0; i < 4; i++) {
			bigHoursRow.append((i < numberOfRedLights) ? RED_LIGHT : DISABLED_LIGHT);
		}

		return bigHoursRow.toString();
	}

	private String getLowerHoursRow(Time time) {
		StringBuilder lowerHoursRow = new StringBuilder();
		int numberOfRedLights = time.getHours() % 5;

		for (int i = 0; i < 4; i++) {
			lowerHoursRow.append((i < numberOfRedLights) ? RED_LIGHT : DISABLED_LIGHT);
		}

		return lowerHoursRow.toString();
	}

	private String getTopMinutesRow(Time time) {
		StringBuilder topMinutesRow = new StringBuilder();
		int numberOfEnabledLights = time.getMinutes() / 5;

		for (int i = 0; i < 11; i++) {
			if (i < numberOfEnabledLights) {
				if ((i + 1) % 3 == 0) {
					topMinutesRow.append(RED_LIGHT);
				} else {
					topMinutesRow.append(YELLOW_LIGHT);
				}
			} else {
				topMinutesRow.append(DISABLED_LIGHT);
			}
		}

		return topMinutesRow.toString();
	}

	private String getLowerMinutesRow(Time time) {
		StringBuilder lowerMinutesRow = new StringBuilder();
		int numberOfYellowLights = time.getMinutes() % 5;

		for (int i = 0; i < 4; i++) {
			lowerMinutesRow.append((i < numberOfYellowLights) ? YELLOW_LIGHT : DISABLED_LIGHT);
		}

		return lowerMinutesRow.toString();
	}

}
