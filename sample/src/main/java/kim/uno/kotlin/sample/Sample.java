package kim.uno.kotlin.sample;

import kim.uno.kotlin.base.item.RecyclerItem;

/**
 * Created by Uno.kim on 2016. 8. 5..
 */
public class Sample implements RecyclerItem {

    public String message;

    @Override
    public int getViewType() {
        return 0;
    }

}
