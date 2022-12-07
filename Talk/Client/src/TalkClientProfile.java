import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JSeparator;

public class TalkClientProfile extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField userName;
	private JTextField selfChatText;
	private JTextField editProfileText;
	public JTextField stateMsg;

	
	private ImageIcon msg = new ImageIcon(TalkClientProfile.class.getResource("./img/msg.png"));
	private ImageIcon edit = new ImageIcon(TalkClientProfile.class.getResource("./img/profileEdit.png"));
	private ImageIcon backgroundImg = new ImageIcon(TalkClientProfile.class.getResource("./img/greyBack.jpeg"));
	public ImageIcon UserProfile;
	
	private String UserName;
	public String state;
	
	
	public JButton profilePhoto;
	public JButton editProfileButton;
	public JButton selfChatButton;
	
    public ChatClientMainView mainView;
	public TalkClientProfile clientProfile;
	
	/**
	 * Create the frame.
	 */
	public TalkClientProfile(ChatClientMainView mainView, ImageIcon userimg, String username, String statemsg) {
		clientProfile = this;
		this.mainView = mainView;
		this.UserName = username;
		this.UserProfile = userimg;
		this.state = statemsg;
		
		setBounds(150, 80, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		msg = mainView.imageSetSize(msg, 35, 35);
		selfChatButton = new JButton(msg);
		selfChatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new ChatClientChatRoomView(username, ip_addr, port_no);
				//new TalkClientSelfRoom(username, socket, ois, oos);
				dispose();
			}
		});
		selfChatButton.setBounds(65, 471, 50, 50);
		selfChatButton.setBorderPainted(false);
		selfChatButton.setContentAreaFilled(false);
		selfChatButton.setOpaque(false);
		contentPane.add(selfChatButton);
		
		edit = mainView.imageSetSize(edit, 35, 35);
		editProfileButton = new JButton(edit);
		editProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dispose();
				TalkProfileEditor profileEditor = new TalkProfileEditor(mainView, clientProfile, UserProfile, UserName, state);
			}
		});
		editProfileButton.setBounds(264, 471, 50, 50);
		editProfileButton.setBorderPainted(false);
		editProfileButton.setContentAreaFilled(false);
		editProfileButton.setOpaque(false);
		contentPane.add(editProfileButton);
		
		profilePhoto = new JButton(mainView.imageSetSize(UserProfile, 90, 90));
		profilePhoto.setBounds(153, 295, 90, 90);
		profilePhoto.setBorderPainted(false);
		profilePhoto.setContentAreaFilled(false);
		profilePhoto.setOpaque(false);
		contentPane.add(profilePhoto);
		
		userName = new JTextField();
		userName.setEditable(false); //수정 불가능하게 막아버림
		userName.setHorizontalAlignment(SwingConstants.CENTER);
		userName.setBackground(new Color(254, 255, 255));
		userName.setText(username);
		userName.setBounds(142, 397, 117, 26);
		userName.setOpaque(false);
		userName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(userName);
		userName.setColumns(10);
		
		selfChatText = new JTextField();
		selfChatText.setHorizontalAlignment(SwingConstants.CENTER);
		selfChatText.setEditable(false);
		selfChatText.setText("나와의 채팅");
		selfChatText.setBounds(27, 522, 130, 26);
		selfChatText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		selfChatText.setOpaque(false);
		contentPane.add(selfChatText);
		selfChatText.setColumns(10);
		
		editProfileText = new JTextField();
		editProfileText.setHorizontalAlignment(SwingConstants.CENTER);
		editProfileText.setEditable(false);
		editProfileText.setText("프로필 편집");
		editProfileText.setBounds(228, 522, 130, 26);
		editProfileText.setOpaque(false);
		editProfileText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(editProfileText);
		editProfileText.setColumns(10);
		
		JSeparator line = new JSeparator();
		line.setBounds(41, 458, 296, 12);
		contentPane.add(line);
		
		state = statemsg; //profileInfo.getStateMsg();
		stateMsg = new JTextField(state);
		stateMsg.setEditable(false);
		stateMsg.setHorizontalAlignment(SwingConstants.CENTER);
		stateMsg.setBounds(6, 426, 382, 35);
		stateMsg.setOpaque(false);
		stateMsg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(stateMsg);
		stateMsg.setColumns(10);
		
		JButton background = new JButton(mainView.imageSetSize(backgroundImg, 394, 605));
		background.setBackground(new Color(121, 121, 121));
		background.setBounds(0, 0, 394, 605);
		contentPane.add(background);
		setVisible(true);
	}
	

}
