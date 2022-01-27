import java.io.Serializable;

public class Ticket implements Serializable{
    String classTicket;
    String place;
    double price;

    public String getClassTicket() {
        return classTicket;
    }

    public void setClassTicket(String classTicket) {
        this.classTicket = classTicket;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "classTicket = '" + classTicket + '\'' +
                ", place = '" + place + '\'' +
                ", price = " + price;
    }
}
