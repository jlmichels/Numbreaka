import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GridSquareMouseAdapter extends MouseAdapter {

  private final Numbreaka numbreaka;
  
  public GridSquareMouseAdapter(Numbreaka numbreaka) {
    this.numbreaka = numbreaka;
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
    if (!numbreaka.getGameOver()) {
      GridSquare gs = (GridSquare) e.getSource();
      numbreaka.processGridSquareInteraction(gs); 
    }
  }

}
