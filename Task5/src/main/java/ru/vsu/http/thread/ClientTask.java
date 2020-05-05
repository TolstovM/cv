package ru.vsu.http.thread;

import ru.vsu.http.Server;

public class ClientTask implements Runnable {
    private Server server;

    public ClientTask(Server server) {
        this.server = server;
    }


    @Override
    public void run() {
        while (true) {
            this.server.listen();
        }
    }
}
