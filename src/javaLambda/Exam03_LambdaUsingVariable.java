package javaLambda;

/*
 * 람다식을 정의해서 사용할 때 주의해야 할 점이 있어요!!
 * 클래스의 맴버(필드 + 메소드)와 로컬변수(지역변수)의 사용에 약간의 제약이 있어요
 * 
 * 특히 this keyword를 사용할 때 주의해야 해요!
 * this : 현재 사용되는 객체의 reference 
 * 람다식은 익명 객체를 만들어 내는 코드에요. 
 * 람다식의 실행코드 내에서 this keyword를 쓰면 익명객체를 지칭하지 않아요!
 * 상위 객체를 지칭하게 되요!
 * 
 * 람다식 안에서는 지역변수를 readonly 형태로 사용하게 되요!
 */

@FunctionalInterface
interface Exam03_LambdaIF{
	public void myFunc();
}

class outerClass {
		// Field ( 기본적으로 class의 field는 private )
		public int outerField = 100;
		
		public outerClass() {
			//defalult 생성자
			
			System.out.println(this.getClass().getName());
		}
		
		// class안에 다른 class를 정의할거에요. ( inner class )
		class innerClass {
			int innerField = 200; // Field
			
		
			Exam03_LambdaIF fieldLambda = () -> {
				System.out.println("outerField : "+outerField);
				System.out.println("outerClass의 객체를 찾아요 : " + outerClass.this.outerField);
				System.out.println("innerField : "+innerField);
				System.out.println("this.innerField : " + this.innerField);
				System.out.println(this.getClass().getName()); 
				};
		 
			// field
			// 생성자
			public innerClass() {
				System.out.println(this.getClass().getName());
			}
			// 일반 method
			public void innerMethod() {
				int localVal = 100;		// 지역변수 
				                        // 지역변수는 stack영역에 저장이 되고
				                        // method가 호출되면 생기고 
				                        // method가 끝나면 날라간다 
				Exam03_LambdaIF localLambda = () -> {
					System.out.println(localVal);
					//localVal = 50;		// final로 설정하면 값을 못바꿔요
											// 값을 바꿀수가 없어요 (readonly)
											// 람다에서 지역변수는 final로 내부적으로 지정함 
				};
				localLambda.myFunc();
			}
		}
}

// 프로그램의 시작을 위한 dummy class로 사용 
public class Exam03_LambdaUsingVariable {
	public static void main(String[] args) {
		// 람다식을 사용하려면 innerClass의 interface가 존재해야 해요!
		// 그런데 하필이면 이 innerClass가 inner class 내 ??
		// inner class의 instance를 생성하려면 outer class의 instance부터
		// 생서해야 해요!
		outerClass outer = new outerClass();
		outerClass.innerClass inner = outer.new innerClass();
		inner.fieldLambda.myFunc();
		inner.innerMethod();
	}
}
