package kim.uno.sample.recyclerview;

import kim.uno.kotlin.base.item.RecyclerItem;

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
