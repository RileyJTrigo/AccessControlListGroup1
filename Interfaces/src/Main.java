//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import Presentation.*;
import Transport.*;

/*
 * Description of the Class or method purpose:
 * A main class that calls functions in the other classes to make sure they produce correct outputs.
 * This main class is not meant to be used by actual users only developers for testing purposes.
 *
 * @author Bill Phillips
 *
 * @version $ Revision log: 1.0
 */

class TestProg
{
   static public void main() {

      ACSInterface aa = new ACSConnector();
      aa.setUserName("XXX");
      aa.setPassword("ZZZ");
      aa.setRole(SubsystemRoles.DATAANALYST);
      aa.setDest(SubsystemEnums.DAS);
      aa.msgReceived();

      ACSBoundary aasB = ACSBoundary.Instance();

      CState cs = new CState();

      cs.setUsername("Bill");
      cs.setPassword("password");
      cs.setRole(SubsystemRoles.DATAANALYST);

      aasB.setValues(cs);

      cs.setUsername("Bill");
      cs.setPassword("password");
      cs.setRole(SubsystemRoles.DATAANALYST);

      aasB.setValues(cs);
   }

}
