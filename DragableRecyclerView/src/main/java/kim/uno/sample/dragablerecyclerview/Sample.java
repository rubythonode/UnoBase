package kim.uno.sample.dragablerecyclerview;

import kim.uno.kotlin.base.item.RecyclerItem;

public class Sample implements RecyclerItem {

    public String message;

    @Override
    public int getViewType() {
        return 0;
    }

}