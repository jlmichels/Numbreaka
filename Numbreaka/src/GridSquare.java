import java.awt.Color;

import javax.swing.JLabel;



public class GridSquare extends JLabel {
  
  private static final long serialVersionUID = 1L;
  private final int gridSquareID;
  private boolean isBroken = false;
  private final Color backgroundColor;
  private GridSquare gridSquareUp;
  private GridSquare gridSquareDown;
  private GridSquare gridSquareLeft;
  private GridSquare gridSquareRight;
  
  public enum Neighbor {
    UP, DOWN, LEFT, RIGHT;
  }
  
  GridSquare (int id, Color backgroundColor) {
    super();
    this.backgroundColor = backgroundColor;
    gridSquareID = id;
  }
  
  public void breakSquare() {
    isBroken = true;
    setBackground(Color.BLACK);
  }
  
  public void repairSquare() {
    isBroken = false;
    setBackground(backgroundColor);
  }
  
  // Resets grid square to initial conditions
  public void reset() {
    isBroken = false;
    setText("");
    setBackground(backgroundColor);
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
  
  public void setValue(int value) {
    if (value == 0) {
      setText("");
    } else {
      setText(Integer.toString(value)); 
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
