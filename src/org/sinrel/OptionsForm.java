package org.sinrel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import java.awt.Button;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Panel;
import javax.swing.JLabel;

import net.minecraft.MinecraftUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import anjocaido.minecraftmanager.MinecraftBackupManager;

import com.sun.xml.internal.ws.api.server.Container;
import javax.swing.SwingConstants;

public class OptionsForm extends JFrame {

	private JPanel contentPane;
	private JTextField serverField;
	private JTextField portField;
	private JPanel panel;
	private JRadioButton useAutoConnectRadioButton;
	
	public static final String SERVER_OPTION = "sinrel-server";
	public static final String PORT_OPTION = "sinrel-port";
	public static final String UPDATE_OPTION= "";
	
	private Label updateLabel;
	private JTextField updateField;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame parent = new JFrame(); 
					parent.setBounds(0, 0, 1440, 900);
					OptionsForm frame = new OptionsForm(parent);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void saveOptions()
	{
		try {
			Options options = MinecraftUtil.getOptions();
			if(useAutoConnectRadioButton.isSelected()){
				if(serverField.getText().trim() != "") options.setOption(SERVER_OPTION, serverField.getText());
				else options.setOption(SERVER_OPTION, "nothing");
				if(portField.getText().trim() != "") options.setOption(PORT_OPTION, portField.getText());
				else options.setOption(PORT_OPTION, "nothing");
			}else
			{
				options.setOption(SERVER_OPTION, "nothing");
				options.setOption(PORT_OPTION, "nothing");
			}
			options.setOption(UPDATE_OPTION, updateField.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setEnablePanel(Boolean value)
	{
		panel.setEnabled(value);
		for(Component c : panel.getComponents()) c.setEnabled(value);
	}
	
	/**
	 * Create the frame.
	 */
	public OptionsForm(Frame parent) {
		try {
			UIManager.setLookAndFeel(MinecraftBackupManager.feel);
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Options options = MinecraftUtil.getOptions();
		Boolean enable = true;
		String server = null;
		String port = null;
		try {
			server = options.getOption(SERVER_OPTION);
			System.out.println(server);
		    port = options.getOption(PORT_OPTION);
			if (server == null || server.equals("nothing")) enable = false;
			else enable = true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setResizable(false);
		setBounds(parent.getBounds().x + parent.getBounds().width / 2,
				parent.getBounds().y + parent.getBounds().height / 2, 
				256,
				284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton saveButton = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveOptions();
				try {
					OptionsForm.this.setVisible(false);
					OptionsForm.this.dispose();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		saveButton.setBounds(113, 213, 127, 32);
		contentPane.add(saveButton);
		
		useAutoConnectRadioButton = new JRadioButton("\u0410\u0432\u0442\u043E\u043F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435 \u043A \u0441\u0435\u0440\u0432\u0435\u0440\u0443");
		useAutoConnectRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setEnablePanel(useAutoConnectRadioButton.isSelected());
			}
		});
		useAutoConnectRadioButton.setBounds(10, 6, 197, 23);
		
		contentPane.add(useAutoConnectRadioButton);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 36, 230, 87);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setEnabled(enable);
		
		serverField = new JTextField();
		serverField.setBounds(74, 11, 146, 26);
		panel.add(serverField);
		serverField.setColumns(10);
		serverField.setEnabled(enable);
		if(enable) serverField.setText(server);
		
		JLabel serverLabel = new JLabel("\u0421\u0435\u0440\u0432\u0435\u0440:");
		serverLabel.setBounds(22, 16, 54, 14);
		serverLabel.setEnabled(enable);
		panel.add(serverLabel);
		
		portField = new JTextField();
		portField.setBounds(134, 47, 86, 26);
		panel.add(portField);
		portField.setColumns(10);
		portField.setEnabled(enable);
		if(enable) portField.setText(port);
		
		//if (options.getOption(SERVER_OPTION) == null) 
		
		JLabel portLabel = new JLabel("\u043F\u043E\u0440\u0442:");
		portLabel.setBounds(97, 52, 46, 14);
		portLabel.setEnabled(enable);
		panel.add(portLabel);
		useAutoConnectRadioButton.setSelected(enable);
		
		updateLabel = new Label("\u0421\u0435\u0440\u0432\u0435\u0440 \u043E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u0439:");
		updateLabel.setBounds(10, 134, 230, 23);
		contentPane.add(updateLabel);
		
		updateField = new JTextField();
		updateField.setBounds(10, 177, 230, 26);
		contentPane.add(updateField);
		updateField.setColumns(10);
		try {
			updateField.setText(MinecraftUtil.getOptions().getOption(OptionsForm.UPDATE_OPTION));
			
			Label label = new Label("(\u043D\u0435 \u0440\u0435\u043A\u043E\u043C\u0435\u043D\u0434\u0443\u0435\u0442\u0441\u044F \u0438\u0437\u043C\u0435\u043D\u044F\u0442\u044C)");
			label.setBounds(10, 151, 230, 23);
			contentPane.add(label);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
