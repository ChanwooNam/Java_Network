package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam02_EchoServer {

	public static void main(String[] args) throws IOException {
		//서버쪽 프로그램은 클라이언트의 소켓 접속을 대기구현
		//ServerSocket class를 이용해서 구현
		ServerSocket server = null;
		//클라이언트와 접속된 후 Socket 객체가 있어야지 클라이언트와 데이터 통신이 가능
		Socket socket = null;
		
		try {
			//port번호를 가지고 ServerSocket객체를 생성
			//0~65535 사용가능 0~1023까지는 예약되어 있음
			server = new ServerSocket(5554);
			System.out.println("클라이언트 접속대기");
			while(true) {
				socket = server.accept(); //클라이언트 접속대기 block
				ServerThread st = new ServerThread(socket);
				st.start();
				System.out.println(socket.getInetAddress()+"님 입장");

//				// input stream 생성
//				BufferedReader br = new BufferedReader(
//						new InputStreamReader(socket.getInputStream()));
//				// output stream 생성
//				PrintWriter out = new PrintWriter(socket.getOutputStream());
//				
//				// br로 부터 데이터를 읽어서 out을 통해 다시 전달
//				String msg = br.readLine();
//				out.println(msg);
//				
//				out.flush();//내부 buffer를 비우고 데이터를 전달명령
//				out.close();
//				socket.close();
//				server.close();
			}
		}  finally{
			if (socket != null)
				socket.close();
			if (server != null)
				server.close();
			System.out.println("**서버 종료**");   
		}
	}		
}

class ServerThread extends Thread{
	 //멤버변수로 선언
	 private Socket socket;
	 private BufferedReader br = null;
	 private PrintWriter out = null;
	 //private String userIP = socket.getInetAddress().toString();
	 
	 ServerThread(Socket socket){
		 this.socket = socket;
	 }
	 //오버라이딩일 경우 throw 불가.
	 public void run() {
		 try{
			 service();
		 }catch(IOException e){
			 //System.out.println("**"+userIP+"님 접속 종료.");
			 e.printStackTrace();
		 }finally{
			 try {
				 closeAll();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
	 }
	 
	 private void service() throws IOException{
		 br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 out = new PrintWriter(socket.getOutputStream(), true);
		 String msg = null;
		 while(true){
			 msg = br.readLine();
			 if(msg == null){
				 //System.out.println(userIP+"님이 연결을 종료했습니다.");
				 System.out.println("1");
				 break;
			 }
			 //System.out.println(userIP+"님: "+msg);
			 System.out.println("2");
			 out.println(msg);
		 }
	 }
	 public void closeAll()throws IOException{
		 if (out != null)
			 out.close();
		 if (br != null)
			 br.close();
		 if (socket != null)
			 socket.close();
	 }
}

