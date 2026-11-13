package football.exception;

public class InvalidDataException extends Exception {
    private final int lineNumber;

    public InvalidDataException(String message, int lineNumber) {
        super("Line " + lineNumber + ": " + message);
        this.lineNumber = lineNumber;
    }

    public InvalidDataException(String message) {
        super(message);
        this.lineNumber = -1;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}