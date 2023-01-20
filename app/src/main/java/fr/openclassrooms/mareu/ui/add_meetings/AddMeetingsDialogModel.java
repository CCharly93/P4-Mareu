package fr.openclassrooms.mareu.ui.add_meetings;

import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;

import fr.openclassrooms.mareu.di.DI;
import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.model.Room;
import fr.openclassrooms.mareu.model.User;
import fr.openclassrooms.mareu.service.MeetingsApiService;
import fr.openclassrooms.mareu.utils.DateEasy;

public class AddMeetingsDialogModel implements AddMeetingsDialogContract.Model{
    /**
     * The meeting date
     */
    private Instant mMeetingDate = DateEasy.now();

    /**
     * The persons invited to the meeting
     */
    private Set<User> mUsersInvitedToTheMeeting = new TreeSet<>();

    /**
     * The API service
     */
    private final MeetingsApiService mApiService = DI.getMeetingsApiService();

    /**
     * Update the meeting date
     * @param meetingDate the meeting date
     */
    @Override
    public void saveMeetingDate(Instant meetingDate) {
        mMeetingDate = meetingDate;
    }

    /**
     * Update the persons invited to the meeting
     * @param users the persons invited to the meeting
     */
    @Override
    public void saveInvitedUsers(Set<User> users) {
        mUsersInvitedToTheMeeting = users;
    }

    /**
     * Save the meeting to through the service
     * @param room the place of the meeting
     * @param subject the subject of the meeting
     */
    @Override
    public void saveMeeting(String room, String subject) {
        // create the meeting
        Meeting meeting = new Meeting(mMeetingDate, subject, new Room(room));
        // add the persons invited to the meeting
        for (User user : mUsersInvitedToTheMeeting) {
            // add the user to the meeting
            meeting.addUser(user);
        }
        // persist the meeting through the service
        mApiService.addMeeting(meeting);
    }

    /**
     * Get the meeting date
     * @return the meeting date
     */
    @Override
    public Instant getMeetingDate() {
        return mMeetingDate;
    }

    /**
     * Get the persons invited to the meeting
     * @return the persons invited to the meeting
     */
    @Override
    public Set<User> getInvitedUsers() {
        return mUsersInvitedToTheMeeting;
    }
}
