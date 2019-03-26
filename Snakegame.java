package application;


import javafx.geometry.*;


import java.io.File;

import java.beans.Transient;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;
import java.util.List;
import java.util.Random;


import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.image.Image;

/**
 * This class controls the main part of the game.
 * The object of this class controls every part of the game i.e. the snake, 
 * tokens, background and animation. This class object serialized and 
 * de-serialized according to the code. 
 * @author Pranjal
 *
 */

public final class Snakegame implements Serializable{

	private static final long serialVersionUID = 1L;
	private transient Pane root;
	private Snake snake;
	private List<Tokens> bullets;
	private List<Tokens> Blocks;
	private List<Tokens> Balls;
	private List<Tokens> Bombs;
	private List<Tokens> Magnets;
	private List<Tokens> Sheilds;
	private List<Tokens> Walls;
	private List<Tokens> DeadBlocks;
	private List<Tokens> AllObjects;
	private List<Rectangle> Charge;
	private  int score=0;
	private transient AnimationTimer timer;
	private int speed=2,Height=700,Width=530;
	private int ticks=0;
	public transient Random rand;
	private transient ChoiceBox<String> dropdown;
	private transient Text text2, textScore;
	private boolean start;
	private int[] Arrx=null;
	private boolean[] ArrBoolean=null;
	private boolean Magnetalive,SheildAlive,ResumeCheck;
	private int MagnetC,SheildC=0,charge=0,chargeC;
	private transient Color[] arrC= {Color.DARKORCHID,Color.AQUA,Color.CHARTREUSE,Color.CORNFLOWERBLUE,Color.DEEPPINK};
	private transient Scene mainPage;
	private transient Stage primaryStage;
	private Main main;
	private Leaderboard leaderboard;
	private Media sound;
	private MediaPlayer mediaPlayer;

	/**
	 * PARAMETERIZED CONSTRUCTOR of the snakegame class
	 * @param mainPage
	 * @param primaryStage
	 * @param main
	 * @param leaderboard
	 */
	public Snakegame(Scene mainPage,Stage primaryStage,Main main,Leaderboard leaderboard) {

		
		this.leaderboard=leaderboard;
		this.main=main;
		this.primaryStage=primaryStage;
		this.mainPage=mainPage;
		dropdown=new ChoiceBox<>();
		bullets=new ArrayList<>();
		Blocks=new ArrayList<>();
		Balls=new ArrayList<>();
		Bombs=new ArrayList<>();
		Magnets=new ArrayList<>();
		Sheilds=new ArrayList<>();
		Walls=new ArrayList<>();
		DeadBlocks=new ArrayList<>();
		AllObjects=new ArrayList<>();
		Charge=new ArrayList<>();
		rand=new Random();
		start=true;
		
		primaryStage.setOnCloseRequest(e->{
			try {
				Exit();
			} catch (InterruptedException | IOException e1) {
				
			}
		});
	}

	/**
	 * This function checks if the function is in resume state. ///////////////////////////////////////////////////////// 
	 * @return boolean value : if true then- ////////// false then- ///////////
	 */
	public boolean isResumeCheck() {
		return ResumeCheck;
	}

	/**
	 * If the user has called the resume button the it is true. else false
	 * @param resumeCheck 
	 * @return void
	 */
	public void setResumeCheck(boolean resumeCheck) {
		ResumeCheck = resumeCheck;
	}

