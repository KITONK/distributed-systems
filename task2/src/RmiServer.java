import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Locale;


public class RmiServer{
   public static void main(String[] args) throws Exception {
       LocateRegistry.createRegistry(1099);
       ServiceObject obj = new ServiceObject();
       Naming.rebind("//localhost/tickets", obj);
   }
}
