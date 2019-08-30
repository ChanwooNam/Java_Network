package javaThread;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam07_ThreadDaemon extends Application {
	
	TextArea textarea;
	Button btn;
	
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
		btn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출 
			// Thread를 생성(for loop를 1초 sleep하면서 10번 반복)
			// 이 Thread는 dead 상태로 가기 위해서는 10초가 걸려요!
			Thread thread = new Thread(()-> {
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			});
			thread.setDaemon(true);		// 해당 Thread를 daemon thread로 설정
										// 자식 thread가 된다고 생각하면 되요.
										// 부모 Thread가 중지되면 자동적으로 자식 thread도 중지. 
										
			thread.start();
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