	/**
	 * This function deserializes the Snake Speed and Score of the game. 
	 * @return Integer Array: defines the snake speed and score. 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int[] Deserialize() throws IOException, ClassNotFoundException {
		ObjectInputStream	in	=	null;	
		int[] c3=null;
		try {
			in=new ObjectInputStream(new FileInputStream("OutSnakeScoreandSpeed.txt"));
			 c3=(int[])in.readObject();	
		}
		finally{
			in.close();
		}
		return c3;
		
	}
	/**
	 * This Function deserializes the array that contains the boolean values of the magnet check and shield check. 
	 * at that current stage.
	 * @return List of Tokens
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static boolean[] Deserialize2() throws IOException, ClassNotFoundException {
		ObjectInputStream	in	=	null;	
		boolean[] c3=null;
		try {
			in=new ObjectInputStream(new FileInputStream("OutSnakeMagnetandSheildCheck.txt"));
			 c3=(boolean[])in.readObject();	
		}
		finally{
			in.close();
		}
		return c3;
		
	}

	/**
	 * This Function deserializes the List of tokens on the main screen of the game 
	 * at that current stage.
	 * @return List of Tokens
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Tokens> Deserializetokens() throws IOException, ClassNotFoundException {
		ObjectInputStream	in	=	null;	
		List<Tokens> c3=null;
		try {
			in=new ObjectInputStream(new FileInputStream("OutSnakeGame.txt"));
			 c3=(List<Tokens>)in.readObject();	
		}
		finally{
			in.close();
		}
		return c3;
		
	}

	/**
	 * This function serializes the Snake Score and speed . 
	 * @param c2
	 * @throws IOException
	 */
	public static void Serialize(int[] c2 ) throws IOException  {
		ObjectOutputStream out	=	null;
		try {
			out=new	ObjectOutputStream	(new FileOutputStream("OutSnakeScoreandSpeed.txt"));
			out.writeObject(c2);
			
		}
		finally {
			out.close();
		}
		
		
	}
	/**
	 * This Function deserializes the array that contains the boolean values of the magnet check and shield check. 
	 * @param c2
	 * @throws IOException
	 */
	public static void Serialize2(boolean[] c2 ) throws IOException  {
		ObjectOutputStream out	=	null;
		try {
			out=new	ObjectOutputStream	(new FileOutputStream("OutSnakeMagnetandSheildCheck.txt"));
			out.writeObject(c2);
			
		}
		finally {
			out.close();
		}
		
		
	}
	/**
	 * This function serializes the List of tokens on the main page of the game at that current moment. 
	 * @param c2
	 * @throws IOException
	 */
	public static void SerializeSnake(List<Tokens> c2 ) throws IOException  {
		ObjectOutputStream out	=	null;
		
		try {
			out=new	ObjectOutputStream	(new FileOutputStream("OutSnakeGame.txt"));
			out.writeObject(c2);
			
		}
		finally {
			out.close();
		}
	}

