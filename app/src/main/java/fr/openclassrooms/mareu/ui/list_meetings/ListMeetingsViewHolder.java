package fr.openclassrooms.mareu.ui.list_meetings;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Set;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.openclassrooms.mareu.R;
import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.model.User;
import fr.openclassrooms.mareu.utils.DateEasy;
import fr.openclassrooms.mareu.utils.UsersListFormatter;

public class ListMeetingsViewHolder extends RecyclerView.ViewHolder {
    private Meeting mMeeting;

    /**
     * UI Components
     */

    // subject of the meeting label
    @BindView(R.id.fragment_meetings_item_title_text)
    TextView mSubjectText;

    // button to drop a meeting
    @BindView(R.id.fragment_meetings_item_delete_btn)
    ImageButton mDeleteButton;

    // date of the meeting label
    @BindView(R.id.fragment_meetings_item_date_text)
    TextView mDateText;

    // place of the meeting label
    @BindView(R.id.fragment_meetings_item_room_text)
    TextView mRoomText;

    // expandable card view containing the persons invited to the meeting (expand button)
    @BindView(R.id.fragment_meetings_item_expand_btn)
    ImageButton mExpandButton;

    // expandable card view containing the persons invited to the meeting (collapse button)
    @BindView(R.id.fragment_meetings_item_collapse_btn)
    ImageButton mCollapseButton;

    // list of the persons invited to the meeting label
    @BindView(R.id.fragment_meetings_item_users_text)
    TextView mUsersFlattenListText;

    // Empty meeting (no persons invited yet) string
    @BindString(R.string.empty_meeting_users_list)
    String mEmptyMeetingInvitedUsersList;

    // expandable card view containing the persons invited to the meeting (card view)
    @BindView(R.id.fragment_meetings_card_view)
    CardView mCardView;

    /**
     * Constructor
     *
     * @param itemView the view of the item
     * @param onClickListener the listener to be called when the delete button is clicked
     */
    public ListMeetingsViewHolder(@NonNull View itemView,
                                  ListMeetingsAdapter.DropClickListener onClickListener) {
        // always call super constructor
        super(itemView);
        // bind the ui components to java code
        ButterKnife.bind(this, itemView);
        // call the listener when the delete button is clicked
        mDeleteButton.setOnClickListener(
                v -> onClickListener.onClick(v, getLayoutPosition())
        );
        // call the listener when the expand button is clicked
        mExpandButton.setOnClickListener(v -> expandOrCollapseInvitedUsers());
        // call the listener when the collapse button is clicked
        mCollapseButton.setOnClickListener(v -> expandOrCollapseInvitedUsers());
        // set the click listener on the whole item (show/hide given meeting details)
        itemView.setOnClickListener(v -> expandOrCollapseInvitedUsers());
    }

    /**
     * Set the meeting to be displayed as item of the recycler view
     * @param meeting the meeting to be displayed as item of the recycler view
     */
    public void setMeeting(Meeting meeting) {
        // set the meeting to display
        mMeeting = meeting;
        // update the ui accordingly (date, subject, place)
        mDateText.setText(DateEasy.localeSpecialStringFromInstant(meeting.getDate()));
        mSubjectText.setText(meeting.getSubject());
        mRoomText.setText(meeting.getRoom().getName());
        // update the persons invited to the meeting list
        setUsersList();
    }

    /**
     * Handle the expandable card view, that display the persons list
     */
    private void expandOrCollapseInvitedUsers() {
        // if the card view is collapsed, expand it
        if (mUsersFlattenListText.getVisibility() == View.GONE) {
            // show the persons list
            mUsersFlattenListText.setVisibility(View.VISIBLE);
            // show the collapse button
            mCollapseButton.setVisibility(View.VISIBLE);
            // hide the expand button
            mExpandButton.setVisibility(View.GONE);
        } else {
            // else collapse it
            mUsersFlattenListText.setVisibility(View.GONE);
            // hide the collapse button
            mCollapseButton.setVisibility(View.GONE);
            // show the expand button
            mExpandButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Set the list of persons invited to the meeting in the UI
     */
    private void setUsersList() {
        // get persons
        Set<User> users = mMeeting.getUsers();
        // create persons formatter
        UsersListFormatter usersListFormatter = new UsersListFormatter(users);
        // if persons list is empty, display a message
        if(users.isEmpty()) {
            mUsersFlattenListText.setText(mEmptyMeetingInvitedUsersList);
        } else {
            // else, display persons list
            mUsersFlattenListText.setText(usersListFormatter.format());
        }
    }
}
