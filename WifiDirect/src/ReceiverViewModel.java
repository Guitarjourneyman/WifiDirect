import java.io.*;
import java.net.*;

public class ReceiverViewModel {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private static final int PORT = 1995;

    public void startServer() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("연결 대기 중...");
            socket = serverSocket.accept();
            System.out.println("기기와 연결되었습니다.");

            // ObjectOutputStream을 먼저 생성하고 플러시하여 초기화
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();  // 스트림 초기화 직후 flush

            // ObjectInputStream 생성
            in = new ObjectInputStream(socket.getInputStream());

            // 클라이언트의 IP 주소를 가져오기
            String clientIP = socket.getInetAddress().getHostAddress();

            // 메시지 리스닝 기능
            while (true) {
                try {
                    // 객체 수신
                    Object receivedObject = in.readObject();  // 직렬화된 객체 수신
                    if (receivedObject instanceof String) {
                        String receivedMessage = (String) receivedObject;

                        // 메시지의 앞부분 20글자만 잘라서 표시 -> 메시지 내용이 너무 길면 오류가 발생하여 다음과 같이 수정함
                        String truncatedMessage = receivedMessage.length() > 20
                                ? receivedMessage.substring(0, 20) + "..."  // 메시지가 20글자를 초과하면 자르고 "..." 추가
                                : receivedMessage;  // 메시지가 20글자 이하일 경우 그대로

                        System.out.println("수신된 메시지 from " + clientIP + ": " + truncatedMessage);

                        // 수신 확인 메시지를 클라이언트에게 전송
                        out.writeObject("수신된 메시지 from " + clientIP + ": " + receivedMessage.length() + " bytes received");
                        out.flush();  // 데이터를 즉시 전송

                        // 메시지 수신 후 연결 종료
                        break;
                    }
                } catch (EOFException e) {
                    System.out.println("클라이언트 연결이 종료되었습니다.");
                    break;  // 클라이언트가 연결을 닫으면 루프를 종료
                } catch (SocketException e) {
                    System.out.println("연결이 리셋되었습니다: " + e.getMessage());
                    break;  // 연결이 리셋되었을 때도 루프를 종료
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
