package Problem;

public class Instance {
    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    private int profit;
    private int weight;
    private boolean state;

    public Instance(int profit,int weight){
        this.profit=profit;
        this.weight=weight;
        state=false;
    }

    public Instance(){
        this.profit=profit;
        this.weight=weight;
        state=false;
    }

    public void bitFlip(){
        state=!state;
    }
}
