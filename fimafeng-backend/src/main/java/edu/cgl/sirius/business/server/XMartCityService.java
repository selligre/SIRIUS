package edu.cgl.sirius.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cgl.sirius.business.dto.Student;
import edu.cgl.sirius.business.dto.Students;
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
        SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.group FROM \"ezip-ing1\".students t"),
        INSERT_STUDENT("INSERT into \"ezip-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)"),

        SELECT_ALL_USERS("SELECT * FROM \"user\";"),
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

        if (request.getRequestOrder().equals("INSERT_STUDENT")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Student student = mapper.readValue(request.getRequestBody(), Student.class);
                PreparedStatement preparedStatement = connection.prepareStatement(Queries.INSERT_STUDENT.query);
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getFirstname());
                preparedStatement.setString(3, student.getGroup());
                int rows = preparedStatement.executeUpdate();
                return new Response(request.getRequestId(), "{\"student_id\": " + rows + " }");
            } catch (Exception E) {
                System.out.println(E.getMessage());
            }
        } else if (request.getRequestOrder().equals("SELECT_ALL_STUDENTS")) {
            try {
                PreparedStatement statement_select = connection.prepareStatement(Queries.SELECT_ALL_STUDENTS.query);
                ResultSet resultSet = statement_select.executeQuery();
                Students student_List = new Students();
                while (resultSet.next()) {
                    Student student = new Student().build(resultSet);
                    
                    student.setName(resultSet.getString("name"));
                    student.setFirstname(resultSet.getString("firstname"));
                    student.setGroup(resultSet.getString("group"));
                    
                    student_List.add(student);
                }
                ObjectMapper mapper = new ObjectMapper();
                return new Response(request.getRequestId(), mapper.writeValueAsString(student_List));

            } catch (Exception EX) {
                System.out.println(EX.getMessage());
            }
        }
        return response;
    }

}
