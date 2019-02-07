package tests;

import com.tylerkv.application.listitems.ShoppingListItem;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.Status;

public class ShoppingListItemTests {
    public static void testShoppingListItem() {
        testShoppingListItemConstructors();
        testShoppingListItemGettersAndSetters();
        testShoppingListItemMethods();
    }
    public static void testShoppingListItemConstructors() {
        ShoppingListItem testItem = new ShoppingListItem("Test Item", "This is a test item", 5);

        assert testItem.getListItemType() == ListType.SHOPPING;
        assert testItem.getStatus() == Status.INCOMPLETE;
        assert testItem.getItemName() == "Test Item";
        assert testItem.getDescription() == "This is a test item";
        assert testItem.getIsMutable();
        assert testItem.getItemCreated() != null;
    }

    public static void testShoppingListItemGettersAndSetters() {
        ShoppingListItem testItem = new ShoppingListItem("Test Item", "This is a test item", 5);
        testItem.setIsMutable(false);

        assert !testItem.getIsMutable();
    }

    public static void testShoppingListItemMethods() {
        ShoppingListItem testItem = new ShoppingListItem("Test Item", "This is a test item", 5);

        testItem.toggleComplete();

        assert testItem.getStatus() == Status.COMPELTE;
        assert testItem.getCompletedTime() != null;

        testItem.toggleComplete();

        assert  testItem.getStatus() == Status.INCOMPLETE;
        assert  testItem.getCompletedTime() == null;

    }
}
