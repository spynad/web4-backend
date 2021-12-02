package app.exception;

public class PointNotFoundException extends RuntimeException{
    public PointNotFoundException(long id) {
        super("Point with id " + id + " not found");
    }
}
