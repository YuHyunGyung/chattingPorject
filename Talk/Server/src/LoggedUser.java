import java.sql.Date;

import javax.swing.ImageIcon;

public class LoggedUser {
	private static final long serialVersionUID = 1L;
	public String UserName;
	public String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	public String data; //ä�� �޼��� 
	public String UserStatus; //���� online ���� 
	public String UserStatusMsg; //���� ���¸޼��� 
	public Date date; //���������� ���� ä�� �ð�
	
	public ImageIcon img; //�ְ�޴� ���� 
	public ImageIcon profile; //�����ʻ���
	
	LoggedUser(ImageIcon profile, String UserName, String UserStatus, String UserStatusMsg) {
		this.profile = profile;
		this.UserName = UserName;
		this.UserStatus = UserStatus;
		this.UserStatusMsg = UserStatusMsg;
	}
}
