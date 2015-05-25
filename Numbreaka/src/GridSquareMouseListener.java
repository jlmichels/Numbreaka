import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GridSquareMouseListener implements MouseListener {

  private final Numbreaka numbreaka;
  
  public GridSquareMouseListener(Numbreaka numbreaka) {
    this.numbreaka = numbreaka;
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
    if (!numbreaka.getGameOver()) {
      GridSquare gs = (GridSquare) e.getSource();
      numbreaka.processGridSquare(gs);
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
