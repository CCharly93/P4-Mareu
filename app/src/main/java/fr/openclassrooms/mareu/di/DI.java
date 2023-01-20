package fr.openclassrooms.mareu.di;

import fr.openclassrooms.mareu.service.DummyMeetingsApiService;
import fr.openclassrooms.mareu.service.MeetingsApiService;

public class DI {

    private static MeetingsApiService meetingsApiService = new DummyMeetingsApiService();

    public static MeetingsApiService getMeetingsApiService() {
        return meetingsApiService;
    }

    public static MeetingsApiService getNewInstanceMeetingsApiService() {
        // Hack for instrumented tests
        meetingsApiService = new DummyMeetingsApiService();
        return meetingsApiService;
    }

}