	/**
	 * This function creates the content of the game board. 
	 * If resume is hit, then it desealizes the previous board. if not then 
	 * it randomizes the position of tokens and their occurences and adds this content 
	 * to the main pane of the game. 
	 * @return //////////////////// what is parent
	 * 
	 */
	private Parent createContent() {
		root=new Pane();
		root.setPrefSize(Width, Height+70);
		snake=new Snake(root, Walls, Width, AllObjects);
		if(ResumeCheck) {
			List<Tokens> temp=new ArrayList<>();
			int[] temp2=new int[3];
			boolean[] temp3 = new boolean[2];
			try {
				temp=Deserializetokens();
				temp2=Deserialize();
				temp3=Deserialize2();
			}
			catch(NullPointerException e) {
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			score=temp2[0];
			speed=temp2[1];
			Magnetalive=temp3[0];
			SheildAlive=temp3[1];
			int snakelength=temp2[2];
			snake.deserealizedAdd(snakelength, temp2[3]);
			for(Tokens obj:temp) {
				 if(obj.getClass()==Blocks.class) {				
					Blocks s=new Blocks();
					s.getView().setTranslateX(obj.getXcor());
					s.getView().setTranslateY(obj.getYcor());
					Text txt=new Text(Integer.toString(obj.getValue()));
					txt.setFont(Font.font("Sans serif",FontWeight.EXTRA_BOLD,FontPosture.REGULAR,20));
					txt.setFill(Color.WHITE);
					s.setText(txt);
					s.getText().setTranslateX(obj.getXcor());
					s.getText().setTranslateY(obj.getYcor());
					Blocks.add(s);
					AllObjects.add(s);
					root.getChildren().add(s.getView());
					root.getChildren().add(s.getText());
				}
				else if(obj.getClass()==Bullet.class) {				
					Bullet s=new Bullet();
					s.getView().setTranslateX(obj.getXcor());
					s.getView().setTranslateY(obj.getYcor());
					bullets.add(s);
					root.getChildren().add(s.getView());
					AllObjects.add(s);
				}
				else if(obj.getClass()==Magnet.class) {				
					Magnet s=new Magnet();
					s.getView().setTranslateX(obj.getXcor());
					s.getView().setTranslateY(obj.getYcor());
					Magnets.add(s);
					root.getChildren().add(s.getView());
					AllObjects.add(s);
				}
				else if(obj.getClass()==Bomb.class) {				
					Bomb s=new Bomb();
					s.getView().setTranslateX(obj.getXcor());
					s.getView().setTranslateY(obj.getYcor());
					Bombs.add(s);
					root.getChildren().add(s.getView());
					AllObjects.add(s);
				}
				else if(obj.getClass()==Sheild.class) {				
					Sheild s=new Sheild();
					s.getView().setTranslateX(obj.getXcor());
					s.getView().setTranslateY(obj.getYcor());
					Sheilds.add(s);
					root.getChildren().add(s.getView());
					AllObjects.add(s);
				}
				else if(obj.getClass()==Ball.class) {
					Ball s=new Ball();
					s.getView().setTranslateX(obj.getXcor());
					s.getView().setTranslateY(obj.getYcor());
					Text text=new Text(Integer.toString(obj.getValue()));
					text.setFill(Color.ORANGE);
					text.setFont(Font.font("Sans serif",FontWeight.NORMAL,FontPosture.REGULAR,20));
					s.setText(text);
					s.getText().setTranslateX(obj.getXcor());
					s.getText().setTranslateY(obj.getYcor()-10);
					
					Balls.add(s);
					AllObjects.add(s);
					root.getChildren().add(s.getView());
					root.getChildren().add(s.getText());
				}		
			}
			
			for(Tokens obj:AllObjects) {
				obj.setSpeed(speed);
			}
		}
		
		Charge.add(new Rectangle(53,20,Color.CHARTREUSE));
		BackgroundFill BackgroundFillbutton = new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(10, 10, 10, 10));
		for(int u=1;u<9;u++) {
			Charge.add(new Rectangle(53,20,Color.CHARTREUSE));
			Charge.get(u).setTranslateX(53*(u));
			Charge.get(u).setTranslateY(Height+40);
			Charge.get(u).setArcHeight(15);
			Charge.get(u).setArcWidth(15);
			root.getChildren().add(Charge.get(u));
			Charge.get(u).setVisible(false);
			
		}
		Rectangle rec=new Rectangle(Width,10);
		rec.setTranslateX(0);
		rec.setTranslateY(Height + 30);
		rec.setFill(Color.WHITE);
		root.getChildren().add(rec);
		root.setBackground(new Background(BackgroundFillbutton));
		Blocks b=new Blocks();
		Arrx=new int[Width/b.getSize()];
		ArrBoolean=new boolean[Arrx.length];
		for(int u=0;u<Arrx.length;u++) {
			Arrx[u]=u*b.getSize()+u*2 + 5;
		}
		if(snake.getSnakes().size()==0) {
			snake.snakeStartAdd(7);
		}
		text2=new Text(Integer.toString(snake.getSnakes().size()));
		textScore=new Text(Integer.toString(score));
		 
		text2.setTranslateX(460);
		text2.setTranslateY(100);
		text2.setFill(Color.WHITE);
		text2.setFont(Font.font("Sans serif",FontWeight.BOLD,FontPosture.REGULAR,50));
		root.getChildren().add(text2);
		

		textScore.setTranslateX(35);
		textScore.setTranslateY(100);
		textScore.setFill(Color.WHITE);
		textScore.setFont(Font.font("Sans serif",FontWeight.EXTRA_BOLD,FontPosture.REGULAR,50));
		root.getChildren().add(textScore);

		dropdown.getItems().addAll("New Game","Exit");
		dropdown.setTranslateX(400);
		dropdown.setTranslateY(17);
		dropdown.setValue("Exit");
		root.getChildren().add(dropdown);
		
		
		
		
		timer=new AnimationTimer() {
			
		    @Override
			public void handle(long now) {		
					try {
						onUpdate();
					} 
					catch (NullPointerException | RanoutOfLength e) {
						try {
							Exit();
						} 
						catch (InterruptedException | IOException e1) {
							// TODO Auto-generated catch block
						}
					}
					catch(IndexOutOfBoundsException e) {
						try {
							Exit();
						} 
						catch (InterruptedException | IOException e1) {
							
						}
					}	
			}		
		};
		timer.start();
		return root;
	}

