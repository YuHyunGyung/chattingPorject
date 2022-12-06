import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Friend extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public ImageIcon UserImg;
	JButton imgBtn;
	JLabel name, stateMsg;
	public String UserName;
	public String UserStatus;
	public String UserStatusMsg;
	
	ChatClientMainView mainView;
	
	public Friend(ChatClientMainView mainView, ImageIcon userimg, String username, String userstatus, String statemsg) {
		this.mainView = mainView;
		UserImg = userimg;
		UserName = username;
		UserStatus = userstatus;
		UserStatusMsg = statemsg;
		
		setPreferredSize(new Dimension(312, 60));
		setLayout(null);
		setVisible(true);
		
		
		UserImg = mainView.imageSetSize(UserImg, 55, 55);
		imgBtn = new JButton(UserImg);
		imgBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 if(UserName.equals(mainView.UserName)) {
					 TalkClientProfile myProfile = new TalkClientProfile(mainView, UserImg, UserName, UserStatusMsg);
				 }
				 else {
					 TalkClientProfileFriend friendProfile = new TalkClientProfileFriend(mainView, UserImg, UserName, UserStatusMsg);
				 }
			}
		});
		imgBtn.setBounds(5, 2, 55, 55);
		add(imgBtn);
		
		name = new JLabel(UserName);
		name.addMouseListener(new ChatRoomOpenListener());
		name.setBounds(72, 7, 155, 16);
		add(name);
		
		stateMsg = new JLabel(UserStatusMsg);
		stateMsg.setBounds(72, 35, 155, 16);
		add(stateMsg);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBounds(280, 18, 26, 23);
		add(chckbxNewCheckBox);
		
		
	}
	
	class ChatRoomOpenListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {    // 마우스 클릭 시
			System.out.println("채팅방 클릭");
			ChatMsg cm = new ChatMsg(UserName, "500", UserName + " " + mainView.UserName);
			mainView.SendObject(cm);
			ChatClientChatRoomView chatRoom = new ChatClientChatRoomView(mainView, mainView.UserName, UserName, UserName, mainView.ip_addr, mainView.port_no);
        }        
    }
	
	//프로필 사진 바꾸었을때
	public void SetIcon(ChatMsg cm) {
		UserImg = cm.img;
		ImageIcon Img = mainView.imageSetSize(UserImg, imgBtn.getWidth(), imgBtn.getHeight());
		imgBtn.setIcon(Img);
	}
	
	public void SetStatusMsg(ChatMsg cm) {
		
	}
	
	public void SetStatusChangeActive() {
		
	}
	
	public void SetProfileButtonActive() {
		
	}
}
