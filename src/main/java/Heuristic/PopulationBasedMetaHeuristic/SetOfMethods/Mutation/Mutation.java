package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class Mutation extends PopulationHeuristic {
    public Mutation(Problem problem,Random random) {
        super(problem, random);
    }

    public int deltaEvaluation(int index){
        return problem.deltaEvaluation(index);
    }
}
