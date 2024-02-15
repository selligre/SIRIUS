package edu.cgl.sirius.backend;

import java.io.IOException;
import java.sql.SQLException;

public class MainBackendServer {

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        final CoreBackendServer coreBackendServer = new CoreBackendServer();
        coreBackendServer.join();
    }

}
