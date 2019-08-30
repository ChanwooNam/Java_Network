package javaThread;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

class UserPanel extends FlowPane {
	private TextField nameField = new TextField();
	// 0 : 빈거 , 1 : 가득차있는거
	private ProgressBar progressBar = new ProgressBar(0.0);
	private ProgressIndicator progressIndicator = new ProgressIndicator(0.0);
	
	public UserPanel() {
	}
	
	// string 인자를 받아드리는 생성자를 만들자
	public UserPanel(String name) {
		// 판때기 size
		setPrefSize(700, 50);
		// 안에 들어가는 요소의 크기 
		nameField.setPrefSize(100, 50);
		progressBar.setPrefSize(500, 50);
		progressIndicator.setPrefSize(50, 50);
		// 이름 설정
		nameField.setText(name);
		// panel에 부착 
		getChildren().add(nameField);
		getChildren().add(progressBar);
		getChildren().add(progressIndicator);
	}

	public TextField getNameField() {
		return nameField;
	}
	public void setNameField(TextField nameField) {
		this.nameField = nameField;
	}
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}
	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}
	
}



class ProgressRunnable implements Runnable{
	
	private int i;
	private String name;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;
	private TextArea textarea;
	
	public ProgressRunnable(String name, ProgressBar progressBar, ProgressIndicator progressIndicator,
			TextArea textarea) {
		super();
		this.name = name;
		this.progressBar = progressBar;
		this.progressIndicator = progressIndicator;
		this.textarea = textarea;
	}

	@Override
	public void run() {
		// Thread가 동작해서 progressBar를 제어하면 될 거 같아요!
		
		Random random = new Random();
		double k = 0;
		int i = 0;
		while(progressBar.getProgress() < 1.0) {
			
			try {
				//Thread.sleep(1000); // 1초 동안 현재 Thread sleep
				k += random.nextDouble() * 0.1;
				final double tt = k;
				// k값이 지속적으로 증가.
				Platform.runLater(()->{
					progressBar.setProgress(tt);
					progressIndicator.setProgress(tt);
				});
				if( k > 1.0 ) {
					
				    final int ii = i;
				    System.out.println(i);
				    
					Thread thread = Thread.currentThread();
					thread.setName(name);
					Platform.runLater(()->{
						
						textarea.appendText(ii+"등 : "+name+"\n");
						
					});
					System.out.println( Thread.currentThread().getName() );
					
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
	}
	
}

public class Exam02_ThreadRace extends Application {
	
	private List<String> names = Arrays.asList("홍길동","이순신","감강찬");
	
	// progressBar를 제어할 Thread가 FlowPane 1개당 1개씩 존재해야 해요
	// thread를 관리해야 하는데 
	// thread가 실행시킬 runnable 객체를 list에 넣어서 관리하자
	private List<ProgressRunnable> uRunnable = new ArrayList<ProgressRunnable>();
	
	TextArea textarea;
	Button btn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// start()를 호출해 주는 애가 누구지?? 
		System.out.println( Thread.currentThread().getName() );
		// JavaFX는 내부적으로 화면을 제어하는 Thread를 생성해서 사용해요 
		
		
		/////////// 화면 구성해서 window 띄우는 코드 //////////////
		
		// 화면기본 layout을 설정 => 화면을 동서남북중앙( 5개 영역 )으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// center부분을 차지할 TilePane을 생성해야 해요 
		TilePane center = new TilePane();
		center.setPrefColumns(1);  // 1열만 존재하는 TilePane
		center.setPrefRows(4);     // 4행이 존재하는 TilePane
		
		// 메세지가 출력될 TextArea 생성 및 크기 결정 
		textarea = new TextArea();
		textarea.setPrefSize(600, 100);

		// 이제 만들어지 TilePane에 3개의 FlowPane과 TextArea를 부착 
		for(String name : names) {
			// 사람마다 판때기 만들어주고
			UserPanel panel = new UserPanel(name);
			// panel 붙이기
			center.getChildren().add(panel);	
			// runnable 객체 생성 (나중에 thread로 파생되어 사용될)
			// thread가 가지고 있어야할 것들을 인자로 생성자에 전달
			// ArrayList에 runnable 객체 add
			uRunnable.add(new ProgressRunnable(panel.getNameField().getText(),
												panel.getProgressBar(),
												panel.getProgressIndicator(),
												textarea));
			
			
		}
		// textarea 붙이기
		center.getChildren().add(textarea);
		
		// center영역에 TilePane 붙이기
		root.setCenter(center);
		
		btn = new Button("버튼 클릭");
		btn.setPrefSize(250, 50);
		
		// 버튼 누르면 Thread start 
		btn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출 
			// uRunnable(ArrayList)를 돌면서 Thread를 생성하고
			// start() 호출
			for(ProgressRunnable runnable : uRunnable) {
				// runnable 객체 꺼내서 start
				new Thread(runnable).start();
			}
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
		primaryStage.setTitle("Thread Race... ");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		
		launch();
	}

}

