import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.UIManager;

public class ChatRoom extends JPanel{
	private static final long serialVersionUID = 1L;
	public ChatClientMainView mainView;
	String UserName;
	ImageIcon img;
	String roomId;
	String userlist;
	String LastMsg;
	
	JLabel roomid, chatMsg, lblNewLabel;
	//String UserStatusMsg;
	
	
	public ChatRoom(ChatClientMainView mainView, String roomId, String chatmsg) {
		//setBorder(null);	
		this.mainView = mainView;
		//UserName = username;
		this.roomId = roomId;
		//this.userlist = userlist;
		
		setPreferredSize(new Dimension(312, 60));
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 200, 60);
		setLayout(null);
		
		JButton roomImg = new JButton();
		roomImg.setBounds(5, 4, 50, 50);
		roomImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("채팅방 클릭 ");
				ChatClientChatRoomView chatRoom = new ChatClientChatRoomView(mainView, mainView.UserName, roomId, roomId, mainView.ip_addr, mainView.port_no);
			}
		});
		add(roomImg);
		
		roomid = new JLabel(roomId);
		roomid.setBackground(new Color(255, 38, 0));
		roomid.setBounds(60, 7, 220, 20);
		roomid.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		add(roomid);
		
		chatMsg = new JLabel(chatmsg);
		chatMsg.setBounds(60, 33, 231, 15);
		add(chatMsg);
		
//		lblNewLabel = new JLabel();
//		add(lblNewLabel);
		
		//mainView.textPaneChatList.insertComponent(this);
		
	}
	
	public void SetLastMsg(ChatMsg cm) {
		LastMsg = cm.data;
		chatMsg.setText(LastMsg);
		
	}
}
