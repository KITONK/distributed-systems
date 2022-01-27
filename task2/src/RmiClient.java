import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import javax.rmi.PortableRemoteObject;

public class RmiClient {
    public static void main(String[] args) throws Exception {
        Object o = (TicketsInterface) Naming.lookup("//localhost/tickets");
        TicketsInterface ticketsInterface = (TicketsInterface) PortableRemoteObject.narrow(o, TicketsInterface.class);

        CallbackImpl clientObj = new CallbackImpl();

        ticketsInterface.register(clientObj);


        ArrayList<Ticket> ticketList = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setClassTicket("S class");
        ticket.setPlace("2 A");
        ticket.setPrice(15600.5);
        ticketList.add(ticket);
        Ticket ticket1 = new Ticket();
        ticket1.setClassTicket("B class");
        ticket1.setPlace("4 F");
        ticket1.setPrice(2400.1);
        ticketList.add(ticket1);
        for(Ticket tickets : ticketList) {
            System.out.println(tickets);
        }
    }
}

