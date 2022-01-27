import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicketsInterface extends Remote {

    boolean register(Object callbackObj) throws RemoteException;

    boolean reservationTicket(Ticket ticket) throws RemoteException;
}
