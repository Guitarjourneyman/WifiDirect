import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiverViewModelUdp {

    private static final int PORT = 1995;
    private static final int BUFFER_SIZE = 1024;

    public void startServer() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("UDP ������ " + PORT + " ��Ʈ���� ���۵Ǿ����ϴ�. �޽��� ��� ��...");

            while (true) {
                try {
                    // ���� ����
                    byte[] buffer = new byte[BUFFER_SIZE];

                    // ������ ��Ŷ ����
                    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                    // ������ ����
                    socket.receive(receivePacket);

                    // ���ŵ� ������ ó��
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    // Ŭ���̾�Ʈ IP �ּ� ��������
                    InetAddress clientAddress = receivePacket.getAddress();
                    String clientIP = clientAddress.getHostAddress();

                    // ���� �ð��� hh:mm:ss.SSS �������� ��������
                    String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                                      

                    // �޽����� �պκ� 20���ڸ� �߶� ǥ��
                    String truncatedMessage = receivedMessage.length() > 20
                            ? receivedMessage.substring(0, 20) + "..."
                            : receivedMessage;

                    // �޽��� ���  // �ð��� �޽��� ���� �߰�
                    System.out.println("���ŵ� �޽��� from " + clientIP + ": " + truncatedMessage + " [" + timeStamp + "]" );

                    // ���� ������ ���� Ư�� �޽����� �����ϸ� ������ ����
                    if ("exit".equalsIgnoreCase(receivedMessage.trim())) {
                        System.out.println("���� ���� ����� �����߽��ϴ�. ������ �����մϴ�.");
                        break;
                    }

                } catch (Exception e) {
                    System.out.println("������ ���� �� ���� �߻�: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

}
