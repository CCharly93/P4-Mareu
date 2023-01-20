package fr.openclassrooms.mareu.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.service.DummyMeetingsApiService;

public class MeetingFragment extends Fragment {

    private DummyMeetingsApiService mApiService;
    private List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;
    private static final String LOG_NAME = "MeetingFragment";

    /**
     * Create and return a new instance
     * @return @{@link MeetingFragment}
     */
    public static MeetingFragment newInstance(Boolean favorite_only) {
        MeetingFragment fragment = new MeetingFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

}
