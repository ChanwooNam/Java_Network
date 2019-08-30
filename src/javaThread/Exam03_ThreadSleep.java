package javaThread;



import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam03_ThreadSleep extends Application {
	
	TextArea textarea;
	Button btn;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
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
		
		btn = new Button("버튼 클릭");
		btn.setPrefSize(250, 50);
		
		// listener 객체가 들어감. 람다식으로 표현 가능 
		btn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출 
			// 1부터 5까지 5번 반복 => for문 이용
			/* 
			 * for(int i=0; i<5; i++) {
			 * 		이 경우에는 for문을 이용하는게 좋지만 stream을 한번 사용해 보았다  
			 * }
			 */
			IntStream intStream =  IntStream.rangeClosed(1,5);
			intStream.forEach(value->{
				// 1부터 5까지 5번 반복하면서 Thread 생성
				
				Thread thread = new Thread(()-> {
					// thread가 실행 될때 수행되는 코드 
					for(int i=0; i<5; i++) {
						try {
							Thread.sleep(3000);
							printMsg( i + " : " + Thread.currentThread().getName() );
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				thread.setName("ThreadNumber-" + value);
				thread.start();
			});
		});	
		
		// 판때기 하나 만들고 그위에 버튼 부착
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(btn);
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

