package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class HillClimb extends PopulationHeuristic {
    public HillClimb(Problem problem, Random random) {
        super(problem, random);
    }

    @Override
    public abstract void applyHeuristic(int index);

    public abstract boolean acceptMove(double current, double candidate);

    public double deltaEvaluation(int index){
        return problem.deltaEvaluation(index);
    }
}
