### `BaseRecyclerView`를 기반으로한 Drag & Drop 샘플 코드입니다.

- ROW 단위로 Swap enable 조절구문 추가 및 스크롤 제한 구문이 추가됨

### PREVIEW
![gif_recyclerview_dragable]

# USE

### XML
```
<kim.uno.kotlin.base.ui.BaseRecyclerView
    android:id="@+id/recycler_sample"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### JAVA

- extends `DragRecyclerAdapter` : 핸들을 이용한 아이템 Swap 관련 코드 간소화

```
public class SampleAdapter extends DragRecyclerAdapter {

    public SampleAdapter() {
        super();
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
        return new SampleHolder(this, parent);
    }

}
```

- implements `BaseDragView` : Drag 관련 handleView, swapable 조건, state 값을 전달할 interface

```
public class SampleHolder extends BaseViewHolder<Sample> implements BaseDragView {

    TextView tvContent;
    View vHandle;

    public SampleHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        vHandle = itemView.findViewById(R.id.v_handle);
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        tvContent.setText(item.message);
        vHandle.setVisibility(item.isSwapable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDragStateChanged(boolean isSelected) {
        itemView.setAlpha(isSelected ? 0.8f : 1f);
    }

    @Nullable
    @Override
    public View getHandleView() {
        return vHandle;
    }

    @Override
    public boolean isSwapable() {
        Sample item = getItem();
        return item != null && item.isSwapable;
    }
}
```

[gif_recyclerview_dragable]: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_dragable.gif?raw=true