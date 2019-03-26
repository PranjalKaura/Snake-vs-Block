package application;

import java.io.Serializable;
import java.util.Comparator;
/**
 * This class sets the score of the snake. 
 * @author Sezal
 *
 */
public class Score implements Serializable{

	private static final long serialVersionUID = 1L;
	private final int Points;
	private final String Date;
	public Score(int score,String date) {
		// TODO Auto-generated constructor stub
		Points=score;
		Date=date;
	}
	
	public int getPoints() {
		return Points;
	}

	public String getDate() {
		return Date;
	}

	
}

/**
 * This class is used to compare the top 10 scores of the player. 
 * @author Sezal
 *
 */
class ScoreComparator implements Comparator<Score>{

	@Override
	public int compare(Score o1, Score o2) {
		// TODO Auto-generated method stub
		return o1.getPoints()-o2.getPoints();
	}
	
}
