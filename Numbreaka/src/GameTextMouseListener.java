import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;


public class GameTextMouseListener implements MouseListener {

  private final GameFrame gameFrame;
  
  public GameTextMouseListener(GameFrame gameFrame) {
    this.gameFrame = gameFrame;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    JLabel gameTextLabel = (JLabel) e.getSource();
    gameFrame.highlightAsNecessary(gameTextLabel);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    JLabel gameTextLabel = (JLabel) e.getSource();
    gameFrame.returnColorAsNecessary(gameTextLabel);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    JLabel gameTextLabel = (JLabel) e.getSource();
    gameFrame.performActionAsNecessary(gameTextLabel);
    
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    JLabel gameTextLabel = (JLabel) e.getSource();
    gameFrame.returnColorAsNecessary(gameTextLabel);
  }
  
  
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
