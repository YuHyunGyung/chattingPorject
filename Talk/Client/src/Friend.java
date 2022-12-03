import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class Friend extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public ImageIcon UserImg;
	public String UserName;
	public String UserStatusMsg;
	
	
	
	public Friend(ChatClientMainView mainView, ImageIcon userimg, String username, String userstatus, String statemsg) {
		UserImg = userimg;
		UserName = username;
		
		setPreferredSize(new Dimension(312, 60));
		setLayout(null);
		setVisible(true);
		
		UserImg = imageSetSize(UserImg, 55, 55);
		JButton img = new JButton(UserImg);
		img.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("채팅방 클릭 ");
				ChatMsg cm = new ChatMsg(UserName, "500", UserName + " " + mainView.UserName);
				mainView.SendObject(cm);
				ChatClientChatRoomView chatRoom = new ChatClientChatRoomView(mainView, mainView.UserName, UserName, UserName, mainView.ip_addr, mainView.port_no);
			}
		});
		img.setBounds(5, 2, 55, 55);
		add(img);
		
		JLabel name = new JLabel(UserName);
		name.setBounds(72, 7, 155, 16);
		add(name);
		
		JLabel stateMsg = new JLabel(UserStatusMsg);
		stateMsg.setBounds(72, 35, 155, 16);
		add(stateMsg);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBounds(280, 18, 26, 23);
		add(chckbxNewCheckBox);
		
		
	}
	
	public void SetStatusMsg(ChatMsg cm) {
		
	}
	
	public void SetStatusChangeActive() {
		
	}
	
	public void SetProfileButtonActive() {
		
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
