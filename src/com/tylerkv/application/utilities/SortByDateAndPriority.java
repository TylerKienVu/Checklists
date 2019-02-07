package com.tylerkv.application.utilities;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.ToDoListItem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class SortByDateAndPriority implements Comparator<ListItem> {

    public int compare(ListItem itemA, ListItem itemB){
        ToDoListItem typeCastItemA = (ToDoListItem) itemA;
        ToDoListItem typeCastItemB = (ToDoListItem) itemB;

        int daysUntilA = (int)ChronoUnit.DAYS.between(LocalDateTime.now()
                , typeCastItemA.getCompletionRangeEndDate());
        int daysUntilB = (int)ChronoUnit.DAYS.between(LocalDateTime.now()
                , typeCastItemB.getCompletionRangeEndDate());

        // NOTE: Comparator's work by comparing and returning one of the following: 1, 0, -1

        // If the due dates are the same, use priority to settle difference
        if (daysUntilA == daysUntilB){
            return Double.compare(typeCastItemB.getItemPriority(), typeCastItemA.getItemPriority());
        }
        else {
            return daysUntilA - daysUntilB;
        }
    }
}
