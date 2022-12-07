import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class TalkProfileEditor extends JFrame{
	private static final long serialVersionUID = 1L;
	

	public ChatClientMainView mainView;
	public TalkClientProfile clientProfile;
	
	private JPanel contentPane;
	
	private ImageIcon UserIcon;
	private ImageIcon profile;
	private ImageIcon back = new ImageIcon(TalkProfileEditor.class.getResource("./img/greyBack.jpeg"));

	
	private String UserName;
	private String UserStatusMsg;
	
	
	private JButton profilePhoto;
	private JButton checkBtn;
	
	private JTextField name;
	private JTextField stateMsg;
	
	private Frame frame;
	private FileDialog fd;

	
	public TalkProfileEditor(ChatClientMainView mainView, TalkClientProfile clientProfile, ImageIcon UserImage, String username, String UserStatusMsg) {
		this.mainView = mainView;
		this.clientProfile = clientProfile;
		this.UserName = username;
		this.UserStatusMsg = UserStatusMsg;
		this.profile = UserImage;
		UserIcon = UserImage;

		setBounds(200, 60, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		profile = mainView.imageSetSize(profile, 90, 90);
		profilePhoto = new JButton(profile);
		profilePhoto.setBounds(159, 366, 90, 90);
		profilePhoto.setBorderPainted(false);
		profilePhoto.setContentAreaFilled(false);
		profilePhoto.setOpaque(false);
		contentPane.add(profilePhoto);
		
		/*
		back = imageSetSize(back, 394, 605);
		JButton background = new JButton(back);
		background.setBackground(new Color(121, 121, 121));
		background.setBounds(0, 0, 394, 605);
		contentPane.add(background);
		*/
		
		name = new JTextField(UserName);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setOpaque(false);
		name.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name.setBounds(6, 468, 382, 26);
		contentPane.add(name);
		name.setColumns(10);
		
		stateMsg = new JTextField(UserStatusMsg);
		stateMsg.setHorizontalAlignment(SwingConstants.CENTER);
		stateMsg.setOpaque(false);
		stateMsg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		stateMsg.setBounds(6, 511, 388, 26);
		contentPane.add(stateMsg);
		stateMsg.setColumns(10);
		
//		JButton background = new JButton(mainView.imageSetSize(back, 394, 605));
//		background.setBackground(new Color(121, 121, 121));
//		background.setBounds(0, 0, 394, 605);
//		contentPane.add(background);

		JSeparator separator2 = new JSeparator();
		separator2.setForeground(new Color(254, 255, 255));
		separator2.setBounds(390, 549, 388, 12);
		contentPane.add(separator2);
		
		JSeparator separator1 = new JSeparator();
		separator1.setForeground(new Color(254, 255, 255));
		separator1.setBounds(0, 487, 388, 12);
		contentPane.add(separator1);
		
		checkBtn = new JButton("확인");
		checkBtn.setBounds(260, 22, 117, 29);
		checkBtn.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(checkBtn);
		setVisible(true);
		
		ImageSendAction action = new ImageSendAction();
		profilePhoto.addActionListener(action);
		checkBtn.addActionListener(action);

	}
	
	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
					
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == profilePhoto) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				fd.setVisible(true);
				
				if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
					img = mainView.imageSetSize(img, 90, 90);
					profilePhoto.setIcon(img);
					UserIcon = img;
				}
			}
			
			if (e.getSource() == checkBtn) {
				ChatMsg obcm2 = new ChatMsg(UserName, "600", "IMG/StatusMsg");
				UserStatusMsg = stateMsg.getText();
				obcm2.UserStatusMsg = UserStatusMsg;
				obcm2.img = UserIcon;
				
				clientProfile.UserProfile = UserIcon;
				clientProfile.state = UserStatusMsg;
				clientProfile.profilePhoto.setIcon(UserIcon);
				clientProfile.stateMsg.setText(UserStatusMsg);
				mainView.SendObject(obcm2);
				dispose();
			}
		}
	}
}
