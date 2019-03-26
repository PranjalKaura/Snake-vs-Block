package application;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



import javafx.geometry.*;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This is an abstract class which defines the common features of all the tokens in our game. 
 * These include: Ball, Blocks, Bomb, Bullets, Wall, Magnet and Shields. 
 *
 * @author Sezal
 */

public   abstract class Tokens implements Serializable {

	private static final long serialVersionUID = 1L;
	public transient Node view; 
	protected double Xcor,Ycor=0;
	protected double speed=4;
	protected  int Height=700,Width=530;
	protected transient Point2D velocity=new Point2D(0, 0);
	private boolean alive=true;
	private int size;
	protected int value;
	protected transient Text text;
	protected transient Color color;
	protected transient Random rand=new Random();
	

	public Tokens() {
		
	}
	public int getValue() {
		return value;
	}
	
	public void setValue(int number) {
		value=number;
	}
	public double getXcor() {
		return Xcor;
	}
	public void setXcor(int xcor) {
		Xcor = xcor;
	}
	public double getYcor() {
		return Ycor;
	}
	public void setYcor(int ycor) {
		Ycor = ycor;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @param sets the view of the tokens
	 */
	public void setView(Node view) {
		this.view=view;
	}	
	/**
	 * @param value that sets the speed of the snake
	 */
	public void setSpeed(double value) {
		speed=value;
	}	
	/**
	 * @return speed of snake
	 */
	public double getSpeed() {
		return speed;
	}	
	public int getSize() {
		return size;
	}	
	public void setSize(int size) {
		this.size=size;
	}	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}	
	public boolean isDead() {
		return !alive;
	}
	public Point2D getVelocity() {
		return velocity;
	}	
	public void setVelocity(Point2D point2d) {
		this.velocity = point2d;
	}	
	/**
	 * @return view of the tokens : node object
	 */
	public Node getView() {
		return view;
	}
	public Tokens(Node view){
		 this.view=view;
	}
	public double getRotate() {
		return view.getRotate();
	}
	/**
	 * @param the value to be written in the token
	 */
	public void setText(Text t) {
		text=t;
	}	
	/**
	 * @return the text value inside the token
	 */
	public Text getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}
	

	
    /**
    * This method is used to update the position of the tokens at every instant 
    *
    * @return void
    * @since version 1.00
    */

	public void update() {
		view.setTranslateX(view.getTranslateX()+velocity.getX());
		view.setTranslateY(view.getTranslateY()+speed);
		Bounds boundsInScene = view.localToScene(view.getBoundsInLocal());
		
		Xcor=boundsInScene.getMinX();
		
		Ycor=boundsInScene.getMinY();
		if(text!=null) {
		text.setTranslateX(view.getTranslateX()+velocity.getX()+size/2-size/8);
		text.setTranslateY(view.getTranslateY()+speed+size/2+size/10);
		}
		
		
	}

	/**
	 * this method is used to check if there a collision between the tokens. 
	 * @param other This is a token variable
	 * @return a boolean value 
	 */
	public boolean isCollide(Tokens other) {
		return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	}

}


/**
 * This class makes small rectangular objects which can be seen when the snake collides
 * with the block. These small blocks use animation to show their dispersing. 
 * These come into play also when the snake collides with the bomb and all the blocks on 
 * current screen blast off. 
 * @author Pranjal
 *
 */
class BlockExtension extends Tokens{
	
	private double Xchange=0;
	private double Ychange=0;
	public BlockExtension() {
		// TODO Auto-generated constructor stub
		super(new Rectangle(3,3,Color.WHITE));
		
		Xchange=rand.nextDouble()+0.5;
		Ychange=rand.nextDouble()/2; 
	}

	/**
	 * this function is an over-ridden function from the token class.
	 * this basically updates the position of all the small rectangular objects. 
	 * 
	 * @return void
	 */
	public void update() {
		int SignCheck=rand.nextInt(2);
		if(SignCheck==0)
		view.setTranslateX(view.getTranslateX()-Xchange);
		else {
			view.setTranslateX(view.getTranslateX()+Xchange);
		}
		view.setTranslateY(view.getTranslateY()+Ychange);
		Ychange+=Ychange/10;
	}
	
}


/**
 * This class makes small circular objects which can be seen when the snake collides
 * with the ball. These small balls use animation to show their dispersing. 
 * @author Pranjal
 *
 */

class BallExtension extends Tokens{
	
