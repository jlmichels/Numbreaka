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

public class Numbreaka implements MouseListener {
  
  // Declares instance variables
  private boolean gameOver = false;
  private boolean isHighScore;
  private final Color gameBackgroundColor = new Color(202, 221, 221);
  private final Color highlightColor = new Color(134, 39, 39);
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

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (e.getSource() instanceof JLabel){
      JLabel temp = (JLabel) e.getSource();
      if (temp.getText().equals("START")) {
        temp.setForeground(highlightColor);
      } else if (temp.getText().equals("QUIT")) {
        temp.setForeground(highlightColor);
      } else if (temp.getText().equals("HIGH SCORES")) {
        temp.setForeground(highlightColor);
      } else if (temp.getText().equals("RETRY")) {
        temp.setForeground(highlightColor);
      } else if (temp.getText().equals("Return to Main Menu")) {
        temp.setForeground(highlightColor);
      }
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (e.getSource() instanceof JLabel){
      JLabel temp = (JLabel) e.getSource();
      if (temp.getText().equals("START")) {
        temp.setForeground(Color.DARK_GRAY);
      } else if (temp.getText().equals("QUIT")) {
        temp.setForeground(Color.DARK_GRAY);
      } else if (temp.getText().equals("HIGH SCORES")) {
        temp.setForeground(Color.DARK_GRAY);
      } else if (temp.getText().equals("RETRY")) {
        temp.setForeground(Color.BLACK);
      } else if (temp.getText().equals("Return to Main Menu")) {
        temp.setForeground(Color.BLACK);
      }
    }
  }

  // Todo: Split into separate mouselisteners for grid squares and menu items
  @Override
  public void mousePressed(MouseEvent e) {
    if (!gameOver) {
      
      // Won't need this is using a separate MouseListener
      if (e.getSource() instanceof GridSquare) {
        GridSquare gs = (GridSquare) e.getSource();
        
        // If grid square is not broken, break it and check surrounding grid squares
        // If grid square is broken, ignore mouse press
        if (!gs.isBroken()) {
          
          // Break it and increment gridSquareFilled if empty, else clear number
          gs.breakIt();
          if (gs.getText().equals("")) {
            gridSquaresFilled++;
          } else {
            gs.setText("");
          }
                  
          // If grid square above is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareUp() != null && !gs.getGridSquareUp().isBroken()) {
            if (gs.getGridSquareUp().getText().equals("")) {
              gs.getGridSquareUp().setText(Integer.toString(currentNumber));
              gridSquaresFilled++;
            } else {
              gs.getGridSquareUp().setText(Integer.toString(gs.getGridSquareUp().getValue() + currentNumber));
            }
          }
          
          // If grid square to right is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareRight() != null && !gs.getGridSquareRight().isBroken()) {
            if (gs.getGridSquareRight().getText().equals("")) {
              gs.getGridSquareRight().setText(Integer.toString(currentNumber));
              gridSquaresFilled++;
            } else {
              gs.getGridSquareRight().setText(Integer.toString(gs.getGridSquareRight().getValue() + currentNumber));
            }
          }
          
          // If grid square to left is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareLeft() != null && !gs.getGridSquareLeft().isBroken()) {
            if (gs.getGridSquareLeft().getText().equals("")) {
              gs.getGridSquareLeft().setText(Integer.toString(currentNumber));
              gridSquaresFilled++;
            } else {
              gs.getGridSquareLeft().setText(Integer.toString(gs.getGridSquareLeft().getValue() + currentNumber));
            }
          }
          
          // If grid square below is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareDown() != null && !gs.getGridSquareDown().isBroken()) {
            if (gs.getGridSquareDown().getText().equals("")) {
              gs.getGridSquareDown().setText(Integer.toString(currentNumber));
              gridSquaresFilled++;
            } else {
              gs.getGridSquareDown().setText(Integer.toString(gs.getGridSquareDown().getValue() + currentNumber));
            }
          }
      
          // Increment current number
          currentNumber++;
          
          // Update helper
          gameFrame.getLeftTitleBox().setText(Integer.toString(currentNumber));
          
          // Check if game over by comparing gridSquaresFilled to total number of grid squares
          if (gridSquaresFilled == (numberOfGridSquares)) {
            gameOver = true;
            gameFrame.highlightNumbers();
            score = calculateScore();
            processScore(score, "JLM");
            gameFrame.displayFinalScorePopup(score, isHighScore);
            isHighScore = false;
          }
        }
      } 
    }
    
    // Process JLabels
    if (e.getSource() instanceof JLabel) {
      // Change start menu buttons to new class extending JLabel?
      // Make a separate mouse listener to handle this stuff?
      JLabel temp = (JLabel) e.getSource();
      if (temp.getText().equals("START")) {
        gameFrame.displayGameWindow();
      } else if (temp.getText().equals("QUIT")) {
        System.exit(0);
      } else if (temp.getText().equals("HIGH SCORES")) {
        gameFrame.displayHighScoresScreen();
      } else if (temp.getText().equals("RETRY")) {
        gameFrame.getGameTitle().setForeground(highlightColor);
        resetGame();
        gameFrame.displayGameWindow();
      } else if (temp.getText().equals("NUMBREAKA")) {
        gameFrame.getGameTitle().setForeground(highlightColor);
        resetGame();
        gameFrame.displayMainMenu();
      } else if (temp.getText().equals("Return to Main Menu")) {
        gameFrame.displayMainMenu();
      }
    }
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
  
  @Override
  public void mouseReleased(MouseEvent e) {
    if (e.getSource() instanceof JLabel) {
      JLabel temp = (JLabel) e.getSource();
      if (temp.getText().equals("RETRY")) {
        gameFrame.getGameTitle().setForeground(Color.BLACK);
      } else if (temp.getText().equals("NUMBREAKA")) {
        gameFrame.getGameTitle().setForeground(Color.BLACK);
      }
    }
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

  public Color getGameBackgroundColor() {
    return gameBackgroundColor;
  }
  
  public Color getHighlightColor() {
    return highlightColor;
  }
  
  public TreeMap<Integer, String> getHighScores() {
    return highScores;
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
