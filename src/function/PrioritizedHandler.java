package function;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import type.of.task.*;

import java.io.IOException;

import java.util.Set;
import java.util.stream.Collectors;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    protected PrioritizedHandler(TaskManager taskManager) {
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
            Set<TaskDTO> dto = taskManger.getPrioritizedTasks()
                    .stream()
                    .map(task -> {
                        if (task instanceof Epic) {
                            return EpicDTO.convertToDTO(task);
                        } else if (task instanceof Subtask) {
                            return SubtaskDTO.convertToDTO(task);
                        } else {
                            return TaskDTO.convertToDTO(task);
                        }
                    })
                    .collect(Collectors.toSet());
            String response = gson.toJson(dto);
            sendText(exchange, response);

        } else {
            sendGetWrongMethod(exchange);
        }
    }

}
