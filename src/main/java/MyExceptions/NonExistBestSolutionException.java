package MyExceptions;

/**
 * the exception that indicate whether best solution is null
 */
public class NonExistBestSolutionException extends RuntimeException{
    /**
     * create exception
     */
    public NonExistBestSolutionException(){
        super("best solution should be intialized first");
    }
}
