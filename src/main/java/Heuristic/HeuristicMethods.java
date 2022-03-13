package Heuristic;

import Problem.Problem;

import java.util.Random;


public abstract class HeuristicMethods {
    public final int CURRENT_SOLUTION_INDEX=0;
    public final int BACK_UPP_SOLUTION_INDEX=1;

    private Random random;
    private Problem problem;
    public HeuristicMethods(){
        random=new Random();
    }

    abstract public void applyHeuristic(Problem problem);
}
