---
layout: post
title: "RecyclerView 사용 간소화를 위한 기반 코드 작성"
date: 2016-11-10
categories:
  - Android
description:
image:
image-sm:
---

## RecyclerView 사용 간소화를 위한 기반 코드 작성

## XML
```
<kim.uno.kotlin.base.ui.BaseRecyclerView
    android:id="@+id/recycler_sample"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## JAVA
##### implements `RecyclerItem` : View holder type 반환을 위한 Interface

```
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

```

##### extends `BaseRecyclerAdapter` : 데이터 type(implements RecyclerItem) 분기를 통한 홀더 생성 및 Bind 간소화

```
public class SampleAdapter extends BaseRecyclerAdapter {

    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {

        // 단일 홀더 처리
        return new SampleHolder(this, parent);
    }

//    @Override
//    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
//
//        // 다중 홀더 처리를 위한 분기
//        BaseViewHolder holder = null;
//        switch (type) {
//            case 0: holder = new SampleHolder(this, parent); break;
//            case 1: holder = new SampleAnotherHolder(this, parent); break;
//            default: break;
//        }
//        return holder;
//    }

}

```

##### extends `BaseViewHolder` : inflate 구문 간소화 및 Generics 문법으로 position에 해당하는 객체를 onBindView 에 직접 전달

```
public class SampleHolder extends BaseViewHolder<Sample> {

    TextView tvContent;

    public SampleHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        tvContent.setText(item.message);
    }
}
```

##### ROW의 추가 (`addItem, addItems, setItems`)

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseRecyclerView recyclerSample = (BaseRecyclerView) findViewById(R.id.recycler_sample);
        recyclerSample.setScrollToTopButton(findViewById(R.id.v_top));
        recyclerSample.setLayoutManager(new LinearLayoutManager(this));

        SampleAdapter adapter = new SampleAdapter();
        recyclerSample.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            adapter.addItem(new Sample("sample position " + (i + 1)));
        }

        adapter.notifyDataSetChanged();
    }

}
```
