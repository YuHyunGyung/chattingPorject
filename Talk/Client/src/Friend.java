import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Friend extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public ImageIcon UserImg;
	public JButton imgBtn;
	public JLabel name;
	public JLabel stateMsg;
	
	public String UserName;
	public String UserStatus;
	public String UserStatusMsg;
	public JCheckBox checkBox;
	
	public Boolean online;
	public Icon online_notchecked = new ImageIcon(Friend.class.getResource("./img/online.png"));
	public Icon offline_notchekced = new ImageIcon(Friend.class.getResource("./img/offline.png"));

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
		
		checkBox = new JCheckBox("", true);
		//checkBox.setIcon(online_notchecked);
		//checkBox.setSelectedIcon(online_notchecked);
		//checkBox.setDisabledIcon(online_notchecked);
		checkBox.setBorder(null);
		checkBox.setBounds(280, 18, 26, 23);
		checkBox.setEnabled(false);
		add(checkBox);
		
		
	}
	
	class ChatRoomOpenListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {    // 마우스 클릭 시
			System.out.println("채팅방 클릭");
			ChatMsg cm = new ChatMsg(UserName, "500", UserName + " " + mainView.UserName);
			mainView.SendObject(cm);
			ChatClientChatRoomView chatRoom = new ChatClientChatRoomView(mainView, mainView.UserName, UserName, UserName);
        }        
    }
	
	//프로필 사진 바꾸었을때
	public void SetIcon(ChatMsg cm) {
		//UserImg = cm.img;
		ImageIcon Img = mainView.imageSetSize(UserImg, imgBtn.getWidth(), imgBtn.getHeight());
		imgBtn.setIcon(Img);
	}
	
	//상태메세지 바꾸었을때
	public void SetStatusMsg(ChatMsg cm) {
		//UserStatusMsg = cm.UserStatusMsg;
		stateMsg.setText(UserStatusMsg);
	}
	
	//checkbox 상태에 따라 이미지 다르게
	public void SetSelectCheckBox(Boolean onoff) {
		if(online) {
			//checkBox.setIcon();
			checkBox.setSelected(false);
		}
		checkBox.setEnabled(onoff);
	}
	
	public void SetOnline(Boolean online) {
		
	}
	
	public void SetStatusChangeActive() {
		
	}
	
	public void SetProfileButtonActive() {
		
	}
}
