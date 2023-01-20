package fr.openclassrooms.mareu.ui.add_users;

import java.util.Set;

import fr.openclassrooms.mareu.model.User;

/**
 * Factory to create AddPersonsDialogFragment
 */
public class AddUsersDialogFactory {

    /**
     * Create a new instance of AddPersonsDialogFragment
     * @param initialUsers the initial persons to display
     * @param onUsersSetFinalChangedListener the listener to call when final persons set changed
     * @return the new instance of AddPersonsDialogFragment
     */
    public AddUsersDialogFragment getFragment(
            Set<User> initialUsers,
            AddUsersDialogContract.Model.OnUsersSetFinalChangedListener
                    onUsersSetFinalChangedListener){

        // create the model
        AddUsersDialogContract.Model model = new AddUsersDialogModel();
        model.setInitialUsers(initialUsers);
        model.setOnUsersSetFinalChangedListener(onUsersSetFinalChangedListener);

        // create the fragment (view)
        AddUsersDialogFragment fragment = AddUsersDialogFragment.newInstance();

        // create the presenter
        new AddUsersDialogPresenter(fragment, model);

        // return the fragment
        return fragment;
    }
}