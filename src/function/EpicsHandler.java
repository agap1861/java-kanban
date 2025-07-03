package function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import type.of.task.Epic;
import type.of.task.EpicDTO;
import type.of.task.SubtaskDTO;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    protected EpicsHandler(TaskManager taskManager) {
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
            List<EpicDTO> epicsDTO = taskManger.showUpEpic().values()
                    .stream()
                    .map(epic -> {
                        EpicDTO dto = EpicDTO.convertToDTO(epic);
                        return dto;
                    })
                    .collect(Collectors.toList());
            String response = gson.toJson(epicsDTO);
            sendText(exchange, response);
        } else if (split.length == 3) {
            try {
                int id = Integer.parseInt(split[2]);
                Epic epic = taskManger.searchEpicById(id);
                EpicDTO dto = EpicDTO.convertToDTO(epic);
                String response = gson.toJson(dto);
                sendText(exchange, response);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }

        } else if (split.length == 4 && split[3].equals("subtasks")) {
            int id = Integer.parseInt(split[2]);
            try {
                Epic epic = taskManger.getEpicById(id);
                List<SubtaskDTO> listSubtaskOfEpic = taskManger.listSubtaskOfEpic(epic)
                        .stream()
                        .map(subtask -> {
                            SubtaskDTO dto = SubtaskDTO.convertToDTO(subtask);
                            return dto;
                        })
                        .collect(Collectors.toList());
                String response = gson.toJson(listSubtaskOfEpic);
                sendText(exchange, response);
            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }


        }


    }

    private void postHandle(HttpExchange exchange) throws IOException {

        String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        EpicDTO transfer = gson.fromJson(json, EpicDTO.class);

        Epic epic = new Epic(transfer.name, transfer.description);
        try {
            taskManger.addNewEpic(epic);
            EpicDTO dto = new EpicDTO();
            dto.id = epic.getId();
            dto.name = epic.getName();
            dto.description = epic.getDescription();
            dto.status = epic.getStatus();
            String response = "Добавили : " + gson.toJson(dto);
            sendText(exchange, response);
        } catch (DuplicateTaskException e) {
            sendHasDuplicateTask(exchange);
        } catch (Exception e) {
            System.out.println("Ошибка");
        }


    }

    private void putHandle(HttpExchange exchange) throws IOException {
        String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        EpicDTO transfer = gson.fromJson(json, EpicDTO.class);
        String uri = exchange.getRequestURI().toString();
        String[] split = uri.split("/");
        if (split.length == 2) {
            try {
                Epic oldEic = taskManger.getEpicById(transfer.id);
                Epic newEpic = Epic.createdEpicWithStatus(transfer.name, transfer.description,
                        transfer.status, transfer.id);
                taskManger.updateEpic(oldEic, newEpic);
                EpicDTO dto = EpicDTO.convertToDTO(newEpic);
                String response = "Успешно обновили " + gson.toJson(dto);
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

        String response = "Удалили: ";
        if (spit.length == 3) {
            try {
                Epic epic = taskManger.getEpicById(Integer.parseInt(spit[2]));
                EpicDTO dto = EpicDTO.convertToDTO(epic);
                ;
                response = response + gson.toJson(dto);

            } catch (NotFoundException e) {
                sendNotFound(exchange);
            }
            taskManger.removeEpicById(Integer.parseInt(spit[2]));
            sendText(exchange, response);

        } else {
            sendGetWrongMethod(exchange);
        }

    }
}
