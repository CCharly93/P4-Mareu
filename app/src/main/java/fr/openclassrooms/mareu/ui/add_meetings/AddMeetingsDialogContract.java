package fr.openclassrooms.mareu.ui.add_meetings;

import java.time.Instant;
import java.util.Set;

import fr.openclassrooms.mareu.core.SimpleMvp;
import fr.openclassrooms.mareu.model.User;

public interface AddMeetingsDialogContract extends SimpleMvp {

    interface Model extends SimpleMvp.Model {

        void saveMeetingDate(Instant meetingDate);

        void saveMeetingUsers(Set<User> users);

        void saveMeetingRoom(String room);

        void saveMeetingSubject(String subject);

        Instant getMeetingDate();

        Set<User> getInvitedUsers();

    }

    interface View extends SimpleMvp.View {

        void showDialog();

        void returnBackToMeetings();

        void updateMeetingDate(Instant meetingDate);

        void updateUsersInvitedToTheMeeting(String usersFlatList);

        void setErrorTopicIsEmpty();
        // the place must be set
        void setErrorPlaceIsEmpty();
        // the date must be set
        void setErrorDateIsEmpty();
        // the date is in the wrong format (see DateEasy utils class)
        void setErrorDateIsInWrongFormat();

        // trigger the view to launch the date picker dialog
        void triggerDatePickerDialog(Instant meetingDate);
        // trigger the view to launch the time picker dialog
        void triggerTimePickerDialog(Instant meetingDate);
        // trigger the view to launch the add persons dialog
        void triggerAddUsersDialog(Set<User> initialUsers);

    }

    interface Presenter extends SimpleMvp.Presenter {

        // on resume view request (after screen rotation)
        void onResumeRequest();

        // method called when the user click on the save toolbar button, to persist the meeting
        void onCreateMeetingRequest(String topic, String meetingDateTextInput, String place);

        // method called when the user click on the date text input, to launch the date picker dialog
        void onMeetingDatePickRequest(String meetingDateTextInput);

        // method called when the user has selected a date in the date picker dialog
        void onMeetingDateSelected(Instant date);

        // method called when the user has selected a time in the time picker dialog
        void onMeetingTimeSelected(Instant mergedDateAndTime);

        // method called when the user click on the persons text input, to launch the add persons dialog
        void onAddUsersRequest();

        // method called when the user has selected persons in the add persons dialog
        void onUsersChanged(Set<User> users);
    }

}
