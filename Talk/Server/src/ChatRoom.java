import java.io.Serializable;

import javax.swing.ImageIcon;

public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	public ImageIcon img;
	public String roomId;
	public String userList;
	
	public ChatRoom(String roomId, String userList) {
		this.roomId = roomId;
		this.userList = userList;
	}
}
