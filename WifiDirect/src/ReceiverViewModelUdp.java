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
            System.out.println("UDP 서버가 " + PORT + " 포트에서 시작되었습니다. 메시지 대기 중...");

            while (true) {
                try {
                    // 버퍼 생성
                    byte[] buffer = new byte[BUFFER_SIZE];

                    // 수신할 패킷 생성
                    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                    // 데이터 수신
                    socket.receive(receivePacket);

                    // 수신된 데이터 처리
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    // 클라이언트 IP 주소 가져오기
                    InetAddress clientAddress = receivePacket.getAddress();
                    String clientIP = clientAddress.getHostAddress();

                    // 현재 시간을 hh:mm:ss.SSS 형식으로 가져오기
                    String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                                      

                    // 메시지의 앞부분 20글자만 잘라서 표시
                    String truncatedMessage = receivedMessage.length() > 20
                            ? receivedMessage.substring(0, 20) + "..."
                            : receivedMessage;

                    // 메시지 출력  // 시간을 메시지 끝에 추가
                    System.out.println("수신된 메시지 from " + clientIP + ": " + truncatedMessage + " [" + timeStamp + "]" );

                    // 종료 조건을 위해 특정 메시지를 수신하면 서버를 종료
                    if ("exit".equalsIgnoreCase(receivedMessage.trim())) {
                        System.out.println("서버 종료 명령을 수신했습니다. 서버를 종료합니다.");
                        break;
                    }

                } catch (Exception e) {
                    System.out.println("데이터 수신 중 오류 발생: " + e.getMessage());
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
