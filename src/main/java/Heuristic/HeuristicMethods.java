package Heuristic;

import Problem.Problem;

import java.util.Random;


public abstract class HeuristicMethods {
    private Random random;
    private Problem problem;
    public HeuristicMethods(){
        random=new Random();
    }

    abstract public void applyHeuristic(Problem problem);
}
