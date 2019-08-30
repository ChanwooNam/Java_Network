package javaLambda;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/*
 *  Supplier��� �Ҹ��� �Լ��� �������̽� �������� �츮���� �����Ǵµ�
 *  �� �������̽��� Ư¡�� �Ű������� �����!
 *  getXXX()��� method�� �߻� �޼ҵ� ���·� �������̽��ȿ� ����Ǿ� �־��
 *  
 *  ģ�� ����� List<String> ���·� ������ 
 * */

public class Exam05_LambdaUsingSupplier {
	// �ζ� ��ȣ (1~45)�� �ڵ����� �����ϰ� ����ϴ� ������ method�� �ۼ� 
	public static void generateLotto(IntSupplier supplier,
			Consumer<Integer> consumer) {
		Set<Integer> set = new HashSet<Integer>();
		while(set.size() !=6) {
			set.add(supplier.getAsInt());
		}
		for(Integer i : set) {
			consumer.accept(i);
		}
		
	}
	
	public static void main(String[] args) {

		final List<String> myBuddy = Arrays.asList("ȫ�浿","��浿","�ֱ浿","�̼���");
		
		// Supplier�� �̿��ؼ� �������� 1���� ģ���� ����� ���ƿ�! 
		// Math.random() : 0�̻� 1�̸��� �Ǽ� => 0�̻� 4�̸��� �Ǽ�
		Supplier<String> supplier = () -> {
			return myBuddy.get((int)(Math.random() * 4));
			
		};
		System.out.println(supplier.get());
		
		/////////////////////////////
		// IntSupplier : �������� 1�� �����ϴ� supplier
		// �ζ� ��ȣ�� �ڵ����� �����ϰ� ����ϴ� ������ method�� �ۼ�
		// generateRotto(supplier,consumer)
		generateLotto(() -> {
			return (int)(Math.random() * 45 + 1);
		},t-> { System.out.println(t +" ");} );
	}
}
