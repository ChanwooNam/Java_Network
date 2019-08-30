package javaThread;

/*
 * 1부터 100까지 숫자의 합을 구하려고 해요
 * 1~10까지 1개의 Thread가 합을 계산해서 결과를 return
 * 11~20까지 1개의 Thread가 합을 계산해서 결과를 return
 * ....
 * 91~100까지 1개의 Thread가 합을 계산해서 결과를 return
 * 
 * ==> Thread Pool을 이용해야 하고 Callable을 이용해서 return값을 받아야해요.
 * ==> 10개의 Thread로부터 각가 데이터를 받아들이는 Thread를 하나 만들어야 해요.
 * 
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam10_ThreadCompleteService extends Application {

	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : Thread Pool을 종료하는 버튼

	ExecutorService executorService;
	// executorService : Thread Pool

	ExecutorCompletionService<Integer> excutorCompletionService;
	private int total = 0;

	private void printMsg(String msg) {
		Platform.runLater(() -> {
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
			excutorCompletionService = 
					new ExecutorCompletionService<Integer>(executorService);
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
			for (int i = 0; i < 101; i=i+10) {
				final int k = i;

				Callable<Integer> callable = new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						
						IntStream intstream = IntStream.rangeClosed(k, k+9);
						int sum = intstream.sum();
						
						return sum;
					}
				};
				// 실행
				excutorCompletionService.submit(callable);
			}
			
			// 결과값 취합하는 Thread를 만들자 
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for(int i=0; i<10; i++) {
						// thread가 끝나는 시간을 모르기 때문에 
						// 확장된 thread pool은 take()라는 method가 있음
						// 대기하고 있다가 thread가 끝나면 감지해서 return값을 가져온다 
						try {
							Future<Integer> future =excutorCompletionService.take();
							// 결과를 얻어오면 total에 누적
							total += future.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
					printMsg("최종 결과값은 : " + total );
				}
			};
			// 결과값 안받는 일반적인 thread 
			executorService.execute(runnable);
			
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
