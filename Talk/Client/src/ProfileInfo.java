import java.io.Serializable;

import javax.swing.ImageIcon;

public class ProfileInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	public String username; 
	public ImageIcon profileImg = new ImageIcon(ProfileInfo.class.getResource("./img/standardProfile.png"));
	public ImageIcon backgroundImg = new ImageIcon(ProfileInfo.class.getResource("./img/greyBack.jpeg"));
	public String stateMsg;
	
	public ProfileInfo(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ImageIcon getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(ImageIcon profileImg) {
		this.profileImg = profileImg;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}
	
	public ImageIcon getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(ImageIcon backgroundImg) {
		this.backgroundImg = backgroundImg;
	}
}