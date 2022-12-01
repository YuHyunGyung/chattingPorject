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

	public ChatClientSelectFriendDialog(ChatClientMainView mainView) {
		
		
		setBounds(100, 100, 240, 380);
		getContentPane().setLayout(null);
		
		JButton addChatRoom = new JButton("채팅방 추가");
		addChatRoom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String roomId = "";
				for(int i = 0; i <FriendLabel.size(); i++) {
					if(FriendLabel.get(i).check)
						roomId += FriendLabel.get(i).UserName + " ";
				}
				ChatMsg cm = new ChatMsg(mainView.UserName, "500", roomId);
				mainView.SendObject(cm);
				System.out.println("Room ID : " + roomId);
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
		textPane.setLayout(null);
		textPane.setBounds(0, 0, 227, 290);
		scrollPane.setViewportView(textPane);
		scrollPane.add(textPane);
		//getContentPane().add(textPane);
		
		for(int i = 0; i < mainView.FriendVector.size(); i++) {
			int len = textPane.getDocument().getLength();
			textPane.setCaretPosition(len);
			FriendLabel f = new FriendLabel(mainView.FriendVector.get(i).UserImg, mainView.FriendVector.get(i).UserName);
			FriendLabel.add(f);
			textPane.insertComponent(f);
			textPane.setCaretPosition(0);
			repaint();
		}
		
	}
}
