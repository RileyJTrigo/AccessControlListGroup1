package Test;

import Application.AccessControlList;
import Presentation.SubsystemEnums;
import Presentation.SubsystemRoles;

import java.io.File;
import java.lang.reflect.Field;

/**
 * TP-ACL-2025-001 v1.0 — Black-Box Unit Test Suite
 * Moravian University | CSCI 234
 *
 * Test Manager : David
 * 31 test cases covering all public methods of AccessControlList.
 *
 * Run: java -cp out Test.AccessControlListTest
 */
public class AccessControlListTest {

    private static int total  = 0;
    private static int passed = 0;
    private static int failed = 0;

    private static final String TEST_FILE   = "ACL_TEST.dat";
    private static final String TEST_FILE_2 = "ACL_TEST2.dat";

    // ── Entry Point ───────────────────────────────────────────────────────────

    public static void main(String[] args) {

        // Redirect ACL away from production ACS.dat before any instantiation
        AccessControlList.chgFilename(TEST_FILE);
        deleteFile(TEST_FILE);
        deleteFile(TEST_FILE_2);

        printHeader();

        runGroupA_Singleton();            resetACL(TEST_FILE);
        runGroupB_InitialState();         resetACL(TEST_FILE);
        runGroupC_AddUser();              resetACL(TEST_FILE);
        runGroupD_FindSingleArg();        resetACL(TEST_FILE);
        runGroupE_FindFourArg();          resetACL(TEST_FILE);
        runGroupF_DeleteUserSingleArg();  resetACL(TEST_FILE);
        runGroupG_DeleteUserFourArg();    resetACL(TEST_FILE);
        runGroupH_GetNumUsers();          resetACL(TEST_FILE);
        runGroupI_SAPassword();           resetACL(TEST_FILE);
        runGroupJ_FilenameAndPersistence();

        printSummary();

        deleteFile(TEST_FILE);
        deleteFile(TEST_FILE_2);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group A — Singleton Pattern
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupA_Singleton() {
        groupHeader("Group A — Singleton Pattern");

        AccessControlList a1 = AccessControlList.Instance();
        AccessControlList a2 = AccessControlList.Instance();
        check("TC-01", "Instance() returns same object reference on repeated calls", a1 == a2);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group B — Initial State & SysAdmin Seed
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupB_InitialState() {
        groupHeader("Group B — Initial State & SysAdmin Seed");

        AccessControlList acl = AccessControlList.Instance();

        check("TC-02", "ACL_TEST.dat created on fresh instantiation (no prior file)",
              new File(TEST_FILE).exists());

        check("TC-03", "SysAdmin record present after auto-seed",
              acl.find("SysAdmin"));

        check("TC-04", "getSAPW() returns default password \"SAPass\"",
              "SAPass".equals(acl.getSAPW()));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group C — addUser
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupC_AddUser() {
        groupHeader("Group C — addUser");

        AccessControlList acl = AccessControlList.Instance();

        acl.addUser("alice", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);
        check("TC-05", "addUser valid record — find(uName) returns true",
              acl.find("alice"));

        acl.addUser("bob", "pw2", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);
        check("TC-06", "add second distinct user — both alice and bob findable",
              acl.find("alice") && acl.find("bob"));

        // Intentionally add alice again with different password (allowed by design)
        acl.addUser("alice", "pw_alt", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);
        check("TC-07", "duplicate username appended — find(\"alice\") still true",
              acl.find("alice"));

        check("TC-08", "getNumUsers() == 2 after adding alice and bob (SysAdmin excluded)",
              acl.getNumUsers() == 2);

        // Persistence: reset singleton WITHOUT deleting the file, then re-read
        check("TC-09", "alice persists across singleton reset (file round-trip)",
              resetAndFind("alice", TEST_FILE));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group D — find(String uName)
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupD_FindSingleArg() {
        groupHeader("Group D — find(uName)");

        AccessControlList acl = AccessControlList.Instance();
        acl.addUser("alice", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);

        check("TC-10", "find existing user returns true",
              acl.find("alice"));

        check("TC-11", "find non-existent user returns false",
              !acl.find("nobody"));

        check("TC-12", "find(\"SysAdmin\") returns true — SysAdmin is included in search",
              acl.find("SysAdmin"));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group E — find(uName, pw, SubsystemEnums, SubsystemRoles)
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupE_FindFourArg() {
        groupHeader("Group E — find(uName, pw, SubsystemEnums, SubsystemRoles)");

        AccessControlList acl = AccessControlList.Instance();
        acl.addUser("alice", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);

        check("TC-13", "full match (all four fields correct) returns true",
              acl.find("alice", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN));

        check("TC-14", "wrong password returns false",
              !acl.find("alice", "wrongpw", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN));

        check("TC-15", "wrong subsystem returns false",
              !acl.find("alice", "pw1", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN));

        check("TC-16", "non-existent username returns false",
              !acl.find("nobody", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group F — deleteUser(String uName)
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupF_DeleteUserSingleArg() {
        groupHeader("Group F — deleteUser(uName)");

        AccessControlList acl = AccessControlList.Instance();
        acl.addUser("alice", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);
        acl.addUser("bob",   "pw2", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);

        acl.deleteUser("alice");
        check("TC-17", "delete existing user — find returns false afterward",
              !acl.find("alice"));

        boolean noException = true;
        try { acl.deleteUser("nobody"); }
        catch (Exception e) { noException = false; }
        check("TC-18", "delete non-existent user — no exception thrown",
              noException);

        acl.deleteUser("SysAdmin");
        check("TC-19", "SysAdmin cannot be deleted (protected at iterator index 0)",
              acl.find("SysAdmin"));

        check("TC-20", "getNumUsers() decrements to 1 after deleting alice",
              acl.getNumUsers() == 1);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group G — deleteUser(uName, pw, SubsystemEnums, SubsystemRoles)
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupG_DeleteUserFourArg() {
        groupHeader("Group G — deleteUser(uName, pw, SubsystemEnums, SubsystemRoles)");

        AccessControlList acl = AccessControlList.Instance();
        acl.addUser("bob", "pw2", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);

        acl.deleteUser("bob", "pw2", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);
        check("TC-21", "exact-match delete removes user — find returns false",
              !acl.find("bob"));

        acl.addUser("bob", "pw2", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);
        acl.deleteUser("bob", "badpw", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);
        check("TC-22", "wrong password — record not removed, find still true",
              acl.find("bob"));

        acl.deleteUser("SysAdmin", "SAPass", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);
        check("TC-23", "SysAdmin protected from four-arg delete",
              acl.find("SysAdmin"));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group H — getNumUsers()
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupH_GetNumUsers() {
        groupHeader("Group H — getNumUsers()");

        AccessControlList acl = AccessControlList.Instance();
        check("TC-24", "fresh ACL with only SysAdmin — getNumUsers() == 0",
              acl.getNumUsers() == 0);

        acl.addUser("alice", "pw1", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);
        acl.addUser("bob",   "pw2", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN);
        check("TC-25", "after adding 2 users — getNumUsers() == 2",
              acl.getNumUsers() == 2);

        acl.deleteUser("alice");
        check("TC-26", "after deleting alice — getNumUsers() == 1",
              acl.getNumUsers() == 1);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group I — getSAPW() and chgSAPass()
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupI_SAPassword() {
        groupHeader("Group I — getSAPW() and chgSAPass()");

        AccessControlList acl = AccessControlList.Instance();
        check("TC-27", "getSAPW() returns \"SAPass\" by default",
              "SAPass".equals(acl.getSAPW()));

        // Change the static password, delete file, reset singleton
        // so a new SysAdmin seed record is written with the new password
        AccessControlList.chgSAPass("NewPass");
        try {
            Field f = AccessControlList.class.getDeclaredField("cf");
            f.setAccessible(true);
            f.set(null, null);
            deleteFile(TEST_FILE);
            AccessControlList acl2 = AccessControlList.Instance();

            check("TC-28", "getSAPW() returns \"NewPass\" after chgSAPass(\"NewPass\")",
                  "NewPass".equals(acl2.getSAPW()));

            check("TC-29", "old password \"SAPass\" no longer matches SysAdmin record",
                  !acl2.find("SysAdmin", "SAPass", SubsystemEnums.ACS, SubsystemRoles.SYSTEMADMIN));

        } catch (Exception e) {
            check("TC-28", "getSAPW() returns \"NewPass\" after chgSAPass()", false);
            check("TC-29", "old password no longer matches SysAdmin record",  false);
        } finally {
            // Always restore default so other groups are unaffected
            AccessControlList.chgSAPass("SAPass");
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Group J — chgFilename() and Cross-session Persistence
    // ══════════════════════════════════════════════════════════════════════════

    private static void runGroupJ_FilenameAndPersistence() {
        groupHeader("Group J — chgFilename() and Persistence");

        // TC-30: redirect ACL to a second file; original TEST_FILE untouched
        resetSingleton();
        deleteFile(TEST_FILE_2);
        AccessControlList.chgFilename(TEST_FILE_2);

        AccessControlList aclAlt = AccessControlList.Instance();
        aclAlt.addUser("carol", "pw3", SubsystemEnums.TST, SubsystemRoles.SYSTEMADMIN);
        check("TC-30", "user added to alternate file (ACL_TEST2.dat) is findable",
              aclAlt.find("carol"));

        // TC-31: full reset — drop singleton, keep file, re-read, verify record survived
        resetSingleton();
        // chgFilename still points to TEST_FILE_2
        AccessControlList acl2 = AccessControlList.Instance();
        check("TC-31", "carol survives full singleton reset and file re-read",
              acl2.find("carol"));

        deleteFile(TEST_FILE_2);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Helpers
    // ══════════════════════════════════════════════════════════════════════════

    /** Record and print one test result. */
    private static void check(String tcId, String description, boolean result) {
        total++;
        if (result) passed++; else failed++;
        System.out.printf("  %-6s  %-6s  %s%n",
                result ? "PASS" : "FAIL", tcId, description);
    }

    /** Print a group header. */
    private static void groupHeader(String title) {
        System.out.println();
        System.out.println("  " + title);
        System.out.println("  " + "─".repeat(title.length()));
    }

    /** Print the opening banner. */
    private static void printHeader() {
        System.out.println("=======================================================");
        System.out.println(" AccessControlList — Black-Box Unit Test Suite");
        System.out.println(" Moravian University  CSCI 234  —  Test Manager: David");
        System.out.println("=======================================================");
    }

    /** Print the closing summary. */
    private static void printSummary() {
        System.out.println();
        System.out.println("=======================================================");
        System.out.printf("  TOTAL: %d   PASSED: %d   FAILED: %d%n",
                total, passed, failed);
        System.out.println("=======================================================");
    }

    /** Delete a file if it exists. */
    private static void deleteFile(String filename) {
        File f = new File(filename);
        if (f.exists()) f.delete();
    }

    /**
     * Use reflection to null-out the private static cf field, then delete the
     * backing file and reset the filename — giving us a fresh ACL state.
     */
    private static void resetACL(String filename) {
        resetSingleton();
        deleteFile(filename);
        AccessControlList.chgFilename(filename);
    }

    /** Null-out the singleton field only (keeps the file intact). */
    private static void resetSingleton() {
        try {
            Field f = AccessControlList.class.getDeclaredField("cf");
            f.setAccessible(true);
            f.set(null, null);
        } catch (Exception e) {
            System.out.println("  [WARN] Could not reset singleton: " + e.getMessage());
        }
    }

    /**
     * Null-out the singleton without deleting the file, then re-instantiate
     * and check if the given username is still present. Used to verify persistence.
     */
    private static boolean resetAndFind(String uName, String filename) {
        try {
            Field f = AccessControlList.class.getDeclaredField("cf");
            f.setAccessible(true);
            f.set(null, null);
            AccessControlList.chgFilename(filename);
            return AccessControlList.Instance().find(uName);
        } catch (Exception e) {
            return false;
        }
    }
}
