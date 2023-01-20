package fr.openclassrooms.mareu.ui.add_users;

import fr.openclassrooms.mareu.model.User;

/**
 * The presenter for the AddPersonsDialog
 */
public class AddUsersDialogPresenter implements AddUsersDialogContract.Presenter {

    /**
     * The view
     */
    private final AddUsersDialogContract.View mView;

    /**
     * The model
     */
    private final AddUsersDialogContract.Model mModel;

    /**
     * Constructor for the presenter
     * @param view the view
     * @param model the model
     */
    public AddUsersDialogPresenter(AddUsersDialogContract.View view, AddUsersDialogContract.Model model) {
        mView = view;
        mModel = model;
        // important : immediately attach the presenter to the view
        mView.attachPresenter(this);
    }

    /**
     * Add a person to the list (set) of persons
     * @param person the person to add
     */
    @Override
    public void onUserAdded(User user) {
        mModel.addUser(user);
        mView.updateUsersList(mModel.getUsersSet());
    }

    /**
     * Ask explicitly the presenter to refresh the persons set from the model
     */
    @Override
    public void onViewLoaded() {
        mView.updateUsersList(mModel.getUsersSet());
    }

    /**
     * Tell the model that's the final list of persons that is currently set
     * So we can notify the listener
     */
    @Override
    public void commit() {
        mModel.commit();
    }

    /**
     * Init the view, and show it!
     */
    @Override
    public void init() {
        mView.showDialog();
    }
}