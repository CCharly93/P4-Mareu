package fr.openclassrooms.mareu.core;

/**
 * The MVP (Model-View-Presenter) pattern is a software design pattern commonly
 * used in Android development.
 *
 * It allows :
 *  - to avoid activities class consisting of thousands of lines
 *  - separation of concerns, separate UI layer from the business logic (easily interchange views)
 *  - for better separation of concerns and improved testability
 *  - an application to be easily extensible and maintainable
 *
 * Three distinct part of the pattern are :
 *
 *  - the Presenter act as a mediator between the View and the Model : it basically
 *    retrieves data from the model and updates the View with this data,
 *    as well as handling user input and updating the Model accordingly
 *    A presenter is permanently attached to a given view.
 *
 *  - the View display the data to the user. View is basically an Activity (or a Fragment).
 *
 *  - the Model represents the app data and the business logic.
 *
 */

public interface SimpleMvp {

    // The Model interface
    interface Model {
        // super interface Model, not used in this app, but keep in mind that Model is
        // always needed in the MVP pattern
    }

    // The View interface that is implemented indirectly by the Activity (or Fragment)
    // Generic type T is the Presenter type
    interface View<T> {

        // A given presenter need to be attached to the view :
        // - the view will need to call the presenter's methods when the user interacts with the UI
        // - the view is called by the presenter to update the UI when needed
        void attachPresenter(T presenter);

    }

    // The Presenter interface
    // - call the view's methods to update the UI
    // - interact with the model to retrieve and save data
    interface Presenter {

        // the presenter will need to gather some initial data from the model when it is created
        void init();

    }
}