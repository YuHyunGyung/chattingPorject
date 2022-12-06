// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String UserName;
	public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	public String data;
	public ImageIcon img = new ImageIcon(ChatClientMainView.class.getResource("./img/standardProfile.png")); //profile
	public ImageIcon Image; //사진파일
	public String time;
	public Date date;
	public String roomId;
	public String userlist;
	public String UserStatusMsg; //유저 상태메세지
	public String UserStatus; //유저 상태
	
	public ChatMsg(String UserName, String code, String msg) {
		this.UserName = UserName;
		this.code = code;
		this.data = msg;
	}
}