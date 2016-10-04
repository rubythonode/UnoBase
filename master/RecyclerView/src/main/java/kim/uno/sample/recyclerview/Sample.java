package kim.uno.sample.recyclerview;

import kim.uno.kotlin.base.item.RecyclerItem;

/**
 * Created by Uno.kim on 2016. 8. 5..
 */
public class Sample implements RecyclerItem {

    public String message;

    public Sample(String message) {
        this.message = message;
    }

    @Override
    public int getViewType() {
        return 0;
    }

}
