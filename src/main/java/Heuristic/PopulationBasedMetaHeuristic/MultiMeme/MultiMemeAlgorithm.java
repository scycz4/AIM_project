package Heuristic.PopulationBasedMetaHeuristic.MultiMeme;

import Heuristic.Crossover.CrossoverHeuristic;
import Heuristic.Crossover.OnePointCrossover;
import Heuristic.Crossover.PTX1;
import Heuristic.Crossover.UniformCrossover;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.PopulationBasedSearchMethod;
import Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods.*;
import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public class MultiMemeAlgorithm extends PopulationBasedSearchMethod {
    private Random random;
    private final double innovationRate;
    private BitMutation mutation;
    private final CrossoverHeuristic[] crossover;
    private final Replacement replacement;
    private final TournamentSelection selection;

    private final SimpleInheritanceMethod inheritance;

    private final PopulationHeuristic[] lss;

    public MultiMemeAlgorithm(Problem problem,int populationSize, double innovationRate, CrossoverHeuristic[] crossoverHeuristic
                , BitMutation mutation, Replacement replacement, TournamentSelection selection, SimpleInheritanceMethod simpleInheritanceMethod,
                              PopulationHeuristic[] lss) {
        super(problem, populationSize);

        this.innovationRate = innovationRate;
        this.crossover = crossoverHeuristic;
        this.mutation = mutation;
        this.replacement = replacement;
        this.selection = selection;
        this.inheritance = simpleInheritanceMethod;
        this.lss = lss;

    }

    public MultiMemeAlgorithm(Problem problem,int populationSize,double innovationRate){
        this(
                problem,
                populationSize,
                innovationRate,
                new CrossoverHeuristic[]{
                        new OnePointCrossover(problem),
                        new PTX1(problem),
                        new UniformCrossover(problem)
                },
                new BitMutation(problem),
                new Replacement(),
                new TournamentSelection(problem,populationSize),
                new SimpleInheritanceMethod(problem),
                new PopulationHeuristic[]{
                        new DBHC_OI(problem),
                        new DBHC_IE(problem),
                        new SDHC_OI(problem),
                        new SDHC_IE(problem)
                }
        );
    }

    @Override
    protected void runMainLoop() {
        final int tSize=3;

        for(int i=0;i<POP_SIZE;i+=2){
            int p1 = selection.tournamentSelection(tSize);
            int p2 = selection.tournamentSelection(tSize);

            int c1=i+POP_SIZE/2;
            int c2=c1+1;

            CrossoverHeuristic heuristic=applyCrossoverForChildDependentOnMeme(c1,1);
            heuristic.applyHeuristic(p1,p2,c1,c2);

            inheritance.performMemeticInheritance(p1,p2,c1,c2);

            performMutationOfMemeplex(c1);
            performMutationOfMemeplex(c2);

            applyMutationForChildDependentOnMeme(c1,0);
            applyMutationForChildDependentOnMeme(c2,0);

            applyLocalSearchForChildDependentOnMeme(c1,2);
            applyLocalSearchForChildDependentOnMeme(c2,2);

        }

        replacement.doReplacement(problem,POP_SIZE);


    }

    public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {

        // TODO implementation of mutation embedding intensity of mutation from memes
        mutation.setMutationRate(problem.getMeme(childIndex,memeIndex).getOption());
        mutation.applyHeuristic(childIndex);
    }

    public CrossoverHeuristic applyCrossoverForChildDependentOnMeme(int childIndex,int memeIndex){
        return crossover[problem.getMeme(childIndex,memeIndex).getOption()];
    }

    public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {

        // TODO implementation of local search dependent on memes
        lss[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    public void performMutationOfMemeplex(int solutionIndex) {

        // TODO implementation of mutation of memeplex
        for(int i=0;i< problem.getNumberOfMemes();i++){
            if(rng.nextDouble()<innovationRate){
                int option;
                do{
                    option=rng.nextInt(problem.getMeme(solutionIndex,i).getTotalOptions());
                }
                while(option==problem.getMeme(solutionIndex,i).getOption());
                problem.getMeme(solutionIndex,i).setOption(option);
            }
        }
    }
}
