package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

public class PTX1 extends CrossoverHeuristic{
    private Random random;
    public PTX1(Problem problem) {
        super(problem);
        random=new Random();
    }

    @Override
    public void applyHeuristic(int p1,int p2,int c1,int c2) {
        int i = this.random.nextInt(this.problem.getNumberOfVariables() - 2) + 1;
        this.problem.copySolution(p1, c1);
        this.problem.copySolution(p2, c2);

        for (p1 = 0; p1 < this.problem.getNumberOfVariables(); p1++) {
            if (p1 < i) {
                this.problem.exchangeBits(c1, c2, p1);
            }
        }
    }
}
