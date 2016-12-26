---
layout: post
title: "BaseRecyclerView를 사용하여 스크롤 애니메이션, 플링효과 주기"
date: 2016-11-10
categories:
  - Android
description:
image: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_animation.gif?raw=true
image-sm: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_animation.gif?raw=true
---

## `BaseRecyclerView`를 사용하여 스크롤 애니메이션, 플링효과 주기

- [RecyclerView 사용시 코드 간소화를 위한 Base class(feat.Kotlin)](http://uno.kim/2016/11/10/2.BaseRecyclerView/)
- [BaseRecyclerView를 사용하여 스크롤 애니메이션, 플링효과 주기](http://uno.kim/2016/11/10/3.ScrollChangedRecyclerView/)
- [BaseRecyclerView를 사용하여 Drag & Drop기능 사용하기](http://uno.kim/2016/11/10/4.DragAndDropRecyclerView/)

## PREVIEW
![gif_recyclerview_scroll_animation] ![gif_recyclerview_scroll_fling_vertical] ![gif_recyclerview_scroll_fling_horizontal] ![gif_recyclerview_scroll_focus_resize]

## SAMPLE [(java)](https://github.com/uno-dos/UnoBase/tree/master/ScrollChangedRecyclerView)

BaseRecyclerView 스크롤 동작을 ViewPager의 페이징 처리되도록 지정 가능하다. <br>
FlingGravity는 START, CENTER, END 3가지 유형을 제공한다. <br>
`baseRecyclerView.setFlingEnable(true, FlingGravity.START);`

BaseViewHolder는 RecyclerView container의 크기를 기준으로 제공되는 position factor와 view의 스크롤처리가 완료된 단위별 dx, dy 값을 이용해 사용자가 원하는 애니메이션을 구현할 수 있다.

`MARGIN OFFSET ANIMATION`

```
MarginOffsetHolder.java

@Override
public void onScrollChanged(float position, int dx, int dy) {
    super.onScrollChanged(position, dx, dy);
    if (isHorizontal) {
        ivContent.setTranslationX(scrollMargin - (scrollMargin * position));
        tvContent.setTranslationX(dx);
    } else {
        ivContent.setTranslationY(scrollMargin - (scrollMargin * position));
        tvContent.setTranslationY(dy);
    }
}
```

`FOCUS RESIZE ANIMATION`

```
FocusResizeHolder.java

@Override
public void onScrollChanged(float position, int dx, int dy) {
    super.onScrollChanged(position, dx, dy);

    position -= focusOffset;

    float normalize = Math.max(foldFactor, Math.min(1f, 1 - Math.abs(position)));
    tvContent.setAlpha(normalize * 1.5f);
    int computeSize = (int) (focusSize * normalize);
    if (isHorizontal) {
        itemView.getLayoutParams().width = computeSize;
    } else {
        itemView.getLayoutParams().height = computeSize;
    }

    itemView.requestLayout();
}
```

[gif_recyclerview_scroll_animation]: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_animation.gif?raw=true
[gif_recyclerview_scroll_fling_vertical]: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_fling_vertical.gif?raw=true
[gif_recyclerview_scroll_fling_horizontal]: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_fling_horizontal.gif?raw=true
[gif_recyclerview_scroll_focus_resize]: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_focus_resize.gif?raw=true
