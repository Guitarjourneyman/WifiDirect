import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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
         // 현재 시간을 hh:mm:ss.SSS 형식으로 가져오기
            String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
            
            String message = messageBuilder.toString();
            
            // 메시지 전송 + 타임스탬프 추가
            out.println("From Window "+"["+timeStamp+"]"+message);
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