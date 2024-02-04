package edu.cgl.sirius.backend;

import java.io.IOException;
import java.sql.SQLException;

public class MainBackendServer {

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        System.out.println("1");
        final CoreBackendServer coreBackendServer = new CoreBackendServer();
        System.out.println("2");
        coreBackendServer.join();
    }

}
