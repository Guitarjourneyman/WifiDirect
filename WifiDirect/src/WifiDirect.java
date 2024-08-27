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
    private JButton sendButton;
    private JButton receiveButton;
    private ReceiverViewModel receiver;
    private SenderViewModel sender;
    private JTextField inputIp;
    
    public WifiDirect() {
        // GUI 기본 설정
        setTitle("Wifi P2P");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 메시지 수신 영역
        receivedMessagesArea = new JTextArea();
        receivedMessagesArea.setEditable(false);
        receivedMessagesArea.setLineWrap(true);
        receivedMessagesArea.setWrapStyleWord(true);
        JScrollPane receivedScrollPane = new JScrollPane(receivedMessagesArea);

        // 메시지 전송 영역
        sendMessageArea = new JTextArea(5, 30);
        sendMessageArea.setLineWrap(true);
        sendMessageArea.setWrapStyleWord(true);
        JScrollPane sendScrollPane = new JScrollPane(sendMessageArea);

        // 버튼 생성
        sendButton = new JButton("메시지 보내기");
        receiveButton = new JButton("수신 대기");
        //패널 텍스트필드
        inputIp = new JTextField(15);
        
        // 패널 레이아웃 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(sendButton);
        buttonPanel.add(receiveButton);
        buttonPanel.add(inputIp);


        // 메인 레이아웃 설정
        setLayout(new BorderLayout());
        add(receivedScrollPane, BorderLayout.CENTER);
        add(sendScrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);
        
        // 버튼 이벤트 처리
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender = new SenderViewModel();
                String serverIP = inputIp.getText();           
                sender.startClient(serverIP);
                receivedMessagesArea.append("메시지가 전송되었습니다.\n");
            }
        });

        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver = new ReceiverViewModel();
                new Thread(() -> receiver.startServer()).start();
                receivedMessagesArea.append("수신 대기 중...\n");
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
