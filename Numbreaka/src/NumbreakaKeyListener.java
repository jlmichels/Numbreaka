import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class NumbreakaKeyListener implements KeyListener {
  
  private Numbreaka numbreaka;
  private GameFrame gameFrame;
  
  public NumbreakaKeyListener(Numbreaka numbreaka, GameFrame gameFrame) {
    this.numbreaka = numbreaka;
    this.gameFrame = gameFrame;
  }
  
  // Responds to keyboard input
  @Override
  public void keyPressed(KeyEvent keyEvent) {
    if (keyEvent.getKeyChar() == 'q') {
      // Quits
      System.exit(0);
    } else if (keyEvent.getKeyChar() == 'r') {
      // Resets game to initial conditions
      gameFrame.getGameTitle().setForeground(gameFrame.getHighlightColor());
      numbreaka.resetGame();
    } else if (keyEvent.getKeyChar() == 'h') {
      // Resets high scores for testing
//      numbreaka.resetHighScores();
    }
  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    if (keyEvent.getKeyChar() == 'r') {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    }
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }
}