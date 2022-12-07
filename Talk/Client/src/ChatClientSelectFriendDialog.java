import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ChatClientSelectFriendDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	public Vector<FriendLabel> FriendLabel = new Vector<FriendLabel>();
	private ChatClientMainView mainView;
	public String roomId;
	public String userlist;

	public ChatClientSelectFriendDialog(ChatClientMainView mainView) {
		this.mainView = mainView;
		
		setBounds(100, 100, 240, 380);
		getContentPane().setLayout(null);
		
		JButton addChatRoom = new JButton("채팅방 추가");
		addChatRoom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				roomId = mainView.UserName;
				userlist = mainView.UserName;
				for(int i = 0; i <FriendLabel.size(); i++) {
					if(FriendLabel.get(i).check) { //선택됐으면 roomId에 포함 = userList
						roomId += " " + FriendLabel.get(i).UserName;
						userlist += " " + FriendLabel.get(i).UserName;
					}
				}
				System.out.println("Dialog roomId: " + roomId);
				System.out.println("Dialog userlist: " + roomId);
				
				ChatMsg cm = new ChatMsg(mainView.UserName, "500", "New Room");
				cm.roomId = roomId;
				cm.userlist = userlist;
				mainView.SendObject(cm);
				dispose();
				//mainView.AddChatRoom(cm);
			}
			
		});
		addChatRoom.setBounds(140, 305, 92, 40);
		getContentPane().add(addChatRoom, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLayout(null);
		scrollPane.setBounds(6, 6, 227, 290);
		getContentPane().add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setLayout(null);
		textPane.setBounds(0, 0, 227, 290);
		scrollPane.setViewportView(textPane);
		scrollPane.add(textPane);
		//getContentPane().add(textPane);
		
		
		//user profile 선택할 수 있도록 붙이기
		for(int i = 0; i < mainView.FriendVector.size(); i++) {
			if(mainView.FriendVector.get(i).UserName != mainView.UserName) {
				int len = textPane.getDocument().getLength();
				textPane.setCaretPosition(len);
				FriendLabel f = new FriendLabel(mainView, mainView.FriendVector.get(i).UserImg, mainView.FriendVector.get(i).UserName);
				FriendLabel.add(f);
				textPane.insertComponent(f);
				textPane.setCaretPosition(0);
				repaint();
			}
		}
		
	}
}
