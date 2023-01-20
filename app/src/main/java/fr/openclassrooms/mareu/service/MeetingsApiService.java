package fr.openclassrooms.mareu.service;

import java.util.List;

import fr.openclassrooms.mareu.model.Meeting;

/**
 * Meeting API client
 */
public interface MeetingsApiService {

    /**
     * Get all my Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Add a Meeting
     * @param meeting
     */
    void addMeeting(Meeting meeting);

    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

}
