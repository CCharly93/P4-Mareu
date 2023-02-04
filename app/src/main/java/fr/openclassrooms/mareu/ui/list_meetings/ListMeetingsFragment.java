package fr.openclassrooms.mareu.ui.list_meetings;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.openclassrooms.mareu.R;
import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.ui.add_meetings.AddMeetingsDialogFactory;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFactory;
import fr.openclassrooms.mareu.ui.pickers.date.DatePickerFragment;
import fr.openclassrooms.mareu.utils.ui.SimpleTextWatcherFactory;

import static com.google.common.base.Preconditions.checkNotNull;

public class ListMeetingsFragment extends Fragment implements ListMeetingsContract.View {

    private static final String TAG = "MeetingsListFragment";

    /**
     * Meetings list presenter
     */
    private ListMeetingsContract.Presenter mPresenter;

    /**
     * Meetings list adapter
     */
    private ListMeetingsAdapter mListMeetingsAdapter;

    /**
     * Ui components
     */

    // the recycler view to display the list of the meetings
    @BindView(R.id.fragment_meetings_recycler_view)
    RecyclerView mRecyclerView;

    // the card view to allow the user to filter the meetings list
    @BindView(R.id.fragment_meetings_card_view_filter)
    CardView mFilterCardView;

    // the filters expand button
    @BindView(R.id.fragment_meetings_card_view_filter_expand_btn)
    ImageButton mFilterExpandButton;

    // the filters collapse button
    @BindView(R.id.fragment_meetings_card_view_filter_collapse_btn)
    ImageButton mFilterCollapseButton;

    // the filters apply button
    @BindView(R.id.fragment_meetings_card_view_filter_apply_btn)
    MaterialButton mFilterApplyButton;

    // the place text input filter
    @BindView(R.id.fragment_meetings_card_view_filter_place)
    TextInputLayout mFilterPlaceTextInput;

    // the start date text input filter
    @BindView(R.id.fragment_meetings_card_view_filter_start_date)
    TextInputLayout mFilterStartDateTextInput;

    // the end date text input filter
    @BindView(R.id.fragment_meetings_card_view_filter_end_date)
    TextInputLayout mFilterEndDateTextInput;

    // the place icon
    @BindDrawable(R.drawable.ic_baseline_place_24)
    Drawable mPlaceIconDrawable;

    // the clock icon
    @BindDrawable(R.drawable.ic_baseline_access_time_24bis)
    Drawable mDateTimeIconDrawable;

    // the expand icon
    @BindDrawable(R.drawable.ic_baseline_expand_24)
    Drawable mExpandIconDrawable;

    // the collapse icon
    @BindDrawable(R.drawable.ic_baseline_clear_24)
    Drawable mClearIconDrawable;

    /**
     * Constructor
     */
    public ListMeetingsFragment() {
        // always call the super constructor
        super();
    }

    /**
     * Create a new instance of the fragment
     *
     * @return the fragment
     */
    public static ListMeetingsFragment newInstance() {
        return new ListMeetingsFragment();
    }

