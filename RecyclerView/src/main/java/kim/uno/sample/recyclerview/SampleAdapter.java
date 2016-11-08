package kim.uno.sample.recyclerview;

import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;

public class SampleAdapter extends BaseRecyclerAdapter {

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {

        // 단일 홀더 처리
        return new SampleHolder(this, parent);
    }

//    @NotNull
//    @Override
//    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
//
//        // 다중 홀더 처리를 위한 분기
//        BaseViewHolder holder = null;
//        switch (type) {
//            case 0: holder = new SampleHolder(this, parent);
//                break;
//
//            case 1: holder = new SampleAnotherHolder(this, parent);
//
//            default:
//                break;
//
//        }
//        return holder;
//    }

}