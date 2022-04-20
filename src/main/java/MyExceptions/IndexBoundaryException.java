package MyExceptions;

/**
 * the exception that indicate whether the index is out of the solution boundary
 */
public class IndexBoundaryException extends RuntimeException{
    /**
     * create exception
     * @param message the message to print out
     */
    public IndexBoundaryException(String message){
        super(message);
    }
}
