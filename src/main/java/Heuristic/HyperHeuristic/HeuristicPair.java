package Heuristic.HyperHeuristic;

public class HeuristicPair {
    private final int h1;

    private final int h2;

    public HeuristicPair(int h1,int h2){
        this.h1=h1;
        this.h2=h2;
    }

    public int getFirst(){
        return h1;
    }

    public int getLast(){
        return h2;
    }
}
