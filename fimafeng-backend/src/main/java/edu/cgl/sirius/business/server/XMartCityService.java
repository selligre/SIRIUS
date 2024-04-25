package edu.cgl.sirius.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.AnnounceLocation;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.business.dto.AnnouncesLocation;
import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.business.dto.Users;
import edu.cgl.sirius.commons.Request;
import edu.cgl.sirius.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {

        SELECT_ALL_USERS("SELECT * FROM users;"),
        SELECT_ALL_ANNOUNCES("SELECT * FROM announces;"),
        SELECT_ANNOUNCES_FOR_LOCATION(
                "SELECT announce_id, ref_author_id, publication_date, status, type, title, description, date_time_start, duration, date_time_end, is_recurrent, slots_number, slots_available, price FROM announces JOIN locations ON ref_location_id = location_id WHERE name = '?';"),
        SELECT_ANNOUNCES_FOR_TAG(
                "SELECT announce_id, ref_author_id, publication_date, status, type, title, description, date_time_start, duration, date_time_end, is_recurrent, slots_number, slots_available, price, ref_location_id FROM announces JOIN announce_tags ON ref_announce_id = announce_id WHERE ref_tag_id = 1;");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;

    public static final XMartCityService getInstance() {
        if (inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException {
        Response response = null;

        PreparedStatement pstmt;
        Statement stmt;
        ResultSet res;
        ObjectMapper mapper;
        int rows;
        try {
            switch (request.getRequestOrder()) {
                // Premier essai avec la bdd de test, inutile maintenant mais on garde
                // temporairement pour l'exemple
                case "SELECT_ALL_USERS":
                    stmt = connection.createStatement();
                    res = stmt.executeQuery(Queries.SELECT_ALL_USERS.query);
                    Users users = new Users();
                    while (res.next()) {
                        User user = new User().build(res);
                        users.add(user);
                    }
                    mapper = new ObjectMapper();

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(users));
                    System.out.println(response.getResponseBody());
                    break;

                case "SELECT_ALL_ANNOUNCES":
                    stmt = connection.createStatement();
                    res = stmt.executeQuery(Queries.SELECT_ALL_ANNOUNCES.query);
                    Announces announces = new Announces();
                    while (res.next()) {
                        Announce announce = new Announce().build(res);
                        announces.add(announce);
                    }
                    mapper = new ObjectMapper();

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(announces));
                    System.out.println(response.getResponseBody());
                    break;

                case "SELECT_ANNOUNCES_FOR_LOCATION":
                    mapper = new ObjectMapper();
                    AnnounceLocation location = mapper.readValue(request.getRequestBody(), AnnounceLocation.class);
                    pstmt = connection.prepareStatement(Queries.SELECT_ANNOUNCES_FOR_LOCATION.query);
                    pstmt.setString(1, location.getLocation_id());
                    res = pstmt.executeQuery();
                    // stmt = connection.createStatement();
                    // res = stmt.executeQuery(Queries.SELECT_ANNOUNCES_FOR_LOCATION.query);

                    AnnouncesLocation announcesLocation = new AnnouncesLocation();
                    while (res.next()) {
                        AnnounceLocation announceLocation = new AnnounceLocation().build(res);
                        announcesLocation.add(announceLocation);
                    }
                    mapper = new ObjectMapper();

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(announcesLocation));
                    System.out.println(response.getResponseBody());
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
