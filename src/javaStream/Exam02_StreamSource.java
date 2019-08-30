package javaStream;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * java.util.stream package �ȿ� �츮�� ����� �� �ִ� stream�� �����ؿ�!
 * BaseStream���κ��� ��ӹ޾Ƽ� ���� Stream�� ����
 * Stream => �ش� Stream �ȿ� ��ü �� �ִ� ���
 * IntStream => �ش� Stream �ȿ� int���� �� �ִ� ���
 * LongStream
 * DoubleStream
 * 
 * �������� ������ �پ��� source���� Stream�� �� �� �־��! 
 * 
 * 
 */
public class Exam02_StreamSource {
	
	private static List<String> names = 
			Arrays.asList("ȫ�浿","��浿","�ֱ浿");
	
	private static int myArr[] = {10,20,30,40,50};
	
	public static void main(String[] args) {
		
		// List�κ��� Stream ����
		Stream<String> stream1 = names.stream();
	
		// �迭�κ��� Stream ����
		IntStream stream2 = Arrays.stream(myArr);
		System.out.println(stream2.sum());
		
		// ������ ���� ������ �̿��ؼ� Stream�� ����
		IntStream stream3 = IntStream.rangeClosed(1, 10);
		
		// file�� ���� Stream ����
		// ��� path ��ü ��� ( File��ü(java.io)�� ������ java.nio�� ���Ե� class path )
		Path path = Paths.get("asset/readme.txt");
		try {
			
			// stream ����
			Stream<String> stream4 = Files.lines(path,
					Charset.forName("UTF-8"));
			
			stream4.forEach(t -> System.out.println(t));
			
			stream4.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
