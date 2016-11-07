# UnoBase

[![License](https://img.shields.io/hexpm/l/plug.svg)]()

코틀린으로 작성된 라이브러리 프로젝트이며 자바 범용을 지향합니다.

- RecyclerView 베이스 코드
 - `BaseRecyclerView` scrollToTopButton, scroll fling animation, adapter recyclerView 중계 역할
 - `BaseRecyclerAdapter` attached holder 관리, set/add items 자동화, scroll dx/dy 전달역할
 - `BaseViewHolder` inflate, getItem(adapterPosition), scroll dx/dy 처리
 - `BaseDragView` drag & drop view 처리(state changed, gethandlerView, spawable)
 - `DragRecyclerAdapter` drag & drop 데이터 처리(top/bottom swap limit, state 중계)
 - `RecyclerItem` row type 반환을 위한 모델 interface

  ![gif_recyclerview_dragable] ![gif_recyclerview_scroll_offset_animation]

- Volley
 - `VolleyRequester` Volley 사용 간소화 코드
 - `VolleyObjectListener` response 응답 결과처리(json to Object)


- Util
 - `DateUtil` Date formatter/parser
 - `DisplayUtil` Display width/height 간소화, xhdpi 기준 상대값 추출
 - `ToastUtil` Toast 간소화
 - `ProgressUtil` ProgressDialog 간소화
 - `PrefUtil` SharedPreferences 간소화

## Requirements

- Target Sdk Version : 24
- Min Sdk Version : 15

## License

```
Copyright 2016 UnoBase

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[gif_recyclerview_dragable]: resources/gif_recyclerview_dragable.gif
[gif_recyclerview_scroll_offset_animation]: resources/gif_recyclerview_scroll_offset_animation.gif
