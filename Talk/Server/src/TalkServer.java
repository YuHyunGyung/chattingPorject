//JavaObjServer.java ObjectStream 기반 채팅 Server

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

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	
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
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					new_user.start(); // 만든 객체의 스레드 실행
					AppendText("현재 참가자 수 " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg chatMsg) {
		// textArea.append("사용자로부터 들어온 object : " + str+"\n");
		
		textArea.append("code = " + chatMsg.code + "\n");
		textArea.append("id = " + chatMsg.UserName + "\n");
		textArea.append("data = " + chatMsg.data + "\n");
		textArea.append("img = " + chatMsg.img + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User 당 생성되는 Thread
	// Read One 에서 대기 -> Write All
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
			// 매개변수로 넘어온 자료 저장
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
			AppendText(UserName + "로그인");
			System.out.println("UserName: "+cm.UserName);
			
			
			LoggedUser user;
			if((user = SearchUser(cm.UserName)) != null) { //이미 로그인한 user찾기 
				user.UserStatus = "O";
				
				//user.UserStatusMsg = cm.UserStatusMsg;
				
				//다시 로그인한 유저에게 마지막 프로필을 보여주도록
				ChatMsg cm2 = new ChatMsg(cm.UserName, "600", "profile modified");
				cm2.img = user.profile;
				cm2.UserStatus = user.UserStatus;
				cm2.UserStatusMsg = user.UserStatusMsg;
				WriteOneObject(cm2); //프로필 바뀐거 알려주고 
			}
			else {
				user = new LoggedUser(cm.img, cm.UserName, "O", cm.UserStatusMsg);
				FriendVector.add(user);
			}
			WriteOthersObject(cm);
			
			//로그인한 유저에게 기존 유저 목록 보내줌
			for(LoggedUser User: FriendVector) {
				for(int i=0; i < user_vc.size(); i++) {
					UserService us = (UserService) user_vc.elementAt(i);
					if(us.UserName != User.UserName) { //나를 제외한 기존 유저
						ChatMsg obcm = new ChatMsg(User.UserName, "100", "Login");
						obcm.img = User.profile;
						obcm.UserStatus = User.UserStatus;
						obcm.UserStatusMsg = User.UserStatusMsg;
						us.WriteOneObject(obcm);
					}
				}
			}
			
			/**/
			//채팅 목록 보내줌
			for(ChatRoom Room: ChatRoomVector) {
				if(Room.userList.contains(UserName)) {
					ChatMsg obcm = new ChatMsg(UserName, "500", "New Room");
					obcm.roomId = Room.roomId;
					obcm.userlist = Room.userList;
					WriteOneObject(obcm);
					
//					for(ChatMsg cm2 : Room.ChatMsgList) { //메세지 백업
//						
//					}
				}
			}
						
		}

		public void Logout() {
			String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
			UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
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
			WriteAll(msg); // 나를 제외한 다른 User들에게 전송
			AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
		}

		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}
		// 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}
		//
		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthersObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}


		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
			}
		}


		// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
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

		// UserService Thread가 담당하는 Client 에게 1:1 전송
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
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		// 귓속말 전송
		public void WritePrivate(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("귓속말", "200", msg);
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
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
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
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
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
						UserStatus = "O"; // Online 상태
						Login(cm);
					} else if (cm.code.matches("200")) {
						msg = String.format("[%s] %s", cm.UserName, cm.data);
						AppendText(msg); // server 화면에 출력
						String[] args = msg.split(" "); // 단어들을 분리한다.
						if (args.length == 1) { // Enter key 만 들어온 경우 Wakeup 처리만 한다.
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
						} else if (args[1].matches("/to")) { // 귓속말
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								if (user.UserName.matches(args[2]) && user.UserStatus.matches("O")) {
									String msg2 = "";
									for (int j = 3; j < args.length; j++) {// 실제 message 부분
										msg2 += args[j];
										if (j < args.length - 1)
											msg2 += " ";
									}
									// /to 빼고.. [귓속말] [user1] Hello user2..
									user.WritePrivate(args[0] + " " + msg2 + "\n");
									//user.WriteOne("[귓속말] " + args[0] + " " + msg2 + "\n");
									break;
								}
							}
						} else { // 일반 채팅 메시지
							UserStatus = "O";
							
							String data = cm.getData();
							String first_data = data.substring(0, 1); //처음글자 "("
							String final_data = data.substring(data.length()-1, data.length()); //마지막글자 ")"
														
							String[] emojiList = {"(곤란)", "(궁금)", "(깜찍)", "(놀람)", "(눈물)",
									"(당황)", "(메롱)", "(미소)", "(민망)", "(반함)",
									"(버럭)", "(부끄)", "(아픔)", "(안도)", "(우웩)",
									"(윙크)", "(으으)", "(잘난척)", "(잘자)", "(잠)"};
							ImageIcon[] emojiIconList = {
									new ImageIcon("src/emoji/곤란.png"),
									new ImageIcon("src/emoji/궁금.png"),
									new ImageIcon("src/emoji/깜찍.png"),
									new ImageIcon("src/emoji/놀람.png"),
									new ImageIcon("src/emoji/눈물.png"),
									new ImageIcon("src/emoji/당황.png"),
									new ImageIcon("src/emoji/메롱.png"),
									new ImageIcon("src/emoji/미소.png"),
									new ImageIcon("src/emoji/민망.png"),
									new ImageIcon("src/emoji/반함.png"),
									new ImageIcon("src/emoji/버럭.png"),
									new ImageIcon("src/emoji/부끄.png"),
									new ImageIcon("src/emoji/아픔.png"),
									new ImageIcon("src/emoji/안도.png"),
									new ImageIcon("src/emoji/우웩.png"),
									new ImageIcon("src/emoji/윙크.png"),
									new ImageIcon("src/emoji/으으.png"),
									new ImageIcon("src/emoji/잘난척.png"),
									new ImageIcon("src/emoji/잘자.png"),
									new ImageIcon("src/emoji/잠.png"),
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
					} else if (cm.code.matches("400")) { // logout message 처리
						Logout();
						break;
					} else if (cm.code.matches("300")) { //Image, 이모티콘 처리
						WriteAllObject(cm);
						
					 } else if (cm.code.matches("500")) {
						 AppendText("채팅방이름 : " + cm.roomId);
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
						if((user = SearchUser(cm.UserName)) != null) { //이미 로그인한 user찾기 
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
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
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
