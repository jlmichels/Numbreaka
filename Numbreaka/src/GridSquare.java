import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;



public class GridSquare extends JLabel {
  
  private final int gridSquareID;
  private boolean isBroken = false;
  private GameOptions gameOptions;
  private GridSquare gridSquareUp;
  private GridSquare gridSquareDown;
  private GridSquare gridSquareLeft;
  private GridSquare gridSquareRight;
  
  public enum Neighbor {
    UP, DOWN, LEFT, RIGHT;
  }
  
  GridSquare (int id, GameOptions gameOptions) {
    super();
    this.gameOptions = gameOptions;
    gridSquareID = id;
  }
  
  public void breakSquare() {
    if (isBroken) {
      throw new IllegalStateException();
    }
    isBroken = true;
    setBackground(Color.BLACK);
    clear();
  }
  
  public void repairSquare() {
    if (!isBroken) {
      throw new IllegalStateException();
    }
    isBroken = false;
    setBackground(gameOptions.getGameBackgroundColor());
  }
  
  // Resets grid square to initial conditions
  public void reset() {
    isBroken = false;
    clear();
    setBackground(gameOptions.getGameBackgroundColor());
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
      clear();
    } else {
      String valueString = Integer.toString(value);
      Font currentFont = this.getFont();
      int valueLength = valueString.length();
      if (valueLength <= 2 && currentFont != gameOptions.getTwoDigitFont()) {
        this.setFont(gameOptions.getTwoDigitFont());
      } else if (valueLength == 3 && currentFont != gameOptions.getThreeDigitFont()) {
        this.setFont(gameOptions.getThreeDigitFont());
      } else if (valueLength == 4 && currentFont != gameOptions.getFourDigitFont()) {
        this.setFont(gameOptions.getFourDigitFont());
      } else if (valueLength == 5 && currentFont != gameOptions.getFiveDigitFont()) {
        this.setFont(gameOptions.getFiveDigitFont());
      } else if (valueLength == 6 && currentFont != gameOptions.getSixDigitFont()) {
        this.setFont(gameOptions.getSixDigitFont());
      }
      setText(valueString); 
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
    setFont(gameOptions.getTwoDigitFont());
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
