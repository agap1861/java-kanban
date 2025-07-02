package function;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import type.of.task.Status;
import type.of.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    int port = 8080;

    TaskManager manager = Managers.getDefault();
    HttpTaskServer taskServer = new HttpTaskServer(manager);

    Gson gson = HttpTaskServer.getGson();


    HttpTaskServerTest() throws IOException {
    }
    @BeforeEach
    public void clear(){
        taskServer.start();
        manager.removeAllTask();
        manager.removeAllEpic();
        manager.removeAllSubtask();

    }
    @AfterEach
    public void stop(){
        taskServer.stop();
    }
    @Test
    public void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2", "Testing task 2",30,
                LocalDateTime.now());
        // конвертируем её в JSON
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json;charset=utf-8")
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200,response.statusCode());


    }

}