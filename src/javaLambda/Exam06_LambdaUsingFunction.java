package javaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
 * Funtion 함수적 인터페이스는 입력 매개변수와 리턴값이 applyXXX() method가 제공되요!!
 * 일반적으로 입력 매개변수를 리턴값으로 mapping 시킬 때 일반적으로 사용되요!
 * 
 * Function<T,R> func = t -> { return ~~ };
 * T : 입력 매개변수의 generic
 * R : 리턴갑의 generic
 * 
 */


// Student VO class를 정의
class Exam06_Student {
	
	private String sName;
	private int sKor;
	private int sEng;
	private int sMath;
	
	public Exam06_Student() {}

	public Exam06_Student(String sName, int sKor, int sEng, int sMath) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public int getsKor() {
		return sKor;
	}
	public void setsKor(int sKor) {
		this.sKor = sKor;
	}
	public int getsEng() {
		return sEng;
	}
	public void setsEng(int sEng) {
		this.sEng = sEng;
	}
	public int getsMath() {
		return sMath;
	}
	public void setsMath(int sMath) {
		this.sMath = sMath;
	}
	
}

public class Exam06_LambdaUsingFunction {
	
	private static List<Exam06_Student> students = 
			Arrays.asList(new Exam06_Student("홍길동",10,20,30),
					new Exam06_Student("김길동",50,60,70),
					new Exam06_Student("이순신",90,20,30),
					new Exam06_Student("신사임당",10,100,70));
	
	private static void printName(Function<Exam06_Student,String> function) {
		
		for(Exam06_Student s : students) {
			System.out.println(function.apply(s));
		}
	}
	private static double getAvg(Function<Exam06_Student,Integer> function) {
		
		int sum = 0;
		
		for(Exam06_Student s : students) {
			sum = sum + function.apply(s);
			
		}
		return (double)sum/students.size();
	}
	private static double getAvgg(ToIntFunction<Exam06_Student> function) {
		
		int sum = 0;
		
		for(Exam06_Student s : students) {
			sum = sum + function.applyAsInt(s);
			
		}
		return (double)sum/students.size();
	}
	
	public static void main(String[] args) {

		// 학생 이름을 출력!
		printName( t ->  t.getsName() );
		
		// getAvg() static method를 만들어서 다음의 내용을 출력하세요 
		// 학생들의 국어성적 평균 => getAvg()
		System.out.println("국어평균" + getAvg( k -> k.getsKor()) );
		// 학생들의 영어성적 평균 => getAvg()
		System.out.println("영어평균" + getAvg( e -> e.getsEng()) );
		// 학생들의 수학성적 평균 => getAvg()
		System.out.println("수학평균" + getAvg( m -> m.getsMath()) );
	}
}
