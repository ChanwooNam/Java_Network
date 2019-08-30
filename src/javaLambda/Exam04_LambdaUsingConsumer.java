package javaLambda;




import java.util.Arrays;
import java.util.List;

/*


import java.util.Arrays;
import java.util.List;

/*
 * 람다식은 추상메소드가 1개인 인터페이스의 객체를 생성하는
 * 표현식  => 
 * 이때 사용하는 인터페이스를 우리가 직접 만들어서 사용하나요??
 * ==> 그렇지 않아요!! 람다식이 대입되는 target type은
 * 일반적으로 Java가 제공하는 API를 이용.
 * 대표적인게... Runnable, Event처리 interface를 람다의 target type으로 사용 
 * 
 * Java에서는 람다의 target type으로 사용될 수 있는 
 * interface를 여러개 만들어서 우리에게 package형태로 제공
 * ( java.util.function package )
 * 제공되는 interface는 총 5가지 종류로 분류할 수 있어요! 
 * Consumer, Supplier, Function, Operation, Predicate
 * 
 * Consumer : 함수형 interface ( 람다식이 대입될 수 있는 target type으로 사용할 수 있는 interface를 지칭  )
 * Consumer는 Java가 우리에게 제공하는 interface이고 추상 메소드를 단 한개만 가지고 있어요 
 * accept()라는 method를 제공 
 * 값을 소비만 하는 역활을 담당. accept()라는 함수의 리턴 타입은 void  
 * */

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;
/*
 * ﻿
 * 람다식은 추상메소드가 1개인 인터페이스의 객체를 생성하는
 * 표현식 => 
 * 이때 사용하는 인터페이스를 우리가 직접 만들어서 사용하나요??
 * ==> 그렇지 않아요!! 람다식이 대입되는 target type은
 * 일반적으로 Java가 제공하는 API를 이용.
 * 대표적인게... Runnable, Event처리 interface를 람다의 target type으로 사용 
 * 
 * Java에서는 람다의 target type으로 사용될 수 있는 
 * interface를 여러개 만들어서 우리에게 package형태로 제공
 * ( java.util.function package )
 * 제공되는 interface는 총 5가지 종류로 분류할 수 있어요! 
 * Consumer, Supplier, Function, Operation, Predicate

 * Consumer : 함수형 interface ( 람다식이 대입될 수 있는 target type으로 사용할 수 있는 interface를 지칭 )
 * Consumer는 Java가 우리에게 제공하는 interface이고 추상 메소드를 단 한개만 가지고 있어요 
 * accept()라는 method를 제공 
 * 값을 소비만 하는 역활을 담당. accept()라는 함수의 리턴 타입은 void 
 * 
 * */

public class Exam04_LambdaUsingConsumer {
	// method를 하나 정의하는데 static으로 정의할래요. ( 편하게 쓸려고 )
	public static List<String> names = Arrays.asList("홍길동","김길동","최길동","박길동");
	// 일반적인 method 호출은 사용하는 data가 인자로 전달되는 형태
	// 람다식을 사용하면 method를 호출할 때 data가 아니라 실행코드를 넘겨줄 수 있어요. (눈에 보이는 형태만 그래요..)
	// 일반적으로 프로그래밍 언어에서 이렇게 함수를 다른 함수의 인자로
	// 사용할 수 있는데 이런 함수를 fist-classes function이라고 해요
	// 일급함수라고 표현해요 (javascript가 대표적)
	// Java 언어도 람다를 도입해서 마치 1급 함수를 사용하는 것 처럼 쓸수 있어요! 
	
	public static void printName(Consumer<String> consumer) {
		for(String name : names) {
			consumer.accept(name);
		}
	}
	
	public static void main(String[] args) {
		
		printName(t -> {System.out.println(t);});
		
		Consumer<String> consumer = t -> {
			System.out.println(t);
		};
		// t에 mapping 됨 
		consumer.accept("소리없는 아우성!");
		
		BiConsumer<String, String> biConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		biConsumer.accept("소리없는", "아우성");
		
		IntConsumer intConsumer = i -> System.out.println(i);
		intConsumer.accept(100);
		
		ObjIntConsumer<String> objIntConsumer = (a,b) -> {
			System.out.println("a + b");
		};
		objIntConsumer.accept("10", 20);
		
		
	}
}
