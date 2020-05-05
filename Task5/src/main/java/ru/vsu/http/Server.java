package ru.vsu.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.http.entities.Request;
import ru.vsu.http.entities.Response;
import ru.vsu.http.entities.Session;
import ru.vsu.http.handlers.HttpHandler;
import ru.vsu.http.handlers.HttpRequestHandler;
import ru.vsu.http.thread.ClientTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final String _LOGGER_SOCKET_IS_BROKEN = "Socket is broken";
    public static final String UUID = "uuid";

    private int port;
    private int bufferSize;
    private int threadPoolSize;

    private String folder;
    private HttpHandler httpHandler;
    private HttpRequestHandler httpRequestHandler;

    private ServerSocket serverSocket;
    private Map<UUID, Session> sessionMap = new HashMap<>();
    private final ExecutorService pool;

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static final String LOGGER_START_MESSAGE = "EntryPoint started at %s port";
    private static final String LOGGER_SESSION_MESSAGE = "Session for user: %s with first visit at %s";
    private static final String LOGGER_LISTENING_ERROR_MESSAGE = "Error in handling request";
    private static final String LOGGER_GOT_REQUEST_MESSAGE = "Got request";
    private static final String LOGGER_WRONG_REQUEST_MESSAGE = "Got wrong request";



    public Server(int port, String folder, int bufferSize, int threadPoolSize) {
        this.bufferSize = bufferSize;
        this.threadPoolSize = threadPoolSize;
        this.port = port;
        this.folder = folder;
        this.pool = Executors.newFixedThreadPool(this.threadPoolSize);
        this.httpHandler = new HttpHandler(folder);
        this.httpRequestHandler = new HttpRequestHandler(bufferSize);
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public int getPort() {
        return port;
    }

    public String getFolder() {
        return folder;
    }


    public void start() {
        LOGGER.info(String.format(LOGGER_START_MESSAGE, port));
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            for (int i = 0; i < threadPoolSize; i++) {
                pool.execute(new ClientTask(this));
            }
            Thread.currentThread().join();
        } catch (IOException e) {
            LOGGER.error(LOGGER_LISTENING_ERROR_MESSAGE, e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void listen() {
        try(Socket socket = serverSocket.accept(); InputStream inputStream = socket.getInputStream(); OutputStream outputStream = socket.getOutputStream()) {
            LOGGER.info(LOGGER_GOT_REQUEST_MESSAGE);
            Request request = httpRequestHandler.getRequestFromInputStream(inputStream);
            Response response = new Response();
            Session session = getOrCreateSessionFromRequest(request, response);
            LOGGER.info(String.format(LOGGER_SESSION_MESSAGE, session.getUuid(), session.getFirstVisitDate()));
            httpHandler.handle(request, response);
            response.setCookie(UUID, session.getUuid().toString());
            new ResponseWriter(bufferSize, outputStream).write(response);
        } catch (SocketException socketException) {
            LOGGER.error(LOGGER_WRONG_REQUEST_MESSAGE, socketException);
        } catch (IOException e) {
            LOGGER.error(_LOGGER_SOCKET_IS_BROKEN, e);
        }
    }

    private synchronized Session getOrCreateSessionFromRequest(Request request, Response response) {
        UUID uuid = httpHandler.getUUID(request, response);
        Session session = sessionMap.get(uuid);
        if (session != null) {
            return session;
        }

        session = new Session(uuid);
        sessionMap.put(uuid, session);
        return session;
    }
}