	/**
	 * This function is called when the user selects the EXIT button of the screen.
	 * It serializes all the tokens, snake and its features. 
	 * Adds and saves our score to the leaderboard.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void Exit() throws InterruptedException, IOException {
		if(snake.getSnakes().size()==0) {
		PlaySound("ExitSound.wav");
		PlaySound("ExitSound2.wav");
		SerializeSnake(null);
		Serialize(null);
		}
		else if(snake.getSnakes().size()!=0) {
		SerializeSnake(AllObjects);
		boolean[] serboolean=new boolean[2];
		serboolean[0]=Magnetalive;
		serboolean[1]=SheildAlive;
		Serialize2(serboolean);
		int[] ser=new int[4];
		ser[0]=score;
		ser[1]=speed;
		ser[2]=snake.getSnakes().size();
		ser[3]=(int)snake.getSnakes().get(0).getView().getTranslateX();
			
		
		Serialize(ser);
		}
		
		timer.stop();	
		main.setPrevScore(score);
		Date currentDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM//dd//yyyy HH:mm:ss");//there is a stop function in code. Modify that. 
		if(leaderboard==null) {
			leaderboard=new Leaderboard(mainPage);
		}
		leaderboard.setScore(score,sdf.format(currentDate));
		primaryStage.setScene(mainPage);
		
	}

	/**
	 * Charges the gun over a period of time. 
	 */
	protected void Charge() {
		if(charge!=8) {
			chargeC++;
			if(chargeC%50==0) {
				Charge.get(chargeC/50).setVisible(true);
				charge++;
				if(charge==8) {
					for(int u=1;u<=8;u++) {
						Rectangle rec=Charge.get(u);
						root.getChildren().remove(Charge.get(u));
						rec.setFill(Color.RED);
						root.getChildren().add(rec);						
					}
				}
			}	
		}
	}

	/**
	 * Checks if the magent is alive or not and changes the game accordingly. 
	 */
	private void MagnetCheck() {
		if(Magnetalive) {
			if(MagnetC%20==0) {
				int random=rand.nextInt(arrC.length);
				for(int u=0;u<snake.getSnakes().size();u++) {
					Circle c=(Circle)snake.getSnakes().get(u).getView();
					c.setFill(arrC[random]);
				}
			}
			MagnetC++;
			if(MagnetC>600) {
				for(int u=0;u<snake.getSnakes().size();u++) {
					Circle c=(Circle)snake.getSnakes().get(u).getView();
					c.setFill(Color.CHARTREUSE);
				}
				Ball.magnetAlive=false;
				Magnetalive=false;
				MagnetC=0;
			}
		}
	}

	/**
	 * Checks if the magent is alive or not and changes the game accordingly. 
	 */
	private void SheildCheck() {
		if(SheildAlive) {
			if(SheildC%20==0) {
				int random=rand.nextInt(arrC.length);
				for(int u=0;u<snake.getSnakes().size();u++) {
					Circle c=(Circle)snake.getSnakes().get(u).getView();
					c.setFill(arrC[random]);
				}
			}
			SheildC++;
			if(SheildC>333) {
				for(int u=0;u<snake.getSnakes().size();u++) {
					Circle c=(Circle)snake.getSnakes().get(u).getView();
					c.setFill(Color.CHARTREUSE);
				}
				SheildAlive=false;
				SheildC=0;
			}
		}
	}

