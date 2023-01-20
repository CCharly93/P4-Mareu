package fr.openclassrooms.mareu.ui.pickers.date;

import java.time.Instant;

import fr.openclassrooms.mareu.utils.DateEasy;

/**
 * Model for the date picker
 */
public class DatePickerModel implements DatePickerContract.Model {

    /**
     * When a date is saved, always convert to the same date but at the end of the day
     */
    private Boolean endOfDayMode;

    /**
     * Current date
     */
    private Instant mDate;

    /**
     * Min date
     */
    private Instant mMinDate;

    /**
     * Initial date
     */
    private Instant mInitialDate;

    /**
     * Listener to notify when the date has changed
     */
    private DatePickerContract.Model.OnDateChangedListener mOnDateChangedListener;

    /**
     * When a date is saved, always convert to the same date but at the end of the day
     * @param endOfDayMode true or false
     */
    @Override
    public void setEndOfDayMode(boolean endOfDayMode) {
        this.endOfDayMode = endOfDayMode;
    }

    /**
     * Set the min date
     * @param minDate min date
     */
    @Override
    public void setMinDate(Instant minDate) {
        mMinDate = minDate;
    }

    /**
     * Set the initial date
     * @param initialDate initial date
     */
    @Override
    public void setInitialDate(Instant initialDate) {
        mInitialDate = initialDate;
    }

    /**
     * Get the saved date
     * @return date
     */
    @Override
    public Instant getDate() {
        if(mDate == null)
            return mInitialDate;
        return mDate;
    }

    /**
     * Get the min date
     * @return min date
     */
    @Override
    public Instant getMinDate() {
        return mMinDate;
    }

    /**
     * Save the date from year, month, and day
     * @param year year
     * @param month month
     * @param day day
     */
    @Override
    public void saveDate(int year, int month, int day) {
        if (endOfDayMode) {
            mDate = DateEasy.endOfDay(DateEasy.computeInstantFromLocalDate(year, month, day));
        } else {
            mDate = DateEasy.computeInstantFromLocalDate(year, month, day);
        }
        mOnDateChangedListener.onDateChanged(mDate);
    }

    /**
     * Set the listener to notify when the date has changed
     * @param onDateChangedListener listener
     */
    @Override
    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        mOnDateChangedListener = onDateChangedListener;
    }
}
