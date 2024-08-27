import java.io.*;
import java.net.*;

public class ReceiverViewModel {
    private String receivedMessage = null;
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private static final int PORT = 1995;
    

    public void startServer() {
    	String cmd = null; //Ŀ�ǵ� �Է� �ޱ�
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(" ���� ��� ��... ");
            socket = serverSocket.accept();
            System.out.println(" ���� ���� �Ǿ����ϴ�. ");
            BufferedReader in = new BufferedReader
        			(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter
            		(socket.getOutputStream(), true);//Socket�� in,out �����͸� �о���� ���ϰ� ���ִ� �޼ҵ� PrintWriter ���
          
         // Ŭ���̾�Ʈ�� IP �ּҸ� ��������
            String clientIP = socket.getInetAddress().getHostAddress();
            
            //�޽��� ������ ��� 
            while(true) {
            	String line = in.readLine();
            	out.println("���ŵ� �޼��� from" + clientIP + ": " + line.indexOf(10));
            	
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
