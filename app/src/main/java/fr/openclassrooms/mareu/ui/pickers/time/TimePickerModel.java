package fr.openclassrooms.mareu.ui.pickers.time;

import java.time.Instant;

import fr.openclassrooms.mareu.utils.DateEasy;

/**
 * Model for the time picker
 */
public class TimePickerModel implements TimePickerContract.Model {

    /**
     * Current date time
     */
    private Instant mDateTime;

    /**
     * Initial date time
     */
    private Instant mInitialDateTime;

    /**
     * Listener to notify when the time has changed
     */
    private TimePickerContract.Model.OnTimeChangedListener mOnTimeChangedListener;

    /**
     * Set the initial date and time, thanks to Java 8's Instant
     * @param initialTime the initial date and time
     */
    @Override
    public void setInitialDateTime(Instant initialTime) {
        mInitialDateTime = initialTime;
    }

    /**
     * Get the current date and time
     * @return the current date and time
     */
    @Override
    public Instant getDateTime() {
        if(mDateTime == null)
            return mInitialDateTime;
        return mDateTime;
    }

    /**
     * Save the current date and time, by merging the picked time with the initial date
     * @param hour the picked hour
     * @param minute the picked minute
     */
    @Override
    public void saveTime(int hour, int minute) {
        mDateTime = DateEasy.mergeInstantAndLocalZonedTime(mInitialDateTime, hour, minute);
        mOnTimeChangedListener.onTimeChanged(mDateTime);
    }

    /**
     * Notify the listener when the time has changed
     * @param onTimeChangedListener the listener to notify when the time has changed
     */
    @Override
    public void setOnTimeChangedListener(TimePickerContract.Model.OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

}
