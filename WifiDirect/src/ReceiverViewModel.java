import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiverViewModel {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private static final int PORT = 1995;

    public void startServer() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("���� ��� ��...");
            socket = serverSocket.accept();
            System.out.println("���� ����Ǿ����ϴ�.");

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            String clientIP = socket.getInetAddress().getHostAddress();
            String hostIP = InetAddress.getLocalHost().getHostAddress();
            
                try {
                    Object receivedObject = in.readObject();
                    if (receivedObject instanceof String) {
                        String receivedMessage = (String) receivedObject;

                        String truncatedMessage = receivedMessage.length() > 20
                                ? receivedMessage.substring(0, 20) + "..."
                                : receivedMessage;
                        // ���� �ð��� hh:mm:ss.SSS �������� ��������
                        String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                        
                        System.out.println("���ŵ� �޽��� from " + clientIP + ": " + truncatedMessage + " [" + timeStamp + "]" );
                        // Ŭ���̾�Ʈ�� ���� Ȯ�� �޽��� ����
                        String acknowledgmentMessage = "Window���� �޽����� �޾ҽ��ϴ�.";
                        out.writeObject(acknowledgmentMessage); // �޽����� ����ȭ�Ͽ� Ŭ���̾�Ʈ�� �۽�
                        out.flush(); // �޽����� ��� ����
                        
                    }
                } catch (EOFException e) {
                    System.out.println("Ŭ���̾�Ʈ ������ ����Ǿ����ϴ�.");
                   
                } catch (SocketException e) {
                    System.out.println("������ ���µǾ����ϴ�: " + e.getMessage());
                   
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                   
                }
            

        } catch (BindException e) {
            System.out.println("��Ʈ�� �̹� ��� ���Դϴ�: " + e.getMessage());
            try {
                Thread.sleep(1000); // 1�� ���
                serverSocket = new ServerSocket(PORT); // �ٽ� �õ�
            } catch (InterruptedException | IOException ie) {
                ie.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null && !socket.isClosed()) socket.close();
                if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
