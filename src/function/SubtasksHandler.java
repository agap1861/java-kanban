package function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import type.of.task.Subtask;
import type.of.task.SubtaskDTO;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    protected SubtasksHandler(TaskManager taskManager) {
        super(taskManager);
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
            List<SubtaskDTO> subtasksDTO = taskManger.showUpSubtask().values()
                    .stream()
                    .map(subtask -> {
                        SubtaskDTO dto = SubtaskDTO.convertToDTO(subtask);
                        return dto;
                    })
                    .collect(Collectors.toList());
            String response = gson.toJson(subtasksDTO);
            sendText(exchange, response);

        } else {
            try {
                int id = Integer.parseInt(split[2]);
                Subtask subtask = taskManger.searchSubtaskById(id);
                SubtaskDTO dto = SubtaskDTO.convertToDTO(subtask);

                String response = gson.toJson(dto);
                sendText(exchange, response);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }

        }


    }

    private void postHandle(HttpExchange exchange) throws IOException {

        String uri = exchange.getRequestURI().toString();
        String[] spit = uri.split("/");
        String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        SubtaskDTO transfer = gson.fromJson(json, SubtaskDTO.class);
        if (spit.length == 2) {
            try {
                Subtask subtask = new Subtask(transfer.name, transfer.description,
                        (int) transfer.duration.toMinutes(), transfer.startTime,
                        taskManger.getEpicById(transfer.idEpic));
                taskManger.addNewSubtask(subtask);
                SubtaskDTO dto = SubtaskDTO.convertToDTO(subtask);
                String response = "Добавили: " + gson.toJson(dto);
                sendText(exchange, response);
            } catch (ConcurrentTaskException e) {
                sendHasInteractions(exchange);
            } catch (DuplicateTaskException e) {
                sendHasDuplicateTask(exchange);
            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }
        } else {
            sendGetWrongMethod(exchange);
        }


    }

    private void putHandle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();
        String[] spit = uri.split("/");
        String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        SubtaskDTO transfer = gson.fromJson(json, SubtaskDTO.class);
        if (spit.length == 2) {
            try {
                Subtask oldSubtask = taskManger.getSubtaskById(transfer.id);
                Subtask newSubtask = new Subtask(transfer.name, transfer.description, transfer.status,
                        (int) transfer.duration.toMinutes(), transfer.startTime, transfer.id,
                        taskManger.getEpicById(transfer.idEpic));
                taskManger.updateSubtask(oldSubtask, newSubtask);
                SubtaskDTO dto = SubtaskDTO.convertToDTO(newSubtask);
                String response = "Успешно обновили " + gson.toJson(dto);
                sendSuccessfulUpdate(exchange, response);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            } catch (ConcurrentTaskException e) {
                sendHasInteractions(exchange);
            }

        }

    }

    private void deleteHandle(HttpExchange exchange) {
        String uri = exchange.getRequestURI().toString();
        String[] spit = uri.split("/");
        String response = "Удалили: ";
        if (spit.length == 3) {
            try {
                Subtask subtask = taskManger.getSubtaskById(Integer.parseInt(spit[2]));
                SubtaskDTO dto = SubtaskDTO.convertToDTO(subtask);
                response = response + gson.toJson(dto);
                taskManger.removeSubtaskById(Integer.parseInt(spit[2]));
                sendText(exchange, response);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }

        } else {
            sendGetWrongMethod(exchange);
        }

    }


}
