package tests;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.ShoppingListItem;
import com.tylerkv.application.utilities.ListDriver;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;
import com.tylerkv.application.utilities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ListDriverTests {
    public static void testListDriver() {
        testListDriverConstructors();
        testListDriverGettersAndSetters();
        testListDriverMethods();
    }
    public static void testListDriverConstructors() {
        ListUser testUser = new ListUser("tester", "pass");
        ListDriver testDriver = new ListDriver(testUser);

        assert testDriver.getOwner().equals(testUser);
    }
    public static void testListDriverGettersAndSetters() {

    }
    public static void testListDriverMethods() {
        ListUser testUser = new ListUser("tester", "pass");
        ListDriver testDriver = new ListDriver(testUser);

        testDriver.addShoppingList("test");
        testDriver.addShoppingList("test2");
        testDriver.addToDoList("test");
        testDriver.addToDoList("test2", 0.5);
        testDriver.addGoalList("test");
        testDriver.addTeamList("test");
        testDriver.addTeamList("test2", 0.8);

        testDriver.addItemToList("test2", ListType.SHOPPING, new ShoppingListItem("testItem", "desc", 5));
        ArrayList<ListItem> items = testDriver.getShoppingLists().get(1).getItemList();

        assert items.size() == 1;
        assert items.get(0).getItemName().equals("testItem");

        testDriver.deleteItemFromList("test2", ListType.SHOPPING, "testItem");
        items = testDriver.getShoppingLists().get(1).getItemList();

        assert items.size() == 0;

        testDriver.deleteList("test2", ListType.SHOPPING);
        testDriver.deleteList("test2", ListType.TODO);
        testDriver.deleteList("test2", ListType.TEAM);

        assert testDriver.getShoppingLists().size() == 1;
        assert testDriver.getToDoLists().size() == 1;
        assert testDriver.getTeamLists().size() == 1;

        ShoppingListItem testItem = new ShoppingListItem("item1", "desc", 5);
        testDriver.addItemToList("test", ListType.SHOPPING, testItem);

        assert testDriver.getShoppingLists().get(0).getItemList().get(0).getItemName().equals("item1");

        testDriver.toggleItemComplete("test", ListType.SHOPPING, "item1");

        assert testItem.getStatus() == Status.COMPELTE;

        testItem.setCompletedTime(LocalDateTime.of(2019,2,3,0,0));

        assert testDriver.getShoppingLists().get(0).getItemList().size() == 1;

        testDriver.cleanAllLists();

        assert testDriver.getShoppingLists().get(0).getItemList().size() == 0;
    }
}
