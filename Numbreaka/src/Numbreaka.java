import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

  /*  
   * Known issues:
   *   - TODO Change highscores from TreeMap to -> insert something here (use preferences?)
   */

public class Numbreaka {
  
  private static GameFrame gameFrame;
  private static final GameOptions gameOptions = new GameOptions();
  private List<HighScore> highScores;
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
  
  private List<HighScore> loadHighScores() {
    return new ArrayList<HighScore>();
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
        highScores = highScores.subList(0, 3);
      }
    }
  }
  
  private int determineInsertionIndex(int newScore) {
    int size = highScores.size();
    switch (size) {
      case 3: return determineIndexOfThree(newScore);
      case 2: return determineIndexOfTwo(newScore);
      case 1: return determineIndexOfOne(newScore);
      case 0: return 0;
      default: System.out.println("Invalid high score size.");
               System.exit(0);
               return -1; // Unreachable?
    }
  }
  
  private int determineIndexOfThree(int newScore) {
    int firstScore = highScores.get(0).getScore();
    int secondScore = highScores.get(1).getScore();
    int thirdScore = highScores.get(2).getScore();
    if (newScore > firstScore) {
      return 0;
    } else {
      if (newScore > secondScore) {
        return 1;
      } else {
        if (newScore > thirdScore) {
          return 2;
        } else {
          return 3;
        }
      }
    }
  }
  
  private int determineIndexOfTwo(int newScore) {
    int firstScore = highScores.get(0).getScore();
    int secondScore = highScores.get(1).getScore();
    if (newScore > firstScore) {
      return 0;
    } else {
      if (newScore > secondScore) {
        return 1;
      } else {
        return 2;
      }
    }
  }
  
  private int determineIndexOfOne(int newScore) {
    int onlyScore = highScores.get(0).getScore();
    if (newScore > onlyScore) {
      return 0;
    } else {
      return 1;
    }
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
    if (highScores.size() == 0) {
      return true;
    } else {
      int lastIndex = highScores.size() - 1;
      return possibleHighScore > highScores.get(lastIndex).getScore();
    }
  }
  
  public String getHighScore(int number) {
    int index = number - 1;
    if (highScores.size() >= number) {
      return highScores.get(index).toString();
    } else {
      return "";
    }
  }
  
  // TODO Do power-up calculations here?
  private int calculateValue(GridSquare gs) {
    return gs.getValue() + currentNumber;
  }
  
  private void fillNeighboringGridSquare(GridSquare gs, int value) {
    gs.setText(Integer.toString(value));
  }
  
  private class HighScore {
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
