import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.border.LineBorder;

  /*  
   * Known issues:
   *   - Change highscores from TreeMap to -> insert something here
   */

public class Numbreaka {
  
  // Declares instance variables
  private boolean gameOver = false;
  private boolean isHighScore;
  private int currentNumber = 1;
  private final int gridX = 5;
  private final int gridY = 5;
  private int gridSquaresFilled = 0;
  private final int numberOfGridSquares = gridX*gridY;
  private int score = 0;
  private int currentHighScore = 0;
  private GameFrame gameFrame;
  private final TreeMap<Integer, String> highScores = new TreeMap<Integer, String>();
  private LineBorder blackLineBorder = new LineBorder(Color.BLACK);
  private Preferences preferences;

  public static void main (String[] args) {    
    Numbreaka numbreaka = new Numbreaka();
    numbreaka.go();
  }

  private void go() {
    formatFont();
    getPreferences();
    gameFrame = new GameFrame(this, gridX, gridY);
  }
  
  private void formatFont() {
    // Enables anti-aliased text
    System.setProperty("awt.useSystemAAFontSettings","on");
    System.setProperty("swing.aatext", "true");
  }
  
  private void getPreferences() {
    preferences = Preferences.userRoot().node(this.getClass().getName());
    
    // Reads highscores from preferences
    int firstScore = preferences.getInt("firstScore", -1);
    int secondScore = preferences.getInt("secondScore", -2);
    int thirdScore = preferences.getInt("thirdScore", -3);
    String firstInitials = preferences.get("firstInitials", "AAA");
    String secondInitials = preferences.get("secondInitials", "AAA");
    String thirdInitials = preferences.get("thirdInitials", "AAA");
    
    // Sets highscores from preferences
    highScores.put(firstScore, firstInitials);
    highScores.put(secondScore, secondInitials);
    highScores.put(thirdScore, thirdInitials);
  }
  
  public void setPreferences(String firstInitials, int firstScore, String secondInitials, int secondScore, String thirdInitials, int thirdScore) {
      preferences.put("firstInitials", firstInitials);
      preferences.putInt("firstScore", firstScore);
      preferences.put("secondInitials", secondInitials);
      preferences.putInt("secondScore", secondScore);
      preferences.put("thirdInitials", thirdInitials);
      preferences.putInt("thirdScore", thirdScore);
  }
  
  // Resets game to initial conditions when 'r' is pressed
  public void resetGame() {
    gameFrame.reset();
    gridSquaresFilled = 0;
    currentNumber = 1;
    gameFrame.getLeftTitleBox().setText(Integer.toString(currentNumber));
    gameFrame.displayMainMenu();
    gameOver = false;
  }  

  
  // Calculates final score by summing each grid square
  private int calculateScore() {
    int sum = 0;
    
    GridSquare[][] gridSquares = gameFrame.getGridSquares();
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        sum = gridSquares[i][j].getValue() + sum;
      }
    }
    return sum;
  }

  // Checks if score should be in top 3 and puts in if so
  public void processScore(int score, String initials) {
    if (score >= highScores.firstKey()) {
      isHighScore = true;
      highScores.remove(highScores.firstKey());
    }
    highScores.put(score, initials);
  }
  
  public int getCurrentHighScore() {
    return currentHighScore;
  }
  
  public TreeMap<Integer, String> getHighScores() {
    return highScores;
  }
  
  public boolean getGameOver() {
    return gameOver;
  }
  
  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }
  
  public void incrementGridSquaresFilled() {
    gridSquaresFilled++;
  }
  
  public void incrementCurrentNumber() {
    currentNumber++;
  }
  
  public int getGridSquaresFilled() {
    return gridSquaresFilled;
  }
  
  public int getCurrentNumber() {
    return currentNumber;
  }
  
  public void setHighScores(String firstInitials, int firstScore, String secondInitials, int secondScore, String thirdInitials, int thirdScore) {
    setPreferences(firstInitials, firstScore, secondInitials, secondScore, thirdInitials, thirdScore);
  }
  
  public LineBorder getLineBorder() {
    return blackLineBorder;
  }

  public void setLineBorder(LineBorder lineBorder) {
    this.blackLineBorder = lineBorder;
  }
  
  public int getNumberOfGridSquares() {
    return numberOfGridSquares;
  }
  
  public void checkIfGameOver() {
    if (getGridSquaresFilled() == (numberOfGridSquares)) {
      gameOver = true;
      gameFrame.highlightNumbers();
      score = calculateScore();
      processScore(score, "JLM");
      gameFrame.displayFinalScorePopup(score, isHighScore);
      isHighScore = false;
    }
  }
  
  public void resetHighScores() {
    String defaultInitials = "AAA";
    int defaultScore1 = -1;
    int defaultScore2 = -2;
    int defaultScore3 = -3;
    
    highScores.clear();
    highScores.put(defaultScore1, "AAA");
    highScores.put(defaultScore2, "AAA");
    highScores.put(defaultScore3, "AAA");
    this.setPreferences(defaultInitials, defaultScore1, defaultInitials, defaultScore2, defaultInitials, defaultScore3);
  }
}
