package it.unibo.squaresgameteam.squares.view.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import it.unibo.squaresgameteam.squares.controller.classes.MusicImpl;

import it.unibo.squaresgameteam.squares.view.interfaces.GUIElements;
import it.unibo.squaresgameteam.squares.view.interfaces.OptionsMenu;

public class OptionsMenuImpl implements OptionsMenu, GUIElements {

	private JFrame frmOptionsMenu;
	private Color col;
	private boolean start=true;
	private JButton btnColor1, btnColor2, btnMusic;
	private MusicImpl mi;

	/**
	 * Create the application.
	 */
	public OptionsMenuImpl(MusicImpl mi) {
		this.mi = mi;
		initialize();
	}
	
	@Override
	public void showGUI()
	{
		frmOptionsMenu.setLocationRelativeTo(null);
		frmOptionsMenu.setVisible(true);
	}
	
	@Override
	public void hideGUI()
	{
		frmOptionsMenu.setVisible(false);
		frmOptionsMenu.dispose();
	}
	
	@Override
	public void setBackground(Color c)
	{
		frmOptionsMenu.getContentPane().setBackground(c);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOptionsMenu = new JFrame();
		frmOptionsMenu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
			}
		});
		frmOptionsMenu.setTitle("Squares");
		frmOptionsMenu.setResizable(false);
		frmOptionsMenu.setBounds(100, 100, 400, 400);
		frmOptionsMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmOptionsMenu.getContentPane().setLayout(null);
		
		JLabel lblColor1 = new JLabel("PLAYER 1 COLOR");
		lblColor1.setFont(new Font("Sitka Text", Font.PLAIN, 16));
		lblColor1.setHorizontalAlignment(SwingConstants.LEFT);
		lblColor1.setBounds(50, 70, 200, 30);
		frmOptionsMenu.getContentPane().add(lblColor1);
		
		btnColor1 = new JButton("");
		btnColor1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setFirstPlayerColour();
			}
		});
		btnColor1.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		btnColor1.setBounds(280, 70, 70, 30);
		frmOptionsMenu.getContentPane().add(btnColor1);
		
		JLabel lblColor2 = new JLabel("PLAYER 2 COLOR");
		lblColor2.setHorizontalAlignment(SwingConstants.LEFT);
		lblColor2.setFont(new Font("Sitka Text", Font.PLAIN, 16));
		lblColor2.setBounds(50, 110, 200, 30);
		frmOptionsMenu.getContentPane().add(lblColor2);

		btnColor2 = new JButton("");
		btnColor2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setSecondPlayerColour();
			}
		});
		btnColor2.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		btnColor2.setBounds(280, 110, 70, 30);
		frmOptionsMenu.getContentPane().add(btnColor2);
		
		JButton btnMusic;
		if(mi.isStarted())
			btnMusic = new JButton("Stop music");
		else
			btnMusic = new JButton("Start music");
		btnMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setMusic();
			}
		});
		btnMusic.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		btnMusic.setBounds(125, 225, 150, 30);
		frmOptionsMenu.getContentPane().add(btnMusic);

		JButton btnBackground = new JButton("Change application background...");
		btnBackground.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setBackground();
			}
		});
		btnBackground.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		btnBackground.setBounds(50, 265, 300, 30);
		frmOptionsMenu.getContentPane().add(btnBackground);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				hideGUI();
				StartMenuImpl sm = new StartMenuImpl(mi);
				sm.setBackground(col);
				sm.showGUI();
			}
		});
		btnBack.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		btnBack.setBounds(10, 330, 130, 30);
		frmOptionsMenu.getContentPane().add(btnBack);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				hideGUI();
				StartMenuImpl sm = new StartMenuImpl(mi);
				sm.setBackground(frmOptionsMenu.getContentPane().getBackground());
				sm.showGUI();
			}
		});
		btnSave.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		btnSave.setBounds(254, 330, 130, 30);
		frmOptionsMenu.getContentPane().add(btnSave);
	}
	
	@Override
	public void setMusic() {
		if(mi.isStarted())
		{
			mi.stopMusic();
			btnMusic.setText("Start music");
		}
		else
		{
			mi.startMusic();
			btnMusic.setText("Stop music");
		}
	}

	@Override
	public void setFirstPlayerColour() {
		btnColor1.setBackground(JColorChooser.showDialog(null, "Choose a color", Color.RED));
	}

	@Override
	public void setSecondPlayerColour() {
		btnColor2.setBackground(JColorChooser.showDialog(null, "Choose a color", Color.RED));
	}

	@Override
	public void setBackground() {
		if(start)
		{
			col = frmOptionsMenu.getContentPane().getBackground();
			start=false;
		}
		setBackground(JColorChooser.showDialog(null, "Choose a color", Color.RED));
	}

}
