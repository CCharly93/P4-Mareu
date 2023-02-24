package fr.openclassrooms.mareu.model;


import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Meeting implements Comparable<Meeting> {
    private String subject;
    private Room room;
    private Instant date;
    private Set<User> users;

    public Meeting(Instant date, String subject, Room room){
        this.subject = subject;
        this.room = room;
        this.date = date;
        this.users = new TreeSet<>();
           }
    public Meeting(Instant date, String subject, Room room, User... users){
        this(date, subject, room);
        this.addUsers(users);
    }

    public String getSubject() {return subject;}

    public void setSubject(String subject) {this.subject = subject;}

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addUsers(User... users) {
        this.users.addAll(Stream.of(users).collect(Collectors.toList()));
    }

    /**
     * Compare two meetings, by comparing their dates
     * @param o Meeting to compare to
     * @return 0 if equal, -1 if this is before o, 1 if this is after o
     */
    @Override
    public int compareTo(Meeting o) {
        return getDate().compareTo(o.getDate());
    }

    /**
     * Test if two meetings are equal, by comparing their dates and places
     * @param o Meeting to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        if (!date.equals(meeting.date)) return false;
        return room.equals(meeting.room);
    }

    /**
     * Get the hashcode of the meeting, by using the hashcode of its date and place
     * @return Hashcode of the meeting
     */
    @Override
    public int hashCode() {
        return Objects.hash(room, date);
    }
}

