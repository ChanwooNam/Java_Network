package javaLambda;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
 * Funtion �Լ��� �������̽��� �Է� �Ű������� ���ϰ��� applyXXX() method�� �����ǿ�!!
 * �Ϲ������� �Է� �Ű������� ���ϰ����� mapping ��ų �� �Ϲ������� ���ǿ�!
 * 
 * Function<T,R> func = t -> { return ~~ };
 * T : �Է� �Ű������� generic
 * R : ���ϰ��� generic
 * 
 */


// Student VO class�� ����
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
			Arrays.asList(new Exam06_Student("ȫ�浿",10,20,30),
					new Exam06_Student("��浿",50,60,70),
					new Exam06_Student("�̼���",90,20,30),
					new Exam06_Student("�Ż��Ӵ�",10,100,70));
	
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

		// �л� �̸��� ���!
		printName( t ->  t.getsName() );
		
		// getAvg() static method�� ���� ������ ������ ����ϼ��� 
		// �л����� ����� ��� => getAvg()
		System.out.println("�������" + getAvg( k -> k.getsKor()) );
		// �л����� ����� ��� => getAvg()
		System.out.println("�������" + getAvg( e -> e.getsEng()) );
		// �л����� ���м��� ��� => getAvg()
		System.out.println("�������" + getAvg( m -> m.getsMath()) );
	}
}
