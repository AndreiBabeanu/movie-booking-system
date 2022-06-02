package unibuc.moviebooking.exception;

public class DuplicateAuditoriumException extends RuntimeException{
    public DuplicateAuditoriumException() {
        super("There already is this auditorium in db!");
    }
}
