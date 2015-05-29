import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

  /*  
   * Known issues:
   * 
   */

public class Numbreaka {
  
  private static GameFrame gameFrame;
  private static final GameOptions gameOptions = new GameOptions();
  private ArrayList<HighScore> highScores;
  private boolean gameOver = false;
  private int currentNumber = 1;
  private int gridSquaresFilled = 0;
  private int score = 0;
  
  public static void main (String[] args) {    
    Numbreaka numbreaka = new Numbreaka();
    numbreaka.go();
  }
  
  private void go() {
    formatFont();
    highScores = loadHighScores();
    gameFrame = new GameFrame(this, gameOptions);
  }
  
  private void formatFont() {
    // Enables anti-aliased text
    System.setProperty("awt.useSystemAAFontSettings","on");
    System.setProperty("swing.aatext", "true");
  }
  
  @SuppressWarnings("unchecked")
  private ArrayList<HighScore> loadHighScores() {
    ArrayList<HighScore> highScores = null;
    try
    {
       FileInputStream fileIn = new FileInputStream("highScores.ser");
       ObjectInputStream in = new ObjectInputStream(fileIn);
       highScores = (ArrayList<HighScore>) in.readObject();
       in.close();
       fileIn.close();
    } catch(FileNotFoundException fnfe) {
      // No previous high scores; create new List
      return new ArrayList<HighScore>();
    } catch(IOException e) {
       e.printStackTrace();
       System.exit(0);
    } catch(ClassNotFoundException c) {
      c.printStackTrace();
      System.exit(0);
    } 
    return highScores;
  }
  
  private void saveHighScores() {
    try {
     FileOutputStream fileOut = new FileOutputStream("highScores.ser");
     ObjectOutputStream out = new ObjectOutputStream(fileOut);
     out.writeObject(highScores);
     out.close();
     fileOut.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public void resetHighScores() {
    highScores = new ArrayList<HighScore>();
  }

  public String getHighScore(int number) {
    int index = number - 1;
    if (highScores.size() >= number) {
      return highScores.get(index).toString();
    } else {
      return "";
    }
  }

  public void resetGame() {
    gameFrame.resetGame();
    gridSquaresFilled = 0;
    currentNumber = 1;
    gameFrame.getLeftTitleBox().setText(Integer.toString(currentNumber));
    gameFrame.displayMainMenu();
    gameOver = false;
  }  

  
  // Sum each grid square
  private int calculateScore() {
    int finalScore = 0;
    
    GridSquare[][] gridSquares = gameFrame.getGridSquares();
    for (GridSquare[] gridSquareArray : gridSquares) {
      for (GridSquare gs : gridSquareArray) {
        finalScore += gs.getValue();
      }
    }
    return finalScore;
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
   
  public void checkIfGameOver() {
    if (getGridSquaresFilled() == (gameOptions.getNumberOfGridSquares())) {
      gameOver = true;
      score = calculateScore();
      boolean isNewHighScore = checkIfNewHighScore(score);
      gameFrame.displayFinalScorePopup(score, isNewHighScore);
      if (isNewHighScore) {
        addToHighScores(score);
      }
    }
  }
  
  private void addToHighScores(int score) {
    String initials = requestInitials();
    HighScore newHighScore = new HighScore(initials, score);
    insertNewHighScore(newHighScore);
  }
  
  private String requestInitials() {
    String initials = null;
    do {
      initials = JOptionPane.showInputDialog(gameFrame, "Enter three initials:");
    } while (initials != null && (!initials.matches("[A-Za-z]+") || initials.length() != 3));
    
    if (initials == null) {
      return "";
    } else {
      return initials.toUpperCase();
    }
  }
  
  private void insertNewHighScore(HighScore newHighScore) {
    int index = determineInsertionIndex(newHighScore.getScore());
    if (index == -1) {
      System.out.println("Problem with switch statement.");
      System.exit(0);
    } else {
      highScores.add(index, newHighScore);
      if (highScores.size() > 3) {
        // Trim to 3
        highScores = (ArrayList<HighScore>) highScores.subList(0, 3);
      }
    }
  }
  
  private int determineInsertionIndex(int newScore) {
    int insertionIndex = 0;
    for (int i = 0; i < highScores.size(); i++) {
      if (newScore < highScores.get(i).getScore()) {
        insertionIndex++;
      } else {
        break;
      }
    }
    return insertionIndex;
  }
  
  public void processGridSquareInteraction(GridSquare gs) {
    breakSquare(gs);
    checkNeighbors(gs);
    incrementCurrentNumber();
    gameFrame.updateHelper();
    checkIfGameOver();
  }
  
  private void breakSquare(GridSquare gs) {
    gs.breakSquare();
    if (gs.isEmpty()) {
      incrementGridSquaresFilled();
    } else {
      gs.clear();
    }
  }
  
  private void checkNeighbors(GridSquare gs) {
    for (GridSquare.Neighbor neighbor : GridSquare.Neighbor.values()) {
      GridSquare neighboringGridSquare = gs.getGridSquare(neighbor);
      
      if (neighboringGridSquare != null && !neighboringGridSquare.isBroken()) {
        if (neighboringGridSquare.isEmpty()) {
          fillNeighboringGridSquare(neighboringGridSquare, currentNumber);
          incrementGridSquaresFilled();
        } else {
          int value = calculateValue(neighboringGridSquare);
          fillNeighboringGridSquare(neighboringGridSquare, value);
        }
      }
    }
  }
  
  private boolean checkIfNewHighScore(int possibleHighScore) {
    if (highScores.size() < 3) {
      return true;
    } else {
      int lastIndex = highScores.size() - 1;
      return possibleHighScore > highScores.get(lastIndex).getScore();
    }
  }
  
  // TODO Do power-up calculations here?
  private int calculateValue(GridSquare gs) {
    return gs.getValue() + currentNumber;
  }
  
  private void fillNeighboringGridSquare(GridSquare gs, int value) {
    gs.setText(Integer.toString(value));
  }
  
  public void quit() {
    saveHighScores();
    System.exit(0);
  }
  
  private static class HighScore implements Serializable {
    private static final long serialVersionUID = -5794159217693346419L;
    private final static String DEFAULT_INITIALS = "AAA";
    private final String initials;
    private final int score;
    
    HighScore(String initials, int score) {
      if (initials.equals("")) {
        this.initials = DEFAULT_INITIALS;
      } else {
        this.initials = initials;  
      }
      this.score = score;
    }
    
    public String getInitials() {
      return initials;
    }
    
    public int getScore() {
      return score;
    }
    
    @Override
    public String toString() {
      return initials + " " + score;
    }
  }
}
