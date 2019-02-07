package tests;

public class TestDriver {
    public static void runTestSuite() {
        ShoppingListTests.testShoppingList();
        ShoppingListItemTests.testShoppingListItem();
        ListUserTests.testListUser();
        ToDoListTests.testToDoList();
        ToDoListItemTests.testToDoListItem();
        GoalListTests.testGoalList();
        GoalListItemTests.testGoalListItem();
        TeamListTests.testTeamList();
        TeamListItemTests.testTeamListItem();
        ListDriverTests.testListDriver();
    }
}
