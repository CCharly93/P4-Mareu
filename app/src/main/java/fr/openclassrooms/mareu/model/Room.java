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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
