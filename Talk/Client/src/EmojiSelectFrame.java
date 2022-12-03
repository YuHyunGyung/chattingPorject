import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmojiSelectFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����

	ChatClientChatRoomView chatroom;
	public JPanel contentPane;

	public JButton[] Buttons = new JButton[20];	
	public ImageIcon[] ImageIcons = {
			new ImageIcon("src/emoji/���.png"),
			new ImageIcon("src/emoji/�ñ�.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/���.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/��Ȳ.png"),
			new ImageIcon("src/emoji/�޷�.png"),
			new ImageIcon("src/emoji/�̼�.png"),
			new ImageIcon("src/emoji/�θ�.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/�β�.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/�ȵ�.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/��ũ.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/�߳�ô.png"),
			new ImageIcon("src/emoji/����.png"),
			new ImageIcon("src/emoji/��.png"),
	};
	
	EmojiSelectFrame(ChatClientChatRoomView chatroom) {
		this.chatroom = chatroom;
		setBounds(100, 300, 360, 321);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setVisible(true);
			
		int x=0, y=0;

		//��ư ũ�� 55x55���� ���߱�
		for(int i=0; i<20; i++) {
			ImageIcon icon = chatroom.imageSetSize(ImageIcons[i], 55, 55); //new ImageIcon(new_img);
			
			if(x >= 350) {
				x = 0;
				y += 60;
			}
			Buttons[i] = new JButton(icon);
			Buttons[i].setBounds(x,y,60,60);
			Buttons[i].addActionListener(new EmojiSendAction());
			Buttons[i].setBorderPainted(false);
			Buttons[i].setContentAreaFilled(false);
			Buttons[i].setOpaque(false);

			x+=60;			
			contentPane.add(Buttons[i]);
		}	
	}
	
	
	// �̹��� ������ ������
	class EmojiSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
			
			for(int i=0; i<Buttons.length; i++) {
				if(e.getSource() == Buttons[i]) {
					ChatMsg obcm = new ChatMsg(chatroom.UserName, "300", "IMG");
					ImageIcon img = ImageIcons[i];
					obcm.img = img;
					chatroom.SendObject(obcm);
					//setVisible(false);
				}
			}
		}
	}

}
