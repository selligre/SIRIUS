package edu.cgl.sirius.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.business.dto.Student;
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
                "SELECT * FROM announces JOIN locations ON ref_location_id = location_id WHERE name = ?;"),
        INSERT_ANNOUNCE(
                "INSERT INTO announces VALUES(DEFAULT,?::int,?::timestamp,?,?,?,?,?::timestamp,?::float,?::timestamp,?::boolean,?::smallint,?::smallint,?::float,?::int  ) RETURNING announce_id;"),
        INSERT_ANNOUNCE_TAGS("INSERT INTO announce_tags VALUES (DEFAULT, ?::int, ?::int)");

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
        Announce announce;

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
                        announce = new Announce().build(res);
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
                    Announce announceL = mapper.readValue(request.getRequestBody(), Announce.class);
                    pstmt = connection.prepareStatement(Queries.SELECT_ANNOUNCES_FOR_LOCATION.query);
                    pstmt.setString(1, announceL.getRef_location_id());
                    res = pstmt.executeQuery();

                    mapper = new ObjectMapper();
                    Announces announcesLocation = new Announces();
                    while (res.next()) {
                        Announce announceLocation = new Announce().build(res);
                        announcesLocation.add(announceLocation);
                    }

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(announcesLocation));
                    System.out.println(response.getResponseBody());
                    break;

                case "INSERT_ANNOUNCE":
                    mapper = new ObjectMapper();
                    announce = mapper.readValue(request.getRequestBody(), Announce.class);
                    pstmt = connection.prepareStatement(Queries.INSERT_ANNOUNCE.query);
                    pstmt.setString(1, announce.getRef_author_id());
                    pstmt.setString(2, announce.getPublication_date());
                    pstmt.setString(3, announce.getStatus());
                    pstmt.setString(4, announce.getType());
                    pstmt.setString(5, announce.getTitle());
                    pstmt.setString(6, announce.getDescription());
                    pstmt.setString(7, announce.getDate_time_start());
                    pstmt.setString(8, announce.getDuration());
                    pstmt.setString(9, announce.getDate_time_end());
                    pstmt.setString(10, announce.getIs_recurrent());
                    pstmt.setString(11, announce.getSlots_number());
                    pstmt.setString(12, announce.getSlots_available());
                    pstmt.setString(13, announce.getPrice());
                    pstmt.setString(14, announce.getRef_author_id());

                    res = pstmt.executeQuery();

                    if (res.next()) {
                        String id = String.valueOf(res.getInt("announce_id"));
                        System.out.println("ID récupéré : " + id);
                        System.out.println(announce.getAnnounceTags());
                    }

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(announce));
                    System.out.println(response.getResponseBody());
                    break;

                case "INSERT_ANNOUNCE_TAGS":

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
