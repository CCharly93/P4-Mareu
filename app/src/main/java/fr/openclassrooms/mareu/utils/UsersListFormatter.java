package fr.openclassrooms.mareu.utils;

import com.google.common.base.Joiner;

import java.util.Set;
import java.util.TreeSet;

import fr.openclassrooms.mareu.model.User;

/**
 * A simple class to format a list of persons
 */
public class UsersListFormatter {

    /**
     * The persons list
     */
    private final Set<User> mUsersSet;

    /**
     * Constructor
     * @param usersSet the persons list
     */
    public UsersListFormatter(Set<User> usersSet) {
        mUsersSet = usersSet;
    }

    /**
     * Format the persons list
     * @return the formatted persons list as string
     */
    public String format(){
        // build the email list from the Person domain model objects
        Set<String> emailsSet = new TreeSet<>();
        // for each person, add the email to the set
        for(User user : mUsersSet){
            // add the email
            emailsSet.add(user.getEmail());
        }
        // build the string from the string set
        return "Persons invited list:\n\n" + Joiner.on("\n").join(emailsSet);
    }

}
