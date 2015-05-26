import java.util.TreeMap;
import java.util.prefs.Preferences;

  /*  
   * Known issues:
   *   - TODO Change highscores from TreeMap to -> insert something here
   *   - TODO Holding 'r' keeps resetting High Scores title to gray instead of letting it stay highlighted like other menu items
   */

public class Numbreaka {
  
//  private static final TreeMap<Integer, String> HIGH_SCORES = new TreeMap<Integer, String>();
  private static GameFrame gameFrame;
  private static final GameOptions gameOptions = new GameOptions();
  private boolean gameOver = false;
  private boolean isHighScore;
  private int currentNumber = 1;
  private int gridSquaresFilled = 0;
  private int score = 0;
//  private int currentHighScore = 0;
//  private Preferences preferences;

  public static void main (String[] args) {    
    Numbreaka numbreaka = new Numbreaka();
    numbreaka.go();
  }
  
  private void go() {
    formatFont();
//    getPreferences();
    gameFrame = new GameFrame(this, gameOptions);
  }
  
  private void formatFont() {
    // Enables anti-aliased text
    System.setProperty("awt.useSystemAAFontSettings","on");
    System.setProperty("swing.aatext", "true");
  }
  
//  private void getPreferences() {
//    preferences = Preferences.userRoot().node(this.getClass().getName());
//    
//    // Reads highscores from preferences
//    int firstScore = preferences.getInt("firstScore", -1);
//    int secondScore = preferences.getInt("secondScore", -2);
//    int thirdScore = preferences.getInt("thirdScore", -3);
//    String firstInitials = preferences.get("firstInitials", "AAA");
//    String secondInitials = preferences.get("secondInitials", "AAA");
//    String thirdInitials = preferences.get("thirdInitials", "AAA");
//    
//    // Sets highscores from preferences
//    HIGH_SCORES.put(firstScore, firstInitials);
//    HIGH_SCORES.put(secondScore, secondInitials);
//    HIGH_SCORES.put(thirdScore, thirdInitials);
//  }
  
  // TODO Only set on game close or error?
//  public void setPreferences(String firstInitials, int firstScore, String secondInitials, int secondScore, String thirdInitials, int thirdScore) {
//      preferences.put("firstInitials", firstInitials);
//      preferences.putInt("firstScore", firstScore);
//      preferences.put("secondInitials", secondInitials);
//      preferences.putInt("secondScore", secondScore);
//      preferences.put("thirdInitials", thirdInitials);
//      preferences.putInt("thirdScore", thirdScore);
//  }
  
  public void resetGame() {
    gameFrame.resetGame();
    gridSquaresFilled = 0;
    currentNumber = 1;
    gameFrame.getLeftTitleBox().setText(Integer.toString(currentNumber));
    gameFrame.displayMainMenu();
    gameOver = false;
  }  

  
  // TODO Gridsquares should be created and handled in Numbreaka?
  // Calculates final score by summing each grid square
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

  // Checks if score should be in top 3 and puts in if so
  public void processScore(int score, String initials) {
//    if (score >= HIGH_SCORES.firstKey()) {
//      isHighScore = true;
//      HIGH_SCORES.remove(HIGH_SCORES.firstKey());
//    }
//    HIGH_SCORES.put(score, initials);
  }
  
//  public int getCurrentHighScore() {
//    return currentHighScore;
//  }
  
//  public TreeMap<Integer, String> getHighScores() {
//    return HIGH_SCORES;
//  }
  
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
  
//  public void setHighScores(String firstInitials, int firstScore, String secondInitials, int secondScore, String thirdInitials, int thirdScore) {
//    setPreferences(firstInitials, firstScore, secondInitials, secondScore, thirdInitials, thirdScore);
//  }
  
  public void checkIfGameOver() {
    if (getGridSquaresFilled() == (gameOptions.getNumberOfGridSquares())) {
      gameOver = true;
//      gameFrame.highlightNumbers();
      score = calculateScore();
      processScore(score, "JLM");
      gameFrame.displayFinalScorePopup(score, isHighScore);
      isHighScore = false;
    }
  }
  
//  public void resetHighScores() {
//    String defaultInitials = "AAA";
//    int defaultScore1 = -1;
//    int defaultScore2 = -2;
//    int defaultScore3 = -3;
//    
//    HIGH_SCORES.clear();
//    HIGH_SCORES.put(defaultScore1, "AAA");
//    HIGH_SCORES.put(defaultScore2, "AAA");
//    HIGH_SCORES.put(defaultScore3, "AAA");
////    this.setPreferences(defaultInitials, defaultScore1, defaultInitials, defaultScore2, defaultInitials, defaultScore3);
//  }
  
  public void processGridSquare(GridSquare gs) {
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
  
  // TODO Do power-up calculations here?
  private int calculateValue(GridSquare gs) {
    return gs.getValue() + currentNumber;
  }
  
  private void fillNeighboringGridSquare(GridSquare gs, int value) {
    gs.setText(Integer.toString(value));
  }
}
