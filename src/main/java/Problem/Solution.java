package Problem;

import java.util.Random;

public class Solution {
    private Instance[] instance;
    private final Meme[] memes;
    private int[] memeStates;
    private double boundary;

    public Solution(Instance[] instance,double boundary,int numberOfMemes,int[] memeStates){
        this.instance=instance;
        this.boundary=boundary;
        this.memes=new Meme[numberOfMemes];

        for(int i=0;i<this.memes.length;i++){
            this.memes[i]=new Meme(new Random().nextInt(memeStates[i]),memeStates[i]);
        }

        if(this.memeStates==null){
            this.memeStates=memeStates;
        }
    }

    private Solution(Instance[] instance,double boundary, Meme[] memes, int[] memeStates) {
        this.instance=instance;
        this.boundary=boundary;
        this.memes = memes;
        this.memeStates = memeStates;
    }

    public double getWeight(){
        double value=0;
        for(int i=0;i<this.instance.length;i++){
            int judge=this.instance[i].isState()?1:0;
            value+=judge*this.instance[i].getWeight();
        }
        return value;
    }

    public String getSolutionAsString(){
        StringBuilder s=new StringBuilder();

        for(int i=0;i<instance.length;i++){
            Instance is=instance[i];
            String s1=is.isState()?"1":"0";
            s.append(s1);
        }
        return s.toString();
    }

    public double getObjectiveValue(){
        double value=0;
        for(int i=0;i<instance.length;i++){
            Instance is=instance[i];
            int judge=is.isState()?1:0;
            value+=judge*is.getProfit();
        }
        if(getWeight()>getBoundary()){
            value=-getWeight()/getBoundary();
        }
        return value;
    }

    public int getNumberOfInstance(){
        return instance.length;
    }

    public double getBoundary() {
        return boundary;
    }

    public void setBoundary(double boundary) {
        this.boundary = boundary;
    }

    public Instance[] getInstance() {
        return instance;
    }

    public void setInstance(Instance[] instance) {
        this.instance = instance;
    }

    public void bitFlip(int index){
        Instance i=instance[index];
        i.setState(!i.isState());
        instance[index]=i;
    }

    public Solution deepCopy(){
        Instance[] instances=new Instance[this.instance.length];
        Meme[] memes=new Meme[this.memes.length];

        for(int i=0;i<this.instance.length;i++){
            double profit=instance[i].getProfit();
            double weight=instance[i].getWeight();
            boolean state=instance[i].isState();
            Instance is=new Instance(profit,weight);
            is.setState(state);
            instances[i]=is;
        }

        for(int i=0;i<memes.length;i++){
            memes[i]=new Meme(this.memes[i].getOption(),this.memeStates[i]);
        }

        return new Solution(instances,this.boundary,memes,this.memeStates);
    }

    public Meme getMeme(int memeNumber){
        return this.memes[memeNumber];
    }
}
