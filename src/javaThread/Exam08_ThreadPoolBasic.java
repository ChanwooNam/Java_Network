package javaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam08_ThreadPoolBasic extends Application {
	
	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : Thread Pool을 종료하는 버튼
	
	ExecutorService executorService;
	// executorService : Thread Pool

	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/////////// 화면 구성해서 window 띄우는 코드 //////////////
		
		// 화면기본 layout을 설정 => 화면을 동서남북중앙( 5개 영역 )으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component 생성해서 BorderPane에 부착 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t -> {
			//// 버튼에서 Action이 발생(클릭)했을 때 호출 
			
			// Thread Pool 생성
			executorService = Executors.newCachedThreadPool();
			// 현재 Thread Pool 안에 thread가 몇개 있나 찍어보자 
			int threadNum = ((ThreadPoolExecutor)executorService).getPoolSize();
			printMsg("현재 pool안의 Thread 개수 : " + threadNum + "\n");
			// 처음에 만들어지는 Thread Pool 안에는 thread가 없어요.
			// 만약 필요하면 내부적으로 Thread를 생성.
			// 만드는 Thread의 수는 제한이 없어요.
			// 60초 동안 Thread가 사용되지 않으면 자동적으로 삭제. 
			
			// Thread Pool 생성
			//executorService = Executors.newFixedThreadPool(5);
			// 처음에 만들어지는 Thread Pool 안에는 thread가 없어요.
			// 만약 필요하면 내부적으로 Thread를 생성.
			// 인자로 들어온 int 수 만큼의 Thread를 넘지 못해요. ( 최대 5개 ) 
			// Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지 
		});	
		
		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			executorService.shutdown();
			printMsg("Thread Pool 종료");
		});
		
		startBtn = new Button("Thread 실행");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for(int i=0; i<10; i++) {
				final int ii = i;
				Runnable runnable = () -> {
						Thread.currentThread().setName("MY-THREAD-" + ii);
						String msg = Thread.currentThread().getName() + 
								" Pool의 개수 : " +
								((ThreadPoolExecutor)executorService).getPoolSize() +
								"\n";
						printMsg(msg);
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							System.out.println(e);
						}
					};
					// runnable 객체만 execute 함수를 이용해서 thread pool에 넣어주면 
					// thread pool이 알아서 실행시킨다 
					executorService.execute(runnable);
				}
		});
		
		// 판때기 하나 만들고 그위에 버튼 부착
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(initBtn);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		// 화면을 띄우기 위해서 Scene 객체 생성
		Scene scene = new Scene(root);
		// window에 화면설정
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다. ");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {

		launch();
	}

}