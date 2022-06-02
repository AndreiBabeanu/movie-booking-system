package unibuc.moviebooking.exception;

public class DuplicateCinemaException extends RuntimeException{
    public DuplicateCinemaException() {
        super("There already exists this cinema in this location");
    }
}
