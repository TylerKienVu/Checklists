package tests;

import com.tylerkv.application.listitems.TeamListItem;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListUser;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeamListItemTests {
    public static void testTeamListItem() {
        testTeamListItemConstructors();
        testTeamListItemGettersAndSetters();
        testTeamListItemMethods();
    }
    public static void testTeamListItemConstructors() {
        LocalDateTime testTime = LocalDateTime.of(2019,1,1,0,0);
        TeamListItem testItem = new TeamListItem(new ItemDetails("testItem", "desc")
                , testTime, 0.5);

        assert testItem.getDeadline().equals(testTime);
        assert testItem.getItemPriority() == 0.5;
        assert testItem.getAssignedUsers().equals(new ArrayList<ListUser>());
    }
    public static void testTeamListItemGettersAndSetters() {

    }
    public static void testTeamListItemMethods() {
        LocalDateTime testTime = LocalDateTime.of(2030,1,1,0,0);
        ListUser testUser = new ListUser("test", "pass");
        TeamListItem testItem = new TeamListItem(new ItemDetails("testItem", "desc")
                , testTime, 0.5);
        testItem.addUser(testUser);

        assert testItem.getDaysUntilDue() > 0;
        assert !testItem.isOverDue();
        assert testItem.getAssignedUsers().size() == 1;

        testItem.deleteUser("test");

        assert testItem.getAssignedUsers().size() == 0;
    }
}