    /**
     * Attach the presenter, to avoid circular dependency issue
     * (the view need the presenter to instantiate, and the presenter need the view to instantiate)
     * @param presenter the presenter
     */
    @Override
    public void attachPresenter(@NonNull ListMeetingsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    /**
     * We override onCreateView, to further define the behavior of the UI at startup
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_meetings, container, false);
        // Bind the ui components
        ButterKnife.bind(this, view);
        // Find the floating action button (add a meeting), then add the setOnClickListener logic
        Objects.requireNonNull(getActivity())
                .findViewById(R.id.activity_meetings_add_meeting_fab)
                .setOnClickListener(v -> mPresenter.onCreateMeetingRequested());
        // Build the recycler view
        mListMeetingsAdapter = new ListMeetingsAdapter(
                // empty list of meetings at startup
                new ArrayList<>(),
                // the action on click to drop a meeting
                (v, position) -> mPresenter.dropMeetingRequested(position)
        );
        // Set the layout manager of the recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Set the adapter of the recycler view
        mRecyclerView.setAdapter(mListMeetingsAdapter);
        // Configure the initial filters
        configureFilters();
        // Return the view
        return view;
    }

    /**
     * Handle possible errors, when user interact with the UI
     */
    @Override
    public void setErrorFilterStartDate() {
        mFilterStartDateTextInput.setError("Please use dd/mm/yy format or leave empty");
    }

    @Override
    public void setErrorFilterEndDate() {
        mFilterEndDateTextInput.setError("Please use the dd/mm/yy format or leave empty");
    }

    /**
     * Configure filters
     */
    private void configureFilters() {

        // Build a listener that trigger the presenter onFiltersChanged method
        View.OnClickListener listener = v -> mPresenter.onFiltersChanged(
                // Set the place text input filter value
                Objects.requireNonNull(mFilterPlaceTextInput.getEditText())
                        .getText()
                        .toString(),
                // Set the start date text input filter value
                Objects.requireNonNull(mFilterStartDateTextInput.getEditText())
                        .getText()
                        .toString(),
                // Set the end date text input filter value
                Objects.requireNonNull(mFilterEndDateTextInput.getEditText())
                        .getText()
                        .toString()
        );


        // On apply button click, set the listener just created
        mFilterApplyButton.setOnClickListener(listener);
        // On card view click, set the listener just created
        mFilterCardView.setOnClickListener(listener);
        // On expand button, set the listener just created
        mFilterExpandButton.setOnClickListener(listener);
        // On collapse button, set the listener just created
        mFilterCollapseButton.setOnClickListener(listener);

        // Configure the text input filters
        configureStartDateTextInput();
        configureEndDateTextInput();
        configurePlaceTextInput();
    }

    /**
     * Configure the place text input filter
     */
    @SuppressLint("ClickableViewAccessibility")
    private void configurePlaceTextInput() {

        // on focus change on the place text input filter, then notify the presenter accordingly
        Objects.requireNonNull(mFilterPlaceTextInput.getEditText()).setOnFocusChangeListener(
                (v, hasFocus) -> {
                    final EditText editText = mFilterPlaceTextInput.getEditText();
                    // notify the presenter that the place text input filter might have changed
                    mPresenter.saveFilterRoom(editText.getText().toString());
                }
        );

        // on touch on the place text input filter, then notify the presenter accordingly
        mFilterPlaceTextInput.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int CHOSEN_DRAWABLE = 2; // pick the clear icon
                final EditText editText = mFilterPlaceTextInput.getEditText(); // get the edit text
                Drawable clearIcon = editText.getCompoundDrawablesRelative()[CHOSEN_DRAWABLE];
                // if the clear icon is touched, then notify the presenter the place has been reset
                if (
                        event.getAction() == MotionEvent.ACTION_UP &&
                                clearIcon != null &&
                                clearIcon.equals(mClearIconDrawable)
                ) {
                    // the touch x position, is next to the icon bounds
                    if (event.getRawX() >= (mFilterPlaceTextInput.getEditText().getRight()
                            - clearIcon.getBounds().width())
                    ) {
                        // reset the place text
                        editText.setText("");
                        mPresenter.saveFilterRoom("");
                        // consume the event
                        return true;
                    }
                }
                // do not consume the event
                return false;
            }
        });

        // on text change on the place text input filter, then notify the presenter accordingly
        SimpleTextWatcherFactory factory = new SimpleTextWatcherFactory();
        mFilterPlaceTextInput.getEditText().addTextChangedListener(
                factory.getDefault(
                        mFilterPlaceTextInput,
                        mPlaceIconDrawable,
                        mClearIconDrawable,
                        mPlaceIconDrawable,
                        null
                )
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureStartDateTextInput() {

        // on focus change on the start date text input filter, then notify the presenter accordingly
        Objects.requireNonNull(mFilterStartDateTextInput.getEditText())
                .setOnFocusChangeListener((v, hasFocus) -> {
                    final EditText editText = mFilterStartDateTextInput.getEditText();
                    if (hasFocus) {
                        // if we have focus, reset the error
                        mFilterStartDateTextInput.setErrorEnabled(false);
                        mPresenter.setFilterStartDate(editText.toString());
                    } else {
                        // we use a different call the presenter, to notify the presenter that the
                        // we don't need to trigger the date picker dialog anymore
                        mPresenter.setFilterStartDateManual(editText.toString());
                    }
                });

        // on start date changed, notify the presenter accordingly
        mFilterStartDateTextInput.getEditText().setOnClickListener(
                v -> mPresenter.setFilterStartDate(
                        mFilterStartDateTextInput.getEditText().getText().toString()
                )
        );

        // on touch on the start date text input filter, then clear the start date text input filter
        mFilterStartDateTextInput.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int CHOSEN_DRAWABLE = 2; // pick the clear icon
                final EditText editText = mFilterStartDateTextInput.getEditText(); // get the edit text
                Drawable clearIcon = editText.getCompoundDrawablesRelative()[CHOSEN_DRAWABLE];
                // if the clear icon is touched, then notify the presenter the place has been reset
                if (
                        event.getAction() == MotionEvent.ACTION_UP &&
                                clearIcon != null &&
                                clearIcon.equals(mClearIconDrawable)
                ) {
                    // the touch x position, is next to the icon bounds
                    if (event.getRawX() >= (mFilterStartDateTextInput.getEditText().getRight()
                            - clearIcon.getBounds().width())
                    ) {
                        // reset the place text
                        editText.setText("");
                        // consume the event
                        return true;
                    }
                }
                // do not consume the event
                return false;
            }
        });

        // on text change on the start date text input filter, then notify the presenter accordingly
        SimpleTextWatcherFactory factory = new SimpleTextWatcherFactory();
        mFilterStartDateTextInput.getEditText().addTextChangedListener(
                factory.getDefault(
                        mFilterStartDateTextInput,
                        mDateTimeIconDrawable,
                        mClearIconDrawable,
                        mDateTimeIconDrawable,
                        mExpandIconDrawable
                )
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureEndDateTextInput() {

        // on focus change on the start date text input filter, then notify the presenter accordingly
        Objects.requireNonNull(mFilterEndDateTextInput.getEditText())
                .setOnFocusChangeListener((v, hasFocus) -> {
                    final EditText editText = mFilterEndDateTextInput.getEditText();
                    if (hasFocus) {
                        // if we have focus, reset the error
                        mFilterEndDateTextInput.setErrorEnabled(false);
                        mPresenter.setFilterEndDate(editText.toString());
                    } else {
                        // we use a different call the presenter, to notify the presenter that the
                        // we don't need to trigger the date picker dialog anymore
                        mPresenter.setFilterEndDateManual(editText.toString());
                    }
                });


        // on start date changed, notify the presenter accordingly
        mFilterEndDateTextInput.getEditText().setOnClickListener(
                v -> mPresenter.setFilterStartDate(
                        mFilterEndDateTextInput.getEditText().getText().toString()
                )
        );

        // on touch on the end date text input filter, then clear the end date text input filter
        mFilterEndDateTextInput.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int CHOSEN_DRAWABLE = 2; // pick the clear icon
                final EditText editText = mFilterEndDateTextInput.getEditText(); // get the edit text
                Drawable clearIcon = editText.getCompoundDrawablesRelative()[CHOSEN_DRAWABLE];
                // if the clear icon is touched, then notify the presenter the place has been reset
                if (
                        event.getAction() == MotionEvent.ACTION_UP &&
                                clearIcon != null &&
                                clearIcon.equals(mClearIconDrawable)
                ) {
                    // the touch x position, is next to the icon bounds
                    if (event.getRawX() >= (mFilterEndDateTextInput.getEditText().getRight()
                            - clearIcon.getBounds().width())
                    ) {
                        // reset the place text
                        editText.setText("");
                        // consume the event
                        return true;
                    }
                }
                // do not consume the event
                return false;
            }
        });

        // on text change on the end date text input filter, then notify the presenter accordingly
        SimpleTextWatcherFactory factory = new SimpleTextWatcherFactory();
        mFilterEndDateTextInput.getEditText().addTextChangedListener(
                factory.getDefault(
                        mFilterEndDateTextInput,
                        mDateTimeIconDrawable,
                        mClearIconDrawable,
                        mDateTimeIconDrawable,
                        mExpandIconDrawable
                )
        );
    }

    /**
     * On resume, tell the presenter to refresh the data (meetings list)
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    /**
     * Method called by the presenter, to define the meetings to display
     * @param meetings the meetings to display
     */
    @Override
    public void updateMeetings(List<Meeting> meetings) {
        mListMeetingsAdapter.updateMeetings(meetings);
    }

    /**
     * Method called by the presenter, to display the meeting registration (add) dialog
     */
    @Override
    public void triggerMeetingRegistrationDialog() {
        AddMeetingsDialogFactory factory = new AddMeetingsDialogFactory();
        factory
                .getFragment()
                .display(getFragmentManager());
    }

    /**
     * Expand or collapse the filters
     */
    @Override
    public void expandOrCollapseFilters() {
        if (mFilterExpandButton.getVisibility() == View.VISIBLE) {
            mFilterExpandButton.setVisibility(View.GONE);
            mFilterStartDateTextInput.setVisibility(View.VISIBLE);
            mFilterEndDateTextInput.setVisibility(View.VISIBLE);
            mFilterApplyButton.setVisibility(View.VISIBLE);
            mFilterPlaceTextInput.setVisibility(View.VISIBLE);
            mFilterCollapseButton.setVisibility(View.VISIBLE);
        } else {
            mFilterStartDateTextInput.setVisibility(View.GONE);
            mFilterCollapseButton.setVisibility(View.GONE);
            mFilterApplyButton.setVisibility(View.GONE);
            mFilterEndDateTextInput.setVisibility(View.GONE);
            mFilterPlaceTextInput.setVisibility(View.GONE);
            mFilterExpandButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Update the filters text input label, and reset the icons around every filter text input
     * Update also the cursor position in the text input, to meet the user expectations
     * @param filterPlace the place filter
     * @param filterStartDate the start date filter
     * @param filterEndDate the end date filter
     */
    @Override
    public void updateFilters(String filterPlace, String filterStartDate, String filterEndDate) {
        // update the place text filter
        Objects.requireNonNull(mFilterPlaceTextInput.getEditText()).setText(filterPlace);
        // update the start date text filter
        Objects.requireNonNull(mFilterStartDateTextInput.getEditText()).setText(
                filterStartDate == null ? "": filterStartDate
        );
        // update the end date text filter
        Objects.requireNonNull(mFilterEndDateTextInput.getEditText()).setText(
                filterEndDate == null ? "" : filterEndDate
        );

        // if the place filter is empty, do not display the clear icon. Otherwise, display it
        setCompoundDrawables(mFilterPlaceTextInput, mPlaceIconDrawable,
                filterPlace.isEmpty() ? null : mClearIconDrawable);
        // reset the start date icons, to the time and clear icons
        setCompoundDrawables(mFilterStartDateTextInput, mDateTimeIconDrawable, mClearIconDrawable);
        // reset the end date icons, to the time and clear icons
        setCompoundDrawables(mFilterEndDateTextInput, mDateTimeIconDrawable, mClearIconDrawable);

        // put the selection cursor at the end of the text
        mFilterPlaceTextInput.getEditText().setSelection(
                mFilterPlaceTextInput.getEditText().getText().length()
        );
        mFilterStartDateTextInput.getEditText().setSelection(
                mFilterStartDateTextInput.getEditText().getText().length()
        );
        mFilterEndDateTextInput.getEditText().setSelection(
                mFilterEndDateTextInput.getEditText().getText().length()
        );
    }

    /**
     * Change icons around the text input layout
     * @param textInputLayout the text input layout
     * @param drawableStart the start icon
     * @param drawableEnd the end icon
     */
    private void setCompoundDrawables(TextInputLayout textInputLayout,
                                      Drawable drawableStart,
                                      Drawable drawableEnd
    ){

        Objects.requireNonNull(textInputLayout.getEditText())
                .setCompoundDrawablesRelativeWithIntrinsicBounds(
                        drawableStart,
                        null,
                        drawableEnd,
                        null
                );

    }

    /**
     * Method called by the presenter, to display the date picker dialog
     * @param date the date to display
     * @param isBegin true if the date is the start date, false if the date is the end date
     */
    @Override
    public void triggerDatePickerDialog(Instant date, boolean isBegin) {
        // create the date picker factory
        DatePickerFactory factory = new DatePickerFactory();
        // get the fragment ..
        DatePickerFragment fragment = factory.getFragment(
                date,
                null,
                !isBegin,
                // on date set, notify the presenter
                (datePicked) -> mPresenter.saveFilterDate(datePicked, isBegin)
        );
        // .. and display it
        fragment.display(getFragmentManager());
    }

}
