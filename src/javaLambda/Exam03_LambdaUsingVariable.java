package javaLambda;


/*
 * ���ٽ��� �����ؼ� ����� �� �����ؾ� �� ���� �־��!!
 * Ŭ������ �ɹ�(�ʵ� + �޼ҵ�)�� ���ú���(��������)�� ��뿡 �ణ�� ������ �־��
 * 
 * Ư�� this keyword�� ����� �� �����ؾ� �ؿ�!
 * this : ���� ���Ǵ� ��ü�� reference 
 * ���ٽ��� �͸� ��ü�� ����� ���� �ڵ忡��. 
 * ���ٽ��� �����ڵ� ������ this keyword�� ���� �͸�ü�� ��Ī���� �ʾƿ�!
 * ���� ��ü�� ��Ī�ϰ� �ǿ�!
 * 
 * ���ٽ� �ȿ����� ���������� readonly ���·� ����ϰ� �ǿ�!
 */

@FunctionalInterface
interface Exam03_LambdaIF{
	public void myFunc();
}

class outerClass {
		// Field ( �⺻������ class�� field�� private )
		public int outerField = 100;
		
		public outerClass() {
			//defalult ������
			
			System.out.println(this.getClass().getName());
		}
		
		// class�ȿ� �ٸ� class�� �����Ұſ���. ( inner class )
		class innerClass {
			int innerField = 200; // Field
			
		
			Exam03_LambdaIF fieldLambda = () -> {
				System.out.println("outerField : "+outerField);
				System.out.println("outerClass�� ��ü�� ã�ƿ� : " + outerClass.this.outerField);
				System.out.println("innerField : "+innerField);
				System.out.println("this.innerField : " + this.innerField);
				System.out.println(this.getClass().getName()); 
				};
		 
			// field
			// ������
			public innerClass() {
				System.out.println(this.getClass().getName());
			}
			// �Ϲ� method
			public void innerMethod() {
				int localVal = 100;		// �������� 
				                        // ���������� stack������ ������ �ǰ�
				                        // method�� ȣ��Ǹ� ����� 
				                        // method�� ������ ���󰣴� 
				Exam03_LambdaIF localLambda = () -> {
					System.out.println(localVal);
					//localVal = 50;		// final�� �����ϸ� ���� ���ٲ��
											// ���� �ٲܼ��� ����� (readonly)
											// ���ٿ��� ���������� final�� ���������� ������ 
				};
				localLambda.myFunc();
			}
		}
}

// ���α׷��� ������ ���� dummy class�� ��� 
public class Exam03_LambdaUsingVariable {
	public static void main(String[] args) {
		// ���ٽ��� ����Ϸ��� innerClass�� interface�� �����ؾ� �ؿ�!
		// �׷��� �����̸� �� innerClass�� inner class �� ??
		// inner class�� instance�� �����Ϸ��� outer class�� instance����
		// �����ؾ� �ؿ�!
		outerClass outer = new outerClass();
		outerClass.innerClass inner = outer.new innerClass();
		inner.fieldLambda.myFunc();
		inner.innerMethod();
	}
}
