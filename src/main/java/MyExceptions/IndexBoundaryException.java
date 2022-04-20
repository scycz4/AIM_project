package MyExceptions;

public class IndexBoundaryException extends RuntimeException{
    public IndexBoundaryException(String message){
        super(message);
    }
}
