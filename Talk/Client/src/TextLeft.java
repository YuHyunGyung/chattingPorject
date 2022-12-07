import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

public class TextLeft extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public JButton profile;
	public JLabel time;
	public ImageIcon profileImg = new ImageIcon(FriendLabel.class.getResource("./img/standardProfile.png"));
	
	//public TextLeft(ChatClientMainView mainView, ImageIcon userImage, String username, String chattime) {
	public TextLeft(ChatMsg cm) {
		setPreferredSize(new Dimension(207, 63));
		setBackground(new Color(147, 208, 250));
		setLayout(null);
		
		profileImg = cm.img; //imageSetSize(profileImg, 56, 56);
		profile = new JButton(profileImg);
		profile.setBounds(5, 5, 50, 50);
		add(profile);
		
		JLabel name = new JLabel(cm.UserName);
		name.setBounds(60, 5, 120, 30);
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		add(name);
		
		time = new JLabel(cm.time);
		time.setBounds(60, 35, 120, 20);
		add(time);
	}
}
