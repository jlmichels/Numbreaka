  /*  
   * Known issues:
   *   - TODO Change highscores from TreeMap to -> insert something here (use preferences?)
   *   - TODO Holding 'r' keeps resetting High Scores title to gray instead of letting it stay highlighted like other menu items
   */

public class Numbreaka {
  
  private static GameFrame gameFrame;
  private static final GameOptions gameOptions = new GameOptions();
  private boolean gameOver = false;
  private boolean isHighScore;
  private int currentNumber = 1;
  private int gridSquaresFilled = 0;
  private int score = 0;

  public static void main (String[] args) {    
    Numbreaka numbreaka = new Numbreaka();
    numbreaka.go();
  }
  
  private void go() {
    formatFont();
    gameFrame = new GameFrame(this, gameOptions);
  }
  
  private void formatFont() {
    // Enables anti-aliased text
    System.setProperty("awt.useSystemAAFontSettings","on");
    System.setProperty("swing.aatext", "true");
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
      gameFrame.displayFinalScorePopup(score, isHighScore);
      isHighScore = false;
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
  
  // TODO Do power-up calculations here?
  private int calculateValue(GridSquare gs) {
    return gs.getValue() + currentNumber;
  }
  
  private void fillNeighboringGridSquare(GridSquare gs, int value) {
    gs.setText(Integer.toString(value));
  }
}
