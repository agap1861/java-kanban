package function;

import com.google.gson.*;
import type.of.task.Epic;
import type.of.task.Subtask;
import type.of.task.Task;

import java.lang.reflect.Type;

public class TaskTypeAdapter implements JsonSerializer<Task>, JsonDeserializer<Task> {
    @Override
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        if (task instanceof Epic) {
            return jsonSerializationContext.serialize(task, Epic.class);

        } else if (task instanceof Subtask) {
            return jsonSerializationContext.serialize(task, Subtask.class);

        } else {
            return jsonSerializationContext.serialize(task, Task.class);
        }
    }

    @Override
    public Task deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String typeOfTask = object.get("typeOfTask").getAsString();
        switch (typeOfTask) {
            case "Task" -> {
                return jsonDeserializationContext.deserialize(jsonElement, Task.class);
            }

            case "Epic" -> {
                return jsonDeserializationContext.deserialize(jsonElement, Epic.class);
            }

            case "Subtask" -> {
                return jsonDeserializationContext.deserialize(jsonElement, Subtask.class);
            }
            default -> throw new RuntimeException("Unknow type " + typeOfTask);

        }
    }
}
