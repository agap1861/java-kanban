package function;

public class WrongStatusExcaption extends RuntimeException {
    public WrongStatusExcaption(String message) {
        super(message);
    }

    public WrongStatusExcaption() {
    }
}
