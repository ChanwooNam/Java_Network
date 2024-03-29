package javaThread;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam06_ThreadInterrupt extends Application {
	
	TextArea textarea;
	Button startbtn, stopbtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		// textarea에 문자열 출력하는 method
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
		
		startbtn = new Button("Thread 시작");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출 
			counterThread = new Thread(() -> {
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
						printMsg(i + "-" + Thread.currentThread().getName());
					}
				} catch (Exception e) {
					// 만약 interrupt()가 걸려있는 상태에서 block상태로 진입하면
					// Exception을 내면서 catch문으로 이동
					printMsg("Thread가 종료 되었어요!");
				}
			});
			
			counterThread.start();
		});	
		stopbtn = new Button("Thread 중지");
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출 
			// 지시자 : 나중에 block 당하면 그만 해야돼~~
			counterThread.interrupt();	// method가 실행된다고 바로 
										// Thread가 종료되지 않아요!!!
			// interrupt() method가 호출된 Thread는 sleep()과 같이
			// block 상태에 들어가야지 interrupt를 시킬 수 있어요!
		});	
		
		// 판때기 하나 만들고 그위에 버튼 부착
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
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

