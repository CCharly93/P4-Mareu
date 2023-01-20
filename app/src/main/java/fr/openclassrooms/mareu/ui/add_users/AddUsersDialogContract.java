package fr.openclassrooms.mareu.ui.add_users;

import java.util.Set;

import fr.openclassrooms.mareu.core.SimpleMvp;
import fr.openclassrooms.mareu.model.User;

/**
 * AddPersonsDialog MVP Contract
 * Contract between the view and the presenter
 * Contract between the presenter and the model
 */
public interface AddUsersDialogContract extends SimpleMvp {

    /**
     * Model interface
     */
    interface Model extends SimpleMvp.Model {
        // return the persons added by the user
        Set<User> getUsersSet();

        // add a person to the list of invited persons to the meeting
        void addUser(User user);

        // the last saved persons list will be the last one, so we can notify the caller
        void commit();

        // set the initial persons in the set/list
        void setInitialUsers(Set<User> initialUsers);

        // allow the model to notify a caller, that the persons set has changed for the last time
        interface OnUsersSetFinalChangedListener {
            void onUsersListFinalChanged(Set<User> userList);
        }

        // set the listener to notify the caller, that the persons set has changed for the last time
        void setOnUsersSetFinalChangedListener(
                OnUsersSetFinalChangedListener onUsersSetChangedListener
        );
    }

    /**
     * View interface
     */
    interface View extends SimpleMvp.View<Presenter> {
        // show the add persons dialog to the screen
        void showDialog();
        // update the ui person set
        void updateUsersList(Set<User> usersSet);
    }

    /**
     * Presenter interface
     */
    interface Presenter extends SimpleMvp.Presenter {
        // method called from the view, when the user click to add a person to the meeting
        void onUserAdded(User user);
        // ask explicitly the presenter to refresh the persons set from the model after view load
        void onViewLoaded();
        // method called from the view, when the user click to validate the persons list
        void commit();
    }

}