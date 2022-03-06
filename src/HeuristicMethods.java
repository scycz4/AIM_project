import java.util.Random;

public abstract class HeuristicMethods {
    private Random random;
    private Problem problem;
    public HeuristicMethods(Problem problem){
        random=new Random();
        this.problem=problem;
    }
}
