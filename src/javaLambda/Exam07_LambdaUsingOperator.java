package javaLambda;


import java.util.function.IntBinaryOperator;

/*
 * Operator�� Function�� �ϴ����� ���� ����ؿ�
 * �Է� �Ű������� �ְ� ���ϰ��� �־��
 * Function�� mapping �뵵�� ���� ���ǿ�. ( �Է� �Ű������� ����Ÿ������ ��ȯ,������ �뵵 )
 * Operator�� ����뵵�� ���� ���ǿ�. ( �Է� �Ű������� �̿��Ͽ� ���� Ÿ���� ���ϰ��� �����ִ� ���·� ��� )
 * 
 * �ִ밪�� �ּҰ��� ���ϴ� static method�� �ϳ� �ۼ��� ���ƿ� ! 
 */
public class Exam07_LambdaUsingOperator {
	
	private static int arr[] = { 100, 92, 50 ,89, 34, 27, 99, 3};
	
	// getMaxMin()�� static method�� ���鲨����!
	// ����ϴ� Operator�� IntBinaryOperator�� �̿�.
	private static int getMaxMin(IntBinaryOperator operator) {
		int result = arr[0];
		
		for(int k : arr) {
			result = operator.applyAsInt(result, k);
		}
		
		return result;
		
	}
	public static void main(String[] args) {

		//getMaxMin("MAX"); // �ִ밪�� ���ϴ� methodȣ��
		//getMaxMin("MIN"); // �ּҰ��� ���ϴ� methodȣ��
		// �̷��� ���� ���ƿ�!!
		// Operator�� �̿��ؼ� ó���� ���ƿ�!
		System.out.println("�ִ밪 : " + getMaxMin( ( a,b ) -> {
			return a >= b ? a : b;
		} ));
		/*
		 * getMaxMin( ( a,b ) -> { return a >= b ? a : b; } );
		 */
		System.out.println("�ּҰ� : "+ getMaxMin( ( a,b ) -> {
			return a < b ? a : b;
		} ));
		/*
		 * getMaxMin( ( a,b ) -> { return a < b ? a : b; } );
		 */
		
	}
}
