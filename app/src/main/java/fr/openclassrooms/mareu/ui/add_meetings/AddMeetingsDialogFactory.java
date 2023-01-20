package fr.openclassrooms.mareu.ui.add_meetings;

public class AddMeetingsDialogFactory {

    public AddMeetingsDialogFragment getFragment(){

        // create the model
        AddMeetingsDialogContract.Model model = new AddMeetingsDialogModel();

        // create the fragment (view)
        AddMeetingsDialogFragment fragment = AddMeetingsDialogFragment.newInstance();

        // create the presenter
        new AddMeetingsDialogPresenter(fragment, model);

        // return the fragment
        return fragment;

    }

}