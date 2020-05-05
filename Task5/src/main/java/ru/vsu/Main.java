package ru.vsu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.http.Server;
import ru.vsu.io.PropertiesLoader;
import ru.vsu.io.PropertiesLoaderFromConsole;

public class Main {

    private static final String FOLDER_KEY = "folder";
    private static final String PORT_KEY = "port";
    private static final String BUFFER_KEY = "buffer";
    public static final String THREAD_POOL_SIZE_KEY = "threadPoolSize";
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String PROPERTIES_PATH = "/app.properties";
    private static final String LOGGER_NO_SUCH_FIELD_MESSAGE = "%s does not contain necessary field";

    public static void main(String[] argv) {
        try {
            PropertiesLoader propertiesLoader = new PropertiesLoaderFromConsole();
            Server server = new Server(Integer.parseInt(propertiesLoader.getProperty(PORT_KEY)),
                    propertiesLoader.getProperty(FOLDER_KEY),
                    Integer.parseInt(propertiesLoader.getProperty(BUFFER_KEY)),
                    Integer.parseInt(propertiesLoader.getProperty(THREAD_POOL_SIZE_KEY))
                    );
            server.start();
        } catch (NoSuchFieldException e) {
            logger.error(String.format(LOGGER_NO_SUCH_FIELD_MESSAGE, PROPERTIES_PATH), e);
        }
    }
}
