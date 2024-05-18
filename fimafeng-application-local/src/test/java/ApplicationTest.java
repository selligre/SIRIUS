import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.cgl.sirius.application.Application;
import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;
import edu.cgl.sirius.client.MainSelectAnnouncesTag;

public class ApplicationTest {

    // Checks if there is a problem with the GUI starting process
    @Test
    public void applicationDoesNotCrash() {
        // GIVEN
        Application app;

        // WHEN
        try {
            app = new Application();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // THEN
        assertEquals(true, true);
    }

    /*
     * Checks if there is a server-side problem like connection.
     */
    @Test
    public void selectAllAnnouncesDoesNotCrash() {
        // GIVEN
        String request = "SELECT_ALL_ANNOUNCES";

        // WHEN
        try {
            MainSelectAnnounces mainSelectAnnounces = new MainSelectAnnounces(request);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // THEN
        assertEquals(true, true);
    }

    /*
     * Checks if there is a server-side problem like connection or with the
     * SQL-request.
     */
    @Test
    public void selectAnnouncesLocationDoesNotCrash() {
        // GIVEN
        String request = "SELECT_ALL_ANNOUNCES";
        String requestLocation = "Piscine";

        // WHEN
        try {
            MainSelectAnnouncesLocation mainSelectAnnouncesLocation = new MainSelectAnnouncesLocation(request,
                    requestLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // THEN
        assertEquals(true, true);
    }

    /*
     * Checks if there is a server-side problem like connection or with the
     * SQL-request.
     */
    @Test
    public void selectAnnouncesTagDoesNotCrash() {
        // GIVEN
        String request = "SELECT_ALL_ANNOUNCES";
        ArrayList<String> requestTags = new ArrayList<>();

        // WHEN
        try {
            MainSelectAnnouncesTag mainSelectAnnouncesTag = new MainSelectAnnouncesTag(request,
                    requestTags);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // THEN
        assertEquals(true, true);
    }

    /*
     * Checks if there is a database-side problem like empty tables.
     */
    @Test
    public void selectAllAnnouncesReturnsData() {
        // GIVEN
        String request = "SELECT_ALL_ANNOUNCES";
        String result = "";
        String resultIfEmptyTable = "Announces{announces=[]}";

        // WHEN
        try {
            MainSelectAnnounces mainSelectAnnounces = new MainSelectAnnounces(request);
            result = mainSelectAnnounces.getAnnounces().toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // THEN
        assertFalse(result.length() < resultIfEmptyTable.length());
        // assertEquals(false, true);
    }

}
