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
            System.out.println("������ ����Ǿ����ϴ�.");
            
            // PrintWriter�� ����Ͽ� ������ �޽����� ����
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            // 60KB�� ���ӵ� "A" ���� ����
            StringBuilder messageBuilder = new StringBuilder(61440);
            for (int i = 0; i < 61440; i++) {
                messageBuilder.append('A');
            }
            String message = messageBuilder.toString();
            
            // �޽��� ����
            out.println(message);
            System.out.println("60KB�� ���ӵ� 'A' �޽����� ������ �����߽��ϴ�.");
            
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
