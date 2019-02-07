package tests;

import com.tylerkv.application.lists.TeamList;
import com.tylerkv.application.utilities.ListUser;

public class TeamListTests {
    public static void testTeamList() {
        testTeamListConstructors();
        testTeamListGettersAndSetters();
        testTeamListMethods();
    }
    public static void testTeamListConstructors() {
        ListUser testUser = new ListUser("test", "pass");
        TeamList testList1 = new TeamList("test1", testUser);
        TeamList testList2 = new TeamList("test2", testUser, 0.5);

        assert testList2.getListPriority() == 0.5;
    }
    public static void testTeamListGettersAndSetters() {

    }
    public static void testTeamListMethods() {

    }
}
