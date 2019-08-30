package javaStream;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream�� Java 8���� ������ �Ǿ����! �����ؾ� �Ұ� java.io�ȿ� �ִ�
 * Stream���� �ٸ� ���̿���.
 * ��� �뵵 : �÷���ó��(List,Set,Map)�� ���ؼ� ����� �ǿ�!
 *          �÷��� ���� �����͸� �ݺ���Ű�� �ݺ����� ��Ȱ�� �ϴ°� stream
 *          ex) ArrayList �ȿ� �л� ��ü�� 5�� ������ �� 5���� �ϳ��� �������� ��Ȱ�� ����.
 *               => �̷��� ������ �����͸� ���ٽ��� �̿��ؼ� ó���� �� �־��!
 */

class Exam01_Student {
	private String name;
	private int kor;
	private int eng;
	public Exam01_Student() {
		super();
	}
	public Exam01_Student(String name, int kor, int eng) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
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
	
}

public class Exam01_StreamBasic {

	private static List<String> myBuddy = 
			Arrays.asList("ȫ�浿","��浿","�ֱ浿","�Ż��Ӵ�");
	
	private static List<Exam01_Student> students =
			Arrays.asList(new Exam01_Student("ȫ�浿",10,20),
					new Exam01_Student("�ֱ浿",60,30),
					new Exam01_Student("�ڱ浿",30,80),
					new Exam01_Student("��浿",90,10));
	
	public static void main(String[] args) {

		//// ����̸��� ����Ϸ��� �ؿ�!
		// ��� 1. �Ϲ� for�� (÷�ڸ� ���)�� �̿��ؼ� ó��.
		for(int i=0; i<myBuddy.size(); i++) {
			System.out.println(myBuddy.get(i));
		}
		// ��� 2. ÷�ڸ� �̿��� �ݺ��� ���ϱ� ���� Iterator�� ���.
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		// ��� 3. Stream 
		// list�� ���� ��θ� ���㼭 �����͸� �ϳ��� stream���� ����شٰ� ��������
		// myBuddy�� ���ؼ� stream �վ�!
		Consumer<String> consumer = t -> {
			System.out.println(t + ", "
					+Thread.currentThread().getName());
		};
		//Stream<String> stream = myBuddy.Stream();
		Stream<String> stream = myBuddy.parallelStream();
		// �ݺ�. forEach : stream �ȿ� �ִ°� �ݺ���
		// i or iter ���� �ݺ��ڰ� �ʿ�����. ���� �ݺ��ڸ� �̿�.
		// ����ó���� ����.
		stream.forEach(consumer);
		//stream.forEach(t -> System.out.println(t) );
		
		// ArrayList�� ���� stream ����
		Stream<Exam01_Student> studentStream = students.stream();
		// mapToInt() : ��ü�� ���������� mapping , ������� ������ų�� ���ٽ��� ���ڷ� ����� 
		double avg = 
				studentStream.mapToInt(t -> t.getKor()).average().getAsDouble();
		
	}
}