	private double Xchange=0;
	private double Ychange=0;
	private double time;
	public BallExtension() {
		// TODO Auto-generated constructor stub
		super(new Circle(2,Color.ORANGE));
		time=System.currentTimeMillis();
		Xchange=rand.nextDouble()+0.5;
		Ychange=rand.nextDouble() + 0.5;
	}

	/**
	 * this function is an over ridden function from the token class.
	 * this basically updates the position of all the small circular objects. 
	 * 
	 * @return void
	 */
	public void update() {
		
		int SignCheck=rand.nextInt(2);
		if(SignCheck==0)
		view.setTranslateX(view.getTranslateX()-Xchange);
		else {
			view.setTranslateX(view.getTranslateX()+Xchange);
		}
		SignCheck=rand.nextInt(2);
		if(SignCheck==0)
			view.setTranslateY(view.getTranslateY()-Ychange);
			else {
				view.setTranslateY(view.getTranslateY()+Ychange);
			}
			
			
		view.setOpacity((2000-(System.currentTimeMillis()-time))/6000);	
		if(System.currentTimeMillis()-time>500) {
			
			this.getView().setVisible(false);
			setAlive(false);
		}
		Ychange+=Ychange/10;
		Xchange+=Xchange/10;
	}
	
}


/**
 * This class takes represents of the small parts of the snake as it colides with the block
 * @author Pranjal
 *
 */
class SnakeExtension extends Tokens{
	
	private double Xchange=0;
	private double Ychange=0;
	private double time;
	public SnakeExtension() {
		
		// TODO Auto-generated constructor stub
		super(new Circle(2,Color.CHARTREUSE));
		
		time=System.currentTimeMillis();
		Xchange=rand.nextDouble()+4.5;
		Ychange=rand.nextDouble()+1.5;
		if(rand.nextInt(5)%5==0) {
			this.setView(new Rectangle(4,4,Color.DARKGREEN));
		}
		else if(rand.nextInt(5)%5==1) {
			this.setView(new Rectangle(3,3,Color.GREENYELLOW));
		}
		
		
		
	}
	
	/**
	 * this function is an over ridden function from the token class.
	 * this basically updates the position of all the small circular objects. 
	 * 
	 * @return void
	 */
	public void update() {
		int SignCheck=rand.nextInt(2);
		if(SignCheck==0)
		view.setTranslateX(view.getTranslateX()-Xchange);
		else {
			view.setTranslateX(view.getTranslateX()+Xchange);
		}
		SignCheck=rand.nextInt(2);
		if(SignCheck==0)
			view.setTranslateY(view.getTranslateY()-Ychange);
			else {
				view.setTranslateY(view.getTranslateY()+Ychange);
			}
			
			
		view.setOpacity((2000-(System.currentTimeMillis()-time))/6000);	
		if(System.currentTimeMillis()-time>500) {
			
			this.getView().setVisible(false);
			setAlive(false);
		}
		Ychange+=Ychange/10;
		Xchange+=Xchange/10;
	}
	
}


/**
 * This class basically handles all the parameters and features of the blocks in the game.
 * This extends the abstract class tokens and add the new features specific to the Blocks. 
 * @author Sezal
 *
 */
class Blocks extends Tokens{
	private transient Color[] arrC= {Color.DARKORCHID,Color.AQUA,Color.CHARTREUSE,Color.CORNFLOWERBLUE,Color.DEEPPINK};
	
	
	
	Blocks(){
		super(new Rectangle(60,60,Color.BLUE));
		Random rand=new Random();
		int index=rand.nextInt(5);
		
		Rectangle rec=new Rectangle(60,60,arrC[index]);
		color=arrC[index];
		rec.setArcHeight(15);
		rec.setArcWidth(15);
		this.setView(rec);
		
		this.setVelocity(new Point2D(0, speed));
		this.setSize(60);
		
		text=new Text(Integer.toString(rand.nextInt(6)+1));
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Sans serif",FontWeight.EXTRA_BOLD,FontPosture.REGULAR,20));
	}
}


/**
 * This class basically handles all the parameters and features of the ball in the game.
 * This extends the abstract class tokens and add the new features specific to the Ball. 
 * @author Sezal
 *
 */
