import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceObject extends UnicastRemoteObject implements TicketsInterface {
    protected ServiceObject() throws RemoteException {

    }

    private  Object callbackObj;

    @Override
    public boolean register(Object callbackObj) throws RemoteException {
        if(callbackObj == null) return false;
        this.callbackObj = callbackObj;
        return true;
    }

    @Override
    public boolean reservationTicket(Ticket ticket) throws RemoteException {
        System.out.println(ticket.toString() + " is reserved by server" + this);
        ((ClientCallbackInterface) callbackObj).accept(ticket.toString());
        return true;
    }
}
