package Problem;

public class Instance {
    private double profit;
    private double weight;
    private boolean state;

    /**
     * get the profit of item
     * @return the profit of item
     */
    public double getProfit() {
        return profit;
    }

    /**
     * set the profit of item
     * @param profit the profit of item
     */
    public void setProfit(double profit) {
        this.profit = profit;
    }

    /**
     * get the weight of item
     * @return the weight of item
     */
    public double getWeight() {
        return weight;
    }

    /**
     * set the weight of item
     * @param weight the weight of item
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * get the state of item
     * @return the state of item
     */
    public boolean isState() {
        return state;
    }

    /**
     * set the state of item
     * @param state the state of item
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * create an item
     * @param profit the profit of item
     * @param weight the weight of item
     */
    public Instance(double profit,double weight){
        this.profit=profit;
        this.weight=weight;
        state=false;
    }

    /**
     * flip the bit
     */
    public void bitFlip(){
        state=!state;
    }
}
