package Problem;

public class Instance {
    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    private double profit;
    private double weight;
    private boolean state;

    public Instance(double profit,double weight){
        this.profit=profit;
        this.weight=weight;
        state=false;
    }

    public void bitFlip(){
        state=!state;
    }
}
