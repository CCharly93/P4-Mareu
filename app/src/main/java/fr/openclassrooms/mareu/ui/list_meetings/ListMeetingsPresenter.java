package fr.openclassrooms.mareu.ui.list_meetings;

import static com.google.common.base.Preconditions.checkNotNull;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.util.List;

import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.utils.DateEasy;

public class ListMeetingsPresenter implements ListMeetingsContract.Presenter {

    private static final String TAG = "MeetingsListPresenter";

    /**
     * The view to be updated
     */
    private final ListMeetingsContract.View mView;

    /**
     * The model to be used
     */
    private final ListMeetingsContract.Model mModel;

    /**
     * Buffer the filtered and sorted meetings list
     */
    private List<Meeting> mFilteredAndSortedMeetings;

    /**
     * Constructor
     * @param view the view to be updated
     * @param model the model to be used
     */
    public ListMeetingsPresenter(@NonNull ListMeetingsContract.View view,
                                 @NonNull ListMeetingsContract.Model model) {
        mView = checkNotNull(view);
        mModel = checkNotNull(model);
        // important : immediately attach the presenter in the view
        mView.attachPresenter(this);
    }

    /**
     * On view initialized, update the view with the meetings list
     */
    @Override
    public void init() {
        onRefreshMeetingsListRequested();
    }

    /**
     * On refresh meetings list request, update the view with the meetings list
     * Depending on the filters set, the list may be empty
     */
    @Override
    public void onRefreshMeetingsListRequested() {
        // get the meetings list, either filtered or not
        List<Meeting> meetings = mModel.getFilteredAndSortedMeetings();
        // buffer the list, in order to ease the deletion of a meeting
        mFilteredAndSortedMeetings = meetings;
        // update the view with the fresh meetings list
        mView.updateMeetings(meetings);
        // update the view with the up to date filters
        mView.updateFilters(
                mModel.getFilterRoom(),
                DateEasy.localeDateTimeStringFromInstant(mModel.getFilterStartDate()),
                DateEasy.localeDateTimeStringFromInstant(mModel.getFilterEndDate())
        );
    }

    /**
     * Registration of a new meeting is requested by the view
     */
    @Override
    public void onCreateMeetingRequested() {
        // so we trigger the meeting registration dialog
        mView.triggerMeetingRegistrationDialog();
    }

    /**
     * Drop a meeting is requested by the view
     * @param position the position of the meeting in the list
     */
    @Override
    public void dropMeetingRequested(int position) {
        // drop the meeting
        mModel.deleteMeeting(mFilteredAndSortedMeetings.get(position));
        // refresh the meetings list
        onRefreshMeetingsListRequested();
    }

    /**
     * Called when the user has changed the filters
     * @param filterRoom the place filter
     * @param filterStartDate the start date filter
     * @param filterEndDate the end date filter
     */
    @Override
    public void onFiltersChanged(String filterRoom, String filterStartDate, String filterEndDate){
        // set error to false
        boolean isError = false;

        // is the start date is empty
        if (filterStartDate.isEmpty()) {
            // set the start date to null
            mModel.setFilterStartDate(null);
        } else {
            // try to parse the start date
            Instant tmp = DateEasy.parseDateStringToInstant(filterStartDate);
            if (tmp == null) {
                // if the start date is not valid, set the error flag to true
                isError = true;
                mView.setErrorFilterStartDate();
            } else {
                // if the start date is valid, set the filter
                mModel.setFilterStartDate(tmp);
            }
        }

        // is the end date is empty
        if (filterEndDate.isEmpty()) {
            // set the end date to null
            mModel.setFilterEndDate(null);
        } else {
            // try to parse the end date
            Instant tmp = DateEasy.parseDateStringToInstant(filterEndDate);
            // if the end date is not valid, set the error flag to true
            if (tmp == null) {
                // if the end date is not valid, set the error flag to true
                isError = true;
                mView.setErrorFilterEndDate();
            } else {
                // if the end date is valid, set the filter
                mModel.setFilterEndDate(tmp);
            }
        }
        // if there is no error
        mModel.setFilterRoom(filterRoom);
        // refresh the meetings list
        onRefreshMeetingsListRequested();
        // expand or collapse the filters
        if (!isError) {
            mView.expandOrCollapseFilters();
        }
    }

    /**
     * Set filter on start date
     * @param filterStartDate the start date filter
     */
    @Override
    public void setFilterStartDate(String filterStartDate) {
        if (filterStartDate.isEmpty()) {
            mModel.setFilterStartDate(null);
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterStartDate);
            if (tmp != null) {
                mModel.setFilterStartDate(tmp);
            }
        }
        mView.triggerDatePickerDialog(mModel.getFilterStartDate(), true);
    }

    /**
     * Set filter on start date with manual mode (do not trigger the date picker at the end)
     * @param filterStartDate the start date filter
     */
    @Override
    public void setFilterStartDateManual(String filterStartDate) {
        if (filterStartDate.isEmpty()) {
            mModel.setFilterStartDate(null);
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterStartDate);
            if (tmp != null) {
                mModel.setFilterStartDate(tmp);
            } else {
                mView.setErrorFilterStartDate();
            }
        }
        onRefreshMeetingsListRequested();
    }

    /**
     * Set filter on end date
     * @param filterEndDate the end date filter
     */
    @Override
    public void setFilterEndDate(String filterEndDate) {
        if (filterEndDate.isEmpty()) {
            mModel.setFilterEndDate(null);
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterEndDate);
            if (tmp != null) {
                mModel.setFilterEndDate(tmp);
            }
        }
        mView.triggerDatePickerDialog(mModel.getFilterEndDate(), false);
    }

    /**
     * Set filter on end date with manual mode (do not trigger the date picker at the end)
     * @param filterEndDate the end date filter
     */
    @Override
    public void setFilterEndDateManual(String filterEndDate) {
        if (filterEndDate.isEmpty()) {
            mModel.setFilterEndDate(null);
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterEndDate);
            if (tmp != null) {
                mModel.setFilterEndDate(tmp);
            } else {
                mView.setErrorFilterEndDate();
            }
        }
        onRefreshMeetingsListRequested();
    }

    /**
     * Save date after picking it from the date picker
     * @param date the date filter
     * @param beginOrEnd true if the filter is on the start date, false if it is on the end date
     */
    @Override
    public void saveFilterDate(Instant date, boolean beginOrEnd) {
        if (beginOrEnd) {
            mModel.setFilterStartDate(date);
        } else {
            mModel.setFilterEndDate(date);
        }
        onRefreshMeetingsListRequested();
    }

    /**
     * Save filter on place
     * @param filterRoom the place filter
     */
    @Override
    public void saveFilterRoom(String filterRoom) {
        mModel.setFilterRoom(filterRoom);
        onRefreshMeetingsListRequested();
    }
}
