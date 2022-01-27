import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackImpl extends UnicastRemoteObject implements ClientCallbackInterface, Serializable {
    protected CallbackImpl() throws RemoteException {

    }

    @Override
    public void accept(String result) throws RemoteException {
        System.out.println("Result on Client = " + result);
    }
}
