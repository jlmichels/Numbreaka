import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JOptionPane;

  /*  
   * Known issues:
   * gridSquaresFilled not updated correctly
   * 
   */

public class Numbreaka {
  
  private static GameFrame gameFrame;
  private static final GameOptions gameOptions = new GameOptions();
  private static final Random random = new Random();
  private ArrayList<HighScore> highScores;
  private Map<Integer, Powerup> powerupLocations = new HashMap<Integer, Powerup>();
  private boolean gameOver = false;
  private int currentNumber = 1;
  private Powerup currentPowerup = Powerup.NOTHING;
  private int gridSquaresFilled = 0;
  private int score = 0;
  
  public static void main (String[] args) {
    Numbreaka numbreaka = new Numbreaka();
    numbreaka.go();
  }
  
  private void go() {
    formatFont();
    highScores = loadHighScores();
    generatePowerupLocations();
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
  
  private void generatePowerupLocations() {
    if (powerupLocations.size() > 0) {
      powerupLocations.clear();
    }
    
    while (powerupLocations.size() < 5) {
      int newRandomLocation = random.nextInt(gameOptions.getNumberOfGridSquares());
      if (!powerupLocations.containsKey(newRandomLocation)) {
        // Generate powerup and put it
        powerupLocations.put(newRandomLocation, randomPowerup());
      } else {
        continue;
      }
    }
    
    for (int k : powerupLocations.keySet()) {
      System.out.println(k);
      System.out.println(powerupLocations.get(k));
    }
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
      while (highScores.size() > 3) {
        highScores.remove(highScores.size() - 1);
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
    if (currentPowerup != Powerup.CONSOLIDATE) {
      breakSquare(gs);  
    }
    processNeighbors(gs);
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
  
  private void processNeighbors(GridSquare currentGridSquare) {
    // ID #s go from 0 to gameOptions.getNumberOfGridSquares() - 1
    if (currentPowerup != Powerup.NOTHING) {
      performPowerupAction(currentGridSquare, currentPowerup);
    } else {
      addNormally(currentGridSquare);
    }
    currentPowerup = getNewPowerup(currentGridSquare);
    gameFrame.updatePowerup(currentPowerup);
  }
  
  private Powerup getNewPowerup(GridSquare currentGridSquare) {
    int id = currentGridSquare.getID();
    if (powerupLocations.containsKey(id)) {
      return powerupLocations.get(id);
    } else {
      return Powerup.NOTHING;
    }
  }

  private void performPowerupAction(GridSquare currentGridSquare, Powerup powerup) {
    switch (powerup) {
      case MULTIPLY:      multiply(currentGridSquare);
                          break;
      case VERTICAL:      addVertically(currentGridSquare);
                          break;
      case HORIZONTAL:    addHorizontally(currentGridSquare);
                          break;
      case RIGHT_ROTATION:rotateNeighborsRight(currentGridSquare);
                          break;
      case LEFT_ROTATION: rotateNeighborsLeft(currentGridSquare);
                          break;
      case REVERSE:       reverseNeighbors(currentGridSquare);
                          break;
      case CONSOLIDATE:   consolidateNeighbors(currentGridSquare);
                          break;
      default:            throw new IllegalArgumentException();
    }
  }
  
  private void addNormally(GridSquare gs) {
    for (GridSquare.Neighbor neighbor : GridSquare.Neighbor.values()) {
      GridSquare neighboringGridSquare = gs.getGridSquare(neighbor);
      // not the right way to do this
      addSquare(neighboringGridSquare);
    }
  }
  
  private void addSquare(GridSquare neighboringGridSquare) {
    if (neighboringGridSquare != null && !neighboringGridSquare.isBroken()) {
      if (neighboringGridSquare.isEmpty()) {
        fillNeighboringGridSquare(neighboringGridSquare, currentNumber);
        incrementGridSquaresFilled();
      } else {
        int value = neighboringGridSquare.getValue() + currentNumber;
        fillNeighboringGridSquare(neighboringGridSquare, value);
      }
    }
  }
  
  // Multiplies by x2 (can be chained if target square is another MULTIPLY)
  // If chaining, multiply, and then increase multiplier (x2 -> x3 -> x4...)
  // TODO CHAINING
  private void multiply(GridSquare gs) {
    for (GridSquare.Neighbor neighbor : GridSquare.Neighbor.values()) {
      GridSquare neighboringGridSquare = gs.getGridSquare(neighbor);
      
      if (neighboringGridSquare != null && !neighboringGridSquare.isBroken()) {
        if (neighboringGridSquare.isEmpty()) {
          fillNeighboringGridSquare(neighboringGridSquare, currentNumber);
          incrementGridSquaresFilled();
        } else {
          int value = neighboringGridSquare.getValue() * 2;
          fillNeighboringGridSquare(neighboringGridSquare, value);
        }
      }
    }
  }
  
  // Performs normal addition only to squares up and down
  private void addVertically(GridSquare currentGridSquare) {
    // not the right way to do this
    addSquare(currentGridSquare.getGridSquare(GridSquare.Neighbor.UP));
    addSquare(currentGridSquare.getGridSquare(GridSquare.Neighbor.DOWN));
  }
  
  // Performs normal addition only to squares left and right
  private void addHorizontally(GridSquare currentGridSquare) {
    // not the right way to do this
    addSquare(currentGridSquare.getGridSquare(GridSquare.Neighbor.LEFT));
    addSquare(currentGridSquare.getGridSquare(GridSquare.Neighbor.RIGHT));
  }
  
  // UP -> RIGHT -> DOWN -> LEFT -> UP
  private void rotateNeighborsRight(GridSquare gs) {
    GridSquare up = gs.getGridSquare(GridSquare.Neighbor.UP);
    int upValue = getValueForRotation(up);
    boolean upBroken = getBrokenForRotation(up);
    GridSquare left = gs.getGridSquare(GridSquare.Neighbor.LEFT);
    int leftValue = getValueForRotation(left);
    boolean leftBroken = getBrokenForRotation(left);
    GridSquare down = gs.getGridSquare(GridSquare.Neighbor.DOWN);
    int downValue = getValueForRotation(down);
    boolean downBroken = getBrokenForRotation(down);
    GridSquare right = gs.getGridSquare(GridSquare.Neighbor.RIGHT);
    int rightValue = getValueForRotation(right);
    boolean rightBroken = getBrokenForRotation(right);
    
    swapValue(up, leftValue);
    swapBroken(up, leftBroken);
    swapValue(right, upValue);
    swapBroken(right, upBroken);
    swapValue(down, rightValue);
    swapBroken(down, rightBroken);
    swapValue(left, downValue);
    swapBroken(left, downBroken);
  }
  
  private void swapValue(GridSquare gridSquare, int newValue) {
    if (gridSquare != null) {
      gridSquare.setValue(newValue);
    } else {
      return;
    }
  }
  
  private void swapBroken(GridSquare gridSquare, boolean isBroken) {
    if (gridSquare != null) {
      if (isBroken) {
        gridSquare.breakSquare();
      } else {
        gridSquare.repairSquare();
      }
    } else {
      return;
    }
  }
  
  private int getValueForRotation(GridSquare gridSquare) {
    if (gridSquare == null) {
      return 0;
    } else {
      return gridSquare.getValue();
    }
  }
  
  private boolean getBrokenForRotation(GridSquare gridSquare) {
    if (gridSquare == null) {
      return true;
    } else {
      return gridSquare.isBroken();
    }
  }
  
  //  UP -> LEFT -> DOWN -> RIGHT -> UP
  private void rotateNeighborsLeft(GridSquare gs) {
    GridSquare up = gs.getGridSquare(GridSquare.Neighbor.UP);
    int upValue = getValueForRotation(up);
    boolean upBroken = getBrokenForRotation(up);
    GridSquare left = gs.getGridSquare(GridSquare.Neighbor.LEFT);
    int leftValue = getValueForRotation(left);
    boolean leftBroken = getBrokenForRotation(left);
    GridSquare down = gs.getGridSquare(GridSquare.Neighbor.DOWN);
    int downValue = getValueForRotation(down);
    boolean downBroken = getBrokenForRotation(down);
    GridSquare right = gs.getGridSquare(GridSquare.Neighbor.RIGHT);
    int rightValue = getValueForRotation(right);
    boolean rightBroken = getBrokenForRotation(right);
    
    swapValue(up, rightValue);
    swapBroken(up, rightBroken);
    swapValue(right, downValue);
    swapBroken(right, downBroken);
    swapValue(down, leftValue);
    swapBroken(down, leftBroken);
    swapValue(left, upValue);
    swapBroken(left, upBroken);
  }
  
  // Reverses values of all neighbors (Ex: 21 -> 12)
  private void reverseNeighbors(GridSquare gs) {
    for (GridSquare.Neighbor neighbor : GridSquare.Neighbor.values()) {
      GridSquare neighboringGridSquare = gs.getGridSquare(neighbor);
      // not the right way to do this
      reverseValueOfNeighbor(neighboringGridSquare);
    }
  }
  
  private void reverseValueOfNeighbor(GridSquare neighboringGridSquare) {
    if (neighboringGridSquare != null) {
      int value = neighboringGridSquare.getValue();
      if (value > 0) {
        int newValue = reverseValue(value);
        neighboringGridSquare.setValue(newValue);  
      } else {
        return;
      }
    } else {
      return;
    }
  }
  
  private int reverseValue(int value) {
    String stringValue = Integer.toString(value);
    StringBuilder sb = new StringBuilder();
    for (int i = stringValue.length() - 1; i >= 0; i--) {
      sb.append(stringValue.charAt(i));
    }
    return Integer.parseInt(sb.toString());
  }
  
  // Developer use
  public void grantPowerup(int number) {
    switch (number) {
      case 1: currentPowerup = Powerup.MULTIPLY;
              gameFrame.updatePowerup(currentPowerup);
              break;
      case 2: currentPowerup = Powerup.VERTICAL;
              gameFrame.updatePowerup(currentPowerup);
              break;
      case 3: currentPowerup = Powerup.HORIZONTAL;
              gameFrame.updatePowerup(currentPowerup);
              break;
      case 4: currentPowerup = Powerup.RIGHT_ROTATION;
              gameFrame.updatePowerup(currentPowerup);
              break;
      case 5: currentPowerup = Powerup.LEFT_ROTATION;
              gameFrame.updatePowerup(currentPowerup);
              break;
      case 6: currentPowerup = Powerup.REVERSE;
              gameFrame.updatePowerup(currentPowerup);
              break;
      case 7: currentPowerup = Powerup.CONSOLIDATE;
              gameFrame.updatePowerup(currentPowerup);
              break;
      default: throw new IllegalArgumentException();
    }
  }
  
  // Does not break square and sets value = current value + value of all neighbors summed
  private void consolidateNeighbors(GridSquare currentGridSquare) {
    int total = 0;
    for (GridSquare.Neighbor neighbor : GridSquare.Neighbor.values()) {
      GridSquare neighboringGridSquare = currentGridSquare.getGridSquare(neighbor);
      if (neighboringGridSquare != null) {
        total += neighboringGridSquare.getValue();
      }
    } 
    currentGridSquare.setValue(total);
  }
  
  private boolean checkIfNewHighScore(int possibleHighScore) {
    if (highScores.size() < 3) {
      return true;
    } else {
      int lastIndex = highScores.size() - 1;
      return possibleHighScore > highScores.get(lastIndex).getScore();
    }
  }
  
  private void fillNeighboringGridSquare(GridSquare gs, int value) {
    if (gs != null) {
      if (value == 0) {
        gs.clear();
      } else {
        gs.setText(Integer.toString(value)); 
      } 
    }
  }
  
  public void quit() {
    saveHighScores();
    System.exit(0);
  }
  
  public enum Powerup {
    NOTHING, MULTIPLY, VERTICAL, HORIZONTAL, RIGHT_ROTATION, LEFT_ROTATION, REVERSE, CONSOLIDATE;
  }
  
  private Powerup randomPowerup(){
    int randomNumber = random.nextInt(Powerup.values().length);
    return Powerup.values()[randomNumber];
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
    
    public int getScore() {
      return score;
    }
    
    @Override
    public String toString() {
      return initials + " " + score;
    }
  }
}
