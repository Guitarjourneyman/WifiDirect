import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class WifiDirect extends JFrame{
	
	private JTextArea receivedMessagesArea;
    private JTextArea sendMessageArea;
    private JButton sendButton_TCP;
    private JButton sendButton_UDP;
    private JButton receiveButton_TCP;
    private JButton receiveButton_UDP;
    private ReceiverViewModel receiver_tcp;
    private ReceiverViewModelUdp receiver_udp;
    private SenderViewModel sender_tcp;
    private SenderViewModelUdp sender_udp;
    private JTextField inputIp;
    
    public WifiDirect() {
        // GUI 기본 설정
        setTitle("Wifi P2P");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 메시지 수신 영역
        receivedMessagesArea = new JTextArea();
        receivedMessagesArea.setEditable(false);
        receivedMessagesArea.setLineWrap(true);
        receivedMessagesArea.setWrapStyleWord(true);
        JScrollPane receivedScrollPane = new JScrollPane(receivedMessagesArea);

        // 메시지 전송 영역
        sendMessageArea = new JTextArea(5,50);
        sendMessageArea.setLineWrap(true);
        sendMessageArea.setWrapStyleWord(true);
        JScrollPane sendScrollPane = new JScrollPane(sendMessageArea);

        // 버튼 생성
        sendButton_TCP = new JButton("TCP메시지 보내기");
        sendButton_UDP = new JButton("UDP메시지 보내기");
        receiveButton_TCP = new JButton("TCP수신 대기");
        receiveButton_UDP = new JButton("UDP수신 대기");
        //패널 텍스트필드
        inputIp = new JTextField(15);
        
        // 패널 레이아웃 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(sendButton_TCP);
        buttonPanel.add(receiveButton_TCP);
        buttonPanel.add(sendButton_UDP);
        buttonPanel.add(receiveButton_UDP);
        buttonPanel.add(inputIp);


        // 메인 레이아웃 설정
        setLayout(new BorderLayout());
        add(receivedScrollPane, BorderLayout.CENTER);
        add(sendScrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);
        
        // 버튼 이벤트 처리
        sendButton_TCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender_tcp = new SenderViewModel();
                String serverIP = inputIp.getText();           
                sender_tcp.startClient(serverIP);
                receivedMessagesArea.append("TCP로 메시지가 전송되었습니다.\n");
                //에코메시지를 수신하기 위해 receiver도 동작하도록 수정
                receiver_tcp = new ReceiverViewModel();
                new Thread(() -> receiver_tcp.startServer()).start();
                receivedMessagesArea.append("TCP 수신 대기 중...\n");
                
            }
        });

        receiveButton_TCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver_tcp = new ReceiverViewModel();
                new Thread(() -> receiver_tcp.startServer()).start();
                receivedMessagesArea.append("TCP 수신 대기 중...\n");
            }
        });
        
        sendButton_UDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender_udp = new SenderViewModelUdp();
                String serverIP = inputIp.getText();           
                sender_udp.startClient(serverIP);
                receivedMessagesArea.append("UDP로 메시지가 전송되었습니다.\n");
              //에코메시지를 수신하기 위해 receiver도 동작하도록 수정
                receiver_udp = new ReceiverViewModelUdp();
                new Thread(() -> receiver_udp.startServer()).start();
                receivedMessagesArea.append("UDP 수신 대기 중...\n");
            }
        });

        receiveButton_UDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver_udp = new ReceiverViewModelUdp();
                new Thread(() -> receiver_udp.startServer()).start();
                receivedMessagesArea.append("UDP 수신 대기 중...\n");
            }
        });
        
    }



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WifiDirect().setVisible(true);
            }
        });
	}
	
}
