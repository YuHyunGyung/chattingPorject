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
	public ImageIcon img = new ImageIcon(TalkServer.class.getResource("./img/standardProfile.png"));
	public ImageIcon Image; //주고받은 이미지
	public String time;
	public Date date;
	public String roomId;
	public String userlist;
	public String UserStatus;
	public String UserStatusMsg;
	
	public ChatMsg(String UserName, String code, String msg) {
		this.UserName = UserName;
		this.code = code;
		this.data = msg;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}


	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public void setImg(ImageIcon profile) {
		this.img = profile;
	}
	public ImageIcon getImg() {
		return img;
	}

	public void setImage(ImageIcon img) {
		this.Image = img;
	}
	public ImageIcon getImage() {
		return Image;
	}

}