package javaNetwork;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javaNetwork.Exam01_socketClient.ClientReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam02_EchoClient extends Application{
	TextArea textarea;
	Button startbtn, stopbtn; // ���� ����, ���� ��ư
	TextField tf;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
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
		//Stage arg0 -> window�� ��Ī��
		//ȭ�鱸���ؼ� window ���� �ڵ�
		//ȭ��⺻ layout�� ���� => ȭ���� ���������߾�(5���� ����)���� �и�
		BorderPane root = new BorderPane();
		//BorderPane�� ũ�⸦ ����=> ȭ���� ���� window�� ũ�⼳��
		root.setPrefSize(700, 500);
		//Component�����ؼ� BorderPane�� ����
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startbtn = new Button("Server�� ����");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t->{
			 if (startbtn.getText().equals("Server�� ����")) {
				 try {
						//�����ʿ� Socket������ �õ�
						socket = new Socket("127.0.0.1",5554);
						System.out.println("**���� ���� OK**");
						//���ӿ� �����ϸ� socket��ü�� �ϳ� ȹ��
						InputStreamReader isr = new InputStreamReader(socket.getInputStream());
						br = new BufferedReader(isr);
						out = new PrintWriter(socket.getOutputStream());
						printMsg("���� ����");
						startbtn.setText("���� ����");
						
					}catch(Exception e) {
						System.out.println(e);
					}
	            } else if (startbtn.getText().equals("���� ����")) {
	                try {
	                    printMsg("������ ���������Ͽ����ϴ�");
	                    startbtn.setText("JOIN");
	                    socket.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
		});
		
		tf = new TextField();
		tf.setPrefSize(200, 40);
		tf.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				try {
					String msg = tf.getText();
					out.println(msg);
					out.flush();
					try {
						String result = br.readLine();
						printMsg(result);
					}catch (Exception e) {
						e.printStackTrace();
					}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
		
//		tf.setOnAction(t -> {
//			// �Է»���(Textfield)���� enter key�� �ԷµǸ� ȣ��
//			String msg = tf.getText();
//			out.println(msg);
//			out.flush();
//			try {
//				String result = br.readLine();
//				printMsg(result);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
		

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư �߰�
		flowpane.getChildren().add(startbtn);
	
		flowpane.getChildren().add(tf);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ�
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Network ���� �Դϴ�.");
		primaryStage.show();
	}

}
