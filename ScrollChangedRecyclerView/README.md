---
layout: post
title: "BaseRecyclerView 기반 스크롤 애니메이션, 플링효과"
date: 2016-11-10
categories:
  - Android
description:
image: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_animation.gif?raw=true
image-sm: https://github.com/uno-dos/UnoBase/blob/master/_resources/gif_recyclerview_scroll_animation.gif?raw=true
---

## `BaseRecyclerView`를 기반으로한 스크롤 애니메이션, 플링효과 샘플코드입니다.

## PREVIEW
![gif_recyclerview_scroll_animation] ![gif_recyclerview_scroll_fling_vertical] ![gif_recyclerview_scroll_fling_horizontal] ![gif_recyclerview_scroll_focus_resize]

## USE

##### FLING

```
recyclerSample.setEnableFling(true);
```

##### MARGIN OFFSET ANIMATION

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

##### FOCUS RESIZE ANIMATION

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
