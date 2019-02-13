package tests;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.ToDoListItem;
import com.tylerkv.application.lists.ToDoList;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListUser;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDoListTests {
    public static void testToDoList() {
        testToDoListConstructors();
        testToDoListGettersAndSetters();
        testToDoListMethods();
    }
    public static void testToDoListConstructors() {
        ListUser testUser = new ListUser("name", "pass");
        ToDoList testList1 = new ToDoList("name", testUser);
        ToDoList testList2 = new ToDoList("name", testUser, 0.50);

        assert testList1.getListPriority() == 0;
        assert testList2.getListPriority() == 0.5;
    }
    public static void testToDoListGettersAndSetters() {

    }
    public static void testToDoListMethods() {
        ListUser testUser = new ListUser("name", "pass");
        ToDoList testList1 = new ToDoList("name", testUser);

        // Testing sorting
        testList1.addItem(new ToDoListItem(new ItemDetails("item1","desc"), LocalDateTime.of(2019,1,5,0,0)));
        testList1.addItem(new ToDoListItem(new ItemDetails("item2","desc"), LocalDateTime.of(2019,1,3,0,0)));
        testList1.addItem(new ToDoListItem(new ItemDetails("item3","desc"), LocalDateTime.of(2019,1,1,0,0)));
        testList1.addItem(new ToDoListItem(new ItemDetails("item4","desc"), LocalDateTime.of(2019,1,2,0,0)));
        testList1.addItem(new ToDoListItem(new ItemDetails("item5","desc"), LocalDateTime.of(2019,1,4,0,0)));
        testList1.addItem(new ToDoListItem(new ItemDetails("item6","desc"), LocalDateTime.of(2019,1,4,0,0), 0.5));
        testList1.addItem(new ToDoListItem(new ItemDetails("item7","desc"), LocalDateTime.of(2019,1,4,0,0), 0.5));
        testList1.addItem(new ToDoListItem(new ItemDetails("item8","desc"), LocalDateTime.of(2019,1,4,0,0), 0.1));

        ArrayList<ListItem> returnList = testList1.calculateListOrder();
        ToDoListItem typecastedItem1 = (ToDoListItem) returnList.get(3);
        ToDoListItem typecastedItem2 = (ToDoListItem) returnList.get(5);

        assert returnList.get(0).getItemName().equals("item3");
        assert returnList.get(7).getItemName().equals("item1");
        assert typecastedItem1.getItemPriority() == 0.5;
        assert typecastedItem2.getItemPriority() == 0.1;

    }
}
