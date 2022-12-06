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
	public ImageIcon img = new ImageIcon(ChatClientMainView.class.getResource("./img/standardProfile.png")); //profile
	public ImageIcon Image; //��������
	public String time;
	public Date date;
	public String roomId;
	public String userlist;
	public String UserStatusMsg; //���� ���¸޼���
	public String UserStatus; //���� ����
	
	public ChatMsg(String UserName, String code, String msg) {
		this.UserName = UserName;
		this.code = code;
		this.data = msg;
	}
}