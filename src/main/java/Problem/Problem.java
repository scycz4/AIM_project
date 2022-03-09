package Problem;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Problem {
    private ArrayList<Instance> instance;
    private InputStream file;

    public int getBoundary() {
        return boundary;
    }

    public void setBoundary(int boundary) {
        this.boundary = boundary;
    }

    private int boundary;
    public Problem(){
        file=getClass().getClassLoader().getResourceAsStream("test1_4_20.txt");

        this.initialize();
    }
    public void setFile(String file){
        this.file=getClass().getResourceAsStream(file);
        this.initialize();
    }
    public void initialize(){
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(file));
            String s=null;
            s=br.readLine();
            String[] instance=s.split(" ");
            int item=Integer.parseInt(instance[0]);
            this.instance=new ArrayList<>(item);
            boundary=Integer.parseInt(instance[1]);
            while((s=br.readLine())!=null){
                instance=s.split(" ");
                int profit=Integer.parseInt(instance[0]);
                int weight=Integer.parseInt(instance[1]);
                this.instance.add(new Instance(profit,weight));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSolutionAsString(){
        StringBuilder s=new StringBuilder();
        Iterator<Instance> it=this.instance.iterator();
        while(it.hasNext()){
            Instance i=it.next();
            String s1=i.isState()?"1":"0";
            s.append(s1);
        }
        return s.toString();
    }

    public int getObjectiveFunctionValue(){
        int value=0;
        Iterator<Instance> it=this.instance.iterator();
        while(it.hasNext()){
            Instance i=it.next();
            int judge=i.isState()?1:0;
            value+=judge*i.getProfit();
        }
        return value;
    }

    public void bitFlip(int index){
        Instance i=instance.get(index);
        i.setState(!i.isState());
        instance.set(index,i);
    }

    public int getWeight(){
        int value=0;
        Iterator<Instance> it=this.instance.iterator();
        while(it.hasNext()){
            Instance i=it.next();
            int judge=i.isState()?1:0;
            value+=judge*i.getWeight();
        }
        return value;
    }
}
