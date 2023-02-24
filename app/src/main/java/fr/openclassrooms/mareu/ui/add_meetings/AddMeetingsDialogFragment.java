package fr.openclassrooms.mareu.ui.add_meetings;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.openclassrooms.mareu.R;
import fr.openclassrooms.mareu.model.User;
import fr.openclassrooms.mareu.ui.add_users.AddUsersDialogFactory;
import fr.openclassrooms.mareu.ui.add_users.AddUsersDialogFragment;
import fr.openclassrooms.mareu.ui.main.MainActivity;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFragment;
import fr.openclassrooms.mareu.ui.pickers.time.TimePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.time.TimePickerFragment;
import fr.openclassrooms.mareu.utils.DateEasy;
import fr.openclassrooms.mareu.utils.ui.SimpleTextWatcher;

public class AddMeetingsDialogFragment extends DialogFragment implements AddMeetingsDialogContract.View {

    private static final String TAG = "MeetingRegistrationDialogFragment";

    private static final int TEXT_INPUT_MIN_LENGTH = 0;

    private AddMeetingsDialogContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

    // save toolbar
    @BindView(R.id.fragment_add_meetings_dialog_toolbar)
    Toolbar mRegistrationToolbar;

    // topic text input
    @BindView(R.id.fragment_add_meetings_title_text_input)
    TextInputLayout mSubjectTextInput;

    // place text input
    @BindView(R.id.fragment_add_meetings_room_text_input)
    TextInputLayout mRoomTextInput;

    // date text input
    @BindView(R.id.fragment_add_meetings_date_text_input)
    TextInputLayout mDateTextInput;

    // persons card view container
    @BindView(R.id.fragment_add_meetings_users_card_view)
    CardView mUsersCardView;

    // full persons list text view
    @BindView(R.id.fragment_add_meetings_users_full_list_text)
    TextView mUsersFullListText;

    public AddMeetingsDialogFragment() {
        // always call the default constructor
        super();
    }

    public static AddMeetingsDialogFragment newInstance() {
        return new AddMeetingsDialogFragment();
    }

