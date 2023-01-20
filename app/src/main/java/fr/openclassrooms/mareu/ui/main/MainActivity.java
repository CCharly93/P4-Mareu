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
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFragment;
import fr.openclassrooms.mareu.ui.pickers.time.TimePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.time.TimePickerFragment;
import fr.openclassrooms.mareu.utils.DateEasy;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final FragmentManager fm = getSupportFragmentManager();
        button.setOnClickListener(v -> {
            System.out.println("Clique sur le bouton");
            // create the date picker factory
            DatePickerFactory factory = new DatePickerFactory();
            // get the fragment ..
            DatePickerFragment fragment = factory.getFragment(
                    DateEasy.now(),
                    null,
                    !false,
                    // on date set, notify the presenter
                    (datePicked) -> System.out.println("Une date a été choisi : " + datePicked)
            );
            // .. and display it
            fragment.display(fm);
        });
        button2.setOnClickListener(v -> {
           TimePickerFactory factory = new TimePickerFactory();
           TimePickerFragment fragment = factory.getFragment(
                   DateEasy.now(),
                   (timePicked) -> System.out.println("Une heure a été choisie " + timePicked)
           );
           fragment.display(fm);
        });
        button3.setOnClickListener(v -> {
            Set<User> users = new TreeSet<>();
            users.add(new User("charly@gmail.com"));
            users.add(new User("charlie@gmail.com"));
            AddUsersDialogFactory factory = new AddUsersDialogFactory();
            AddUsersDialogFragment fragment = factory.getFragment(
                    users,
                    (usersSet) -> usersSet.stream().map(x -> x.getEmail()).forEach(System.out::println)
            );
            fragment.display(fm);
        });
        button4.setOnClickListener(v -> {
            AddMeetingsDialogFactory factory = new AddMeetingsDialogFactory();
            AddMeetingsDialogFragment fragment = factory.getFragment();
            fragment.display(fm);
        });


    }

    public void updateMeetingsFragments() {
        DI.getMeetingsApiService().getMeetings().stream().map(x -> x.getSubject()).forEach(System.out::println);
    }

}