package function;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import type.of.task.Epic;

import type.of.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;


class HttpTaskServerTest extends TaskManagerTest {
    int port = 8080;
    TaskManager manager = Managers.getDefault();
    HttpTaskServer taskServer = new HttpTaskServer(manager);

    Gson gson = HttpTaskServer.getGson();


    HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        taskServer.start();


    }

    @AfterEach
    public void stop() {
        taskServer.stop();
    }

    private HttpRequest createRequest(String taskJson,String path) {
        URI url = URI.create("http://localhost:8080/"+path);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json;charset=utf-8")
                .build();
        return request;


    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", 30,
                LocalDateTime.now());
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        String path = "tasks";
        HttpRequest request = createRequest(taskJson,path);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }


    @Test
    public void addEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("name", "descr");
        String epicJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        String path = "epics";
        HttpRequest request = createRequest(epicJson,path);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Override
    protected TaskManager createTaskManager() {
        return Managers.getDefault();
    }
}