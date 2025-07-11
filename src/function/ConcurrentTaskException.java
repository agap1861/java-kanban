package function;

public class ConcurrentTaskException extends Exception {
    public ConcurrentTaskException(String message) {
        super(message);
    }

    public ConcurrentTaskException() {
    }
}