class Ball extends Tokens{
	 static boolean magnetAlive=false;
	Ball(){
		//super(new Circle(7,7,7,Color.ORANGE));
		Circle ball=new Circle(7, 7, 7, Color.ORANGE);
		this.setView(ball);
		this.setVelocity(new Point2D(0, speed));
		this.setSize(14);
		
		Random rand=new Random();
		value=(rand.nextInt(7)+1);
		text=new Text(Integer.toString(value));
		text.setFill(Color.ORANGE);
		text.setFont(Font.font("Sans serif",FontWeight.NORMAL,FontPosture.REGULAR,20));
		
		
	}
	/**
	 * this function is an over ridden function from the token class.
	 * this basically updates the position of all the small circular objects. 
	 * 
	 * @return void
	 */
	public void update() {
		view.setTranslateX(view.getTranslateX()+velocity.getX());
		view.setTranslateY(view.getTranslateY()+speed);
		Circle c=(Circle)view;
		Xcor=c.getCenterX()+c.getTranslateX();
		Ycor=c.getCenterY()+c.getTranslateY();
		if(text!=null) {
		text.setTranslateX(view.getTranslateX()-5+velocity.getX()+getSize()/2);
		text.setTranslateY(view.getTranslateY()-15+speed+getSize()/2);
		}
		
		
	}

	/**
	 * this method is used to check if there a collision between the tokens. 
	 * @param other This is a token variable
	 * @return a boolean value 
	 */
	@Override
	public boolean isCollide(Tokens other) {
		// TODO Auto-generated method stub
		if(magnetAlive) {
			if(Math.pow(view.getTranslateX()-other.getView().getTranslateX(), 2)+Math.pow(view.getTranslateY()-other.getView().getTranslateY(), 2)<8100) {
				String musicFile = "Swoosh.mp3";     // For example

				Media sound = new Media(new File(musicFile).toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.play();
				return true;
			}
		}
		else
		return super.isCollide(other);
		return false;
		
	}

	/**
	 * This method is used to set the Magnet condition of the snake as true.
	 * If value - true then conditions of magnet are applicable if -False, magnet is off. 
	 * @param value boolean
	 * @return void
	 */
	public void setMagnetAlive(boolean value) {
		 magnetAlive=value;
	}
}

/**
 * This class basically handles all the parameters and features of the bomb in the game.
 * This extends the abstract class tokens and add the new features specific to the Bomb. 
 * @author Sezal
 *
 */
class Bomb extends Tokens{
	Bomb(){
		super(new Circle(7,7,7,Color.WHITE));
		Image bomb2=new Image("/application/AYG5.gif");
		ImageView bombImage=new ImageView(bomb2);
		bombImage.setScaleX(0.15);
		bombImage.setScaleY(0.15);
		this.setView(bombImage);
		this.setVelocity(new Point2D(0, speed));
		this.setSize(20);
		
		
	}
	/**
	 * Clears the entire screen from the blocks present on the current screen. 
	 * @param Blocks
	 * @param root
	 * @param DeadBlocks
	 * @return Integer
	 */

	public int Clear(List<Tokens> Blocks,Pane root,List<Tokens> DeadBlocks) {
		int score = 0;
		for(Tokens block:Blocks) {
			block.setAlive(false);
			root.getChildren().remove(block.getView());
			root.getChildren().remove(block.getText());
			score+=Integer.parseInt(block.getText().getText());
			for(int x=0;x<=60;x+=3) {
				for(int y=0;y<=60;y+=3) {
					BlockExtension deadBlock=new BlockExtension();
					deadBlock.getView().setTranslateX(block.getView().getTranslateX()+x);
					deadBlock.getView().setTranslateY(block.getView().getTranslateY()+y);
					Rectangle rec=(Rectangle)deadBlock.getView();
				rec.setFill(block.getColor());
				deadBlock.setView(rec);
					DeadBlocks.add(deadBlock);
					root.getChildren().add(deadBlock.getView());
					
				}
			}
			
		
		
	}
	for(Tokens block:Blocks) {
		block.setAlive(false);
		root.getChildren().remove(block.getView());
		root.getChildren().remove(block.getText());
		score+=Integer.parseInt(block.getText().getText());
	
	
}
		return score;
	}
}
/**
 * This class basically handles all the parameters and features of the SnakeBody in the game.
 * This extends the abstract class tokens and add the new features specific to the SnakeBody. 
 * This class basically represents each and every circular object in the length of the snake.
 * The entire body is an ArrayList of objects of this class. 
 * @author Sezal
 *
 */
 class SnakeBody extends Tokens{
	SnakeBody(){
		Circle ball=new Circle(10, 10, 10, Color.CHARTREUSE);
		this.setView(ball);
		this.setVelocity(new Point2D(0, speed));
		this.setSize(20);
	}
	SnakeBody(Circle ball){
		this.setView(ball);
		this.setVelocity(new Point2D(0, speed));
		this.setSize(20);
	}
	
