package edu.cgl.sirius.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        SELECT_ALL_STUDENTS("SELECT t.name, t.first_name, t.group FROM \"ezip-ing1\".Users t"),
        INSERT_STUDENT("INSERT into \"ezip-ing1\".Users (\"name\", \"first_name\", \"group\") values (?, ?, ?)"),

        SELECT_ALL_USERS("SELECT first_name, last_name, display_name, user_type, email, password FROM public.user;"),
        SELECT_ALL_ACTIVITIES("SELECT * FROM announce AS a LEFT JOIN activity AS a1 ON a.announce_id = a1.ref_announce_id;"),
        INSERT_USER("INSERT INTO \"user\" (first_name, last_name, display_name, user_type, email, password) VALUES ('Gilles', 'MEUNIER', 'selligre', 'admin', 'selligre@gmail.com', 'password1234');");

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
                // Premier essai avec la bdd de test, inutile maintenant mais on garde temporairement pour l'exemple
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
                    break;

                case "INSERT_STUDENT" :
                    mapper = new ObjectMapper();
                    Student student = mapper.readValue(request.getRequestBody(), Student.class);
                    pstmt = connection.prepareStatement(Queries.INSERT_STUDENT.query);
                    pstmt.setString(1, student.getName());
                    pstmt.setString(3, student.getGroup());
                    rows = pstmt.executeUpdate();

                    response = new Response();
                    response.setRequestId(request.getRequestId());
                    response.setResponseBody("{\"student_id\": " + rows + " }");
                    break;
                
                default:
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
