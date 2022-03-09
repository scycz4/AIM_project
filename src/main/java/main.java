import Heuristic.HillClimb.SteepestHC;
import Problem.Problem;

public class main {
    public static void main(String[] args) {
        Problem problem=new Problem();
        problem.getObjectiveFunctionValue();
        SteepestHC hc=new SteepestHC();
        for(int i=0;i<100;i++){
            hc.applyHeuristic(problem);
        }

        System.out.println(problem.getObjectiveFunctionValue());
    }
}
