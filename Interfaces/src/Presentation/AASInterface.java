package Presentation;

/*
 * Description of the Class or method purpose:
 * Contains the methods necessary in the implementation of an AAS control class.
 *
 * @author Bill Phillips
 *
 * @version $ Revision log: 1.0
 */

public interface AASInterface
{
   public void msgReceived();

   public void sendMsg();

   public void setAlertable();
   public boolean getAlertable();

   public void setMsg( String msg );
   public String getMsg();

   public void setDest(SubsystemEnums s);
   public SubsystemEnums getDest();


}
