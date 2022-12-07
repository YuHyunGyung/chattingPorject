//JavaObjServer.java ObjectStream ��� ä�� Server

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class TalkServer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // ��������
	private Socket client_socket; // accept() ���� ������ client ����
	private Vector UserVec = new Vector(); // ����� ����ڸ� ������ ����
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	
	private Vector<ChatRoom> ChatRoomVector = new Vector();
//	public Vector<Friend> FriendVector = new Vector<Friend>();
//	public Vector<String> Friend = new Vector<String>();
	
	public Vector<LoggedUser> FriendVector = new Vector<LoggedUser>();
	public Vector<String> Friend = new Vector<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkServer frame = new TalkServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TalkServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 318, 87, 26);
		contentPane.add(lblNewLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(112, 318, 199, 26);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running..");
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // ������ ���̻� �����Ű�� �� �ϰ� ���´�
				txtPortNumber.setEnabled(false); // ���̻� ��Ʈ��ȣ ������ �ϰ� ���´�
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// ���ο� ������ accept() �ϰ� user thread�� ���� �����Ѵ�.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept�� �Ͼ�� �������� ���� �����
					AppendText("���ο� ������ from " + client_socket);
					// User �� �ϳ��� Thread ����
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // ���ο� ������ �迭�� �߰�
					new_user.start(); // ���� ��ü�� ������ ����
					AppendText("���� ������ �� " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("����ڷκ��� ���� �޼��� : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg chatMsg) {
		// textArea.append("����ڷκ��� ���� object : " + str+"\n");
		
		textArea.append("code = " + chatMsg.code + "\n");
		textArea.append("id = " + chatMsg.UserName + "\n");
		textArea.append("data = " + chatMsg.data + "\n");
		textArea.append("img = " + chatMsg.img + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User �� �����Ǵ� Thread
	// Read One ���� ��� -> Write All
	class UserService extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		private Socket client_socket;
		private Vector user_vc;
		public String UserName = "";
		public String UserStatus;

		public UserService(Socket client_socket) {
			// TODO Auto-generated constructor stub
			// �Ű������� �Ѿ�� �ڷ� ����
			this.client_socket = client_socket;
			this.user_vc = UserVec;
			try {
				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());
			} catch (Exception e) {
				AppendText("userService error");
			}
		}
		
		
		public LoggedUser SearchUser(String UserName) {
			System.out.println("Server LoggedUser In : " + UserName);
			for(LoggedUser f: FriendVector) {
				System.out.println("Server SearchUser : " + f.UserName);
				System.out.println("Server SearchUser Msg : " + f.UserStatusMsg);
				if(f.UserName.equals(UserName)) 
					return f;
			}
			return null;
		}
		
		/**/
		public void Login(ChatMsg cm) {
			AppendText(UserName + "�α���");
			System.out.println("UserName: "+cm.UserName);
			
			
			LoggedUser user;
			if((user = SearchUser(cm.UserName)) != null) { //�̹� �α����� userã�� 
				user.UserStatus = "O";
				
				//user.UserStatusMsg = cm.UserStatusMsg;
				
				//�ٽ� �α����� �������� ������ �������� �����ֵ���
				ChatMsg cm2 = new ChatMsg(cm.UserName, "600", "profile modified");
				cm2.img = user.profile;
				cm2.UserStatus = user.UserStatus;
				cm2.UserStatusMsg = user.UserStatusMsg;
				WriteOneObject(cm2); //������ �ٲ�� �˷��ְ� 
			}
			else {
				user = new LoggedUser(cm.img, cm.UserName, "O", cm.UserStatusMsg);
				FriendVector.add(user);
			}
			WriteOthersObject(cm);
			
			//�α����� �������� ���� ���� ��� ������
			for(LoggedUser User: FriendVector) {
				for(int i=0; i < user_vc.size(); i++) {
					UserService us = (UserService) user_vc.elementAt(i);
					if(us.UserName != User.UserName) { //���� ������ ���� ����
						ChatMsg obcm = new ChatMsg(User.UserName, "100", "Login");
						obcm.img = User.profile;
						obcm.UserStatus = User.UserStatus;
						obcm.UserStatusMsg = User.UserStatusMsg;
						us.WriteOneObject(obcm);
					}
				}
			}
			
			/**/
			//ä�� ��� ������
			for(ChatRoom Room: ChatRoomVector) {
				if(Room.userList.contains(UserName)) {
					ChatMsg obcm = new ChatMsg(UserName, "500", "New Room");
					obcm.roomId = Room.roomId;
					obcm.userlist = Room.userList;
					WriteOneObject(obcm);
					
//					for(ChatMsg cm2 : Room.ChatMsgList) { //�޼��� ���
//						
//					}
				}
			}
						
		}

		public void Logout() {
			String msg = "[" + UserName + "]���� ���� �Ͽ����ϴ�.\n";
			UserVec.removeElement(this); // Logout�� ���� ��ü�� ���Ϳ��� �����
			Friend.removeElement(UserName);
			/*
			for(int i = 0; i < FriendVector.size(); i++) {
				if(FriendVector.get(i).UserName.equals(UserName))
					FriendVector.remove(i);
			}
			*/
			String list = "";
			for(int i = 0; i < Friend.size(); i++) {
				AppendText(Friend.get(i).toString());
				//list += Friend.get(i).toString() + " ";
			}
			WriteAll(msg); // ���� ������ �ٸ� User�鿡�� ����
			AppendText("����� " + "[" + UserName + "] ����. ���� ������ �� " + UserVec.size());
		}

		// ��� User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}
		// ��� User�鿡�� Object�� ���. ä�� message�� image object�� ���� �� �ִ�
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}
		//
		// ���� ������ User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
		public void WriteOthersObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}


		// ���� ������ User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
			}
		}


		// Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
		public byte[] MakePacket(String msg) {
			byte[] packet = new byte[BUF_LEN];
			byte[] bb = null;
			int i;
			for (i = 0; i < BUF_LEN; i++)
				packet[i] = 0;
			try {
				bb = msg.getBytes("euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		// UserService Thread�� ����ϴ� Client ���� 1:1 ����
		public void WriteOne(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("Server WriteOne dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
			}
		}

		// �ӼӸ� ����
		public void WritePrivate(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("�ӼӸ�", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
			}
		}
		
		public void WriteOneObject(Object ob) {
			try {
			    oos.writeObject(ob);
			} 
			catch (IOException e) {
				AppendText("Server WrtieOneObject oos.writeObject(ob) error");		
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout();
			}
		}
		
		public void run() {
			while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm = null;
					
					if (socket == null)
						break;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						AppendObject(cm);
					} else
						continue;
					if (cm.code.matches("100")) {
						UserName = cm.UserName;
						UserStatus = "O"; // Online ����
						Login(cm);
					} else if (cm.code.matches("200")) {
						msg = String.format("[%s] %s", cm.UserName, cm.data);
						AppendText(msg); // server ȭ�鿡 ���
						String[] args = msg.split(" "); // �ܾ���� �и��Ѵ�.
						if (args.length == 1) { // Enter key �� ���� ��� Wakeup ó���� �Ѵ�.
							UserStatus = "O";
						} else if (args[1].matches("/exit")) {
							Logout();
							break;
						} else if (args[1].matches("/list")) {
							WriteOne("User list\n");
							WriteOne("Name\tStatus\n");
							WriteOne("-----------------------------\n");
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								WriteOne(user.UserName + "\t" + user.UserStatus + "\n");
							}
							WriteOne("-----------------------------\n");
						} else if (args[1].matches("/sleep")) {
							UserStatus = "S";
						} else if (args[1].matches("/wakeup")) {
							UserStatus = "O";
						} else if (args[1].matches("/to")) { // �ӼӸ�
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								if (user.UserName.matches(args[2]) && user.UserStatus.matches("O")) {
									String msg2 = "";
									for (int j = 3; j < args.length; j++) {// ���� message �κ�
										msg2 += args[j];
										if (j < args.length - 1)
											msg2 += " ";
									}
									// /to ����.. [�ӼӸ�] [user1] Hello user2..
									user.WritePrivate(args[0] + " " + msg2 + "\n");
									//user.WriteOne("[�ӼӸ�] " + args[0] + " " + msg2 + "\n");
									break;
								}
							}
						} else { // �Ϲ� ä�� �޽���
							UserStatus = "O";
							
							String data = cm.getData();
							String first_data = data.substring(0, 1); //ó������ "("
							String final_data = data.substring(data.length()-1, data.length()); //���������� ")"
														
							String[] emojiList = {"(���)", "(�ñ�)", "(����)", "(���)", "(����)",
									"(��Ȳ)", "(�޷�)", "(�̼�)", "(�θ�)", "(����)",
									"(����)", "(�β�)", "(����)", "(�ȵ�)", "(����)",
									"(��ũ)", "(����)", "(�߳�ô)", "(����)", "(��)"};
							ImageIcon[] emojiIconList = {
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
							
							String[] list = cm.userlist.split(" ");
							 
							for(int i=0; i<emojiList.length; i++) {
								if(data.equals(emojiList[i])) {
									cm.setImage(emojiIconList[i]);
									cm.setCode("300");
								}
							}
							 for (int j = 0; j < user_vc.size(); j++) {
									UserService user = (UserService) user_vc.elementAt(j);
									for(int i = 0; i < list.length; i++)
										if (list[i].equals(user.UserName)) {
											user.WriteOneObject(cm);
										}
								}
						}
					} else if (cm.code.matches("400")) { // logout message ó��
						Logout();
						break;
					} else if (cm.code.matches("300")) { //Image, �̸�Ƽ�� ó��
						WriteAllObject(cm);
						
					 } else if (cm.code.matches("500")) {
						 AppendText("ä�ù��̸� : " + cm.roomId);
						 String [] list = cm.userlist.split(" ");
						 
						 ChatRoom cr = new ChatRoom(cm.roomId, cm.userlist);
						 ChatRoomVector.add(cr);
						 
						 for (int j = 0; j < user_vc.size(); j++) {
							System.out.println("list["+j+"] : "+list[j]);
							UserService user = (UserService) user_vc.elementAt(j);
							for(int i = 0; i < list.length; i++)
								if (list[i].equals(user.UserName)) {
									ChatMsg cm2 = new ChatMsg(list[j], "510", cm.data);
									cm2.roomId = cm.roomId;
									cm2.userlist = cm.userlist;
									user.WriteOneObject(cm2);
								}
						}
					} else if (cm.code.matches("510")) {
						//WriteAllObject(cm);
						
					} else if(cm.code.matches("600")) { //profile modified
						
						LoggedUser user;
						if((user = SearchUser(cm.UserName)) != null) { //�̹� �α����� userã�� 
							user.profile = cm.img;
							user.UserStatusMsg = cm.UserStatusMsg;
							System.out.println("Server protocol 600 - UserStatusMsg: " + user.UserStatusMsg);
						}
						WriteAllObject(cm);
					}
					
					else if(cm.code.matches("800")) {
						
					}
				} catch (IOException e) {
					AppendText("Server ois.readObject() error" + e);
					try {
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
						break;
					} catch (Exception ee) {
						break;
					} // catch�� ��
				} // �ٱ� catch����
			} // while
		} // run
		
		
		
		public void AddChatRoom(ChatMsg cm) {
			
		}
		
		public ChatRoom SearchChatRoom(String roomId) {
			for(ChatRoom r : ChatRoomVector) {
				if(r.roomId.equals(roomId))
					return r;
			}
			return null;
		}
	}
}
