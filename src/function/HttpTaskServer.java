package function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private final TaskManager manager;

    private HttpServer httpServer;
    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    public HttpTaskServer(TaskManager manager) throws IOException {
        int port = 8080;
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.manager = manager;

        httpServer.createContext("/tasks", new TasksHandler(manager));
        httpServer.createContext("/epics", new EpicsHandler(manager));
        httpServer.createContext("/subtasks", new SubtasksHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public void start() {
        httpServer.start();
//        System.out.println("Сервер запущен");
    }

    public void stop() {
        httpServer.stop(0);
//        System.out.println("Сервер остановлен");
    }

    public static void main(String[] args) throws IOException {

        HttpTaskServer server = new HttpTaskServer(Managers.getDefault());
        server.start();


    }

    public static Gson getGson() {
        return gson;
    }

    public void deleteTasks() {
        manager.removeAllTask();
    }

    public void deleteEpic() {
        manager.removeAllEpic();
    }

}
