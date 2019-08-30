package javaThread;


/*
 * 2개의 Thread를 파생시켜서 공용객체를 이용하도록 만들어 보아요!
 * Thread가 공용객체를 동기화해서 
 * 사용하는 경우, 그렇지 않은 경우를
 * 비교해서 이해해 보아요! 
 * 
 * 공용객체를 만들기 위한 class를 정의해 보아요
 * 
 */

// 1.
class SharedObject {

	private int number; // 공용객체가 가지는 field

	// getter & setter ( Thread에 의해서 사용되요 )
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	// Thread에 의해서 사용되는 business method
	// synchronized keyword로 동기화를 할 수 있어요
	// method 동기화는 효율이 좋지 않아요!
	// 동기화 block을 이용해서 처리하는게 일반적.
	public void assignNumber(int number) {
		synchronized (this) {

			this.number = number;
			try {
				Thread.sleep(3000);
				System.out.println("현재 공용객체의 number : " + this.number);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}

//3. 
// runnable interface를 구현한 class( Thread 생성자의 인자로 이용하기 위해 )
class MyRunnable implements Runnable {
	//////////// 공용객체를 사용하겠다 /////////////
	// 공용객체를 가지고 있겠다 = 필드로 가지고 있겠다
	SharedObject shared;
	int input;

	// 생성자 , 공용객체를 인자로 받는다
	public MyRunnable(SharedObject shared, int input) {
		this.shared = shared;
		this.input = input;
	}

	//////////////////////////////////////////
	public void run() {
		// thread 2개 만들어지면
		// run이 동시에 수행됨
		//
		// 공용객체에 assignnumber에 인자 100 or 200 준다

		shared.assignNumber(input);
		// 1번째 thread가 동기화 method assingNumber을 수행
		// 3초간 자고
		// 이때 2번째 thread가 요청해도 block
		// 1번째가 일어나서 화면에 찍고 monitor를 놓게되면
		// 그제서야 2번째 thread가 monitor 획득해서 수행가능
	}
}

public class Exam04_ThreadSync {

	public static void main(String[] args) {
		// 2.
		// 공용객체를 하나 생성해요
		SharedObject shared = new SharedObject();
		// Thread를 생성 (2개), 공용객체를 가지는 Thread를 생성
		// 공용객체를 runnable객체에 인자로 주어서 thread가 하나의 자원을 공유하게 된다
		Thread t1 = new Thread(new MyRunnable(shared, 100));
		Thread t2 = new Thread(new MyRunnable(shared, 200));

		// Thread 실행 ( runnable 상태로 전환 )
		t1.start();
		t2.start();
	}
}
