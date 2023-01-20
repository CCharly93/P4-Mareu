package fr.openclassrooms.mareu;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.openclassrooms.mareu.di.DI;
import fr.openclassrooms.mareu.model.Meeting;
import fr.openclassrooms.mareu.model.Room;
import fr.openclassrooms.mareu.model.User;
import fr.openclassrooms.mareu.service.MeetingsApiService;
import fr.openclassrooms.mareu.utils.DateEasy;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingsApiServiceUnitTest {

    private MeetingsApiService service;

    private Meeting meetingOne;
    private Meeting meetingTwo;
    private Meeting meetingThree;
    private Meeting meetingFour;

    @Before
    public void setup() {
        service = DI.getNewInstanceMeetingsApiService();

        Room roomOne = new Room("Salle conférence A");
        Room roomTwo = new Room("Salle conférence B");

        User userOne = new User("jean@entreprise.fr");
        User userTwo = new User("luc@entreprise.fr");
        User userThree = new User("dora@entreprise.fr");
        User userFour = new User("lucie@entreprise.fr");

        meetingOne = new Meeting(
                DateEasy.parseDateTimeStringToInstant("01/11/23 10:15"),
                "Réunion de service",
                roomOne,
                userOne,
                userTwo
        );

        meetingTwo = new Meeting(
                DateEasy.parseDateTimeStringToInstant("02/11/23 08:00"),
                "Atelier technique Android",
                roomOne,
                userTwo,
                userThree,
                userFour
        );

        meetingThree = new Meeting(
                DateEasy.parseDateTimeStringToInstant("23/11/23 18:00"),
                "Afterwork Repas Noel",
                roomTwo,
                userOne,
                userTwo,
                userThree,
                userFour
        );

        meetingFour = new Meeting(
                DateEasy.parseDateTimeStringToInstant("28/11/23 16:00"),
                "Mise en exploitaion MaReu",
                roomTwo,
                userThree,
                userFour
        );


    }

    @Test
    // On vérifie que la liste des réunions "meetings" est vide au lancement.
    public void emptyMeetingsAtServiceStartup(){
        List<Meeting> meetings = service.getMeetings();
        assertEquals(0, meetings.size());
    }

    @Test
    // On rajoute un meeting (One, Two, Three, Four) à la liste complète des réunions "meetings".
    // On vérifie que tous les meetings sont présent dans la liste peu importe l'ordre.
    public void getMeetings(){
        service.addMeeting(meetingOne);
        service.addMeeting(meetingTwo);
        service.addMeeting(meetingThree);
        service.addMeeting(meetingFour);

        List<Meeting> expectedMeetings = new ArrayList<>();
        expectedMeetings.add(meetingOne);
        expectedMeetings.add(meetingTwo);
        expectedMeetings.add(meetingThree);
        expectedMeetings.add(meetingFour);

        assertThat(
                service.getMeetings(),
                IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray())
        );
    }

    @Test
    // On rajoute un meeting (One, Two, Three, Four) à la liste complète des réunions "meetings" un à un et qui vérifie à chaque fois qu'il est bien présent.
    public void addMeetings(){
        service.addMeeting(meetingOne);
        assertTrue(service.getMeetings().contains(meetingOne));
        service.addMeeting(meetingTwo);
        assertTrue(service.getMeetings().contains(meetingTwo));
        service.addMeeting(meetingThree);
        assertTrue(service.getMeetings().contains(meetingThree));
        service.addMeeting(meetingFour);
        assertTrue(service.getMeetings().contains(meetingFour));

        assertEquals(meetingOne.getDate(), service.getMeetings().get(0).getDate());
        assertEquals(meetingTwo.getDate(), service.getMeetings().get(1).getDate());
        assertEquals(meetingThree.getDate(), service.getMeetings().get(2).getDate());
        assertEquals(meetingFour.getDate(), service.getMeetings().get(3).getDate());

        assertEquals(meetingOne.getRoom(), service.getMeetings().get(0).getRoom());
        assertEquals(meetingTwo.getRoom(), service.getMeetings().get(1).getRoom());
        assertEquals(meetingThree.getRoom(), service.getMeetings().get(2).getRoom());
        assertEquals(meetingFour.getRoom(), service.getMeetings().get(3).getRoom());

        assertEquals(meetingOne.getSubject(), service.getMeetings().get(0).getSubject());
        assertEquals(meetingTwo.getSubject(), service.getMeetings().get(1).getSubject());
        assertEquals(meetingThree.getSubject(), service.getMeetings().get(2).getSubject());
        assertEquals(meetingFour.getSubject(), service.getMeetings().get(3).getSubject());

        assertEquals(meetingOne, service.getMeetings().get(0));
        assertEquals(meetingTwo, service.getMeetings().get(1));
        assertEquals(meetingThree, service.getMeetings().get(2));
        assertEquals(meetingFour, service.getMeetings().get(3));

        assertTrue(
                service.getMeetings().get(0).getUsers().containsAll(meetingOne.getUsers())
        );
        assertTrue(
                service.getMeetings().get(1).getUsers().containsAll(meetingTwo.getUsers())
        );
        assertTrue(
                service.getMeetings().get(2).getUsers().containsAll(meetingThree.getUsers())
        );
        assertTrue(
                service.getMeetings().get(3).getUsers().containsAll(meetingFour.getUsers())
        );
    }


    @Test
    // On rajoute un meeting (One, Two, Three, Four) à la liste complète des réunions "meetings" un à un et qui vérifie à chaque fois qu'il est bien présent.
    // On supprime un meeting un à un et on vérifie à chaque fois qu'il n'est plus dans la liste.
    public void deleteMeetings(){
        service.addMeeting(meetingOne);
        assertTrue(service.getMeetings().contains(meetingOne));
        service.addMeeting(meetingTwo);
        assertTrue(service.getMeetings().contains(meetingTwo));
        service.addMeeting(meetingThree);
        assertTrue(service.getMeetings().contains(meetingThree));
        service.addMeeting(meetingFour);
        assertTrue(service.getMeetings().contains(meetingFour));

        service.deleteMeeting(meetingOne);
        assertFalse(service.getMeetings().contains(meetingOne));
        assertTrue(service.getMeetings().contains(meetingTwo));
        assertTrue(service.getMeetings().contains(meetingThree));
        assertTrue(service.getMeetings().contains(meetingFour));

        service.deleteMeeting(meetingTwo);
        assertFalse(service.getMeetings().contains(meetingOne));
        assertFalse(service.getMeetings().contains(meetingTwo));
        assertTrue(service.getMeetings().contains(meetingThree));
        assertTrue(service.getMeetings().contains(meetingFour));

        service.deleteMeeting(meetingThree);
        assertFalse(service.getMeetings().contains(meetingOne));
        assertFalse(service.getMeetings().contains(meetingTwo));
        assertFalse(service.getMeetings().contains(meetingThree));
        assertTrue(service.getMeetings().contains(meetingFour));

        service.deleteMeeting(meetingFour);
        assertFalse(service.getMeetings().contains(meetingOne));
        assertFalse(service.getMeetings().contains(meetingTwo));
        assertFalse(service.getMeetings().contains(meetingThree));
        assertFalse(service.getMeetings().contains(meetingFour));

    }

    @Test
    // On rajoute les 4 meetings (One, Two, Three, Four) à la liste complète des réunions "meetings".
    // On leur attribue un index.
    // On trie les réunions selon leur index.
    public void sortMeetingsByDatetime(){
        service.addMeeting(meetingTwo);
        service.addMeeting(meetingFour);
        service.addMeeting(meetingOne);
        service.addMeeting(meetingThree);

        assertEquals(meetingOne, service.getMeetings().get(2));
        assertEquals(meetingTwo, service.getMeetings().get(0));
        assertEquals(meetingThree, service.getMeetings().get(3));
        assertEquals(meetingFour, service.getMeetings().get(1));

        Collections.sort(service.getMeetings());
        assertEquals(meetingOne, service.getMeetings().get(0));
        assertEquals(meetingTwo, service.getMeetings().get(1));
        assertEquals(meetingThree, service.getMeetings().get(2));
        assertEquals(meetingFour, service.getMeetings().get(3));
    }



}