	public void update() {
		view.setTranslateX(view.getTranslateX()+velocity.getX());
		view.setTranslateY(view.getTranslateY()+speed);
		Circle c=(Circle)view;
		Xcor=c.getCenterX()+c.getTranslateX();
		Ycor=c.getCenterY()+c.getTranslateY();
		if(text!=null) {
		text.setTranslateX(view.getTranslateX()-5+velocity.getX()+getSize()/2);
		text.setTranslateY(view.getTranslateY()-15+speed+getSize()/2);
		}
		
		
	}
	
	
}

 /**
  * This class basically handles all the parameters and features of the bullets in the game.
  * This extends the abstract class tokens and add the new features specific to the Bullets. 
  * @author Sezal
  *
  */
 class Bullet extends Tokens{
	Bullet(){
		super(new Circle(5,5,5,Color.BROWN));
		this.setSize(10);
	}
	
	public void update() {
		view.setTranslateX(view.getTranslateX()+velocity.getX());
		view.setTranslateY(view.getTranslateY()+velocity.getY());
		Bounds boundsInScene = view.localToScene(view.getBoundsInLocal());
		
		Xcor=boundsInScene.getMinX();
		
		Ycor=boundsInScene.getMinY();
		if(text!=null) {
		text.setTranslateX(view.getTranslateX()+velocity.getX());
		text.setTranslateY(view.getTranslateY()+velocity.getY());
		}
		
		
	}
}

 /**
  * This class basically handles all the parameters and features of the Wall in the game.
  * This extends the abstract class tokens and add the new features specific to the Wall. 
  * @author Sezal
  *
  */
 class Wall extends Tokens{
	Wall(){
		
		super(new Circle(5,5,5,Color.BROWN));
		Random rand=new Random();
		Rectangle r=new Rectangle(rand.nextInt(Width-20)+10,-200,10,300);
		
		this.setVelocity(new Point2D(0, speed));
		this.setView(r);
		r.setFill(Color.WHITE);
		r.setArcWidth(30.0); 
		r.setArcHeight(20.0);  
		r.setStrokeWidth(3.5);
		r.setStroke(Color.BLACK);
	}
}

 /**
  * This class basically handles all the parameters and features of the Magnet in the game.
  * This extends the abstract class tokens and add the new features specific to the Magnet. 
  * @author Sezal
  *
  */

 class Magnet extends Tokens{
	Magnet(){
		
		super(new Circle(8,8,8,Color.AQUAMARINE.darker()));
		Image Magnet=new Image("/application/28NT.gif");
		ImageView MagnetImage=new ImageView(Magnet);
		MagnetImage.setScaleX(0.2);
		MagnetImage.setScaleY(0.2);
		this.setView(MagnetImage);
		this.setVelocity(new Point2D(0, speed));
		this.setSize(16);
	}
}

 /**
  * This class basically handles all the parameters and features of the Shield in the game.
  * This extends the abstract class tokens and add the new features specific to the Shield. 
  * @author Sezal
  *
  */
 class Sheild extends Tokens{
	Sheild(){
		super(new Circle(8,8,8,Color.BLANCHEDALMOND.darker()));
		Image Sheild=new Image("/application/shield-medieval-shield-on-a-black-background-drawing_csp22191709.jpg");
		ImageView ShieldImage=new ImageView(Sheild);
		ShieldImage.setScaleX(0.2);
		ShieldImage.setScaleY(0.2);
		this.setView(ShieldImage);
		this.setVelocity(new Point2D(0, speed));
		this.setSize(15);
	}
}
 

 /**
  * This class contains the entire snake body of the game. 
  * @author Pranjal
  *
  */
class Snake{
	
	private List<Tokens> snakes;
	private List<Tokens> Walls;
	private List<Tokens> AllObjects;
	Pane root;
	private int Width;
	
	/**
	 * Constructor
	 * @param root
	 * @param walls
	 * @param Width
	 * @param AllObjects
	 */
	public Snake(Pane root,List<Tokens> walls,int Width,List<Tokens> AllObjects) {
		this.AllObjects=AllObjects;
		this.Width=Width;
		 Walls=walls;
		 snakes=new ArrayList<>();
		 this.root=root;
	}
	public List<Tokens> getSnakes(){
		return snakes;
	}

