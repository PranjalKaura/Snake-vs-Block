
package application;





import java.awt.MouseInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This is the main class which handles the entire game. 
 * It handles the snakeGame object as well as the leaderboard object. 
 * @author Sezal
 *
 */
public final class Main extends Application implements EventHandler<ActionEvent>,Serializable{

	private static final long serialVersionUID = 1L;
	List<Tokens> snakes=new ArrayList<>();
	private int random=0;
	private int counter=0;
	private int counterResume=0;
	
	private Tokens obj=null;
	private Text text1,text2,text3;
	private Text score,highscore;
	private Scene mainPage;
	private Snakegame newGame;
	private Leaderboard leaderBoard;
	private Button start2;
	private Button Resume;
	private Button Leaderboard;
	private Stage primaryStage;
	private AnimationTimer timer;
	private int prevScore=0;
	private MediaPlayer mediaPlayer2;
	
	/**
	 * This Function returns the prev score of the player
	 * @return Integer
	 */
	public int getPrevScore() {
		return prevScore;
	}
	/**
	 * This function sets the prev score of the player
	 * @param prevScore
	 */
	public void setPrevScore(int prevScore) {
		this.prevScore = prevScore;
	}
	
	/**
	 * this function is called when we launch the object.
	 * @param Stage
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		primaryStage.setResizable(false);
		BorderPane root = new BorderPane();
			int widthcheck=5;
			int Heightcheck=30;
			HBox topmenu=new HBox();
			BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(10, 10, 10, 10));
			root.setBackground(new Background(backgroundFill));
			for(int u=0;u<7;u++) {
				snakes.add(new SnakeBody(new Circle(10,10,10,Color.YELLOW)));
				snakes.get(u).getView().setTranslateX(450);
				snakes.get(u).getView().setTranslateY(150 + u*20);
				root.getChildren().addAll(snakes.get(u).getView());
			}
			

			leaderBoard=new Leaderboard(mainPage);
			
			
			 text1=new Text("Snake");
			text1.setFont(Font.font ("Centauri",60));
			text1.setTranslateX(200-4*widthcheck);
			text1.setTranslateY(100-Heightcheck);
			text1.setStrokeWidth(1.5); 
			text1.setFill(Color.WHITE);
			text1.setTextAlignment(TextAlignment.CENTER);
			
		
			
			 text2=new Text("Vs");
			text2.setFont(Font.font ("Centauri" ,FontWeight.EXTRA_BOLD ,50));
			text2.setFill(Color.WHITE);  
			text2.setStrokeWidth(2); 
			text2.setStroke(Color.BLUE);
			text2.setTranslateX(250-4*widthcheck);
			text2.setTranslateY(200-Heightcheck-10);
			text2.setTextAlignment(TextAlignment.CENTER);
			
			 text3=new Text("Blocks");
			text3.setFont(Font.font ("Centauri",60));
			text3.setTranslateX(200-4*widthcheck);
			text3.setTranslateY(300-Heightcheck-20);
			text3.setFill(Color.WHITE);
			text3.setStrokeWidth(1.5); 
			text3.setTextAlignment(TextAlignment.CENTER);
			
			String musicFile="Background.mp3";
			Media sound = new Media(new File(musicFile).toURI().toString());
			mediaPlayer2 = new MediaPlayer(sound);
			mediaPlayer2.setCycleCount(Integer.MAX_VALUE);
			mediaPlayer2.setVolume(0.15);
			mediaPlayer2.play();
			
			 obj=new Ball();
			 obj.setView(new Circle(10,10,10,Color.WHITE));
			obj.text=new Text();
			obj.getText().setText("7");
			obj.getView().setTranslateX(270-5*widthcheck);
			obj.getView().setTranslateY(350-Heightcheck-30);
			obj.getText().setTranslateX(275-5*widthcheck);
			obj.getText().setTranslateY(340-Heightcheck-30);
			obj.getText().setFont(Font.font ("Centauri",FontWeight.EXTRA_BOLD,16));
			obj.getText().setFill(Color.WHITE);
			
		
			 start2=new Button("Start");
			BackgroundFill BackgroundFillbutton = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0, 0, 0, 0));
			start2.setFont(Font.font ("Bradley Hand ITC",FontWeight.BOLD ,20));
			start2.setTextFill(Color.BLACK);
			start2.setBackground(new Background(BackgroundFillbutton));
			start2.setOnAction(this);
			start2.setOnMouseMoved(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.CHARTREUSE, new CornerRadii(10), new Insets(0, 0, 0, 0));
					Background b=new Background(BackgroundFillbuttonred);
					start2.setBackground(b);
					
				}
			});
	
//			
		 Leaderboard=new Button("LeaderBoard");
			Leaderboard.setFont(Font.font ("Bradley Hand ITC",FontWeight.BOLD ,20));
			Leaderboard.setTextFill(Color.BLACK);
			Leaderboard.setBackground(new Background(BackgroundFillbutton));
			Leaderboard.setOnAction(this);
			Leaderboard.setOnMouseMoved(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.CHARTREUSE, new CornerRadii(10), new Insets(0, 0, 0, 0));
					Background b=new Background(BackgroundFillbuttonred);
					Leaderboard.setBackground(b);
					
				}
			});
	

			
			Image Hscore=new Image("/application/highscore1.jpg");
			ImageView v1=new ImageView(Hscore);
			v1.setTranslateX(130-widthcheck);
			v1.setTranslateY(380-2*Heightcheck);
			v1.setFitWidth(170);
			v1.setFitHeight(100);
			
			Image Score=new Image("/application/tenor.gif");
			ImageView v2=new ImageView(Score);
			v2.setTranslateX(130-widthcheck);
			v2.setTranslateY(390);
			v2.setFitWidth(150);
			v2.setFitHeight(100);
			root.getChildren().add(v2);
			
			
			 score = new Text(Integer.toString(prevScore));
			score.setFont(Font.font ("Lucida Handwriting" ,FontWeight.EXTRA_BOLD ,40));
			score.setFill(Color.WHITE);  
			score.setStrokeWidth(1); 
			score.setStroke(Color.BLUE.darker().darker());
			score.setTranslateX(300);
			score.setTranslateY(470);
			root.getChildren().add(score);
			
		 highscore = new Text(Integer.toString(leaderBoard.getHighScore()));
			highscore.setFont(Font.font ("Lucida Handwriting" ,FontWeight.EXTRA_BOLD ,40));
			highscore.setFill(Color.WHITE);  
			highscore.setStrokeWidth(1); 
			highscore.setStroke(Color.BLUE.darker().darker());
			highscore.setTranslateX(310-widthcheck);
			highscore.setTranslateY(445-2*Heightcheck);
			
			
			 Resume=new Button("Resume");
			Resume.setFont(Font.font ("Bradley Hand ITC",FontWeight.BOLD ,20));
			Resume.setTextFill(Color.BLACK);
			Resume.setBackground(new Background(BackgroundFillbutton));
			Resume.setOnAction(this);
			Resume.setOnMouseMoved(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.CHARTREUSE, new CornerRadii(10), new Insets(0, 0, 0, 0));
					Background b=new Background(BackgroundFillbuttonred);
					Resume.setBackground(b);
					
				}
			});
	
			
			
			topmenu.getChildren().addAll(start2,Leaderboard,Resume);
			topmenu.setTranslateX(120-10*widthcheck);
			topmenu.setTranslateY(500);
			topmenu.setAlignment(Pos.BOTTOM_LEFT);
			topmenu.setSpacing(15);
			topmenu.spacingProperty();

			root.setPadding(new Insets(10, 10, 10, 10));
			root.setTop(topmenu);
			root.getChildren().addAll(text1,text2,text3,obj.getView(),obj.getText(),highscore,v1);
			  timer = new AnimationTimer() {
					
					@Override
					public void handle(long now) {
						try {
							onUpdate();
						} catch(ConcurrentModificationException e) {
							
						}
						
					}
				};
				timer.start();
				
				
			
			
			
			Scene scene = new Scene(root,530,600);
			mainPage=scene;
			primaryStage.setScene(scene);
			primaryStage.show();
		
	}
	
	/**
	 * This function os called when the screen need to be updated. 
	 * This changes the view of the screen as the games proceeds
	 */
	protected void onUpdate() {
		// TODO Auto-generated method stub
		Color[] arr= {Color.CORAL,Color.DARKMAGENTA,Color.DEEPPINK,Color.PINK.brighter(),Color.RED.brighter(),Color.BLUE,Color.LAVENDER,Color.LIGHTGREEN,Color.ORANGE,Color.CHARTREUSE,Color.BLUEVIOLET,Color.CYAN};
		Random rand=new Random();
		 highscore.setText(Integer.toString(leaderBoard.getHighScore()));
		score.setText(Integer.toString(prevScore));
		
		if(counterResume!=0) {
			counterResume++;
			if(counterResume>80) {
				counterResume=0;
				BackgroundFill BackgroundFillbutton = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0, 0, 0, 0));
				Background b=new Background(BackgroundFillbutton);
				Resume.setBackground(b);
			}
		}
		if(!Leaderboard.hoverProperty().getValue()) {
			BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0, 0, 0, 0));
			Background b=new Background(BackgroundFillbuttonred);
			Leaderboard.setBackground(b);
		}
		if(!start2.hoverProperty().getValue()) {
			BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0, 0, 0, 0));
			Background b=new Background(BackgroundFillbuttonred);
			start2.setBackground(b);
		}
		if(!Resume.hoverProperty().getValue()) {
			BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0, 0, 0, 0));
			Background b=new Background(BackgroundFillbuttonred);
			Resume.setBackground(b);
		}
		
	
		
		
		
		
		for(Tokens snake:snakes) {
			snake.getView().setTranslateY(snake.getView().getTranslateY()-5);
			if(snake.getView().getTranslateY()<10) {
				Circle s=(Circle)snake.getView();
				s.setFill(arr[random]);
				
				text1.setStroke(arr[random]);
				text2.setStroke(arr[random]);
				text3.setStroke(arr[random]);
				score.setStroke(arr[random]);
				highscore.setStroke(arr[random]);
				
				snake.setView(s);
				snake.getView().setTranslateY(630);
				
				obj.getText().setFill(arr[random]);
				Circle s2=(Circle)obj.getView();
				s2.setFill(arr[random]);
				obj.setView(s2);
				
				counter++;
				
			}
			if(counter>=7) {
				counter=0;
				random=rand.nextInt(12);
				
				
				
				
			}
		}
		
	}

	/**
	 * This is called when we run the file
	 * this is the first method which runs the entire game. 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * This deserializes The list of Token on the current scene.  
	 * @return 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static List<Tokens> Deserializetokens() throws IOException, ClassNotFoundException {
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
	 * This states the snake game. creates its object and add its to the screen.
	 * @param Resume
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void StartSnakeGame(boolean Resume) throws ClassNotFoundException, IOException {
		
	
		 newGame=new Snakegame(mainPage,primaryStage,this,leaderBoard);
		 
		 if(Resume) {
			 newGame.setResumeCheck(true);
		 }
		
			Scene sceneL=null;
			
			try {
				sceneL = newGame.start();
				
			} catch (Exception e) {
				
			}
			primaryStage.setScene(sceneL);
			primaryStage.show();
	}
	
	/**
	 * This resumes the game. saves the current state of the game and heads off to the main screen.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void ResumeGame() throws ClassNotFoundException, IOException {
		try {
			if(Deserializetokens()==null) {
				BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.RED, new CornerRadii(10), new Insets(0, 0, 0, 0));
				Background b=new Background(BackgroundFillbuttonred);
				Resume.setBackground(b);
				counterResume++;
				return;
			}
		} catch (ClassNotFoundException | IOException  |NullPointerException e1) {
			// TODO Auto-generated catch block
			BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.RED, new CornerRadii(10), new Insets(0, 0, 0, 0));
			Background b=new Background(BackgroundFillbuttonred);
			Resume.setBackground(b);
			counterResume++;
			return;
		}
		
		
		
		 newGame=new Snakegame(mainPage,primaryStage,this,leaderBoard);
		 
		 
			 newGame.setResumeCheck(true);
		 
		
			Scene sceneL=null;
			
			try {
				sceneL = newGame.start();
				
			} catch (Exception e) {
				
			}
			primaryStage.setScene(sceneL);
			primaryStage.show();
	}
	
	/**
	 * Shows the leaderboard. 
	 * creates a leaderboard object and adds it to the stage. 
	 */
	private void showLeadernBoard() {
		leaderBoard=new Leaderboard(mainPage);
		Scene sceneL=null;
		
		sceneL = new Scene(leaderBoard.start(primaryStage),530,600);
		
		primaryStage.setScene(sceneL);
		primaryStage.show();
	}
/**
 * This is called when we click a button on the main screen or perform some action which the event handler need to handle. 
 */
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==start2) {
			try {
				StartSnakeGame(false);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
			}
		}
		else if(event.getSource()==Leaderboard) {
			showLeadernBoard();
		}
		else {
			try {
				ResumeGame();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
			}
			
		}
		
	}
}