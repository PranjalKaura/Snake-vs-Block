
package application;





import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import javafx.scene.shape.*;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * This class handles the our LeaderBoard
 * Its object adds and deletes the score from our board. 
 * Its a serializable object which saves the highest 10 scores 
 * of the player. 
 * @author Sezal
 *
 */
public final class Leaderboard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Tokens> snakes=new ArrayList<>();
	private int HighScore=0;
	private int random=0;
	private int counter=0;
	private int speed=5;
	private boolean turnLeft=false;
	private boolean turnRight=false;
	private boolean turnUp=true;
	private boolean turnDown=false;
	private Rectangle r;
	private Text text1,text2,text3;
	Scene mainPage;
	public static List<Score> Scores=new ArrayList<>();
	private List<Text> TextDate=new ArrayList<>();
	private List<Text> TextIndex=new ArrayList<>();
	private List<Text> TextScore=new ArrayList<>();
	private Button Back;
	
	/**
	 * CONSTRUCTOR
	 * @param mainPage
	 */
	public Leaderboard(Scene mainPage) {
		// TODO Auto-generated constructor stub
		this.mainPage=mainPage;
		try {
			Scores=Deserialize();
		} catch (ClassNotFoundException | IOException  | NullPointerException e) {
			// TODO Auto-generated catch block
		}
		Collections.sort(Scores, new ScoreComparator());
		if(Scores.size()!=0)
		HighScore=Scores.get(Scores.size()-1).getPoints();
	}
	public int getHighScore() {
		return HighScore;
	}
	
	/**
	 * This function serializes the top 10 Scores of the game. 
	 * @param c2
	 * @throws IOException
	 */
	public static void Serialize(List<Score> c2 ) throws IOException  {
		ObjectOutputStream out	=	null;
		try {
			out=new	ObjectOutputStream	(new FileOutputStream("OutScoreofGame.txt"));
			out.writeObject(c2);
			
		}
		finally {
			out.close();
		}
		
		
	}
	
	/**
	 * This function deserializes the TOP 10 scores of the game. 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Score> Deserialize() throws IOException, ClassNotFoundException {
		ObjectInputStream	in	=	null;	
		List<Score> c3=null;
		try {
			in=new ObjectInputStream(new FileInputStream("OutScoreofGame.txt"));
			 c3=(List<Score>)in.readObject();	
		}
		finally{
			in.close();
		}
		return c3;
		
	}
	
	/**
	 * This function launches the leaderboard when it is called. 
	 * @param primaryStage
	 * @return
	 */
	public BorderPane start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
			int widthcheck=40;
			HBox topmenu=new HBox();
			try {
				if(Deserialize()!=null)
				Scores=Deserialize();
			} catch (ClassNotFoundException | IOException  | NullPointerException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			BackgroundFill BackgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(10, 10, 10, 10));
			root.setBackground(new Background(BackgroundFill));
			
			text1=new Text("LeaderBoard");
			text1.setFont(Font.font ("Lucida Handwriting",50));
			text1.setTranslateX(130-widthcheck);
			text1.setTranslateY(100);
			text1.setFill(Color.WHITE);
			text1.setStrokeWidth(1);
			text1.setTextAlignment(TextAlignment.CENTER);
			Collections.sort(Scores, new ScoreComparator());
			for(int u=0;u<10;u++) {
				snakes.add(new SnakeBody(new Circle(10,10,10,Color.YELLOW)));
				snakes.get(u).getView().setTranslateX(490);
				snakes.get(u).getView().setTranslateY(360 + u*20);
				root.getChildren().addAll(snakes.get(u).getView());
			}
			for(int u=0;u<10;u++) {
				snakes.add(new SnakeBody(new Circle(10,10,10,Color.YELLOW)));
				snakes.get(u+10).getView().setTranslateX(20);
				snakes.get(u+10).getView().setTranslateY(200 - u*20);
				root.getChildren().addAll(snakes.get(u+10).getView());
			}
			
			BackgroundFill BackgroundFillbutton = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(2, 2, 2, 2));

			 Back=new Button("Back ");
			Back.setFont(Font.font ("Robota",FontWeight.BOLD ,20));
			//Leaderboard.setTextFill(Color.BLACK);
			Back.setBackground(new Background(BackgroundFillbutton));
			
			Back.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stubs
					primaryStage.setScene(mainPage);
					primaryStage.show();
				}
				
				
			});
			Back.setOnMouseMoved(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.CHARTREUSE, new CornerRadii(10), new Insets(0, 0, 0, 0));
					Background b=new Background(BackgroundFillbuttonred);
					Back.setBackground(b);
					
				}
			});
		
			
		
		
			topmenu.getChildren().addAll(Back);
			topmenu.setTranslateX(250-widthcheck);
			topmenu.setTranslateY(500);
			topmenu.setAlignment(Pos.BOTTOM_LEFT);
			topmenu.setSpacing(15);
			topmenu.spacingProperty();
			
			 r=new Rectangle(330,120,10,400);
			r.setFill(Color.WHITE);
			r.setArcWidth(30.0); 
			r.setArcHeight(20.0);  
			r.setStrokeWidth(3.5);
			
			
			text2=new Text("Date-Time");
			text2.setFont(Font.font ("Lucida Handwriting",20));
			text2.setTranslateX(180-widthcheck);
			text2.setTranslateY(150);
			text2.setFill(Color.WHITE);
			text2.setStrokeWidth(0.6);
			text2.setTextAlignment(TextAlignment.CENTER);
			
			text3=new Text("Score");
			text3.setFont(Font.font ("Lucida Handwriting",20));
			text3.setStrokeWidth(0.6);
			text3.setTranslateX(420-widthcheck);
			text3.setTranslateY(150);
			text3.setFill(Color.WHITE);
			text3.setTextAlignment(TextAlignment.CENTER);
			for(int u=0;u<10;u++) {
				TextDate.add(new Text());
				TextScore.add(new Text());
				TextIndex.add(new Text());
			}
			
			
			for(int i=0;i<=Scores.size()-1;i++) {
				TextDate.get(i).setText(Scores.get(Scores.size()-i-1).getDate());
				TextScore.get(i).setText(Integer.toString(Scores.get(Scores.size()-1-i).getPoints()));
				TextIndex.get(i).setText(Integer.toString(i+1));
				
				
				TextDate.get(i).setFont(Font.font ("Lucida Handwriting",20));
				TextScore.get(i).setFont(Font.font ("Lucida Handwriting",20));
				TextIndex.get(i).setFont(Font.font ("Lucida Handwriting",FontWeight.EXTRA_BOLD,30));
				TextDate.get(i).setStrokeWidth(0.3);
				TextIndex.get(i).setStrokeWidth(0.3);
				TextScore.get(i).setStrokeWidth(0.3);
				TextDate.get(i).setFill(Color.WHITE);
				TextScore.get(i).setFill(Color.WHITE);
				TextIndex.get(i).setFill(Color.WHITE);
			}
			int difference=0;
			for(int i=0;i<Scores.size();i++) {
				TextIndex.get(i).setTranslateX(20);
				TextIndex.get(i).setTranslateY(190 + difference);
				
				 //Setting the properties of the rectangle 
				TextDate.get(i).setTranslateX(70);
				TextDate.get(i).setTranslateY(190 + difference);
				 
				  TextScore.get(i).setTranslateX(380);
				  TextScore.get(i).setTranslateY(190+difference);
				 
				 
				 difference=difference+30;
			}




			root.setPadding(new Insets(10, 10, 10, 10));
			root.setTop(topmenu);
			root.getChildren().addAll(text1,r,text2,text3);
			
			for(int i=0;i<Scores.size();i++) {
				root.getChildren().addAll(TextDate.get(i),TextIndex.get(i),TextScore.get(i));
			}
			 AnimationTimer timer = new AnimationTimer() {
					
					@Override
					public void handle(long now) {
						try {
							onUpdate();
						} catch(ConcurrentModificationException e) {
							
						}
						
					}
				};
				timer.start();
			return root;
		
	}
	protected void turnLeft(Tokens obj) {
		obj.getView().setTranslateX(obj.getView().getTranslateX()-speed);
		turnDown=false;
		turnUp=false;
		turnLeft=true;
		turnRight=false;
	}
	protected void turnRight(Tokens obj) {
		obj.getView().setTranslateX(obj.getView().getTranslateX()+speed);
		turnDown=false;
		turnUp=false;
		turnLeft=false;
		turnRight=true;
	}
	protected void turnUp(Tokens obj) {
		obj.getView().setTranslateY(obj.getView().getTranslateY()-speed);
		turnDown=false;
		turnUp=true;
		turnLeft=false;
		turnRight=false;
	}
	protected void turnDown(Tokens obj) {
		obj.getView().setTranslateY(obj.getView().getTranslateY()+speed);
		turnDown=true;
		turnUp=false;
		turnLeft=false;
		turnRight=false;
	}
	
	public void setScore(int score,String date) {
	Score sc=new Score(score, date);
	try {
		Scores=Deserialize();
	} catch (ClassNotFoundException | IOException  | NullPointerException e1) {
		// TODO Auto-generated catch block
	}
	if(Scores.size()<10) {
	Scores.add(sc);
	
	}
	else {
		if(Scores.get(0).getPoints()<score) {
			Scores.remove(0);
			Scores.add(0, sc);
		}
		Collections.sort(Scores, new ScoreComparator());
		
	}
	try {
		
		Serialize(Scores);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HighScore=Scores.get(Scores.size()-1).getPoints();
	
}
	
	
	/**
	 * This function updates the current screen according to the game state. 
	 */
	protected void onUpdate() {
		// TODO Auto-generated method stub
		Color[] arr= {Color.DEEPSKYBLUE,Color.CORAL,Color.DARKMAGENTA,Color.DEEPPINK,Color.PINK.brighter(),Color.RED.brighter(),Color.BLUE,Color.LAVENDER,Color.LIGHTGREEN,Color.ORANGE,Color.CHARTREUSE,Color.BLUEVIOLET,Color.CYAN};
		Random rand=new Random();
		
		if(!Back.hoverProperty().getValue()) {
			BackgroundFill BackgroundFillbuttonred = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0, 0, 0, 0));
			Background b=new Background(BackgroundFillbuttonred);
			Back.setBackground(b);
		}
		
		for(Tokens snake:snakes) {
			if(snake.getView().getTranslateY()<11) {
				if(snake.getView().getTranslateX()>488) {
				Circle s=(Circle)snake.getView();
				s.setFill(arr[random]);
				text1.setStroke(arr[random]);
				text2.setStroke(arr[random]);
				text3.setStroke(arr[random]);
				
				for(int i=0;i<TextDate.size();i++) {
					TextDate.get(i).setStroke(arr[random]);
					TextScore.get(i).setStroke(arr[random]);
					TextIndex.get(i).setStroke(arr[random]);
				}
				r.setStroke(arr[random]);
				snake.setView(s);
				turnLeft(snake);
				counter++;
				}
				else if(20<snake.getView().getTranslateX()&& snake.getView().getTranslateX()<488) {
					turnLeft(snake);
				}
				else if(snake.getView().getTranslateX()<=20) {
					Circle s=(Circle)snake.getView();
					s.setFill(arr[random]);
					//r.setFill(arr[random]);
					text1.setStroke(arr[random]);
					text2.setStroke(arr[random]);
					text3.setStroke(arr[random]);
					for(int i=0;i<TextDate.size();i++) {
						TextDate.get(i).setStroke(arr[random]);
						TextScore.get(i).setStroke(arr[random]);
						TextIndex.get(i).setStroke(arr[random]);
					}
					r.setStroke(arr[random]);
					snake.setView(s);
					turnDown(snake);
					
					
					counter++;
				}
				
			}
			else if(snake.getView().getTranslateY()>=11 && snake.getView().getTranslateY()<580) {
				if(snake.getView().getTranslateX()<50) {
					turnDown(snake);
				}
				else if(snake.getView().getTranslateX()>450) {
					turnUp(snake);
				}
			}
			else if(snake.getView().getTranslateY()>=580) {
				if(snake.getView().getTranslateX()>488) {
					Circle s=(Circle)snake.getView();
					s.setFill(arr[random]);
					text1.setStroke(arr[random]);
					text2.setStroke(arr[random]);
					text3.setStroke(arr[random]);
					for(int i=0;i<TextDate.size();i++) {
						TextDate.get(i).setStroke(arr[random]);
						TextScore.get(i).setStroke(arr[random]);
						TextIndex.get(i).setStroke(arr[random]);
					}
					r.setStroke(arr[random]);
					snake.setView(s);
					turnUp(snake);
					counter++;
					}
					else if(20<snake.getView().getTranslateX()&& snake.getView().getTranslateX()<=488) {
						turnRight(snake);
					}
					else if(snake.getView().getTranslateX()<=20) {
						Circle s=(Circle)snake.getView();
						s.setFill(arr[random]);
						text1.setStroke(arr[random]);
						text2.setStroke(arr[random]);
						text3.setStroke(arr[random]);
						for(int i=0;i<TextDate.size();i++) {
							TextDate.get(i).setStroke(arr[random]);
							TextScore.get(i).setStroke(arr[random]);
							TextIndex.get(i).setStroke(arr[random]);
						}
						r.setStroke(arr[random]);
						snake.setView(s);
						turnRight(snake);
						
						counter++;
					}
				
			}
			if(counter>=snakes.size()) {
				counter=0;
				random=rand.nextInt(12);
			}
		}
		
	}


}