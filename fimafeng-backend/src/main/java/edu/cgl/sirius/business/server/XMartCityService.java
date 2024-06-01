package edu.cgl.sirius.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.business.dto.Location;
import edu.cgl.sirius.business.dto.Locations;
import edu.cgl.sirius.business.dto.Tag;
import edu.cgl.sirius.business.dto.Tags;
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

        // SELECT Queries
        SELECT_ALL_USERS("SELECT * FROM users;"),
        SELECT_ALL_USERS_EMAILS("SELECT email FROM users;"),
        SELECT_ALL_ANNOUNCES("SELECT * FROM announces;"),
        SELECT_ANNOUNCES_FOR_LOCATION(
                "SELECT * FROM announces JOIN locations ON ref_location_id = location_id WHERE location_id = ?;"),
        SELECT_ANNOUNCES_FOR_TAG_ID(
                "SELECT announce_id, ref_author_id, publication_date, status, type, title, description, date_time_start, duration, date_time_end, is_recurrent, slots_number, slots_available, price, ref_location_id FROM announces JOIN announce_tags ON announce_id = ref_announce_id WHERE ref_tag_id IN (?::int, ?::int, ?::int, ?::int, ?::int) GROUP BY announce_id HAVING COUNT(DISTINCT ref_tag_id) = ?::int;"),
        SELECT_ALL_LOCATIONS("SELECT * FROM locations"),
        SELECT_ALL_TAGS("SELECT * FROM tags"),

        // INSERT Queries
        INSERT_ANNOUNCE(
                "INSERT INTO announces VALUES(DEFAULT, ?::int, ?::timestamp, ?, ?, ?, ?, ?::timestamp, ?::float ,?::timestamp ,?::boolean, ?::smallint, ?::smallint, ?::float, ?::int) RETURNING announce_id;"),
        INSERT_ANNOUNCE_TAGS("INSERT INTO announce_tags VALUES (DEFAULT, ?::int, ?::int);"),
        INSERT_USER(
                "INSERT INTO users VALUES (DEFAULT, ?, ?, 'user', ?, ?, ?) RETURNING user_id;");

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
        int rows; // TODO: pourquoi garder si jamais utiliser ?
        try {
            switch (request.getRequestOrder()) {
                // Premier essai avec la bdd de test, inutile maintenant mais on garde
                // temporairement pour l'exemple

                // SELECT QUERRIES
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

                case "SELECT_ALL_USERS_EMAILS":
                    stmt = connection.createStatement();
                    res = stmt.executeQuery(Queries.SELECT_ALL_USERS.query);
                    Users usersEmails = new Users();
                    while (res.next()) {
                        User user = new User().build(res);
                        usersEmails.add(user);
                    }
                    mapper = new ObjectMapper();
                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(usersEmails));
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
                    Announce announceL = mapper.readValue(request.getRequestBody(), Announce.class);
                    pstmt = connection.prepareStatement(Queries.SELECT_ANNOUNCES_FOR_LOCATION.query);
                    // pstmt.setString(1, announceL.getRef_location_id());
                    pstmt.setInt(1, Integer.parseInt(announceL.getRef_location_id()));
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

                case "SELECT_ANNOUNCES_FOR_TAG_ID":
                    mapper = new ObjectMapper();
                    Announce announceTag = mapper.readValue(request.getRequestBody(), Announce.class);
                    pstmt = connection.prepareStatement(Queries.SELECT_ANNOUNCES_FOR_TAG_ID.query);
                    pstmt.setString(1, announceTag.getAnnounceTags().get(0));
                    pstmt.setString(2, announceTag.getAnnounceTags().get(1));
                    pstmt.setString(3, announceTag.getAnnounceTags().get(2));
                    pstmt.setString(4, announceTag.getAnnounceTags().get(3));
                    pstmt.setString(5, announceTag.getAnnounceTags().get(4));
                    pstmt.setString(6, Long
                            .toString(announceTag.getAnnounceTags().stream().filter(value -> value != null).count()));
                    res = pstmt.executeQuery();
                    mapper = new ObjectMapper();
                    Announces announces2 = new Announces();
                    while (res.next()) {
                        Announce announce = new Announce().build(res);
                        announces2.add(announce);
                    }
                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(announces2));
                    System.out.println(response.getResponseBody());
                    break;

                case "SELECT_ALL_LOCATIONS":
                    stmt = connection.createStatement();
                    res = stmt.executeQuery(Queries.SELECT_ALL_LOCATIONS.query);
                    Locations locations = new Locations();
                    while (res.next()) {
                        Location location = new Location().build(res);
                        locations.add(location);
                    }
                    mapper = new ObjectMapper();
                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(locations));
                    System.out.println(response.getResponseBody());
                    break;

                case "SELECT_ALL_TAGS":
                    stmt = connection.createStatement();
                    res = stmt.executeQuery(Queries.SELECT_ALL_TAGS.query);
                    Tags tags = new Tags();
                    while (res.next()) {
                        Tag tag = new Tag().build(res);
                        tags.add(tag);
                    }
                    mapper = new ObjectMapper();

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(tags));
                    System.out.println(response.getResponseBody());
                    break;

                // INSERT QUERRIES
                case "INSERT_ANNOUNCE":
                    mapper = new ObjectMapper();
                    Announce announce = mapper.readValue(request.getRequestBody(), Announce.class);
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
                    pstmt.setString(14, announce.getRef_location_id());
                    res = pstmt.executeQuery();
                    if (res.next()) {
                        String id = String.valueOf(res.getInt("announce_id"));
                        System.out.println("ID récupéré : " + id);
                        for (String tagId : announce.getAnnounceTags()) {
                            System.out.println(tagId);
                            pstmt = connection.prepareStatement(Queries.INSERT_ANNOUNCE_TAGS.query);
                            pstmt.setString(1, id);
                            pstmt.setString(2, tagId);
                            pstmt.executeUpdate();
                        }
                    }
                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(announce));
                    System.out.println(response.getResponseBody());
                    break;

                case "INSERT_ANNOUNCE_TAGS":
                    break;

                case "INSERT_USER":
                    // INSERT INTO users VALUES
                    // (default, 'gilles', 'meunier', 'selligre', 'user', 'selligre@gmail.com',
                    // 'selligre');
                    mapper = new ObjectMapper();
                    User user = mapper.readValue(request.getRequestBody(), User.class);
                    pstmt = connection.prepareStatement(Queries.INSERT_USER.query);
                    pstmt.setString(1, user.getFirst_name());
                    pstmt.setString(2, user.getLast_name());
                    pstmt.setString(3, user.getDisplay_name());
                    pstmt.setString(4, user.getEmail());
                    pstmt.setString(5, user.getPassword());
                    pstmt.executeQuery();
                    res = pstmt.executeQuery();
                    // if (res.next()) {
                    // String id = String.valueOf(res.getInt("user_id"));
                    // System.out.println("ID récupéré : " + id);
                    // for (String tagId : announce.getAnnounceTags()) {
                    // System.out.println(tagId);
                    // pstmt = connection.prepareStatement(Queries.INSERT_ANNOUNCE_TAGS.query);
                    // pstmt.setString(1, id);
                    // pstmt.setString(2, tagId);
                    // pstmt.executeUpdate();
                    // }
                    // }
                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody(mapper.writeValueAsString(user));
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
