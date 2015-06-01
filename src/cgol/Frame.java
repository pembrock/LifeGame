package cgol;

//import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Frame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1318277466163291821L;
	private Screen s;
	private Simulation sim;
	private float tslu;
	static float PAUSETIME = 0.05f;
	public Frame()
	{
		String input = JOptionPane.showInputDialog(this, "Размер ячейки(>2):");
		
		try 
		{
			if(Integer.parseInt(input) < 3) input = "3";
			Cell.size = Integer.parseInt(input);
		} catch (Exception e) 
		{
			System.exit(0);
		}
		
		input = JOptionPane.showInputDialog(this, "Задержка(Например: 0.01):");
		
		try 
		{
			PAUSETIME = Float.parseFloat(input);
		} catch (Exception e) 
		{
			System.exit(0);
		}
		setExtendedState(MAXIMIZED_BOTH);
	}
	
	public void createScreen()
	{
		sim = new Simulation();
		
		addKeyListener(sim);
		addMouseListener(sim);
		addMouseMotionListener(sim);
		
		s = new Screen();
		s.setBounds(0, 0, Main.width, Main.height);
		add(s);
	}
	
	public void update(float tslf)
	{
		tslu += tslf;
		if(tslu > PAUSETIME)
		{
			sim.update();
			tslu = 0;
		}
	}
	
	public void repaint()
	{
		s.repaint();
	}
	
	private class Screen extends JLabel
	{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5653511807167256206L;

		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			sim.draw(g);
			
		}
	}
}
