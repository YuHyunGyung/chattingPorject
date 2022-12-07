// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String UserName;
	public String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	public String data;
	public ImageIcon img = new ImageIcon(TalkServer.class.getResource("./img/standardProfile.png"));
	public ImageIcon Image; //�ְ���� �̹���
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