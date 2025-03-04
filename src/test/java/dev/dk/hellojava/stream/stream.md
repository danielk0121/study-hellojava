### stream 자주 쓰는 것들

---
- for 대신에 Int 스트림 사용
```
IntStream.rangeClosed(1,10)
```

---
- IntStream.collect() 에러 코드
```
//IntStream.collect() 는 파라미터가 3개임
//Integer Stream 으로 변경하면 collect() 파라미터가 1개임
//IntStream 의 Int 는 primitive 타입이기 때문
IntStream.rangeClosed(1,10).collect(Collectors.toList())
```
- 간단히 int 를 Integer 로 변경, boxed() 사용
```
IntStream.rangeClosed(1,10).boxed().collect(Collectors.toList())
```
- 또는 mapToObj() 사용
```
IntStream.rangeClosed(1,10).mapToObj(x -> "" + x).collect(Colllects.toList())
```

---
- 그럼 IntStream.collect() 파라미터 3개 사용해보자
- 별로 추천되지 않음
- collect(supplier, accumulator, combiner) 를 해석해보면
- int 스트림에 int 들을 collect 하기 위해서
- supplier : 감싸야 하는 객체 생성자
- accumulator : int 를 사용하는 객체의 소비자 함수
- combiner : 각 객체들을 연결하는 함수
```java
class foo {
void bar() {
    List<Integer> list = IntStream.rangeClosed(1,10)
        .collect(
            // java.util.function.Supplier<R> supplier
            // java.util.function.ObjIntConsumer<R> accumulator
            // java.util.function.BiConsumer<R,R> combiner
            () -> new ArrayList(),
            (ArrayList list, int x) -> list.add(x),
            (ArrayList left, ArrayList right) -> left.addAll(right)
        );
}
}
```

---
- ㅁㄴㅇㄹ








