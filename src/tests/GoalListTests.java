package tests;

import com.tylerkv.application.listitems.GoalListItem;
import com.tylerkv.application.lists.GoalList;
import com.tylerkv.application.utilities.ListUser;

import java.time.LocalDateTime;

public class GoalListTests {
    public static void testGoalList() {
        testGoalListConstructors();
        testGoalListGettersAndSetters();
        testGoalListMethods();
    }
    public static void testGoalListConstructors() {
        ListUser testUser = new ListUser("testUser", "pass");
        GoalList testList = new GoalList("test1", testUser);
    }
    public static void testGoalListGettersAndSetters() {

    }
    public static void testGoalListMethods() {
        ListUser testUser = new ListUser("testUser", "pass");
        GoalList testList = new GoalList("test1", testUser);
        testList.addItem(new GoalListItem("item1","desc"
                , LocalDateTime.of(2019,1,1,0,0)));
        testList.addItem(new GoalListItem("item2","desc"
                , LocalDateTime.of(2019,1,2,0,0)));
        testList.addItem(new GoalListItem("item3","desc"
                , LocalDateTime.of(2019,1,3,0,0)));

    }
}
