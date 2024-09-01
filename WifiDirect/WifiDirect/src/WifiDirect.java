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
        // GUI �⺻ ����
        setTitle("Wifi P2P");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // �޽��� ���� ����
        receivedMessagesArea = new JTextArea();
        receivedMessagesArea.setEditable(false);
        receivedMessagesArea.setLineWrap(true);
        receivedMessagesArea.setWrapStyleWord(true);
        JScrollPane receivedScrollPane = new JScrollPane(receivedMessagesArea);

        // �޽��� ���� ����
        sendMessageArea = new JTextArea(5,50);
        sendMessageArea.setLineWrap(true);
        sendMessageArea.setWrapStyleWord(true);
        JScrollPane sendScrollPane = new JScrollPane(sendMessageArea);

        // ��ư ����
        sendButton_TCP = new JButton("TCP�޽��� ������");
        sendButton_UDP = new JButton("UDP�޽��� ������");
        receiveButton_TCP = new JButton("TCP���� ���");
        receiveButton_UDP = new JButton("UDP���� ���");
        //�г� �ؽ�Ʈ�ʵ�
        inputIp = new JTextField(15);
        
        // �г� ���̾ƿ� ����
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(sendButton_TCP);
        buttonPanel.add(receiveButton_TCP);
        buttonPanel.add(sendButton_UDP);
        buttonPanel.add(receiveButton_UDP);
        buttonPanel.add(inputIp);


        // ���� ���̾ƿ� ����
        setLayout(new BorderLayout());
        add(receivedScrollPane, BorderLayout.CENTER);
        add(sendScrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);
        
        // ��ư �̺�Ʈ ó��
        sendButton_TCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender_tcp = new SenderViewModel();
                String serverIP = inputIp.getText();           
                sender_tcp.startClient(serverIP);
                receivedMessagesArea.append("TCP�� �޽����� ���۵Ǿ����ϴ�.\n");
                //���ڸ޽����� �����ϱ� ���� receiver�� �����ϵ��� ����
                receiver_tcp = new ReceiverViewModel();
                new Thread(() -> receiver_tcp.startServer()).start();
                receivedMessagesArea.append("TCP ���� ��� ��...\n");
                
            }
        });

        receiveButton_TCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver_tcp = new ReceiverViewModel();
                new Thread(() -> receiver_tcp.startServer()).start();
                receivedMessagesArea.append("TCP ���� ��� ��...\n");
            }
        });
        
        sendButton_UDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender_udp = new SenderViewModelUdp();
                String serverIP = inputIp.getText();           
                sender_udp.startClient(serverIP);
                receivedMessagesArea.append("UDP�� �޽����� ���۵Ǿ����ϴ�.\n");
              //���ڸ޽����� �����ϱ� ���� receiver�� �����ϵ��� ����
                receiver_udp = new ReceiverViewModelUdp();
                new Thread(() -> receiver_udp.startServer()).start();
                receivedMessagesArea.append("UDP ���� ��� ��...\n");
            }
        });

        receiveButton_UDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver_udp = new ReceiverViewModelUdp();
                new Thread(() -> receiver_udp.startServer()).start();
                receivedMessagesArea.append("UDP ���� ��� ��...\n");
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
