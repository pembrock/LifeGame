package cgol;

public class Main {
	static int width;
	static int height;
	public static void main(String[] args) 
	{
		Frame f = new Frame();
		f.setDefaultCloseOperation(3);
		//f.setUndecorated(true);
		f.setVisible(true);
		f.setResizable(false);
		
		width = f.getWidth();
		height = f.getHeight()-150;
		
		f.createScreen();
		
		long lastFrame = System.currentTimeMillis();
		while(true)
		{
			long thisFrame = System.currentTimeMillis();
			float tslf = (float) ((thisFrame - lastFrame) / 1000.0);
			lastFrame = thisFrame;
			
			f.update(tslf);
			
			f.repaint();
			
			try 
			{
				Thread.sleep(10);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
		}
	}

}
