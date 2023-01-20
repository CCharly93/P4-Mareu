package fr.openclassrooms.mareu.ui.add_users;

import java.util.Set;
import java.util.TreeSet;

import fr.openclassrooms.mareu.model.User;

/**
 * Model for the AddPersonsDialog
 */
public class AddUsersDialogModel implements AddUsersDialogContract.Model {

    /**
     * The list of (set of unique) persons
     */
    private final Set<User> mUsersSet = new TreeSet<>();

    /**
     * The listener to notify when the list of persons is updated
     */
    private OnUsersSetFinalChangedListener mOnUsersSetFinalChangedListener;

    /**
     * Get the list (set) of persons
     * @return the list (set) of persons
     */
    @Override
    public Set<User> getUsersSet() {
        return mUsersSet;
    }

    /**
     * Add a person to the list (set) of persons
     * @param user the person to add
     */
    @Override
    public void addUser(User user) {
        mUsersSet.add(user);
    }

    /**
     * Tell the model that's the final list of persons that is currently set
     * So we can notify the listener
     */
    @Override
    public void commit() {
        if (mOnUsersSetFinalChangedListener != null) {
            mOnUsersSetFinalChangedListener.onUsersListFinalChanged(mUsersSet);
        }
    }

    @Override
    public void setInitialUsers(Set<User> initialUsers) {
        mUsersSet.clear();
        mUsersSet.addAll(initialUsers);
    }

    /**
     * Set the listener to notify when the list of persons is updated
     * @param onUsersSetFinalChangedListener the listener
     */
    @Override
    public void setOnUsersSetFinalChangedListener(
            OnUsersSetFinalChangedListener onUsersSetFinalChangedListener) {
        mOnUsersSetFinalChangedListener = onUsersSetFinalChangedListener;
    }
}