package Heuristic.PopulationBasedMetaHeuristic.MultiMeme;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.*;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.PopulationBasedSearchMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.BestInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.InheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.SimpleInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.*;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.UniformBitMutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.BoundaryMutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.Mutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.RandomBitFlip;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.*;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate.*;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.RankBasedSelection;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.RouletteWheelSelection;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.Selection;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.TournamentSelection;
import Problem.Problem;

import java.util.ArrayList;

/**
 * this class will try to find optima of the problem by applying better low level heuristic
 * on population efficiently and effectively, and both the solution and meme(contains information
 * of different low level heuristic) will be improved
 */
public class MultiMemeAlgorithm extends PopulationBasedSearchMethod {
    private final double innovationRate;
    private Mutation[] mutation;
    private final CrossoverHeuristic[] crossover;
    private final Replacement[] replacement;
    private final Selection[] selection;

    private final InheritanceMethod[] inheritance;

    private final HillClimb[] lss;

    private ArrayList<Double> best=new ArrayList<Double>();
    private ArrayList<Double> worst=new ArrayList<Double>();

    private static final int tSize=4;

    private final int MUTATION=0;
    private final int CROSSOVER=1;
    private final int LOCAL_SEARCH =2;
    private final int INHERITANCE=3;
    private final int REPLACEMENT=4;
    private final int SELECTION=5;


    /**
     * create a multi-meme object to search the optima
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     * @param innovationRate the rate of whether to mutate meme
     * @param crossoverHeuristic the array contains crossover methods
     * @param mutation the array contains mutation methods
     * @param replacement the array contains replacement methods
     * @param selection the array contains selection methods
     * @param inheritanceMethod the array contains inheritance methods
     * @param lss the array contains local search methods
     */
    public MultiMemeAlgorithm(Problem problem, int populationSize, double innovationRate, CrossoverHeuristic[] crossoverHeuristic
                , Mutation[] mutation, Replacement[] replacement, Selection[] selection, InheritanceMethod[] inheritanceMethod,
                              HillClimb[] lss) {
        super(problem, populationSize);

        this.innovationRate = innovationRate;
        this.crossover = crossoverHeuristic;
        this.mutation = mutation;
        this.replacement = replacement;
        this.selection = selection;
        this.inheritance = inheritanceMethod;
        this.lss = lss;

    }

    /**
     * create a multi-meme object
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     * @param innovationRate the rate of whether to mutate meme
     */
    public MultiMemeAlgorithm(Problem problem,int populationSize,double innovationRate){
        this(
                problem,
                populationSize,
                innovationRate,
                new CrossoverHeuristic[]{
                        new OnePointCrossover(problem),
                        new UniformCrossover(problem),
                        new TwoPointCrossover(problem),
                        new ReducedSurrogateCrossover(problem),
                        new OneBitAdaptiveCrossover(problem)
                },
                new Mutation[]{
                        new UniformBitMutation(problem),
                        new RandomBitFlip(problem),
                        new BoundaryMutation(problem),
                        new RuinRecreateHighestValueSolution(problem),
                        new RuinRecreateLowestValueSolution(problem),
                        new RuinRecreateHighestWeightSolution(problem),
                        new RuinRecreateLowestWeightSolution(problem),
                        new RuinRecreateHighestProfitDivWeightSolution(problem),
                        new RuinRecreateLowestProfitDivWeightSolution(problem)
                },
                new Replacement[]{
                        new ReplacementWithElitists(problem,populationSize),
                        new ReplacementWithStrongElitists(problem,populationSize),
                        new ReplacementWithRandomToWorst(problem,populationSize)
                },
                new Selection[]{
                        new TournamentSelection(problem,populationSize,tSize),
                        new RouletteWheelSelection(problem,populationSize),
                        new RankBasedSelection(problem,populationSize)
                },

                new InheritanceMethod[]{
                        new SimpleInheritanceMethod(problem,populationSize),
                        new BestInheritanceMethod(problem,populationSize)
                },
                new HillClimb[]{
                        new DBHC_OI(problem),
                        new DBHC_IE(problem),
                        new SDHC_OI(problem),
                        new SDHC_IE(problem),
                        new RMHC_OI(problem),
                        new RMHC_IE(problem),
                        new NDHC_OI(problem),
                        new NDHC_IE(problem)
                }
        );
    }

    /**
     * get the total best objective value of all generations
     * @return the total best objective value of all generations
     */
    public ArrayList<Double> getBest() {
        return best;
    }

    /**
     * get the total worst objective value of all generations
     * @return the total worst objective value of all generations
     */
    public ArrayList<Double> getWorst() {
        return worst;
    }

