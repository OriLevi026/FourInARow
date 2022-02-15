package games;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FourInARow extends JFrame implements MouseListener {

	JLabel[][] grid;
	JLabel label,scoreRed, scoreBlue;
	JButton restart;
	int red = 0,blue = 0;
	ImageIcon emptyCircle, redCircle, blueCircle;
	boolean PlayerColor = true; // TRUE = RED || FALSE = BLUE


	public FourInARow(){
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(770,920);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setTitle("Play Four In A Row"); // edit later
		
		this.emptyCircle = new ImageIcon("C:/Users/USER/workspace/FunGame/src/games/EmptyCircleSmall.png");
		this.redCircle = new ImageIcon("C:/Users/USER/workspace/FunGame/src/games/RedCircleSmall.png");
		this.blueCircle = new ImageIcon("C:/Users/USER/workspace/FunGame/src/games/BlueCircleSmall.png");

		label = new JLabel("RED FIRST - START !",JLabel.CENTER);
		label.setBackground(Color.RED);
		label.setForeground(Color.WHITE);
		label.setBounds(0, 0, 750, 25);
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setOpaque(true);

		restart = new JButton("RESTART");
		restart.setBounds(300, 750, 150, 100);
		restart.setFont(new Font("Tahoma", Font.BOLD, 20));
		restart.addMouseListener(this);

		scoreRed = new JLabel(""+this.red,JLabel.CENTER);	
		scoreRed.setBounds(50, 750, 150, 100);
		scoreRed.setBackground(Color.RED);
		scoreRed.setForeground(Color.white);
		scoreRed.setFont(new Font("Tahoma", Font.BOLD, 28));
		scoreRed.setOpaque(true);

		scoreBlue =new JLabel(this.blue+"",JLabel.CENTER);
		scoreBlue.setBounds(550, 750, 150, 100);
		scoreBlue.setBackground(Color.BLUE);
		scoreBlue.setForeground(Color.white);
		scoreBlue.setFont(new Font("Tahoma", Font.BOLD, 28));
		scoreBlue.setOpaque(true);

		setGrid();

		this.add(restart);
		this.add(label);
		this.add(scoreRed);
		this.add(scoreBlue);

		setVisible(true);
	}

	public void setGrid() {// Sets the Game grid
		this.grid = new JLabel[7][7];
		for(int i=0; i<7;i++){
			for(int j=0;j<7;j++){
				grid[i][j] = new JLabel();
				grid[i][j].setBounds(30+(j*100),30+ (i*100), 95, 95);
				grid[i][j].setIcon(this.emptyCircle);
				grid[i][j].setForeground(Color.WHITE);
				grid[i][j].setOpaque(true);
				grid[i][j].addMouseListener(this);
				this.add(grid[i][j]);
			}
		}

	}

	private void restartGrid() {// Reset the Game Grid - Keeping Score
		for(int i=0; i<7;i++){
			for(int j=0;j<7;j++){
				this.grid[i][j].setIcon(this.emptyCircle);
				this.grid[i][j].setForeground(Color.WHITE);
				if(PlayerColor){
					this.label.setText("GAME RESTARTED - RED TURN!");
					this.label.setBackground(Color.RED);
				}
				else {
					this.label.setText("GAME RESTARTED - BLUE TURN!");
					this.label.setBackground(Color.BLUE);
				}
			}
		}

		this.scoreRed.setText(this.red+"");
		this.scoreBlue.setText(this.blue+"");
	}

	public boolean isWinner(int i, int j, Color color){ // check for winner after each turn
		int dis = 1;
		int k = j+1;// k stands for direction of advance
		while(k < 7){//---------------------------------------RIGHT
			if(grid[i][k].getForeground() == color){
				dis++;
				k++;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}
		k = j-1;
		while(k >= 0){//--------------------------------------LEFT
			if(grid[i][k].getForeground() == color){
				dis++;
				k--;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}
		// check diagonal between top_left to bottom_right
		dis = 1;
		k = 1;
		while(i+k < 7 && j+k < 7){//--------------------------DIAGONAL LEFT+DOWN
			if(grid[i+k][j+k].getForeground() == color){
				dis++;
				k++;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}
		k = 1;
		while(i-k >= 0 && j+k < 7){//----------------------DIAGONAL RIGHT+UP
			if(grid[i-k][j+k].getForeground() == color){
				dis++;
				k++;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}
		// check diagonal between bottom_right to top_left
		dis = 1;
		k = 1;
		while(i + k < 7 && j - k >= 0){//------------------------DIAGONAL RIGHT+DOWN
			if(grid[i+k][j-k].getForeground() == color){
				dis++;
				k++;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}
		k = 1;
		while(i-k >= 0 && j-k >= 0){//DIAGONAL LEFT+UP
			if(grid[i-k][j-k].getForeground() == color){
				dis++;
				k++;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}
		dis = 1;
		k = i+1;
		while(k < 7 ){//---------------------------------DOWN
			if(grid[k][j].getForeground() == color){
				dis++;
				k++;
				if(dis == 4)return true;
			}else { // color is white or the opponent color
				break;
			}
		}

		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) { // event listener for the game
		if(e.getComponent() == this.restart)restartGrid();
		for(int i=0; i<7;i++){
			for(int j=0;j<7;j++){
				if(e.getComponent() == this.grid[i][j]){
					if(this.grid[i][j].getForeground() != Color.WHITE){//pressed on a painted square
						this.label.setText("ALREADY TAKEN !");
					}
					else{
						int index = fallDown(i,j);
						int i_index = i, j_index = j;
						//sing thread to simulate the coin drop
						Thread t = new Thread(() -> {
							for(int k = i_index; k<index;k++){
								if(this.PlayerColor)grid[k][j_index].setIcon(this.blueCircle);
								else grid[k][j_index].setIcon(this.redCircle);
								try {
									Thread.sleep(120);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								grid[k][j_index].setIcon(emptyCircle);
							}
						});
						t.start();
						if(PlayerColor == true){// painting label for opponent
							grid[index][j].setForeground(Color.RED);
							grid[index][j].setIcon(this.redCircle);
							PlayerColor = false;
							this.label.setBackground(Color.BLUE);
							this.label.setText("BLUE TURN !");


						}else {
							grid[index][j].setForeground(Color.BLUE);
							grid[index][j].setIcon(this.blueCircle);
							PlayerColor = true;
							this.label.setText(" RED TURN !");
							this.label.setBackground(Color.RED);

						}
						//check if wining acquired and by whom
						if (isWinner(index,j,this.grid[index][j].getForeground()) == true){
							System.out.println("and we have a winner");
							setWinner(this.grid[index][j].getForeground());
						}
					}
				}
			}
		}
	}
	// recursive function - to drop the coin to possible bottom
	private int fallDown(int i, int j) {  
		if(i == 7) return  i-1;
		if(this.grid[i][j].getForeground() == Color.WHITE )return fallDown(i+1,j);
		return i-1;
	}
	// sets the winner color, score
	private void setWinner(Color color) { // paint the grid in the winner color , adding score , top label declaring winner
		for(int i=0; i<7;i++){
			for(int j=0;j<7;j++){
				this.grid[i][j].setForeground(color);
				if(color == Color.RED)this.grid[i][j].setIcon(this.redCircle);
				else this.grid[i][j].setIcon(this.blueCircle);
			}
		}
		if(color == Color.RED){
			this.label.setText("RED IS THE WINNER");
			this.red++;
		}
		else {
			this.label.setText("BLUE IS THE WINNER");
			this.blue++;
		}
		this.label.setBackground(color);
	}
	//main - start the game
	public static void main(String[] args) {
		FourInARow game = new FourInARow();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
