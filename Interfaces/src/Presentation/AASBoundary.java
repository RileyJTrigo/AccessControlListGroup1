package Presentation;
import Transport.*;

/*
 * Description of the Class or method purpose:
 * A boundary/interface class that the user can interact with to utilize some of the
 * methods in the subsystem/control class which in this case would be AASConnector.
 *
 * @author Bill Phillips
 *
 * @version $ Revision log: 1.0
 */

public class AASBoundary
{

   private AASInterface    AASConnector;
   private static AASBoundary aA = null;
   private AASInterface              aS;
   private CState                    cS;
   private ClientServices           cSv;
   private final RunTimeVars  rtv =
                 RunTimeVars.Instance();
   private AASBoundary()
   {
      aS = new AASConnector();
      cS = new       CState();
   }

   public static AASBoundary Instance()
   {
      if (aA == null) aA = new AASBoundary();
      return aA;
   }

   public AASInterface getConnector()
   {
      return AASConnector;
   }

   public void sendMsg()
   {
      final String sm = aS.getMsg();
      final boolean v = aS.getAlertable();
      final int vi = v? 1 : 0;
      cS.mid = MessageID.AUTH;
      cS.setMessage(sm);
      cS.setV(vi);
      String toIP = rtv.getAASIP();
      cSv.Connect(toIP, rtv.getServerPort());
      cSv.send(cS);
      cSv.Disconnect();
   }

}

