package sample;

import java.util.*;
import java.util.stream.Collectors;

public class VariantnessList {

    private String name;
    private List<Double> all;
    private Map<Double, Integer> state;

    public VariantnessList(List<List<Double>> data, String name) {
        this.name = name;
        List<Double> unique = new ArrayList<>();
        for(List<Double> list : data){
            for(Double d : list){
                all.add(d);
                if(!unique.contains(d)) {
                    unique.add(d);
                }
            }
        }

        state = new HashMap<>();
        for(Double d : unique){
            Integer counter = 0;
            for(List<Double> doubles : data){
                if(doubles.size() == d){
                    counter += Math.toIntExact((long) doubles.stream().mapToDouble(Double::doubleValue).sum());
                }
            }
            state.put(d, counter);
        }
        Collections.sort(all);
    }

    public Double rank(Double value){
        int index = 0;
        for(int i = 0; i < all.size(); i++){
            if(Objects.equals(all.get(i), value)){
                index = i;
                break;
            }
        }
        index++;
        long count = all.stream().filter(x -> Objects.equals(x, value)).count();
        long maxInd = count + index - 1;
        return (index + maxInd) / 2.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getAll() {
        return all;
    }

    public void setAll(List<Double> all) {
        this.all = all;
    }

    public Map<Double, Integer> getState() {
        return state;
    }

    public void setState(Map<Double, Integer> state) {
        this.state = state;
    }
}
