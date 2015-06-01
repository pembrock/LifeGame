package cgol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Simulation implements KeyListener, MouseMotionListener, MouseListener{

	private Cell[] [] cells;
	private Random random;
	private int width = Main.width/Cell.size;
	private int height = Main.height/Cell.size;
	private int generation;
	private boolean go;
	private int button;
	
	public Simulation(){
		
		random = new Random();
		cells = new Cell [width][height];
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				cells[x][y] = new Cell(x,y);
				//cells[x][y].setAlive(random.nextBoolean());
			}
		}
	}
	
	public void update()
	{
		if(go)
		{
			generation++;
			System.out.println("height = " + height + "; width = " + width);
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					int mx = x - 1;
					if (mx < 0) mx = width - 1;
					int my = y - 1;
					if (my < 0) my = height - 1;
					int gx = (x + 1) % width;
					int gy = (y + 1) % height;
					
					int alivecounter = 0;
					if(cells[mx][my].isAlive()) alivecounter++;
					if(cells[mx][y].isAlive()) alivecounter++;
					if(cells[mx][gy].isAlive()) alivecounter++;
					if(cells[x][my].isAlive()) alivecounter++;
					if(cells[x][gy].isAlive()) alivecounter++;
					if(cells[gx][my].isAlive()) alivecounter++;
					if(cells[gx][y].isAlive()) alivecounter++;
					if(cells[gx][gy].isAlive()) alivecounter++;
					
					if(alivecounter < 2 || alivecounter > 3) cells[x][y].setNextRound(false);
					else if(alivecounter == 2) cells[x][y].setNextRound(cells[x][y].isAlive());
					else if(alivecounter == 3) cells[x][y].setNextRound(true);
					
					/*System.out.println("<------------------------------------------>");
					System.out.println("height = " + height + "; width = " + width);
					System.out.println("mx = " + mx + "; my = " + my);
					System.out.println("gx = " + gx + "; gy = " + gy);
					System.out.println("<------------------------------------------>");
					System.out.println("");*/
				}
				
			}
			
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					cells[x][y].nextRound();
				}
			}
		}
	}
	
	public void draw(Graphics g){
		
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				cells[x][y].draw(g);
			}
		}
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif", Font.BOLD, 25));
		g.drawString("Количество циклов: " + generation, 10, (Main.height - 5) + g.getFont().getSize());
		g.drawString("Случайное заполнение: Z", 10, (Main.height + 20) + g.getFont().getSize());
		g.drawString("Очистить ячейки: R", 10, (Main.height + 45) + g.getFont().getSize());
		g.drawString("Отключить сетку: G", 10, (Main.height + 70) + g.getFont().getSize());
		
		g.drawString("Информация: I", 410, (Main.height - 5) + g.getFont().getSize());
		g.drawString("Старт/стоп: SPACE", 410, (Main.height + 20) + g.getFont().getSize());
		g.drawString("Выход: ESC", 410, (Main.height + 45) + g.getFont().getSize());
		g.drawString("Заполнить/стереть ячейку: ЛКМ/ПКМ", 410, (Main.height + 70) + g.getFont().getSize());
		
	}

	public void keyPressed(KeyEvent e) 
	{
		
		
	}

	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_G)
		{
			if(Cell.grid) Cell.grid = false;
			else Cell.grid = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			generation = 0;
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					cells[x][y].setAlive(random.nextBoolean());
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			generation = 0;
			go = false;
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					cells[x][y].setAlive(false);
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(go) go = false;
			else go = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P)
		{
			System.out.println(Frame.PAUSETIME);
			Frame.PAUSETIME = (float) (Frame.PAUSETIME + 0.1);
		}

		if(e.getKeyCode() == KeyEvent.VK_M)
		{
			System.out.println(Frame.PAUSETIME);
			if (Frame.PAUSETIME >= 0.01)
			Frame.PAUSETIME = (float) (Frame.PAUSETIME - 0.1);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_1)
		{
		
			Cell.size--;
			Frame nf = new Frame();
			nf.repaint();
		
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_2)
		{
		
			Cell.size++;
		
		}
		
		if(e.getKeyCode() == KeyEvent.VK_I)
		{
			final ImageIcon icon = new ImageIcon("D:\\rules.png");
			String info = "• Место действия этой игры — «вселенная» — \n"
					+ "это размеченная на клетки поверхность или плоскость — \n"
					+ "безграничная, ограниченная, или замкнутая (в пределе — бесконечная плоскость). \n\n"
					+ "• Каждая клетка на этой поверхности может находиться в двух состояниях: \n"
					+ "быть «живой» или быть «мёртвой» (пустой).\n"
					+ "Клетка имеет восемь соседей (окружающих клеток).\n\n"
					+ "• Распределение живых клеток в начале игры называется первым поколением.\n"
					+ "Каждое следующее поколение рассчитывается на основе предыдущего по таким правилам:\n"
					+ "	1) в пустой (мёртвой) клетке, рядом с которой ровно три живые клетки, зарождается жизнь;\n"
					+ "	2) если у живой клетки есть две или три живые соседки, то эта клетка продолжает жить;\n "
					+ "в противном случае (если соседей меньше двух или больше трёх) клетка умирает \n"
					+ "(«от одиночества» или «от перенаселённости»)\n\n"
					+ "• Игра прекращается, если на поле не останется ни одной «живой» клетки,\n "
					+ "если при очередном шаге ни одна из клеток не меняет своего состояния \n"
					+ "(складывается стабильная конфигурация)\n"
					+ "или если конфигурация на очередном шаге в точности (без сдвигов и поворотов)\n"
					+ "повторит себя же на одном из более ранних шагов (складывается периодическая конфигурация).\n\n"
					+ "Эти простые правила приводят к огромному разнообразию форм, которые могут возникнуть в игре.\n\n"
					+ "Игрок не принимает прямого участия в игре, а лишь расставляет или генерирует начальную \n"
					+ "конфигурацию «живых» клеток,\n"
					+ "которые затем взаимодействуют согласно правилам уже без его участия (он является наблюдателем).\n\n"; 
			JOptionPane.showMessageDialog(null, info, "Правила", JOptionPane.INFORMATION_MESSAGE, icon);
		}
		
	}

	public void keyTyped(KeyEvent e) 
	{

		
	}

	public void mouseDragged(MouseEvent e) 
	{
		if(!go)
		{
			int mx = e.getX()/Cell.size;
			int my = e.getY()/Cell.size;
			if(button == 1) cells[mx][my].setAlive(true);
			else cells[mx][my].setAlive(false);
		}
	}

	public void mouseMoved(MouseEvent e) 
	{
		/*if(!go)
		{
			int mx = e.getX()/Cell.size;
			int my = e.getY()/Cell.size;
			if(button == 1) cells[mx][my].setAlive(true);
			else cells[mx][my].setAlive(false);
		}*/
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) 
	{
		button = e.getButton();
		if(!go)
		{
			int mx = e.getX()/Cell.size;
			int my = (e.getY()-1)/Cell.size;
			if(button == 1) cells[mx][my].setAlive(true);
			else cells[mx][my].setAlive(false);
			/*System.out.println("Cell.size = " + Cell.size);
			System.out.println("getX = " + e.getX() + "; getY = " + e.getY());
			System.out.println("mx = " + mx + "; my = " + my);*/
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		button = -1;
		
	}

}
