import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SenderViewModel {
    private static final int PORT = 1995;
    
    public void startClient(String serverIP) {
        Scanner scanner = new Scanner(System.in);
        
        Socket socket = null;
        PrintWriter out = null;
        
        try {
            socket = new Socket(serverIP, PORT);
            System.out.println("서버에 연결되었습니다.");
            
            // PrintWriter를 사용하여 서버로 메시지를 전송
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            // 60KB의 연속된 "A" 문자 생성
            StringBuilder messageBuilder = new StringBuilder(61440);
            for (int i = 0; i < 61440; i++) {
                messageBuilder.append('A');
            }
            String message = messageBuilder.toString();
            
            // 메시지 전송
            out.println(message);
            System.out.println("60KB의 연속된 'A' 메시지를 서버로 전송했습니다.");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (socket != null) socket.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
