package function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import type.of.task.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    protected HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {

            getHandle(exchange);

        } else {
            sendGetWrongMethod(exchange);
        }

    }

    private void getHandle(HttpExchange exchange) {
        String uri = exchange.getRequestURI().toString();
        String[] split = uri.split("/");
        if (split.length == 2) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(Duration.class, new DurationAdapter())
                //    .registerTypeAdapter(Task.class,new TaskTypeAdapter())
                    .create();

            List<TaskDTO> tasksDTO = taskManger.getHistory()
                    .stream()
                    .map(task -> {
                        if (task instanceof Epic){
                            return EpicDTO.convertToDTO(task);
                        } else if (task instanceof Subtask) {
                            return SubtaskDTO.convertToDTO(task);
                        }else {
                            return TaskDTO.convertToDTO(task);
                        }


                    })
                    .collect(Collectors.toList());
            String response = gson.toJson(tasksDTO);
            sendText(exchange,response);

        } else {
            sendGetWrongMethod(exchange);
        }


    }

}
