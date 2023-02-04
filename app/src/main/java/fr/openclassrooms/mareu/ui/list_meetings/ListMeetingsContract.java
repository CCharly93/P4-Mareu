package fr.openclassrooms.mareu.ui.list_meetings;

import java.time.Instant;
import java.util.List;

import fr.openclassrooms.mareu.core.SimpleMvp;
import fr.openclassrooms.mareu.model.Meeting;

public class ListMeetingsContract {

    interface Model extends SimpleMvp.Model {

        // get the meetings list, regarding the current filters set
        List<Meeting> getFilteredAndSortedMeetings();

        // get the filter place text
        String getFilterRoom();

        // get the filter start date
        Instant getFilterStartDate();

        // get the filter end date
        Instant getFilterEndDate();

        // drop a meeting by its position
        void deleteMeeting(Meeting meeting);

        // set the start date filter
        void setFilterStartDate(Instant filterStartDate);

        // set the end date filter
        void setFilterEndDate(Instant filterEndDate);

        // set the place filter
        void setFilterRoom(String filterRoom);

    }

    /**
     * View interface
     */
    interface View extends SimpleMvp.View<Presenter> {

        // update the meetings list in the view
        void updateMeetings(List<Meeting> meetings);

        // update the filters labels in the view
        void updateFilters(String filterRoom, String filterStartDate, String filterEndDate);

        // trigger the meeting registration dialog
        void triggerMeetingRegistrationDialog();

        // expand or collapse the filter card view
        void expandOrCollapseFilters();

        // set the error on the filter start date
        void setErrorFilterStartDate();

        // set the error on the filter end date
        void setErrorFilterEndDate();

        // trigger the date picker dialog
        void triggerDatePickerDialog(Instant date, boolean beginOrEnd);

    }

    /**
     * Presenter interface
     */
    interface Presenter extends SimpleMvp.Presenter {

        // refresh the meetings list requested
        void onRefreshMeetingsListRequested();

        // on create new meeting request
        void onCreateMeetingRequested();

        // on filters have changed
        void onFiltersChanged(String filterRoom, String filterStartDate, String filterEndDate);

        // drop a meeting request
        void dropMeetingRequested(int position);

        // set the filter start date
        void setFilterStartDate(String filterStartDate);

        // set the filter start date manually
        void setFilterStartDateManual(String filterStartDate);

        // set the filter end date
        void setFilterEndDate(String filterEndDate);

        // set the filter end date manually
        void setFilterEndDateManual(String filterEndDate);

        // save the filter date
        void saveFilterDate(Instant date, boolean beginOrEnd);

        // save the filter place
        void saveFilterRoom(String filterRoom);
    }
}
