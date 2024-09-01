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
            System.out.println("연결 대기 중...");
            socket = serverSocket.accept();
            System.out.println("기기와 연결되었습니다.");

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            String clientIP = socket.getInetAddress().getHostAddress();
            String hostIP = InetAddress.getLocalHost().getHostAddress();
            while (true) {
                try {
                    Object receivedObject = in.readObject();
                    if (receivedObject instanceof String) {
                        String receivedMessage = (String) receivedObject;

                        String truncatedMessage = receivedMessage.length() > 20
                                ? receivedMessage.substring(0, 20) + "..."
                                : receivedMessage;
                        // 현재 시간을 hh:mm:ss.SSS 형식으로 가져오기
                        String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                        
                        System.out.println("수신된 메시지 from " + clientIP + ": " + truncatedMessage + " [" + timeStamp + "]" );
                        System.out.println("Step 1");
                        out.writeObject("에코 메시지 from " + hostIP + ": " + truncatedMessage + " bytes received");
                        System.out.println("Step 2");
                        out.flush();
                        System.out.println("Step 3");
                        break;
                    }
                } catch (EOFException e) {
                    System.out.println("클라이언트 연결이 종료되었습니다.");
                    break;
                } catch (SocketException e) {
                    System.out.println("연결이 리셋되었습니다: " + e.getMessage());
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }

        } catch (BindException e) {
            System.out.println("포트가 이미 사용 중입니다: " + e.getMessage());
            try {
                Thread.sleep(1000); // 1초 대기
                serverSocket = new ServerSocket(PORT); // 다시 시도
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
