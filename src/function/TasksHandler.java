package function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import type.of.task.Task;
import type.of.task.TaskDTO;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class TasksHandler extends BaseHttpHandler implements HttpHandler {


    public TasksHandler(TaskManager taskManger) {
        super(taskManger);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET" -> getHandle(exchange);
            case "POST" -> postHandle(exchange);
            case "PUT" -> putHandle(exchange);
            case "DELETE" -> deleteHandle(exchange);
            default -> sendGetWrongMethod(exchange);
        }

    }

    private void getHandle(HttpExchange exchange) {
        String uri = exchange.getRequestURI().toString();
        String[] split = uri.split("/");
        if (split.length == 2) {
            List<TaskDTO> tasksDTO = taskManger.showUpTask().values()
                    .stream()
                    .map(task -> {
                        TaskDTO dto = TaskDTO.convertToDTO(task);
                        return dto;
                    })
                    .collect(Collectors.toList());

            String response = gson.toJson(tasksDTO);
            sendText(exchange, response);


        } else {
            try {
                int id = Integer.parseInt(split[2]);
                Task task = taskManger.searchTaskById(id);
                TaskDTO dto = TaskDTO.convertToDTO(task);
                String response = gson.toJson(dto);
                sendText(exchange, response);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }

        }


    }

    private void postHandle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();
        String[] split = uri.split("/");
        String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        TaskDTO transfer = gson.fromJson(json, TaskDTO.class);
        if (split.length == 2) {
            Task task = new Task(transfer.name, transfer.description, (int) transfer.duration.toMinutes(),
                    transfer.startTime, transfer.status);
            try {
                taskManger.addNewTask(task);
                TaskDTO dto = TaskDTO.convertToDTO(task);
                String response = "Добавлена: " + gson.toJson(dto);
                sendText(exchange, response);
            } catch (ConcurrentTaskException e) {
                sendHasInteractions(exchange);
            } catch (DuplicateTaskException e) {
                sendHasDuplicateTask(exchange);
            }

        } else {
            sendGetWrongMethod(exchange);
        }


    }

    private void putHandle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();
        String[] split = uri.split("/");
        if (split.length == 2) {
            String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            TaskDTO transfer = gson.fromJson(json, TaskDTO.class);
            try {
                Task oldTask = taskManger.getTaskById(transfer.id);
                Task newTask = new Task(transfer.name, transfer.description, transfer.status,
                        (int) transfer.duration.toMinutes(), transfer.startTime, transfer.id);
                taskManger.updateTask(oldTask, newTask);
                TaskDTO dto = TaskDTO.convertToDTO(newTask);
                String response = "Обновили: " + gson.toJson(dto);
                sendSuccessfulUpdate(exchange, response);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }

        } else {
            sendGetWrongMethod(exchange);
        }


    }


    private void deleteHandle(HttpExchange exchange) {
        String uri = exchange.getRequestURI().toString();
        String[] spit = uri.split("/");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
        String response = "Удалили: ";
        if (spit.length == 3) {
            try {
                Task task = taskManger.searchTaskById(Integer.parseInt(spit[2]));
                TaskDTO dto = TaskDTO.convertToDTO(task);
                response = response + gson.toJson(dto);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }
            taskManger.removeTaskById(Integer.parseInt(spit[2]));
            sendText(exchange, response);

        } else {
            sendGetWrongMethod(exchange);
        }

    }


}
