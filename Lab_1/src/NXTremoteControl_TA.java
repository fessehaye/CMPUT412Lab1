/*
 * This program creates a Control Window for controlling NXT brick running NXTtr.java via USB.
 * Used code from http://lejos.sourceforge.net/forum/viewtopic.php?t=1723
 *
 * Modified from original to allow usable of EV3 Blocks and exclusively bluetooth connect 
 * 
 * 
 * RUN NXTtr.java ON THE EV3 BLOCK BEFORE RUNNING NXTremoteControl_TA.java
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import lejos.remote.nxt.*;

public class NXTremoteControl_TA extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JButton quit, connect;
	public static JButton forward,reverse, leftTurn, rightTurn, stop, speedUp, slowDown;
	public static JLabel L1,L2,L3,L4,L5,L6,L7,L8,L9,L10;
	public static ButtonHandler bh = new ButtonHandler();
	public static DataOutputStream outData;
	public static BTConnector link;

	public NXTremoteControl_TA()
	{ 
		setTitle ("Control");
		setBounds(650,350,500,500);
		setLayout(new GridLayout(4,5));

		L1 = new JLabel("");
		add(L1); 
		forward = new JButton("Forward");
		forward.addActionListener(bh);
		forward.addMouseListener(bh);
		forward.addKeyListener(bh);
		add(forward);
		L2 = new JLabel("");
		add(L2);
		L3 = new JLabel("");
		add(L3);
		speedUp = new JButton("Accelerate");
		speedUp.addActionListener(bh);
		speedUp.addMouseListener(bh);
		speedUp.addKeyListener(bh);
		add(speedUp);

		leftTurn = new JButton("Left");
		leftTurn.addActionListener(bh);
		leftTurn.addMouseListener(bh);
		leftTurn.addKeyListener(bh);
		add(leftTurn);
		stop = new JButton("Stop");
		stop.addActionListener(bh);
		stop.addMouseListener(bh);
		stop.addKeyListener(bh);
		add(stop);

		rightTurn = new JButton("Right");
		rightTurn.addActionListener(bh);
		rightTurn.addMouseListener(bh);
		rightTurn.addKeyListener(bh);
		add(rightTurn);
		L4 = new JLabel("");
		add(L4);
		slowDown = new JButton("Decelerate");
		slowDown.addActionListener(bh);
		slowDown.addMouseListener(bh);
		slowDown.addKeyListener(bh);
		add(slowDown);

		L5 = new JLabel("");
		add(L5);
		reverse = new JButton("Reverse");
		reverse.addActionListener(bh);
		reverse.addMouseListener(bh);
		reverse.addKeyListener(bh);
		add(reverse);

		L6 = new JLabel("");
		add(L6);
		L7 = new JLabel("");
		add(L7);
		L8 = new JLabel("");
		add(L8);

		connect = new JButton(" Connect ");
		connect.addActionListener(bh);
		connect.addKeyListener(bh);
		add(connect);

		L9 = new JLabel("");
		add(L9);
		L10 = new JLabel("");
		add(L10);

		quit = new JButton("Quit");
		quit.addActionListener(bh);
		add(quit);

	}

	public static void main(String[] args)
	{
		NXTremoteControl_TA NXTrc = new NXTremoteControl_TA();
		NXTrc.setVisible(true);
		connect();
		NXTrc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}//End main

	private static class ButtonHandler implements ActionListener, MouseListener, KeyListener
	{
		//***********************************************************************
		//Buttons action
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getSource() == quit)  {System.exit(0);}
			if(ae.getSource() == connect) {connect();}

			try{
				if(ae.getSource() == speedUp) {outData.writeInt(6);}
				if(ae.getSource() == slowDown) {outData.writeInt(7);}
				outData.flush(); //This forces any buffered output bytes to be written out to the stream. 
			} 
			catch (IOException ioe) {
				System.out.println("\nIO Exception writeInt");
			}

		}//End ActionEvent(for buttons)


		public void mousePressed(MouseEvent moe) 
		{   
			try {
				if(moe.getSource() == forward)outData.writeInt(1);
				if(moe.getSource() == reverse)outData.writeInt(2);
				if(moe.getSource() == leftTurn)outData.writeInt(3);
				if(moe.getSource() == rightTurn)outData.writeInt(4);
				if(moe.getSource() == speedUp)outData.writeInt(6);
				if(moe.getSource() == slowDown)outData.writeInt(7);

				outData.flush(); 
			}
			catch (IOException ioe) {
				System.out.println("\nIO Exception writeInt");
			}

		}//End mousePressed

		public void mouseReleased(MouseEvent moe) 
		{
			try {
				if(moe.getSource() == forward ||
						moe.getSource() == reverse ||
						moe.getSource() == leftTurn ||
						moe.getSource() == rightTurn)
				{outData.writeInt(5);}
				if(moe.getSource() == slowDown)outData.writeInt(60);
				if(moe.getSource() == speedUp)outData.writeInt(70);

				outData.flush(); 
			} 
			catch (IOException ioe) {
				System.out.println("\nIO Exception writeInt");
			}

		}//End mouseReleased

		//***********************************************************************
		//Keyboard action
		public void keyPressed(KeyEvent ke) {}//End keyPressed

		public void keyTyped(KeyEvent ke) 
		{
			try {
				if(ke.getKeyChar() == 'w')outData.writeInt(1);
				if(ke.getKeyChar() == 's')outData.writeInt(2);
				if(ke.getKeyChar() == 'a')outData.writeInt(3);
				if(ke.getKeyChar() == 'd')outData.writeInt(4);
				if(ke.getKeyChar() == 'i')outData.writeInt(6);
				if(ke.getKeyChar() == 'k')outData.writeInt(7);

				outData.flush(); 
			}
			catch (IOException ioe) {
				System.out.println("\nIO Exception writeInt");
			}

		}//End keyTyped

		public void keyReleased(KeyEvent ke) 
		{
			try {
				if(ke.getKeyChar() == 'w'){outData.writeInt(10);}
				if(ke.getKeyChar() == 's'){outData.writeInt(20);}
				if(ke.getKeyChar() == 'a'){outData.writeInt(30);} 
				if(ke.getKeyChar() == 'd'){outData.writeInt(40);}
				if(ke.getKeyChar() == 'i'){outData.writeInt(60);}
				if(ke.getKeyChar() == 'k'){outData.writeInt(70);}
				if(ke.getKeyChar() == 'q'){System.exit(0);}

				outData.flush();
			} 

			catch (IOException ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}//End keyReleased


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}//End ButtonHandler
	
	/*
	 * Modified from original code to allow bluetooth usage on EV3 block 
	 */
	public static void connect()
	{
		link = new BTConnector();
		//connects directly from EV3 blocks MAC address
		BTConnection btlink = link.connect("00:16:53:44:97:4F", NXTConnection.RAW);

		outData = btlink.openDataOutputStream();
		//displays if EV3 is successfully connected
		System.out.println("\nEV3 is Connected");   

	}//End connect
	
	/*
	 * Used to disconnect the connection after pressing the Q key.
	 */
	public static void disconnect()
	{
		try{
			outData.close();
		} 
		catch (IOException ioe) {
			System.out.println("\nIO Exception writing bytes");
		}
		System.out.println("\nClosed data streams");

	}//End disconnect
}//End ControlWindow class

