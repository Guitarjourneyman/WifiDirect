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
        // GUI �⺻ ����
        setTitle("Wifi P2P");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // �޽��� ���� ����
        receivedMessagesArea = new JTextArea();
        receivedMessagesArea.setEditable(false);
        receivedMessagesArea.setLineWrap(true);
        receivedMessagesArea.setWrapStyleWord(true);
        JScrollPane receivedScrollPane = new JScrollPane(receivedMessagesArea);

        // �޽��� ���� ����
        sendMessageArea = new JTextArea(5, 30);
        sendMessageArea.setLineWrap(true);
        sendMessageArea.setWrapStyleWord(true);
        JScrollPane sendScrollPane = new JScrollPane(sendMessageArea);

        // ��ư ����
        sendButton = new JButton("�޽��� ������");
        receiveButton = new JButton("���� ���");
        //�г� �ؽ�Ʈ�ʵ�
        inputIp = new JTextField(15);
        
        // �г� ���̾ƿ� ����
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(sendButton);
        buttonPanel.add(receiveButton);
        buttonPanel.add(inputIp);


        // ���� ���̾ƿ� ����
        setLayout(new BorderLayout());
        add(receivedScrollPane, BorderLayout.CENTER);
        add(sendScrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);
        
        // ��ư �̺�Ʈ ó��
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender = new SenderViewModel();
                String serverIP = inputIp.getText();           
                sender.startClient(serverIP);
                receivedMessagesArea.append("�޽����� ���۵Ǿ����ϴ�.\n");
            }
        });

        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver = new ReceiverViewModel();
                new Thread(() -> receiver.startServer()).start();
                receivedMessagesArea.append("���� ��� ��...\n");
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
