package fr.upec.episen.sirius.fimafeng;

import fr.upec.episen.sirius.fimafeng.implementations.ParquetReaderImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class BigdataConnectorApplication implements CommandLineRunner {

    @Autowired
    ParquetReaderImplementation parquetReaderImplementation;

    private static Logger LOGGER = Logger.getLogger(BigdataConnectorApplication.class.getName());

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(BigdataConnectorApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        LOGGER.info("BigdataConnectorApplication started");

        if(args.length != 1){
            LOGGER.severe("BigdataConnectorApplication requires exactly one argument");
            System.exit(1);
        }

        String path = args[0];
        LOGGER.info("Starting process with file " + path);
        parquetReaderImplementation.importParquet(path);


    }
}