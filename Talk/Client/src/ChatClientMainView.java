import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.border.EmptyBorder;
import java.net.Socket;


import javax.swing.JTextPane;
import java.awt.Panel;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;

public class ChatClientMainView extends JFrame{
	private static final long serialVersionUID = 1L;
	public Vector<ChatRoom> ChatRoomVector = new Vector<ChatRoom>();
	public String UserName;
	public ChatClientMainView mainView;
	public ImageIcon UserIcon;
	public ImageIcon profile = new ImageIcon(ChatClientMainView.class.getResource("./img/standardProfile.png"));
	
	public JPanel contentPane;
	public JPanel panel;
	public JPanel friendPanel;
	public JPanel chatPanel;
	public JButton friendListBtn;
	public JButton chatListBtn;
	
	public Vector<Friend> FriendVector = new Vector<Friend>();
	
	public JScrollPane scrollPaneFriendList;
	public JScrollPane scrollPaneChatList;
	public JTextPane textPaneFriendList;
	public JTextPane textPaneChatList;
	public JButton addChatRoomBtn;
	public JPanel chatPanelHeader;
	public JPanel friendHeader;
	public JLabel friendLabel;
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JLabel chatLabel;
	public String ip_addr;
	public String port_no;
	
	boolean check = false;
	
	
	public ChatClientMainView(String username, String ip_addr, String port_no) {
		this.mainView = this;
		UserName = username;
		this.ip_addr = ip_addr;
		this.port_no = port_no;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 82, 630);
		panel.setBorder(null);
		panel.setLayout(null);
		contentPane.add(panel);
		
