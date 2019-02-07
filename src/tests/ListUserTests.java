package tests;

import com.tylerkv.application.utilities.ListUser;

public class ListUserTests {
    public static void testListUser() {
        testListUserConstructors();
        testListUserGettersAndSetters();
        testListUserMethods();
    }

    public static void testListUserConstructors() {
        ListUser testUser = new ListUser("TestUser", "pass");

        assert testUser.getUserName() == "TestUser";
    }

    public static void testListUserGettersAndSetters() {
        ListUser testUser = new ListUser("TestUser", "pass");

        assert testUser.getPermissionLevel() == null;

        testUser.setPermissionLevel("medium");

        assert testUser.getPermissionLevel() == "medium";
    }

    public static void testListUserMethods(){

    }
}
