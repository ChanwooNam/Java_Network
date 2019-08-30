package javaLambda;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 * Predicate�� �Է� �Ű������� �־��. boolean ����.
 * ���Ǵ� method�� testXXX()�� ���ǿ�!
 * �Է� �Ű������� �����Ͽ� true, flase���� �����ؾ� �ϴ� ���.  
 * 
 * �л� ��ü�� ���� List�� �����Ұſ���
 * static method�� ���� ���ٽ����� ���ڸ� �Ѱ��ٰſ���
 * ������ ���� Ư�� ������ ����� ���� �� �ֵ��� method�� �ۼ��� ���ƿ�
 * 
 */

class Exam08_student {
	private String name;	// �л��̸�
	private int kor;	// �����
	private int eng;	// �����
	private int math;	// ���м���
	private String gender;	// ����
	
	public Exam08_student() {}
	
	public Exam08_student(String name, int kor, int eng, int math, String gender) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}

public class Exam08_LambdaUsingPredicate {
	
	private static List<Exam08_student> students =
			Arrays.asList(new Exam08_student("ȫ�浿",10,20,30,"����"),
					new Exam08_student("�ڱ浿",20,90,60,"����"),
					new Exam08_student("�Ż��Ӵ�",30,30,90,"����"),
					new Exam08_student("������",80,80,100,"����"),
					new Exam08_student("�̼���",30,10,10,"����"));
	
	// static method�� �ϳ� ���� �ϴµ� ������ ���� Ư�������� ����� ���ϴ� �۾�
	private static double avg(Predicate<Exam08_student> predicate,
			ToIntFunction<Exam08_student> function) {
		
		int sum = 0;
		int count = 0;
		
		for(Exam08_student s : students) {
			if(predicate.test(s)) {
				count ++;
				sum += function.applyAsInt(s);
			}
		}
		System.out.println((double)sum/count);
		return (double)sum/count;
	}
	
	public static void main(String[] args) {
		
		// t -> t.getGender()
		// ���� ���� ��ü�� �����°� string
		// t -> t.getGender().equals("����")
		// �Է��� ��ü ������ true or false
		avg( t -> t.getGender().equals("����"), t -> t.getMath() ); // ���ڵ鿡 ���� ���������?
		avg( t -> t.getGender().equals("����"), t -> t.getEng() );
	}
	
}
