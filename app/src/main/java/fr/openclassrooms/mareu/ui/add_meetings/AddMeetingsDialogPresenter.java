package fr.openclassrooms.mareu.ui.add_meetings;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.util.Set;

import fr.openclassrooms.mareu.model.User;
import fr.openclassrooms.mareu.utils.DateEasy;
import fr.openclassrooms.mareu.utils.UsersListFormatter;

public class AddMeetingsDialogPresenter implements AddMeetingsDialogContract.Presenter{

    /**
     * The view
     */
    private final AddMeetingsDialogContract.View mView;

    /**
     * The model
     */
    private final AddMeetingsDialogContract.Model mModel;

    /**
     * Constructor for the presenter
     * @param view the view
     * @param model the model
     */
    public AddMeetingsDialogPresenter(
            @NonNull AddMeetingsDialogContract.View view,
            @NonNull AddMeetingsDialogContract.Model model) {
        // initialize the view
        mView = view;
        // initialize the model
        mModel = model;
        // important : immediately attach the presenter to the view
        mView.attachPresenter(this);
    }

    /**
     * When the view is created (or recreated)
     */
    @Override
    public void onResumeRequest() {
        // refresh the persons invited to the meeting
        onUsersChanged(mModel.getInvitedUsers());
        // refresh the meeting date
        mView.updateMeetingDate(mModel.getMeetingDate());
    }

    /**
     * View ask the presenter to create a meeting
     * @param title the topic of the meeting
     * @param dateText the meeting date
     * @param room the location of the meeting
     */
    @Override
    public void onCreateMeetingRequest(String title, String dateText, String room) {

        // declare a date, in order to parse it from text
        Instant meetingDate = null;

        // check for error
        boolean isError = false;

        // check if the place is empty
        if (room.isEmpty()) {
            // the place is empty
            isError = true;
            // display the error
            mView.setErrorRoomIsEmpty();
        }

        // check if the topic is empty
        if (title.isEmpty()) {
            // the topic is empty
            isError = true;
            // display the error
            mView.setErrorTopicIsEmpty();
        }

        // check if the date is empty
        if (dateText.isEmpty()) {
            // the date is empty
            isError = true;
            // display the error
            mView.setErrorDateIsEmpty();
            // try to parse the date to proper instant
        } else if ((meetingDate = DateEasy.parseDateTimeStringToInstant(dateText)) == null) {
            // the date has wrong format
            isError = true;
            // display the error
            mView.setErrorDateIsInWrongFormat();
        }

        // if there is no error
        if (!isError) {
            // save the date
            mModel.saveMeetingDate(meetingDate);
            // save the meeting
            mModel.saveMeeting(room, title);
            // return to the meeting list
            mView.returnBackToMeetings();
        }
    }

    /**
     * Method call by the view, when the user click on the date field
     * @param meetingDateTextInput the date text input
     */
    @Override
    public void onMeetingDatePickRequest(String meetingDateTextInput) {
        // save the date to the model
        mModel.saveMeetingDate(DateEasy.parseDateTimeOrDateOrReturnNow(meetingDateTextInput));
        // update the view meeting date
        mView.updateMeetingDate(mModel.getMeetingDate());
        // trigger (show) the date picker dialog
        mView.triggerDatePickerDialog(mModel.getMeetingDate());
    }

    /**
     * Method call by the view, when a date is selected by the user
     * @param date the meeting date
     */
    @Override
    public void onMeetingDateSelected(Instant date) {
        // save the date to the model
        mModel.saveMeetingDate(date);
        // update the view meeting date
        mView.updateMeetingDate(mModel.getMeetingDate());
        // trigger (show) the TIME picker dialog, in order for the user to choose the time
        mView.triggerTimePickerDialog(mModel.getMeetingDate());
    }

    /**
     * Method call by the view, when a time is selected by the user
     * @param mergedDateAndTime the merged date and time
     */
    @Override
    public void onMeetingTimeSelected(Instant mergedDateAndTime) {
        // save the resulting merged date and time to the model
        mModel.saveMeetingDate(mergedDateAndTime);
        // update the view meeting date
        mView.updateMeetingDate(mModel.getMeetingDate());
    }

    /**
     * Method call by the view, when the user click to ask for adding persons invited to the meeting
     */
    @Override
    public void onAddUsersRequest() {
        mView.triggerAddUsersDialog(mModel.getInvitedUsers());
    }

    /**
     * The view give us back the persons invited to the meeting
     * @param users the users invited to the meeting
     */
    @Override
    public void onUsersChanged(Set<User> users) {
        // save the persons
        mModel.saveInvitedUsers(users);
        // create persons formatter
        UsersListFormatter userListFormatter = new UsersListFormatter(users);
        // update the view (list of persons invited to the meeting) accordingly
        mView.updateUsersInvitedToTheMeeting(userListFormatter.format());
    }

    /**
     * Init the view, and show it!
     */
    @Override
    public void init() {
        mView.showDialog();
    }
}
