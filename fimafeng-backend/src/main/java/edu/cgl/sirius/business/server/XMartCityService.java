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

        return response;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Response response = null;
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://172.31.249.180:5432/\"ezip-ing1\"?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
                    "pgil",
                    "ezipspirit");

            PreparedStatement ps = c
                    .prepareStatement("SELECT t.name, t.firstname, t.group FROM \\\"ezip-ing1\\\".students t");
            ResultSet rs = ps.executeQuery();
            System.out.println("ResultSet: " + rs);
        } catch (Exception e) {
            System.out.println("AHHHHH");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

}
