package tests;

import com.tylerkv.application.listitems.GoalListItem;
import com.tylerkv.application.lists.GoalList;
import com.tylerkv.application.utilities.ListUser;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GoalListItemTests {
    public static void testGoalListItem() {
        testGoalListItemConstructors();
        testGoalListItemGettersAndSetters();
        testGoalListItemMethods();
    }
    public static void testGoalListItemConstructors() {
        GoalListItem testItem1 = new GoalListItem("item1", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));
    }
    public static void testGoalListItemGettersAndSetters() {
        GoalListItem testItem1 = new GoalListItem("item1", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));
        GoalListItem testItem2 = new GoalListItem("item2", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));
        GoalListItem testItem3 = new GoalListItem("item3", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));

        testItem1.setParent(testItem2);

        try {
            testItem1.setParent(testItem1);
        }
        catch (IllegalArgumentException e) {
        }


    }
    public static void testGoalListItemMethods() {
        ListUser testUser = new ListUser("testUser", "pass");
        GoalList testList = new GoalList("test1", testUser);

        GoalListItem testItem1 = new GoalListItem("item1", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));
        GoalListItem testItem2 = new GoalListItem("item2", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));
        GoalListItem testItem3 = new GoalListItem("item3", "desc"
                , LocalDateTime.of(2019, 1, 1, 0 , 0));

        testItem1.addChildGoal(testItem2);
        testItem1.addChildGoal(testItem3);

        ArrayList<GoalListItem> returnChildren = testItem1.getChildren();

        assert returnChildren.get(0).getItemName().equals("item2");
        assert returnChildren.get(1).getItemName().equals("item3");

        testItem1.deleteChildGoal("item2");
        testItem1.deleteChildGoal("item3");

        returnChildren = testItem1.getChildren();

        assert returnChildren.size() == 0;
    }
}
