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
		//������ ���α׷��� Ŭ���̾�Ʈ�� ���� ������ ��ⱸ��
		//ServerSocket class�� �̿��ؼ� ����
		ServerSocket server = null;
		//Ŭ���̾�Ʈ�� ���ӵ� �� Socket ��ü�� �־���� Ŭ���̾�Ʈ�� ������ ����� ����
		Socket socket = null;
		
		try {
			//port��ȣ�� ������ ServerSocket��ü�� ����
			//0~65535 ��밡�� 0~1023������ ����Ǿ� ����
			server = new ServerSocket(5554);
			System.out.println("Ŭ���̾�Ʈ ���Ӵ��");
			while(true) {
				socket = server.accept(); //Ŭ���̾�Ʈ ���Ӵ�� block
				ServerThread st = new ServerThread(socket);
				st.start();
				System.out.println(socket.getInetAddress()+"�� ����");

//				// input stream ����
//				BufferedReader br = new BufferedReader(
//						new InputStreamReader(socket.getInputStream()));
//				// output stream ����
//				PrintWriter out = new PrintWriter(socket.getOutputStream());
//				
//				// br�� ���� �����͸� �о out�� ���� �ٽ� ����
//				String msg = br.readLine();
//				out.println(msg);
//				
//				out.flush();//���� buffer�� ���� �����͸� ���޸��
//				out.close();
//				socket.close();
//				server.close();
			}
		}  finally{
			if (socket != null)
				socket.close();
			if (server != null)
				server.close();
			System.out.println("**���� ����**");   
		}
	}		
}

class ServerThread extends Thread{
	 //��������� ����
	 private Socket socket;
	 private BufferedReader br = null;
	 private PrintWriter out = null;
	 //private String userIP = socket.getInetAddress().toString();
	 
	 ServerThread(Socket socket){
		 this.socket = socket;
	 }
	 //�������̵��� ��� throw �Ұ�.
	 public void run() {
		 try{
			 service();
		 }catch(IOException e){
			 //System.out.println("**"+userIP+"�� ���� ����.");
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
				 //System.out.println(userIP+"���� ������ �����߽��ϴ�.");
				 System.out.println("1");
				 break;
			 }
			 //System.out.println(userIP+"��: "+msg);
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

