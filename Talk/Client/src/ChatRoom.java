import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.UIManager;

public class ChatRoom extends JPanel{
	private static final long serialVersionUID = 1L;
	public ChatClientMainView mainView;
	public ChatClientChatRoomView chatRoom;
	
	public ImageIcon RoomIcon;
	public String UserName;
	public String roomId;
	public String userlist;
	public String LastMsg;
	
	public JLabel roomid;
	public JLabel chatMsg;
	public JLabel lblNewLabel;
	
	public Image tmpImg = null;
	public Graphics2D tmpGc;
	
	public JButton roomImg;
	
	public ChatRoom(ChatClientMainView mainView, String username, String RoomId, String UserList, String lastMsg) {	
		this.mainView = mainView;
		UserName = username;
		roomId = RoomId;
		userlist = UserList;
		LastMsg = lastMsg;
		System.out.println("ChatRoom username : " + UserName);
		System.out.println("ChatRoom LastMsg : "+LastMsg);
		
		setPreferredSize(new Dimension(312, 60));
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 200, 60);
		setLayout(null);
		
		roomImg = new JButton("");
		roomImg.setBounds(5, 4, 50, 50);
		roomImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("채팅방 클릭 ");
				chatRoom = new ChatClientChatRoomView(mainView, UserName, roomId, userlist);
			}
		});
		add(roomImg);
		
		roomid = new JLabel(roomId);
		roomid.setBackground(new Color(255, 38, 0));
		roomid.setBounds(60, 7, 220, 20);
		roomid.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		add(roomid);
		
		chatMsg = new JLabel(LastMsg);
		chatMsg.setBounds(60, 33, 231, 15);
		add(chatMsg);

		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(tmpImg == null) {
			tmpImg = createImage(roomImg.getWidth(), roomImg.getHeight());
			tmpGc = (Graphics2D) tmpImg.getGraphics();
			SetChatRoomIcon();
		}
	}
	
	public void NewChatClientRoomView() {
		chatRoom = new ChatClientChatRoomView(mainView, UserName, roomId, userlist);
	}

	public void AppendText(ChatMsg cm) {
		SetLastMsg(cm);
		if(cm.UserName.equals(UserName))
			chatRoom.AppendTextR(cm.data);
		else
			chatRoom.AppendTextL(cm);
	}
	
	public void AppendImage(ChatMsg cm) {
		SetLastMsg(cm);
		if(cm.UserName.equals(UserName))
			chatRoom.AppendImageR(cm.Image);
		else
			chatRoom.AppendImageL(cm.Image);
	}
	
	public void SetLastMsg(ChatMsg cm) {
		LastMsg = cm.data;
		chatMsg.setText(LastMsg);
	}
	public void ChangeFriendProfile(ChatMsg cm) {
		
		SetChatRoomIcon();
	}
	
	public void SetChatRoomIcon() {
		String[] users = userlist.split(" ");
		Image img = null;
		
//		if(users.length == 1) { //나만의 채팅
//			RoomIcon = mainView.UserIcon;
//			img = RoomIcon.getImage().getScaledInstance(roomImg.getWidth(), roomImg.getHeight(), java.awt.Image.SCALE_SMOOTH);
//		} else {
			ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
			
			int k=0;
			for(int i=0; i<users.length && k<4; i++) {
				if(users[i].equals(UserName))  //본인 icon 제외
					continue;
				icons.add(mainView.FriendVector.get(i).UserImg);
				k++;
			}
			
			if(k == 1) { //2명인 경우
				RoomIcon = icons.get(0);
				img = RoomIcon.getImage().getScaledInstance(roomImg.getWidth(), roomImg.getHeight(), java.awt.Image.SCALE_SMOOTH);
			} else {
				if(k == 2) { //3명인 경우
					Image img0 = icons.get(0).getImage().getScaledInstance(roomImg.getWidth()*3/5, roomImg.getHeight()*3/5, java.awt.Image.SCALE_SMOOTH);
					Image img1 = icons.get(1).getImage().getScaledInstance(roomImg.getWidth()*3/5, roomImg.getHeight()*3/5, java.awt.Image.SCALE_SMOOTH);
					
					tmpGc.drawImage(img0, 0, 0, roomImg);
					tmpGc.drawImage(img1, roomImg.getWidth()*2/5, roomImg.getHeight()*2/5, roomImg);
				}
				
				if(k == 3) { //4명인 경우
					Image img0 = icons.get(0).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
					Image img1 = icons.get(1).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
					Image img2 = icons.get(2).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);

					tmpGc.drawImage(img0, roomImg.getWidth()/4, 0, roomImg);
					tmpGc.drawImage(img1, 0, roomImg.getHeight()/2, roomImg);
					tmpGc.drawImage(img2, roomImg.getWidth()/2, roomImg.getHeight()/2, roomImg);

				}
				
				if(k == 4) { //5명인 경우
					Image img0 = icons.get(0).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
					Image img1 = icons.get(1).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
					Image img2 = icons.get(2).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
					Image img3 = icons.get(3).getImage().getScaledInstance(roomImg.getWidth()/2, roomImg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);

					tmpGc.drawImage(img0, 0, 0, roomImg);
					tmpGc.drawImage(img1, roomImg.getWidth()/2, 0, roomImg);
					tmpGc.drawImage(img0, 0, roomImg.getHeight()/2, roomImg);
					tmpGc.drawImage(img1, roomImg.getWidth()/2, roomImg.getHeight()/2, roomImg);

				}
				img = tmpImg;
			}
			roomImg.setIcon(new ImageIcon(img));
		//}
	}
}
