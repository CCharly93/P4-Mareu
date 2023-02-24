package fr.openclassrooms.mareu.model;

import java.util.Objects;

public class User implements Comparable <User> {

    private String email;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Compare two persons by email
     * @param o Object to compare
     * @return true if equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    /**
     * Compare two persons by email
     * @param user User to compare
     * @return 0 if equals, -1 if this is before user, 1 if this is after user
     */
    @Override
    public int compareTo(User user) {
        return email.compareTo(user.getEmail());
    }

    /**
     * Hashcode of a person, based on email
     * @return Hashcode of a person
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
