package edu.cgl.sirius.commons.connectionpool.config;

import edu.cgl.sirius.commons.connectionpool.config.impl.ConnectionPoolImpl;

import java.sql.SQLException;

public class ExperimentConnectionImpl {
    public static void main(String[] args) throws SQLException {
        ConnectionPoolImpl.getInstance("postgresql");
    }
}