    /**
     * create new generation by applying different low level heuristics
     */
    @Override
    protected void runMainLoop() {

        int p1,p2,c1,c2;
        p1=p2=c1=c2=0;

        //do select, crossover, inheritance, mutation, local search to create new generation
        for(int i=0;i<POP_SIZE;i+=2){
            //choose selection methods according to options in meme and select different parents
            do{
                p1 = applySelectionForChildDependentOnMeme(p1,SELECTION);
                p2 = applySelectionForChildDependentOnMeme(p2,SELECTION);
            }while(p1==p2);
            c1=i+POP_SIZE;
            c2=c1+1;

            //choose crossover methods according to options in meme and copy parent into child and crossover by exchanging bits
            applyCrossoverForChildDependentOnMeme(p1,p2,c1,c2,CROSSOVER);

            //choose inheritance methods according to options in meme and make child inherit the better performance meme from parent
            applyInheritanceForChildDependentOnMeme(p1,p2,c1,c2,INHERITANCE);

            //mutate two children's different meme's options according to innovation rate
            performMutationOfMemeplex(c1);
            performMutationOfMemeplex(c2);

            //choose mutation methods according to options in meme and flip some bits in child
            applyMutationForChildDependentOnMeme(c1,MUTATION);
            applyMutationForChildDependentOnMeme(c2,MUTATION);

            //choose local search methods according to options in meme and try to find a local optima of child
            applyLocalSearchForChildDependentOnMeme(c1, LOCAL_SEARCH);
            applyLocalSearchForChildDependentOnMeme(c2, LOCAL_SEARCH);

        }

        //find the best and worst generations in this iteration and add it into arraylist
        double bestObjValue=problem.getObjectiveFunctionValue(0);
        double worstObjValue=problem.getObjectiveFunctionValue(0);
        for(int i=0;i<POP_SIZE;i++){
            double currentObjValue= problem.getObjectiveFunctionValue(i);
            if(bestObjValue<=currentObjValue){
                bestObjValue=currentObjValue;
            }
            if(worstObjValue>=currentObjValue){
                worstObjValue=currentObjValue;
            }
        }
        best.add(bestObjValue);
        worst.add(worstObjValue);

        //replace the solution in this generation
        applyReplacementForChildDependentOnMeme(c1,c2,REPLACEMENT);
    }

    /**
     * choose a selection methods according to meme
     * @param parentIndex the parent which stores meme
     * @param memeIndex the specific meme need to be used
     * @return an index of selected parent
     */
    private int applySelectionForChildDependentOnMeme(int parentIndex, int memeIndex) {
        return selection[problem.getMeme(parentIndex,memeIndex).getOption()].applySelection();
    }

    /**
     * choose a replacement methods according to meme and the meme comes from the child who
     * has a better objective value
     * @param c1 the child
     * @param c2 another child
     * @param memeIndex the specific meme need to be used
     */
    private void applyReplacementForChildDependentOnMeme(int c1,int c2,int memeIndex) {
        if(problem.getObjectiveFunctionValue(c1)>problem.getObjectiveFunctionValue(c2)){
            replacement[problem.getMeme(c1,memeIndex).getOption()].doReplacement();
        }
        else{
            replacement[problem.getMeme(c2,memeIndex).getOption()].doReplacement();
        }
    }

    /**
     * select an inheritance methods which can get the new meme with the better performance according to previous meme options,
     * and meme comes from the parent who has a better objective value
     * @param p1 parent
     * @param p2 parent
     * @param c1 child
     * @param c2 child
     * @param memeIndex the specific meme need to be used
     */
    private void applyInheritanceForChildDependentOnMeme(int p1, int p2, int c1, int c2, int memeIndex) {
        int index;
        if(problem.getObjectiveFunctionValue(p1)>problem.getObjectiveFunctionValue(p2)){
            index=p1;
        } else {
            index=p2;
        }
        inheritance[problem.getMeme(index,memeIndex).getOption()].performMemeticInheritance(p1,p2,c1,c2);
    }

    /**
     * select a mutation methods according to meme and mutate the child
     * @param childIndex child who need to mutate
     * @param memeIndex the specific meme need to be used
     */
    public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {

        mutation[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    /**
     * select a crossover methods according to meme and parents crossover to generate new children
     * @param p1 parent
     * @param p2 parent
     * @param c1 generated child
     * @param c2 generated child
     * @param memeIndex the specific meme need to be used
     */
    public void applyCrossoverForChildDependentOnMeme(int p1,int p2,int c1,int c2,int memeIndex){
        int index;
        if(problem.getObjectiveFunctionValue(p1)>problem.getObjectiveFunctionValue(p2)){
            index=p1;
        }else {
            index=p2;
        }
        crossover[problem.getMeme(index,memeIndex).getOption()].applyHeuristic(p1,p2,c1,c2);
    }

    /**
     * select a local search methods according to meme and attempt to find a local optima for child
     * @param childIndex child
     * @param memeIndex the specific meme need to be used
     */
    public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {
        lss[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    /**
     * mutate all meme options in child according to innovation rate
     * @param solutionIndex the child need to mutate meme options
     */
    public void performMutationOfMemeplex(int solutionIndex) {

        for(int i=0;i< problem.getNumberOfMemes();i++){
            if(random.nextDouble()<innovationRate){
                int option;
                do{
                    option= random.nextInt(problem.getMeme(solutionIndex,i).getTotalOptions());
                    if(problem.getMeme(solutionIndex,i).getTotalOptions()==1){
                        break;
                    }
                }
                while(option==problem.getMeme(solutionIndex,i).getOption());
                problem.getMeme(solutionIndex,i).setOption(option);
            }
        }
    }
}
