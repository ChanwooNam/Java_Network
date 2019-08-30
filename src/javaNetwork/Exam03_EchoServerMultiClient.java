package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

//thread를 만들기 위한 ruunable class 정의
class EchoRunnable implements Runnable {
	
	// 가지고 있어야 되는 field : socket , inputStream, outputStream
	Socket socket; // 클라이언트와 연결된 소켓
	BufferedReader br; // 클라이언트로 부터 데어터 받기위한 스트림
	PrintWriter out; // 클라이언트에 데이터 주기위한 스트림
	
	// 생성자. socket만 인자로 넘겨준다
	public EchoRunnable(Socket socket) {
		super();
		this.socket = socket;
		
		try {
			// socket 가져와서 생성자 이용해서 스트림까지 생성 
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// 클라이언트와 echo처리 구현 
		// 클라이언트가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 전달 
		// 한번하고 종료하는게 아니라 클라이언트가 "/EXIT"라는 문자열을 보낼때까지 지속 
		String line = "";
		// socket이 끊기면 null 
		try {
			while((line = br.readLine()) != null ) {
				if(Thread.currentThread().isInterrupted()) {
					break;
				}
				if(line.equals("/EXIT/")) {
					break; // 가장 근접한 loop를 탈출 
				} else {
					out.println(line);
					out.flush();
				}
			}
			//자원반납 처리
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Exam03_EchoServerMultiClient extends Application{
	
	TextArea textarea;		  // 메세지창 용도로 사용
	Button startbtn, stopbtn; // 서버시작, 서버중지 버튼 
	// ThreadPool 생성
	ExecutorService executorService = Executors.newCachedThreadPool();
	// 클라이언트의 접속을 받아들이는 서버소켓 
	ServerSocket server;
	
	public static void main(String[] args) {		
		launch();
	}
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Stage arg0 -> window를 지칭함
		//화면구성해서 window 띄우는 코드
		//화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리		
		BorderPane root = new BorderPane();
		//BorderPane의 크기를 설정=> 화면을 띄우는 window의 크기설정
		root.setPrefSize(700, 500);
		//Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startbtn = new Button("Echo 서버 시작");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t->{
			// 버튼에서 Action이 발생 했을 때 호출!
			// 서버프로그램을 시작
			// 클라이언트의 접속을 기다려요! -> 접속이 되면 Thread를 하나 생성
			// -> Thread를 시작해서 클라이언트와 Thread가 통신하도록 만들어요
			// 서버는 다시 다른 클라이언트의 접속을 기다려요
			Runnable runnable = () -> {
				
				try {
					server = new ServerSocket(7777);
					printMsg("Echo 서버 기동");
					while(true) {
						printMsg("클라이언트 접속 대기");
						Socket s = server.accept(); // blocking mehtod
						printMsg("클라이언트 접속 성공");
						// 클라이언트가 접속했으니 Thread만들고 시작해야 해요 
						// thread가 socket을 들고있어야함 
						EchoRunnable r = new EchoRunnable(s);
						executorService.execute(r); // thread 실행
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			executorService.execute(runnable);	
		});
		
		stopbtn = new Button("Echo 서버 종료");
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t->{
			executorService.shutdown();
			
		});
		

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼 추가
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
	
		root.setBottom(flowpane);
		
		//Scene객체가 필요
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("다중 클라이언트 Echo 서버.");
		primaryStage.show();
	}

}
