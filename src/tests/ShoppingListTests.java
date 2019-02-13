package tests;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.ShoppingListItem;
import com.tylerkv.application.lists.ShoppingList;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListUser;

import java.util.ArrayList;

// TODO: Make a test for clearFinishedListItems()

public class ShoppingListTests {
    public static void testShoppingList() {
        testShoppingListConstructors();
        testShoppingListGettersAndSetters();
        testShoppingListMethods();
    }
    public static void testShoppingListConstructors() {
        ListUser testUser = new ListUser("Tyler", "pass");
        ShoppingList testList = new ShoppingList("My Test List", testUser);
        ArrayList<ListUser> comparisonUserList = new ArrayList<ListUser>();
        comparisonUserList.add(testUser);

        assert testList.getItemList().equals(new ArrayList<ListItem>());
        assert testList.getUserList().equals(comparisonUserList);
        assert testList.getListName().equals("My Test List");
        assert testList.getOwner().equals(testUser);
    }
    public static void testShoppingListGettersAndSetters() {
        ListUser testUser = new ListUser("Tyler", "pass");
        ShoppingList testList = new ShoppingList("My Test List", testUser);
        testList.setMaxListUsers(5);

        assert testList.getMaxListUsers() == 5;
    }

    public static void testShoppingListMethods() {
        ListUser testUser = new ListUser("Tyler", "pass");
        ShoppingList testList = new ShoppingList("My Test List", testUser);
        ShoppingListItem testItem = new ShoppingListItem(new ItemDetails("TestItem", "Desc"), 1);
        ArrayList<ListItem> comparisonItemList = new ArrayList<ListItem>();
        testList.addItem(testItem);
        comparisonItemList.add(testItem);

        assert testList.getItemList().equals(comparisonItemList);

        testList.deleteItem("TestItem");
        comparisonItemList.remove(0);
        testList.deleteUser("Tyler");

        assert testList.getItemList().equals(comparisonItemList);
        assert testList.getUserList().equals(new ArrayList<ListUser>());



    }
}
