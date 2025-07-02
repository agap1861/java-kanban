package function;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import java.time.Duration;


public class DurationAdapter extends TypeAdapter<Duration> {


    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        if (duration == null){
            jsonWriter.nullValue();
        }else {
            jsonWriter.value(duration.toMinutes());
        }

    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        int minutes = jsonReader.nextInt();
        return Duration.ofMinutes(minutes);
    }
}
