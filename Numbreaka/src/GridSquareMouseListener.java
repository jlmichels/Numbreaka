import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GridSquareMouseListener implements MouseListener {

  private final Numbreaka numbreaka;
  private final GameFrame gameFrame;
  
  public GridSquareMouseListener(Numbreaka numbreaka, GameFrame gameFrame) {
    this.gameFrame = gameFrame;
    this.numbreaka = numbreaka;
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
    if (!numbreaka.getGameOver()) {
      
      // Won't need this is using a separate MouseListener
      if (e.getSource() instanceof GridSquare) {
        GridSquare gs = (GridSquare) e.getSource();
        
        // If grid square is not broken, break it and check surrounding grid squares
        // If grid square is broken, ignore mouse press
        if (!gs.isBroken()) {
          
          // Break it and increment gridSquareFilled if empty, else clear number
          gs.breakIt();
          if (gs.getText().equals("")) {
            numbreaka.incrementGridSquaresFilled();
          } else {
            gs.setText("");
          }
                  
          // If grid square above is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareUp() != null && !gs.getGridSquareUp().isBroken()) {
            if (gs.getGridSquareUp().getText().equals("")) {
              gs.getGridSquareUp().setText(Integer.toString(numbreaka.getCurrentNumber()));
              numbreaka.incrementGridSquaresFilled();
            } else {
              gs.getGridSquareUp().setText(Integer.toString(gs.getGridSquareUp().getValue() + numbreaka.getCurrentNumber()));
            }
          }
          
          // If grid square to right is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareRight() != null && !gs.getGridSquareRight().isBroken()) {
            if (gs.getGridSquareRight().getText().equals("")) {
              gs.getGridSquareRight().setText(Integer.toString(numbreaka.getCurrentNumber()));
              numbreaka.incrementGridSquaresFilled();
            } else {
              gs.getGridSquareRight().setText(Integer.toString(gs.getGridSquareRight().getValue() + numbreaka.getCurrentNumber()));
            }
          }
          
          // If grid square to left is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareLeft() != null && !gs.getGridSquareLeft().isBroken()) {
            if (gs.getGridSquareLeft().getText().equals("")) {
              gs.getGridSquareLeft().setText(Integer.toString(numbreaka.getCurrentNumber()));
              numbreaka.incrementGridSquaresFilled();
            } else {
              gs.getGridSquareLeft().setText(Integer.toString(gs.getGridSquareLeft().getValue() + numbreaka.getCurrentNumber()));
            }
          }
          
          // If grid square below is not out, if empty set number to current number, else set to its number + current number
          if (gs.getGridSquareDown() != null && !gs.getGridSquareDown().isBroken()) {
            if (gs.getGridSquareDown().getText().equals("")) {
              gs.getGridSquareDown().setText(Integer.toString(numbreaka.getCurrentNumber()));
              numbreaka.incrementGridSquaresFilled();
            } else {
              gs.getGridSquareDown().setText(Integer.toString(gs.getGridSquareDown().getValue() + numbreaka.getCurrentNumber()));
            }
          }
      
          // Increment current number
          numbreaka.incrementCurrentNumber();
          
          // Update helper
          gameFrame.updateHelper();
          
          // Check if game over by comparing gridSquaresFilled to total number of grid squares
          numbreaka.checkIfGameOver();
        }
      } 
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
  
  @Override
  public void mouseReleased(MouseEvent e) {
  }

}