	/**
	 * This function to set the length of the snake when deseialization takes place. 
	 * @param snakelength
	 * @param Xcor
	 */
	public void deserealizedAdd(int snakelength,int Xcor) {
		int counter=0;
		for(counter=0;counter<snakelength;counter++) {
			SnakeBody s=new SnakeBody();
			s.getView().setTranslateX(Xcor);
			s.getView().setTranslateY(400 + s.getSize()*counter);
			snakes.add(s);
			AllObjects.add(s);
			root.getChildren().add(s.getView());
		}
	}
	
	/**
	 * This function is used to set the length at the start of the game. 
	 * @param number
	 */
	public void snakeStartAdd(int number) {
		for(int u=0;u<number;u++) {
			snakes.add(new SnakeBody());
			addTokens(snakes.get(u), 300, 400 + u*(snakes.get(u).getSize()));
			AllObjects.add(snakes.get(u));
		}
	}

	/**
	 * This function is called when the length of the snake changes. 
	 * i.e. when increases or decreases. and checks if the length of the snake is zero 
	 * then, ends tha game. 
	 * @param number
	 * @throws RanoutOfLength
	 */
	public void changeLength(int number) throws  RanoutOfLength {
		
		if(number>0) {
			for(int u=0;u<number;u++) {
				snakes.add(new SnakeBody());
				addTokens(snakes.get(snakes.size()-1), snakes.get(snakes.size()-2).getView().getTranslateX(), snakes.get(snakes.size()-2).getView().getTranslateY() + (snakes.get(0).getSize()));

			}
		}
		else {
			for(int u=0;u<Math.abs(number);u++) {
				if(snakes.size()==0) {
					throw new RanoutOfLength("Game Over");
				}
				else {
				root.getChildren().remove(snakes.get(snakes.size()-1).getView());
				snakes.remove(snakes.size()-1);
				}
				if(snakes.size()==0) {
					throw new RanoutOfLength("Game Over");
				}
				
				
			}
		}
		
	}

	/**
	 * This function adds all its parameters to the Screen. 
	 * @param object
	 * @param x
	 * @param y
	 */
	public void addTokens(Tokens object, double x, double y) {
		object.getView().setTranslateX(x);
		object.getView().setTranslateY(y);
		root.getChildren().add(object.getView());
		if(object.getText()!=null)
		root.getChildren().add(object.getText());
		
	
	
	}
	
	
	/**
	 * This function takes care of the Movement of the snake.
	 * It is called when the keyboard key of left is pressed. 
	 * It shifts the snake position by a 5 to the left. 
	 */
	
	public void Left() {
		Tokens snake1=new SnakeBody();
		snake1.getView().setTranslateX(snakes.get(0).getView().getTranslateX()-5);
			if(  snake1.getView().getTranslateX()<=snake1.getSize()+10) {
				return;
			}
		
		for(int u=0;u<Walls.size();u++) {
			for(Tokens snake:snakes) {
				if(snake.isCollide(Walls.get(u)) ) {
					return;
				}
				}
		}
		for(int u=0;u<snakes.size();u++) {
			Tokens snake=snakes.get(u);
			snake.getView().setTranslateX(snake.getView().getTranslateX()-20);

		}

		
	}

	/**
	 * This function takes care of the Movement of the snake.
	 * It is called when the keyboard key of right is pressed. 
	 * It shifts the snake position by a 5 to the right. 
	 */
	public void Right() {
		Tokens snake1=new SnakeBody();
		snake1.getView().setTranslateX(snakes.get(0).getView().getTranslateX()+20);
		if(  snake1.getView().getTranslateX()>=Width-snake1.getSize()-10) {
			return;
		}
		for(int u=0;u<Walls.size();u++) {
			for(Tokens snake:snakes) {
				if(snake.isCollide(Walls.get(u)) ) {
					return;
				}
				}
		}
		for(int u=0;u<snakes.size();u++) {
			Tokens snake=snakes.get(u);
			snake.getView().setTranslateX(snake.getView().getTranslateX()+20);

		}


	}

	/**
	 * This function takes care of the Movement of the snake that is done by the Mouse pad. 
	 * All the left and right movement that the user does using the mouse pad is detected and 
	 * perfomed. 
	 * @param number
	 */
	public void Move(double number) {
		Tokens snake2=new SnakeBody();
		if(number<=snake2.getSize()-10 || number+5>=Width-5-snake2.getSize()) {
			return;
		}
		for(int u=0;u<Walls.size();u++) {
			for(Tokens snake:snakes) {
				if(snake.isCollide(Walls.get(u)) ) {
				return;
			}
			}
		}
		for(Tokens snake:snakes) {
			snake.getView().setTranslateX(number);
		}
		//iv.setTranslateX(number);
	}

}