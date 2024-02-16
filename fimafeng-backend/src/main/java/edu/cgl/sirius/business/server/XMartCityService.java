package edu.cgl.sirius.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cgl.sirius.business.dto.Student;
import edu.cgl.sirius.business.dto.Students;
import edu.cgl.sirius.commons.Request;
import edu.cgl.sirius.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.group FROM \"ezip-ing1\".students t"),
        INSERT_STUDENT("INSERT into \"ezip-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)");

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
                    /*
                     * Le build permet de faire Ã§a :
                     * student.setName(resultSet.getString("name"));
                     * student.setFirstname(resultSet.getString("firstname"));
                     * student.setGroup(resultSet.getString("group"));
                     */
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
