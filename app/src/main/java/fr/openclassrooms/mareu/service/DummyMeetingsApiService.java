package fr.openclassrooms.mareu.service;

import java.util.ArrayList;
import java.util.List;

import fr.openclassrooms.mareu.model.Meeting;

public class DummyMeetingsApiService implements MeetingsApiService {

    private final List<Meeting> meetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        if(!meetings.contains(meeting))
            meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

}
