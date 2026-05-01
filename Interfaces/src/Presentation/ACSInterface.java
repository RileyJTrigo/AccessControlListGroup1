package Presentation;

/*
 * Description of the Class or method purpose:
 * Contains the methods necessary in the implementation of an ACS control class.
 *
 * @author Bill Phillips
 *
 * @version $ Revision log: 1.0
 */

public interface ACSInterface
{
   public void msgReceived();

   public void   setUserName( String uN );
   public String getUserName();

   public void   setPassword(String pw);
   public String getPassword();

   public void   setRole(SubsystemRoles r);
   public SubsystemRoles getRole();

   public void setAuth();
   public void setNotAuth();
   public boolean getAuth();

   public void setDest(SubsystemEnums s);
   public SubsystemEnums getDest();
}
