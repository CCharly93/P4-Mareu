package fr.openclassrooms.mareu.service;

import java.util.List;

import fr.openclassrooms.mareu.model.Meeting;

public interface MeetingsApiService {

    List<Meeting> getMeetings();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

}
