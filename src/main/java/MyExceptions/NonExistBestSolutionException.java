package MyExceptions;

public class NonExistBestSolutionException extends RuntimeException{
    public NonExistBestSolutionException(){
        super("best solution should be intialized first");
    }
}
