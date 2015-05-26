import java.awt.Color;

import javax.swing.JLabel;



public class GridSquare extends JLabel {
  
  private static final long serialVersionUID = 1L;
  private final int gridSquareID;
  private boolean isBroken = false;
  private GridSquare gridSquareUp;
  private GridSquare gridSquareDown;
  private GridSquare gridSquareLeft;
  private GridSquare gridSquareRight;
  
  public enum Neighbor {
    UP, DOWN, LEFT, RIGHT;
  }
  
  // Call JLabel's constructor and set ID number (TODO use to place powerups)
  GridSquare (int id) {
    super();
    gridSquareID = id;
  }
  
  public void breakSquare() {
    isBroken = true;
    setBackground(Color.BLACK);
  }
  
  // Resets grid square to initial conditions
  public void reset(Color gameBackgroundColor) {
    isBroken = false;
    setText("");
    setBackground(gameBackgroundColor);
    setForeground(Color.BLACK);
  }
  
  public int getID() {
    return gridSquareID;
  }
  
  public int getValue() {
    if (getText().equals("")) {
      return 0;
    } else {
      return Integer.parseInt(getText());
    }
  }
  
  public boolean isBroken() {
    return isBroken;
  }
  
  public boolean isEmpty() {
    return getText().equals("");
  }
  
  public void clear() {
    setText("");
  }
  
  public GridSquare getGridSquare(Neighbor neighbor) {
    switch (neighbor) {
      case UP: return gridSquareUp;
      case DOWN: return gridSquareDown;
      case LEFT: return gridSquareLeft;
      case RIGHT: return gridSquareRight;
      default: throw new IllegalArgumentException();
    }
  }
  
  public void setGridSquareUp(GridSquare gridSquareUp) {
    this.gridSquareUp = gridSquareUp;
  }

  public void setGridSquareDown(GridSquare gridSquareDown) {
    this.gridSquareDown = gridSquareDown;
  }

  public void setGridSquareLeft(GridSquare gridSquareLeft) {
    this.gridSquareLeft = gridSquareLeft;
  }

  public void setGridSquareRight(GridSquare gridSquareRight) {
    this.gridSquareRight = gridSquareRight;
  }
  
}
