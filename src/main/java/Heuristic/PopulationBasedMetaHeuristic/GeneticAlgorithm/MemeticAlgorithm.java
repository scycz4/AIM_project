package Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm;

import Heuristic.Crossover.CrossoverHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.Replacement;
import Heuristic.PopulationBasedMetaHeuristic.TournamentSelection;
import Problem.Problem;

import java.util.Random;

public class MemeticAlgorithm extends PopulationBasedSearchMethod{
    private final CrossoverHeuristic crossover;
    private final PopulationHeuristic mutation;
    private final PopulationHeuristic localSearch;
    private final Replacement replacement;
    private final TournamentSelection selection;

    public MemeticAlgorithm(Problem problem, int populationSize, CrossoverHeuristic crossover,
                            PopulationHeuristic mutation, PopulationHeuristic localSearch, Replacement replacement) {

        super(problem, populationSize);

        this.crossover = crossover;
        this.mutation = mutation;
        this.localSearch = localSearch;
        this.replacement = replacement;
        this.selection = new TournamentSelection(problem, populationSize);
    }

    @Override
    public void runMainLoop() {
        for (int i = 0; i < POP_SIZE/2; i++) {
            int parent1 = selection.tournamentSelection(3);
            int parent2 = selection.tournamentSelection(3);
            // ensure parents are different
            while (parent2 == parent1) parent2 = selection.tournamentSelection(3);

            crossover.applyHeuristic(parent1, parent2, POP_SIZE+i*2, POP_SIZE+i*2+1);
            mutation.applyHeuristic(POP_SIZE+i*2);
            mutation.applyHeuristic(POP_SIZE+i*2+1);
            localSearch.applyHeuristic(POP_SIZE+i*2);
            localSearch.applyHeuristic(POP_SIZE+i*2+1);
        }
        replacement.doReplacement(problem, POP_SIZE);
    }
}
