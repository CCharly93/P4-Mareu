package fr.openclassrooms.mareu.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;

import fr.openclassrooms.mareu.R;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.openclassrooms.mareu.di.DI;
import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.model.User;
import fr.openclassrooms.mareu.ui.add_meetings.AddMeetingsDialogFactory;
import fr.openclassrooms.mareu.ui.add_meetings.AddMeetingsDialogFragment;
import fr.openclassrooms.mareu.ui.add_users.AddUsersDialogFactory;
import fr.openclassrooms.mareu.ui.add_users.AddUsersDialogFragment;
/**import fr.openclassrooms.mareu.ui.meetings_registration.MeetingRegistrationDialogFactory;*/
import fr.openclassrooms.mareu.ui.list_meetings.ListMeetingsFragment;
import fr.openclassrooms.mareu.ui.list_meetings.ListMeetingsModel;
import fr.openclassrooms.mareu.ui.list_meetings.ListMeetingsPresenter;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFragment;
import fr.openclassrooms.mareu.ui.pickers.time.TimePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.time.TimePickerFragment;
import fr.openclassrooms.mareu.utils.DateEasy;

public class MainActivity extends AppCompatActivity {

        /**
         * The underlying fragment presenter
         */
        private ListMeetingsPresenter mListMeetingsPresenter;

        /**
         * called when the activity is created (ui life cycle android)
         * @param savedInstanceState the saved instance state
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // call the super method
            super.onCreate(savedInstanceState);
            // set the content view
            setContentView(R.layout.activity_main);

            // try to recover the existing fragment
            ListMeetingsFragment mListMeetingsFragment = (ListMeetingsFragment)
                    getSupportFragmentManager()
                            .findFragmentById(R.id.activity_meetings);

            // if the fragment doesn't exist, create it
            if (mListMeetingsFragment == null) {
                // create the fragment
                mListMeetingsFragment = ListMeetingsFragment.newInstance();
                // add the fragment to the activity
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_meetings, mListMeetingsFragment)
                        .commit();
            }

            // create the model
            ListMeetingsModel listMeetingsModel = new ListMeetingsModel();

            // create the presenter
            mListMeetingsPresenter = new ListMeetingsPresenter(mListMeetingsFragment, listMeetingsModel);
        }

        /**
         * called when a back button is pressed on the registration (add) meeting fragment
         */
        public void updateMeetingsFragments() {
            mListMeetingsPresenter.onRefreshMeetingsListRequested();
        }

}