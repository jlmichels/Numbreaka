import java.awt.Color;

import javax.swing.JLabel;



public class GridSquare extends JLabel {
  
  // Declares instance variables
  private static final long serialVersionUID = 1L;
  private int gridSquareID = 0;
  private boolean isBroken = false;
  private GridSquare gridSquareUp;
  private GridSquare gridSquareDown;
  private GridSquare gridSquareLeft;
  private GridSquare gridSquareRight;
  
  // Call JLabel's constructor and set ID number (Todo: use to place powerups)
  GridSquare (int id) {
    super();
    gridSquareID = id;
  }
  
  // Breaks grid square by setting isBroken true and background black
  public void breakIt() {
    isBroken = true;
    this.setBackground(Color.BLACK);
  }
  
  // Resets grid square to initial conditions
  public void reset(Color gameBackgroundColor) {
    isBroken = false;
    this.setText("");
    this.setBackground(gameBackgroundColor);
    this.setForeground(Color.BLACK);
  }
  
  // Returns ID of grid square
  public int getID() {
    return gridSquareID;
  }
  
  // Returns numerical value of grid square
  public int getValue() {
    // implement better error checking
    if (this.getText().equals("")) {
      return 0;
    } else {
      return Integer.parseInt(this.getText());
    }
  }
  
  // Returns broken status
  public boolean isBroken() {
    return isBroken;
  }
  
  // Returns grid square above this grid square
  public GridSquare getGridSquareUp() {
    return gridSquareUp;
  }

  // Sets grid square above this grid square
  public void setGridSquareUp(GridSquare gridSquareUp) {
    this.gridSquareUp = gridSquareUp;
  }

  // Returns grid square below this grid square
  public GridSquare getGridSquareDown() {
    return gridSquareDown;
  }

  // Sets grid square below this grid square
  public void setGridSquareDown(GridSquare gridSquareDown) {
    this.gridSquareDown = gridSquareDown;
  }

  // Returns grid square to left of this grid square
  public GridSquare getGridSquareLeft() {
    return gridSquareLeft;
  }

  // Sets grid square to left of this grid square
  public void setGridSquareLeft(GridSquare gridSquareLeft) {
    this.gridSquareLeft = gridSquareLeft;
  }

  // Returns grid square to right of this grid square
  public GridSquare getGridSquareRight() {
    return gridSquareRight;
  }

  // Sets grid square to right of this grid square
  public void setGridSquareRight(GridSquare gridSquareRight) {
    this.gridSquareRight = gridSquareRight;
  }
  
}
