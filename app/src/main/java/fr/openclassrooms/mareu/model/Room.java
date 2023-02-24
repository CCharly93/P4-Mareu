package fr.openclassrooms.mareu.model;

import java.util.List;
import java.util.Objects;

public class Room {

        private String name;

        public Room(String name) {

            this.name = name;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) { this.name = name; }

    /**
     * Compare two rooms by name
     * @param o Room to compare
     * @return 0 if equals, -1 if this is before o, 1 if this is after o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name);
    }

    /**
     * Compute the hash code
     * @return Hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