		ImageIcon img = new ImageIcon(ChatClientMainView.class.getResource("./img/user.png"));
		img = imageSetSize(img, 50, 50);
		friendListBtn = new JButton("");
		friendListBtn.setEnabled(false);
		friendListBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chatListBtn.setEnabled(true);
				friendListBtn.setEnabled(false);
				chatPanel.setVisible(false);
				friendPanel.setVisible(true);
			}
		});
		friendListBtn.setBorder(null);
		friendListBtn.setBounds(15, 21, 50, 50);
		friendListBtn.setIcon(img);
		friendListBtn.setBorderPainted(false);
		friendListBtn.setContentAreaFilled(false);
		friendListBtn.setOpaque(false);
		friendListBtn.setFocusPainted(false);
		panel.add(friendListBtn);
		
		ImageIcon img2 = new ImageIcon(ChatClientMainView.class.getResource("./img/msg.png"));
		img2 = imageSetSize(img2, 50, 50);
		chatListBtn = new JButton("");
		chatListBtn.setEnabled(true);
		chatListBtn.setBounds(15, 83, 50, 50);
		//chatList.setBorder(null);
		chatListBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				friendListBtn.setEnabled(true);
				chatListBtn.setEnabled(false);
				friendPanel.setVisible(false);
				chatPanel.setVisible(true);
			}
			
		});
		chatListBtn.setBorder(null);
		chatListBtn.setIcon(img2);
		chatListBtn.setBorderPainted(false);
		chatListBtn.setContentAreaFilled(false);
		chatListBtn.setOpaque(false);
		chatListBtn.setFocusPainted(false);
		panel.add(chatListBtn);
		
		friendPanel = new JPanel();
		friendPanel.setBounds(82, 0, 312, 602);
		friendPanel.setLayout(null);
		contentPane.add(friendPanel);
		
		
		scrollPaneFriendList = new JScrollPane();
		scrollPaneFriendList.setBounds(0, 63, 312, 539);
		scrollPaneFriendList.setLayout(null);
		friendPanel.add(scrollPaneFriendList);
		
		
		textPaneFriendList = new JTextPane();
		textPaneFriendList.setEditable(false);
		scrollPaneFriendList.setViewportView(textPaneFriendList);
		textPaneFriendList.setBounds(0, 0, 312, 602);
		scrollPaneFriendList.add(textPaneFriendList);
		
		
		friendHeader = new JPanel();
		friendHeader.setLayout(null);
		friendHeader.setBackground(new Color(255, 255, 255));
		friendHeader.setBounds(0, 0, 312, 62);
		friendPanel.add(friendHeader);
		
		friendLabel = new JLabel("친구");
		friendLabel.setBounds(10, 15, 54, 30);
		friendLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		friendHeader.add(friendLabel);
		
		chatPanel = new JPanel();
		chatPanel.setVisible(false);
		chatPanel.setBounds(82, 0, 312, 602);
		chatPanel.setLayout(null);
		contentPane.add(chatPanel);
		
		scrollPaneChatList = new JScrollPane();
		scrollPaneChatList.setLayout(null);
		scrollPaneChatList.setViewportBorder(null);
		scrollPaneChatList.setBounds(0, 62, 312, 541);
		chatPanel.add(scrollPaneChatList);
		
		textPaneChatList = new JTextPane();
		textPaneChatList.setEditable(false);
		scrollPaneChatList.setViewportView(textPaneChatList);
		textPaneChatList.setBounds(0, 0, 312, 602);
		scrollPaneChatList.add(textPaneChatList);
		
		chatPanelHeader = new JPanel();
		chatPanelHeader.setBackground(new Color(255, 255, 255));
		chatPanelHeader.setLayout(null);
		chatPanelHeader.setBounds(0, 0, 312, 61);
		chatPanel.add(chatPanelHeader);
		
		ImageIcon img3 = new ImageIcon(ChatClientMainView.class.getResource("./img/chatPlus.png"));
		img3 = imageSetSize(img3, 50, 50);
		addChatRoomBtn = new JButton(img3);
		addChatRoomBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ChatClientSelectFriendDialog dialog = new ChatClientSelectFriendDialog(mainView);
				dialog.setVisible(true);
			}
			
		});
		addChatRoomBtn.setBounds(250, 5, 50, 50);
		addChatRoomBtn.setBorderPainted(false);
		addChatRoomBtn.setContentAreaFilled(false);
		addChatRoomBtn.setOpaque(false);
		addChatRoomBtn.setFocusPainted(false);
		chatPanelHeader.add(addChatRoomBtn);
		
		chatLabel = new JLabel("채팅");
		chatLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		chatLabel.setBounds(10, 15, 100, 30);
		chatPanelHeader.add(chatLabel);
		
		
		setVisible(true);
		
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			
			chatPanel.setVisible(false);
			friendPanel.setVisible(true);
			
			AddFriend(profile, username, "O", "한성대학교 컴퓨터공학부");
			UserIcon = profile;
			
			ChatMsg obcm = new ChatMsg(username, "100", "Login");
			obcm.img = profile;
			obcm.UserStatus = "O";
			obcm.UserStatusMsg = "한성대학교 컴퓨터공학부";
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connect error");
		}
	}
	
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
					} else
						continue;
					switch (cm.code) {
					case "100": //login
						LoginNewFriend(cm);
						break;
					case "200": // chat message
						/*
						if(check==true)
							AppendTextR(msg);
						else
							//AppendText(msg);
							AppendTextL(cm);
						*/
						break;
					case "300": // Image, Icon 첨부
						//AppendText("[" + cm.getId() + "]");
						/*
						if(check==true)
							AppendImageR(cm.img);
						else
							AppendImageL(cm.img);
						*/
						break;
					case "500":
						break;
					case "510": //채팅방 만들기
						AddChatRoom(cm);
						break;
					case "600": //profile modified
						System.out.println("Client Recieve Editor");
						ChangeFriendProfile(cm);
						break;
						
					}
				} catch (IOException e) {
					System.out.println(e + "	ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}
	
	public String ShowDialog() {
		
		return "";
	}
	
	public void LoginNewFriend(ChatMsg cm) {
		for(Friend f : FriendVector) {
			if(f.UserName.equals(cm.UserName)) {
				/*
				if(f.UserName.equals(cm.UserName)) 
					//f.SetOnine(true);
				
				else
					//f.SetOnline(false);
				*/
				//f.SetStatusMsg();
				return;
			
			}
		}
		AddFriend(cm.img, cm.UserName, "O", cm.UserStatusMsg);
		System.out.println("UserStatusMsg: "+cm.UserStatusMsg);
	}
	
	public void LogoutFriend(ChatMsg cm) {
		for(Friend f : FriendVector) {
			if(f.UserName.equals(cm.UserName)) {
				//f.SetOnline(false);
			}
		}
	}
	
	//프로필 사진 변경
	public void ChangeFriendProfile(ChatMsg cm) {
		System.out.println("ChangenFriendProfile");
		
		UserIcon = cm.img;
		for(Friend f : FriendVector) {
			if(f.UserName.equals(cm.UserName)) {
				f.SetIcon(cm);
			}
			//
		}
	}
	
	//새로운 친구 추가될때
	public void AddFriend(ImageIcon icon, String username, String userstatus, String statusmsg) { // on off 표시시 String userstatus 추가 
		int len = textPaneFriendList.getDocument().getLength();
		textPaneFriendList.setCaretPosition(len);
		
		Friend f = new Friend(mainView, icon, username, userstatus, statusmsg);
		//f.UserStatusMsg = statusmsg;		
		textPaneFriendList.insertComponent(f);
		FriendVector.add(f);
		textPaneFriendList.setCaretPosition(0);
		repaint();
	}
	
	public Friend SearchFriend(String name) {
		for(Friend f : FriendVector) {
			if(f.UserName.equals(name))
				return f;
		}
		return null;
	}
	
	
	public void NewChatClientRoomView() {
		//roomView = new ChatClientMainView(mainview, UserName, roomId, UserList);
	}
	
	public void AddChatRoom(ChatMsg cm) {
		int len = textPaneChatList.getDocument().getLength();
		textPaneChatList.setCaretPosition(len);
		
		ChatRoom r = new ChatRoom(mainView, cm.data, "새로운 채팅방이 생성되었습니다.");
		r.userlist = cm.userlist;
		textPaneChatList.insertComponent(r);
		ChatRoomVector.add(r);
		textPaneChatList.setCaretPosition(0);
		repaint();
	}
	
	public ChatRoom SearchChatRoom(String roomId) {
		for(ChatRoom r : ChatRoomVector) {
			if(r.roomId.equals(roomId))
				return r;
		}
		return null;
	}
	

	
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			System.out.println(e + "	SendObject Error");
		}
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ori_img = icon.getImage();  //ImageIcon을 Image로 변환.
		Image new_img = ori_img.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon new_icon = new ImageIcon(new_img); 
		return new_icon;
	}
}