	/**
	 * This function plays the sound of the file which is sent as parameters. 
	 * @param musicFile
	 */
	private void PlaySound(String musicFile) {
		sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
	
	/**
	 * Changes the speed of the snake according to the length of the snake.
	 */
	private void SnakeSpeed() {
		if(snake.getSnakes().size()>15) {
			for(Tokens obj:AllObjects) {			
				obj.setSpeed(2+((double)(snake.getSnakes().size()-15))/15);
				speed=(int)obj.getSpeed();
			}
		}
		else {
			for(Tokens obj:AllObjects) {
				obj.setSpeed(2);
			}
		}
	}

	/**
	 * This updates the Screen at every instant when the game is run. 
	 * @throws RanoutOfLength
	 */
	private void onUpdate() throws  RanoutOfLength {
		
		if(snake.getSnakes().size()%12==0) {
			speed++;
		}
		
		DeadBlocks.forEach(Tokens::update);
		MagnetCheck();
		Charge();
		SheildCheck();
		text2.setText(Integer.toString(snake.getSnakes().size()));
		textScore.setText(Integer.toString(score));
		
		for(Tokens bullet:bullets) {
			if(bullet.getView().getTranslateY()<-10) {
				bullet.setAlive(false);
				root.getChildren().remove(bullet.getView());
			}
			for(Tokens enemy:Blocks) {				
				if(bullet.isCollide(enemy)) {
					score+=Integer.parseInt(enemy.getText().getText());
					bullet.setAlive(false);
					enemy.setAlive(false);					
					for(int x=0;x<=enemy.getSize();x+=3) {
						for(int y=0;y<=enemy.getSize();y+=3) {
							BlockExtension deadBlock=new BlockExtension();
							deadBlock.getView().setTranslateX(enemy.getView().getTranslateX()+x);
							deadBlock.getView().setTranslateY(enemy.getView().getTranslateY()+y);
							Rectangle rec=(Rectangle)deadBlock.getView();
							rec.setFill(enemy.getColor());
							deadBlock.setView(rec);
							DeadBlocks.add(deadBlock);
							root.getChildren().add(deadBlock.getView());
							
						}
					}					
					root.getChildren().removeAll(bullet.getView(),enemy.getView(),enemy.getText());
						PlaySound("blockcrash.mp3");
				}
			}
		}
		
		for(Tokens enemy:Blocks) {
			Tokens snake1=snake.getSnakes().get(0) ;
			
			if(enemy.isCollide(snake1)) {				
				if(!SheildAlive) {
					if(Integer.parseInt(enemy.getText().getText())<=3) {						
						snake.changeLength(-Integer.parseInt(enemy.getText().getText()));
					}
					else {
						for(int u=Integer.parseInt(enemy.getText().getText())-1;u>=0;u--) {
							enemy.getText().setText(Integer.toString(u));
							root.getChildren().remove(enemy.getText());
							root.getChildren().add(enemy.getText());
							
							snake.changeLength(-1);
							
							for(int x=0;x<=12;x+=8) {
								for(int y=0;y<=12;y+=8) {
									int add=rand.nextInt(30)-15;
									SnakeExtension deadBlock=new SnakeExtension();
									
									deadBlock.getView().setTranslateX(snake.getSnakes().get(0).getView().getTranslateX()+add);
									deadBlock.getView().setTranslateY(snake.getSnakes().get(0).getView().getTranslateY());
									DeadBlocks.add(deadBlock);
									root.getChildren().add(deadBlock.getView());
									
								}
							}
							for(int u1=0;u1<10000;u1++) {
								System.out.println("");
							}
							score++;
							return;
						}
					}
				}
				
				PlaySound("blockcrash.mp3");
				enemy.setAlive(false);
				for(int x=0;x<=60;x+=3) {
					for(int y=0;y<=60;y+=3) {
						BlockExtension deadBlock=new BlockExtension();
						deadBlock.getView().setTranslateX(enemy.getView().getTranslateX()+x);
						deadBlock.getView().setTranslateY(enemy.getView().getTranslateY()+y);
						Rectangle rec=(Rectangle)deadBlock.getView();
						rec.setFill(enemy.getColor());
						deadBlock.setView(rec);
						DeadBlocks.add(deadBlock);
						root.getChildren().add(deadBlock.getView());
						
					}
				}
				root.getChildren().remove(enemy.getText());
				score+=Integer.parseInt(enemy.getText().getText());
				root.getChildren().remove(enemy.getView());
			}
			if(enemy.getView().getTranslateY() >Height- enemy.getSize()+5) {
				enemy.getView().setVisible(false);
				enemy.setAlive(false);
				root.getChildren().remove(enemy.getView());
				root.getChildren().remove(enemy.getText());
			}
		}
		
		for(Tokens Snake:DeadBlocks) {
			if(Snake.getView().getTranslateY()>Height -5 || Snake.getView().getTranslateY()<0|| Snake.getView().getTranslateX()>Width ||  Snake.getView().getTranslateX()<0) {
 
				root.getChildren().remove(Snake.getView());
				Snake.setAlive(false);
			}
		}
		for(Tokens DeadBlock:Bombs) {
			if(DeadBlock.getView().getTranslateY()>Height-DeadBlock.getSize()) {
				DeadBlock.getView().setVisible(false);
				root.getChildren().remove(DeadBlock.getView());
				DeadBlock.setAlive(false);
			}
		}
		for(Tokens Snake:snake.getSnakes()) {
			if(Snake.getView().getTranslateY()>Height) {
				Snake.getView().setVisible(false);
			}
		}
		for(Tokens ball:Balls) {
			Ball ball2=(Ball)ball;
			Tokens snake1=snake.getSnakes().get(0) ;
			
			
			if(ball.getView().getTranslateY()>Height-2*ball.getSize()) {
				root.getChildren().remove(ball.getView());
				root.getChildren().remove(ball.getText());
				ball.setAlive(false);
			}
			if(ball2.isCollide(snake1)) {
				PlaySound("coincound.mp3");
				snake.changeLength(Integer.parseInt(ball.getText().getText()));
				ball.setAlive(false);
				root.getChildren().remove(ball.getView());
				root.getChildren().remove(ball.getText());
				for(int x=0;x<=ball.getSize();x+=1) {
					for(int y=0;y<=ball.getSize();y+=1) {
						BallExtension deadBlock=new BallExtension();
						deadBlock.getView().setTranslateX(ball.getView().getTranslateX()+x);
						deadBlock.getView().setTranslateY(ball.getView().getTranslateY()+y);
						DeadBlocks.add(deadBlock);
						root.getChildren().add(deadBlock.getView());
						
					}
				}
				break;
			}			
		}
		
		for(Tokens magnet:Magnets) {
			Tokens snake1=snake.getSnakes().get(0) ;
			if(magnet.getView().getTranslateY()>Height-2*magnet.getSize()) {
				root.getChildren().remove(magnet.getView());
				magnet.setAlive(false);
			}
			if(snake1.isCollide(magnet)) {
				PlaySound("coincound.mp3");
				Ball.magnetAlive=true;
				MagnetC=0;
				root.getChildren().remove(magnet.getView());
				Magnetalive=true;
				magnet.setAlive(false);
				break;
			}
		}
		for(Tokens shield:Sheilds) {
			if(shield.getView().getTranslateY()>Height-2*shield.getSize()) {
				root.getChildren().remove(shield.getView());
				shield.getView().setVisible(false);
				shield.setAlive(false);
				SheildC=0;
			}
			Tokens snake1=snake.getSnakes().get(0) ;
			if(snake1.isCollide(shield)) {
				PlaySound("Shield.mp3");
				root.getChildren().remove(shield.getView());
				SheildAlive=true;
				shield.setAlive(false);
				break;
			}
		}
		
		for(Tokens wall:Walls) {
			if(wall.getView().getTranslateY()>Height-wall.getSize()) {
				wall.getView().setVisible(false);
				wall.setAlive(false);
				root.getChildren().remove(wall.getView());
			}
		}
		
		
		for(Tokens bomb:Bombs) {
			if(bomb.getView().getTranslateY()>Height-bomb.getSize()) {
				bomb.getView().setVisible(false);
				root.getChildren().remove(bomb.getView());
			}
			Tokens snake1=snake.getSnakes().get(0) ;
			Bomb bomb2=(Bomb)bomb;
			
			if(bomb2.isCollide(snake1)) {
				PlaySound("Explosion.mp3");
				bomb.setAlive(false);
				bomb2.setAlive(false);
				score+=bomb2.Clear(Blocks,root,DeadBlocks);
				
				root.getChildren().remove(bomb.getView());
				break;
			}
			
			
		}
		
		SnakeSpeed();
		
		
		Blocks.removeIf(Tokens::isDead);
		bullets.removeIf(Tokens::isDead);
		Balls.removeIf(Tokens::isDead);
		Bombs.removeIf(Tokens::isDead);
		Magnets.removeIf(Tokens::isDead);
		Sheilds.removeIf(Tokens::isDead);
		Walls.removeIf(Tokens::isDead);
		AllObjects.removeIf(Tokens::isDead);
		DeadBlocks.removeIf(Tokens::isDead);
		
		Blocks.forEach(Tokens::update);
		bullets.forEach(Tokens::update);
		Balls.forEach(Tokens::update);
		Bombs.forEach(Tokens::update);
		Walls.forEach(Tokens::update);
		Sheilds.forEach(Tokens::update);
		Magnets.forEach(Tokens::update);
		
		ticks++;
		if(ticks>150) {
			int x=rand.nextInt(7)+5;
			
			for(int u=0;u<x;u++) {
				int temp=rand.nextInt(Arrx.length-1);
				if(!ArrBoolean[temp]) {
					Blocks b=new Blocks();	
					if(u%2==0)
					b.getText().setText(Integer.toString(snake.getSnakes().size()+5));
					b.setValue(Integer.parseInt(b.getText().getText()));
					addBlocks(b, Arrx[temp]+12, -50);
					ArrBoolean[temp]=true;
				}
				if(!ArrBoolean[Arrx.length-temp-1]) {
					Blocks b=new Blocks();	
					if(u%2==0)
					b.getText().setText(Integer.toString(Math.abs(snake.getSnakes().size()-5)));
					b.setValue(Integer.parseInt(b.getText().getText()));
				addBlocks(b, Arrx[Arrx.length-temp-1]+12, -50);
				
				ArrBoolean[Arrx.length-temp-1]=true;
				}
			}

			ticks=0;
			ArrBoolean=new boolean[Arrx.length];
			
		}
		
		if(Math.random()<0.01) {
			Ball ball=new Ball();
			int flag=0;
			while(true) {
				ball.getView().setTranslateX(rand.nextInt(Width-30)+10);
				ball.getView().setTranslateY(-100);
				flag=0;
				for(Tokens enemy:AllObjects) {
					if(enemy.isCollide(ball)) {
						flag=1;
						break;
					}
				}
				if(flag==0) {
					addBalls(ball, ball.getView().getTranslateX(), ball.getView().getTranslateY());
					break;
				}
			}
		}
		
		if(Math.random()<0.001) {
			Sheild ball=new Sheild();
			int flag=0;
			while(true) {
				flag=0;
				ball.getView().setTranslateX(rand.nextInt(Width-50)+20);
				ball.getView().setTranslateY(-200);
				for(Tokens enemy:AllObjects) {
					if(enemy.isCollide(ball)) {						
						flag=1;
						break;
					}
				}
				if(flag==0) {
					addShileds(ball, ball.getView().getTranslateX(), ball.getView().getTranslateY());
					break;
				}
			}
		}
		
		if(Math.random()<0.005) {
				Wall wall=new Wall();
				wall.getView().setTranslateX(Arrx[rand.nextInt(Arrx.length)]);
				wall.getView().setTranslateY(-190);
				addWall(wall, wall.getView().getTranslateX(), wall.getView().getTranslateY());
		}
		
		if(Math.random()<0.001) {
			Bomb bomb=new Bomb();
			while(true) {
				int flag=0;
				bomb.getView().setTranslateX(rand.nextInt(Width-50)+20+10);
				bomb.getView().setTranslateY(-150);
				for(Tokens enemy:AllObjects) {
					if(enemy.isCollide(bomb)) {
						flag=1;
						break;
					}
				}
				if(flag==0) {
					addBombs(bomb, bomb.getView().getTranslateX(), bomb.getView().getTranslateY());
					break;
				}
			}
		}
		
		if(Math.random()<0.001) {
			Magnet magnet=new Magnet();
			while(true) {
				int flag=0;
				for(Tokens enemy:AllObjects) {
					magnet.getView().setTranslateX(rand.nextInt(Width-50)+20);
					magnet.getView().setTranslateY(-150);
					if(enemy.isCollide(magnet)) {
						
						flag=1;
						break;
					}
				}
				if(flag==0) {
					addMagnets(magnet, magnet.getView().getTranslateX(), magnet.getView().getTranslateY());
					break;
				}
			}
		}
	}
	

	/**
	 * This function addBalls to the Screen. 
	 * @param ball
	 * @param x
	 * @param y
	 */
	private void addBalls(Tokens ball,double x, double y ) {
		Balls.add(ball);
		AllObjects.add(ball);
		addTokens(ball, x, y);
	}
	
	/**
	 * This function addWalls to the Screen. 
	 * @param wall
	 * @param x
	 * @param y
	 */
	private void addWall(Tokens wall,double x, double y ) {
		Walls.add(wall);
		AllObjects.add(wall);
		addTokens(wall, x, y);
	}
	
	/**
	 * This function addShield to the Screen. 
	 * @param shield
	 * @param x
	 * @param y
	 */
	private void addShileds(Tokens shield,double x, double y ) {
		Sheilds.add(shield);
		AllObjects.add(shield);
		addTokens(shield, x, y);
	}
	
	/**
	 * This function addBombs to the Screen. 
	 * @param bomb
	 * @param x
	 * @param y
	 */
	private void addBombs(Tokens bomb,double x, double y ) {
		Bombs.add(bomb);
		AllObjects.add(bomb);
		addTokens(bomb, x, y);
	}
	/**
	 * This function addMagnets to the Screen. 
	 * @param magnet
	 * @param x
	 * @param y
	 */
	private void addMagnets(Tokens magnet,double x, double y ) {
		Magnets.add(magnet);
		AllObjects.add(magnet);
		addTokens(magnet, x, y);
	}
	
	/**
	 * This function addBullets to the Screen. 
	 * @param bullet
	 * @param x
	 * @param y
	 */
	private void addBullets(Tokens bullet,double x, double y ) {
		bullets.add(bullet);
		addTokens(bullet, x, y);
	}
	/**
	 * This function addBlocks to the Screen. 
	 * @param enemy
	 * @param x
	 * @param y
	 */
	private void addBlocks(Tokens enemy,double x, double y ) {
		Blocks.add(enemy);
		AllObjects.add(enemy);
		addTokens(enemy	, x, y);
	}

	
	/**
	 * This function adds all its parameters to the Screen. 
	 * @param object
	 * @param x
	 * @param y
	 */
	private void addTokens(Tokens object, double x, double y) {
		object.getView().setTranslateX(x);
		object.getView().setTranslateY(y);
		root.getChildren().add(object.getView());
		if(object.getText()!=null)
		root.getChildren().add(object.getText());
	}
	
	
	/**
	 * This function is called when SNAKEGAME. launch is called. 
	 * It creates the new Scene and adds it to the base.
	 * It calls the other function like createContent which then 
	 * make the game. 
	 * @return Scene
	 */
	public Scene start() {
		// TODO Auto-generated method stub
		Scene s=null;
		
		try {
		 s = new Scene(createContent());
		}
		catch (Exception e) {
			return mainPage;
		}
		rand=new Random();
		s.setOnKeyPressed(e ->{
			if(e.getCode()==KeyCode.LEFT) {
				snake.Left();
			}
			else if(e.getCode()==KeyCode.P) {
				if(start) {
					start=false;
				timer.stop();
				}
				else {
					start=true;
					timer.start();
				}
			}
			
			else if(e.getCode()==KeyCode.RIGHT) {
				snake.Right();
			}
			else if(e.getCode()==KeyCode.ENTER) {
				if(dropdown.getValue().compareTo("Exit")==0) {
					try {
						Exit();
					} catch (InterruptedException | IOException e1) {
						// TODO Auto-generated catch block
					}
				}
				else {
					try {
						timer.stop();
						main.setPrevScore(score);
						Date currentDate=new Date();
						SimpleDateFormat sdf=new SimpleDateFormat("MM//dd//yyyy HH:mm:ss");
						if(leaderboard==null) {
							leaderboard=new Leaderboard(mainPage);
						}
						leaderboard.setScore(score,sdf.format(currentDate));
						main.StartSnakeGame(false);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
					}
				}
				
			}
			else if(e.getCode()==KeyCode.F) {
				if(charge==8) {
					PlaySound("rocket whistle.mp3");
				Bullet bullet=new Bullet();
				bullet.setVelocity(new Point2D(0, -3));
				addBullets(bullet, snake.getSnakes().get(0).getView().getTranslateX(), snake.getSnakes().get(0).getView().getTranslateY());
				for(int u=1;u<9;u++) {
					
					Charge.get(u).setVisible(false);
					Charge.get(u).setFill(Color.CHARTREUSE);
				}
				charge=0;
				chargeC=0;
				}
				
			}
			
		});
		
		
		s.setOnMousePressed(e ->{
		 if(e.isSecondaryButtonDown()) {
				if(charge==8) {
					Bullet bullet=new Bullet();
					bullet.setVelocity(new Point2D(0, -3));
					PlaySound("rocket whistle.mp3");

					addBullets(bullet, snake.getSnakes().get(0).getView().getTranslateX(), snake.getSnakes().get(0).getView().getTranslateY());
					for(int u=1;u<9;u++) {
						
						Charge.get(u).setVisible(false);
						Charge.get(u).setFill(Color.CHARTREUSE);
					}
					charge=0;
					chargeC=0;
					}
			}
		}
				);
		
		
		s.setOnMouseDragged(e ->{
			if(e.isPrimaryButtonDown()) {
				double x=e.getSceneX();
				snake.Move(x);
				
				
			}
		}
				);
		
		return s;
	}
}