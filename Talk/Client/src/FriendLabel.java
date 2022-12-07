import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

public class FriendLabel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final JTextPane textPane = new JTextPane();
	public JButton profile;
	public String UserName;
	public ImageIcon UserImg = new ImageIcon(FriendLabel.class.getResource("./img/standardProfile.png"));
	public boolean check = false;
	public ChatClientMainView mainView;
	
	public FriendLabel(ChatClientMainView mainView, ImageIcon userImg, String username) {
		this.mainView = mainView;
		UserImg = userImg;
		UserName = username;
		
		setPreferredSize(new Dimension(210, 60));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		UserImg = mainView.imageSetSize(UserImg, 56, 56);
		profile = new JButton(UserImg);
		profile.setBounds(4, 5, 50, 50);
		add(profile);
		
		JLabel name = new JLabel(UserName);
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		name.setBounds(59, 20, 40, 40);
		name.setSize(120, 20);
		add(name);
		
		MyItemListener listener = new MyItemListener();
		JCheckBox checkBox = new JCheckBox();
		checkBox.addItemListener(listener);
		checkBox.setBounds(180, 19, 28, 23);
		add(checkBox);
	}
	
	class MyItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				check = true;
				System.out.println("ChatList : " + check);
			}
			else {
				check=false;
				System.out.println("ChatList : " + check);
			}
		}
		
	}
}
