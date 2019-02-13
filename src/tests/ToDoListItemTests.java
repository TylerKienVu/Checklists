package tests;

import com.tylerkv.application.listitems.ToDoListItem;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListType;

import java.time.LocalDateTime;

public class ToDoListItemTests {
    public static void testToDoListItem() {
        testToDoListItemConstructors();
        testToDoListItemGettersAndSetters();
        testToDoListItemMethods();
    }
    public static void testToDoListItemConstructors() {
        ToDoListItem testItem1 = new ToDoListItem(new ItemDetails("name","desc")
                , LocalDateTime.of(2019, 7, 13, 0, 0));
        ToDoListItem testItem2 = new ToDoListItem(new ItemDetails("name","desc")
                , LocalDateTime.now()
                , LocalDateTime.of(2019, 7, 13, 0, 0));
        ToDoListItem testItem3 = new ToDoListItem(new ItemDetails("name","desc")
                , LocalDateTime.of(2019, 7, 13, 0, 0)
                , .50);
        ToDoListItem testItem4 = new ToDoListItem(new ItemDetails("name","desc")
                , LocalDateTime.now()
                , LocalDateTime.of(2019, 7, 13, 0, 0)
                , .50);

        assert testItem1.getItemName() == "name";
        assert testItem1.getDescription() == "desc";
        assert testItem1.getCompletionRangeEndDate() != null;
        assert testItem1.getCompletionRangeStartDate() == null;
        assert testItem1.getItemPriority() == 0;
        assert testItem1.getListItemType() == ListType.TODO;

        assert testItem2.getItemName() == "name";
        assert testItem2.getDescription() == "desc";
        assert testItem2.getCompletionRangeEndDate() != null;
        assert testItem2.getCompletionRangeStartDate() != null;
        assert testItem2.getItemPriority() == 0;
        assert testItem2.getListItemType() == ListType.TODO;

        assert testItem3.getItemName() == "name";
        assert testItem3.getDescription() == "desc";
        assert testItem3.getCompletionRangeEndDate() != null;
        assert testItem3.getCompletionRangeStartDate() == null;
        assert testItem3.getItemPriority() == .50;
        assert testItem3.getListItemType() == ListType.TODO;

        assert testItem4.getItemName() == "name";
        assert testItem4.getDescription() == "desc";
        assert testItem4.getCompletionRangeEndDate() != null;
        assert testItem4.getCompletionRangeStartDate() != null;
        assert testItem4.getItemPriority() == .50;
        assert testItem4.getListItemType() == ListType.TODO;
    }
    public static void testToDoListItemGettersAndSetters() {
        try {
            ToDoListItem testItem = new ToDoListItem(new ItemDetails("name","desc")
                    , LocalDateTime.of(2019, 7, 13, 0, 0)
                    , 1.50);
        }
        catch (IllegalArgumentException e) {
            assert e.getMessage() == "The priority must be between 0.0 and 1.0 inclusive";
        }

        try {
            ToDoListItem testItem = new ToDoListItem(new ItemDetails("name","desc")
                    , LocalDateTime.of(2019, 7, 13, 0, 0)
                    , -0.5);
        }
        catch (IllegalArgumentException e) {
            assert e.getMessage() == "The priority must be between 0.0 and 1.0 inclusive";
        }
    }
    public static void testToDoListItemMethods() {
        LocalDateTime testTime1 = LocalDateTime.of(2050,7,13,0,0);
        LocalDateTime testTime2 = LocalDateTime.of(1996,7,13,0,0);
        ToDoListItem testItem1 = new ToDoListItem(new ItemDetails("TestItem", "Desc"), testTime1);
        ToDoListItem testItem2 = new ToDoListItem(new ItemDetails("TestItem", "Desc"), testTime2);

        try {
            assert testItem1.getDaysUntilDue() > 0;
            assert !testItem1.isOverDue();

            assert testItem2.getDaysUntilDue() < 0;
            assert testItem2.isOverDue();
        }
        catch(IllegalAccessException e) {

        }

    }
}
