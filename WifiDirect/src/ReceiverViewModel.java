import java.io.*;
import java.net.*;

public class ReceiverViewModel {
    private String receivedMessage = null;
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private static final int PORT = 1995;
    

    public void startServer() {
    	String cmd = null; //커맨드 입력 받기
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(" 연결 대기 중... ");
            socket = serverSocket.accept();
            System.out.println(" 기기와 연결 되었습니다. ");
            BufferedReader in = new BufferedReader
        			(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter
            		(socket.getOutputStream(), true);//Socket의 in,out 데이터를 읽어오기 편하게 해주는 메소드 PrintWriter 사용
          
         // 클라이언트의 IP 주소를 가져오기
            String clientIP = socket.getInetAddress().getHostAddress();
            
            //메시지 리스닝 기능 
            while(true) {
            	String line = in.readLine();
            	out.println("수신된 메세지 from" + clientIP + ": " + line.indexOf(10));
            	
            }
      
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