    @Override
    public void attachPresenter(@NonNull AddMeetingsDialogContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void display(FragmentManager fragmentManager) {
        mFragmentManager = checkNotNull(fragmentManager);
        // signal the presenter we want to init and display the dialog
        mPresenter.init();
    }

    @Override
    public void showDialog() {
        show(mFragmentManager, TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // always call the super class method
        super.onCreate(savedInstanceState);
        // responsive purpose : if you rotate the device, the retained fragments
        // will remain there (they're not destroyed and recreated)
        setRetainInstance(true);
        // adopt a light theme dialog, but with a normal style (bar title)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_LightDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // always call the super class method
        super.onCreateView(inflater, container, savedInstanceState);
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_add_meetings_dialog, container,
                false);
        // bind the UI components
        ButterKnife.bind(this, view);
        // configure the subject text input
        configureSubjectTextInput();
        // configure the place text input
        configureRoomTextInput();
        // configure the date text input
        configureDateTextInput();
        // configure the "add persons to the meeting" card view
        configureAddUsersCardView();
        // return back the view
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // always call the super class method
        super.onViewCreated(view, savedInstanceState);
        // load (inflate) the menu into the toolbar
        mRegistrationToolbar.inflateMenu(R.menu.add_meetings_menu);
        // configure the return button on the toolbar to dismiss the dialog
        mRegistrationToolbar.setNavigationOnClickListener(v -> dismiss());
        // configure the save button on the toolbar
        mRegistrationToolbar.setOnMenuItemClickListener(item -> {
            // tell the presenter to create the meeting
            mPresenter.onCreateMeetingRequest(
                    // subject
                    Objects.requireNonNull(mSubjectTextInput.getEditText()).getText().toString(),
                    // date
                    Objects.requireNonNull(mDateTextInput.getEditText()).getText().toString(),
                    // place
                    Objects.requireNonNull(mRoomTextInput.getEditText()).getText().toString()
            );
            // return true to consume the event
            return true;
        });
    }

    private void configureSubjectTextInput() {
        // set the action after the text changed on that input
        Objects.requireNonNull(mSubjectTextInput.getEditText())
                .addTextChangedListener(new SimpleTextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // check if the length is valid, and if not, display an error message
                        if (mSubjectTextInput.getEditText().getText().length() > TEXT_INPUT_MIN_LENGTH) {
                            // trigger an error message
                            mSubjectTextInput.setErrorEnabled(false);
                        }
                    }
                });
    }

    private void configureRoomTextInput() {
        // set the action after the text changed on that input
        Objects.requireNonNull(mRoomTextInput.getEditText())
                .addTextChangedListener(new SimpleTextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // check if the length is valid, and if not, display an error message
                        if (mRoomTextInput.getEditText().getText().length() > TEXT_INPUT_MIN_LENGTH) {
                            // trigger an error message
                            mRoomTextInput.setErrorEnabled(false);
                        }
                    }
                });
    }

    private void configureDateTextInput() {
        // if the user click on the date text input ..
        Objects.requireNonNull(mDateTextInput.getEditText()).setOnClickListener(
                // .. we notify the presenter, in order to display a date picker dialog
                v -> mPresenter.onMeetingDatePickRequest(
                        mDateTextInput.getEditText().getText().toString()
                )
        );

        // if the user focus on the date text input ..
        mDateTextInput.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // disable the error message
                mDateTextInput.setErrorEnabled(false);
                // .. we notify the presenter, in order to display a date picker dialog
                mPresenter.onMeetingDatePickRequest(
                        mDateTextInput.getEditText().getText().toString()
                );
            }
        });

        // if the date as text has changed
        mDateTextInput.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // check the length of the text
                if (mDateTextInput.getEditText().getText().length() > TEXT_INPUT_MIN_LENGTH) {
                    // if not empty, disable the error
                    mDateTextInput.setErrorEnabled(false);
                }
            }
        });

        // on startup, we set the date to the current date
        mDateTextInput.getEditText().setText(DateEasy.localeDateTimeStringFromNow());
    }

    private void configureAddUsersCardView() {
        // set the action when the card view is clicked
        mUsersCardView.setOnClickListener(v -> mPresenter.onAddUsersRequest());
    }

    @Override
    public void onResume() {
        super.onResume();
        // responsive : on screen rotate, we preserve the state of the dialog
        mPresenter.onResumeRequest();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        // We need to update the list of meetings on the main activity
        ((MainActivity) Objects.requireNonNull(getContext())).updateMeetingsFragments();
    }

    @Override
    public void returnBackToMeetings() {
        dismiss();
    }

    @Override
    public void updateMeetingDate(Instant meetingDate) {
        Objects.requireNonNull(mDateTextInput
                        .getEditText())
                .setText(DateEasy.localeDateTimeStringFromInstant(meetingDate));
    }

    @Override
    public void updateUsersInvitedToTheMeeting(String usersFlattenList) {
        mUsersFullListText.setText(usersFlattenList);
        mUsersFullListText.setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorDateIsEmpty() {
        mDateTextInput.setError("Must be set");
    }

    @Override
    public void setErrorDateIsInWrongFormat() {
        mDateTextInput.setError("Date in wrong format");
    }

    @Override
    public void setErrorTopicIsEmpty() {
        mSubjectTextInput.setError("Must be set");
    }

    @Override
    public void setErrorRoomIsEmpty() {
        mRoomTextInput.setError("Must be set");
    }

    @Override
    public void triggerDatePickerDialog(Instant meetingDate) {
        // create the date picker factory
        DatePickerFactory factory = new DatePickerFactory();
        // get the date picker fragment
        DatePickerFragment fragment = factory.getFragment(
                // initial date
                meetingDate,
                // date from
                meetingDate,
                // we don't want the resulting date to be set at the end of the day in that case
                false,
                // when a date is selected by the user, propagate it to the presenter
                (date) -> mPresenter.onMeetingDateSelected(date)
        );
        // show the date picker fragment to the screen
        fragment.display(getFragmentManager());
    }

    @Override
    public void triggerTimePickerDialog(Instant meetingDate) {
        // create the time picker factory
        TimePickerFactory factory = new TimePickerFactory();
        // get the time picker fragment
        TimePickerFragment fragment = factory.getFragment(
                // initial date
                meetingDate,
                // when a time is selected by the user, propagate it to the presenter
                (date) -> mPresenter.onMeetingTimeSelected(date)
        );
        // show the time picker fragment to the screen
        fragment.display(getFragmentManager());
    }

    @Override
    public void triggerAddUsersDialog(Set<User> initialUsers) {
        // create the add persons dialog factory
        AddUsersDialogFactory factory = new AddUsersDialogFactory();
        // get the add persons dialog fragment
        AddUsersDialogFragment fragment = factory
                .getFragment(
                        // initial persons
                        initialUsers,
                        // when persons are saved by the user, propagate it to the presenter
                        (users) -> mPresenter.onUsersChanged(users)
                );
        // show the add persons dialog fragment to the screen
        fragment.display(getFragmentManager());
    }

}
