package main.java.entity;

import java.util.ArrayList;
import java.util.List;

public class Papers {

    List<Paper> paperList = new ArrayList<>();

    public  void add(Paper paper){
        this.paperList.add(paper);
    }

    @Override
    public String toString() {
        return "Papers{" +
                "paperList=" + paperList +
                '}';
    }

}
