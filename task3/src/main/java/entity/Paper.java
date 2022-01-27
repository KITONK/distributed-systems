package main.java.entity;


import java.io.File;
import java.util.List;

public class Paper {

    private String title;
    private String type;
    private boolean monthly;
    private boolean color;
    private int numberOfPage;
    private boolean glossy;
    private int subscriptionIndex;


    public Paper() {
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMonthly(String monthly) {
        this.monthly = new Boolean(monthly);
    }

    public void setColor(String color) {
        this.color = new Boolean(color);
    }

    public void setNumberOfPage(String numberOfPages) {
        this.numberOfPage = new Integer(numberOfPages);
    }

    public void setGlossy(String glossy) {
        this.glossy = new Boolean(glossy);
    }

    public void setSubscriptionIndex(String subscriptionIndex) {
        this.subscriptionIndex = new Integer(subscriptionIndex);
    }

    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", monthly=" + monthly +
                ", color=" + color +
                ", numberOfPage=" + numberOfPage +
                ", glossy=" + glossy +
                ", subscriptionIndex=" + subscriptionIndex +
                '}';
    }
}
