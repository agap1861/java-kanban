package function;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class BaseHttpHandler<T extends TaskManager> {
    protected T taskManger;

    protected  BaseHttpHandler(T taskManager) {
        this.taskManger = taskManager;
    }


    protected void sendText(HttpExchange exchange, String text) {
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=utf-8");
        try {
            exchange.sendResponseHeaders(200, text.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(text.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void sendNotFound(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Content-Type", "text/plain;charset=utf-8");
        String response = "Ресурс не найден!";
        try {
            exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(response.getBytes(StandardCharsets.UTF_8));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendHasInteractions(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Content-Type", "text/plain;charset=utf-8");
        String response = "Задача пересекается и имеющимися";
        try {
            exchange.sendResponseHeaders(406, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void sendHasDuplicateTask(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Content-Type", "text/plain;charset=utf-8");
        String response = "Такая задача уже имеется";
        try {
            exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendGetWrongMethod(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Content-Type", "text/plain;charset=utf-8");
        String response = "Не корректный запрос";
        try {
            exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void sendSuccessfulUpdate(HttpExchange exchange,String text) {
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=utf-8");
        try {
            exchange.sendResponseHeaders(201, text.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(text.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
