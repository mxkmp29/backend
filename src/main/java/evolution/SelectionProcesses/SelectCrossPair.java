package evolution.selectionProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class SelectCrossPair<T> implements SelectionProcessInterface<T> {

    private List<Chromosome<T>> selectList = new ArrayList<>();

    public SelectCrossPair(List<Chromosome<T>> selectList) {
        this.selectList = selectList;
    }

    @Override
    public List<Chromosome<T>> select() {
        int one = (int) (Math.random() * this.selectList.size());
        int two = (int) (Math.random() * this.selectList.size());
        if (one == two) {
            return this.select();
        }

        List<Chromosome<T>> list = new ArrayList<>();
        list.add(this.selectList.get(one));
        list.add(this.selectList.get(two));
        //System.out.println("SelectCrossPair:select()" + list);
        //System.out.println("SelectCrossPair:select()" + list.size());
        return list;
    }

    @Override
    public String toString() {
        return "SelectCrossPair{" +
                "selectList=" + selectList +
                '}';
    }
}
