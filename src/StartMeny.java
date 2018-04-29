import com.sun.scenario.Settings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StartMeny extends JFrame {

    private GameSettings gSettings;
	public JPanel main = null;
	private JRadioButton hostButton;
	private JRadioButton joinButton;
	private JTextField NameInputField = new JTextField(10);
	private JTextField nickNameField;

	public StartMeny(GameSettings settings)
	{
		gSettings = settings;
		main = new JPanel();
		FyllPanel(main);

	}

	private void FyllPanel(JPanel main2) {
		JPanel emptyPanel = new JPanel();
		main.setLayout(new GridLayout(2,2));
		JPanel rute1 = new JPanel();
		rute1.setLayout(new GridLayout(4,0));
		JPanel rute2 = new JPanel();
		JPanel rute3 = new JPanel();

		JPanel rute4 = new JPanel();
		JButton join = MonitorConnectButton();
		JLabel ipAddr = LagIpAddr();
		//Textfield for IP input. Changes when joinButton isSelected();
		JTextField ipInputField = new JTextField(10);
		nickNameField = new JTextField(10);


		ipInputField.setEditable(false);
		NameInputField.setEditable(true);
		ButtonGroup bg1 = new ButtonGroup();
		hostButton = new JRadioButton("Host Game");
		joinButton = new JRadioButton("Join Game");
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ipInputField.setEditable(true);
			}
		});
		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipInputField.setEditable(false);
			}
		});

		bg1.add(hostButton);
		bg1.add(joinButton);
		rute1.add(joinButton);
		rute1.add(hostButton);
		rute1.add(ipAddr);
		rute2.add(new JLabel("Skriv IP-adresse du koble deg til"));
		rute2.add(ipInputField);
		rute3.add(new JLabel("Skriv ditt nickname"));
		rute3.add(nickNameField);

		rute3.add(join);
		main.add(rute1);
		main.add(rute2);
		main.add(rute3);
		main.add(rute4);

	}

	private JLabel LagIpAddr() {
		// TODO Auto-generated method stub
		return new JLabel("Din IpAdresse: Localhost");
	}

	private JButton MonitorConnectButton() {
		JButton retur = new JButton("Connect to Game");
		retur.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	NameInputField.setText("playaaaa");
		        //Here goes the action (method) you want to execute when clicked
		        System.out.println("You clicked the connecto buttono");
		        if(joinButton.isSelected()) {
					System.out.println("debug: joinbutton isselected. FUNGERER");


					// TODO: Åpne vindu for join game
				}
				if(hostButton.isSelected()) {
					System.out.println("debug: hostbutton isselected. FUNGERER");
					gSettings.setPlayer(new Player(nickNameField.toString(), true));
					gSettings.setHost();
					gSettings.setReady(true);
				}
		    }
		});
		return retur;
	}
}
