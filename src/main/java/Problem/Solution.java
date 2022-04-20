package Problem;

import java.util.Random;

/**
 * this class is the total items of solution
 */
public class Solution {
    private Instance[] instance;
    private final Meme[] memes;
    private int[] memeStates;
    private double boundary;

    /**
     * create new solution
     * @param instance the items of solution
     * @param boundary the boundary of solution
     * @param numberOfMemes the number of memes of solution
     * @param memeStates the default state of meme option
     * @param random the random generator
     */
    public Solution(Instance[] instance,double boundary,int numberOfMemes,int[] memeStates,Random random){
        this.instance=instance;
        this.boundary=boundary;
        this.memes=new Meme[numberOfMemes];

        for(int i=0;i<this.memes.length;i++){
            this.memes[i]=new Meme(random.nextInt(memeStates[i]),memeStates[i]);
        }

        if(this.memeStates==null){
            this.memeStates=memeStates;
        }
    }

    /**
     * create new solution
     * @param instance the items of solution
     * @param boundary the boundary of solution
     * @param memes the memes of solution
     * @param memeStates the default state of meme option
     */
    private Solution(Instance[] instance,double boundary, Meme[] memes, int[] memeStates) {
        this.instance=instance;
        this.boundary=boundary;
        this.memes = memes;
        this.memeStates = memeStates;
    }

    /**
     * get the weight of selected items
     * @return the weight of selected items
     */
    public double getWeight(){
        double value=0;
        for(int i=0;i<this.instance.length;i++){
            int judge=this.instance[i].isState()?1:0;
            value+=judge*this.instance[i].getWeight();
        }
        return value;
    }

    /**
     * get the solution as string
     * @return the solution as string
     */
    public String getSolutionAsString(){
        StringBuilder s=new StringBuilder();

        for(int i=0;i<instance.length;i++){
            Instance is=instance[i];
            String s1=is.isState()?"1":"0";
            s.append(s1);
        }
        return s.toString();
    }

    /**
     * get the objective value of solution
     * @return the objective value of solution
     */
    public double getObjectiveValue(){
        double value=0;
        for(int i=0;i<instance.length;i++){
            Instance is=instance[i];
            int judge=is.isState()?1:0;
            value+=judge*is.getProfit();
        }
        return value;
    }

    /**
     * get the number of items
     * @return the number of items
     */
    public int getNumberOfInstance(){
        return instance.length;
    }

    /**
     * get the boundary of solution
     * @return the boundary of solution
     */
    public double getBoundary() {
        return boundary;
    }

    /**
     * set the boundary of solution
     * @param boundary the boundary of solution
     */
    public void setBoundary(double boundary) {
        this.boundary = boundary;
    }

    /**
     * get the instances(items) of solution
     * @return the instances(items) of solution
     */
    public Instance[] getInstance() {
        return instance;
    }

    /**
     * set the instances(items) of solution
     * @param instance set the instances(items) of solution
     */
    public void setInstance(Instance[] instance) {
        this.instance = instance;
    }

    /**
     * flip the bit in that index
     * @param index the index of instances(items)
     */
    public void bitFlip(int index){
        Instance i=instance[index];
        i.setState(!i.isState());
        instance[index]=i;
    }

    /**
     * get the new solution which has same properties
     * @return the new solution which has same properties
     */
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

    /**
     * get the meme of solution
     * @param memeNumber the index of meme
     * @return
     */
    public Meme getMeme(int memeNumber){
        return this.memes[memeNumber];
    }
}
