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
      // Closes game
      System.exit(0); // 0 means exiting on purpose, 1 means exiting due to error
    } else if (keyEvent.getKeyChar() == 'r') {
      // Resets game to initial conditions
      gameFrame.getGameTitle().setForeground(gameFrame.getHighlightColor());
      numbreaka.resetGame();
    } else if (keyEvent.getKeyChar() == 'h') {
      numbreaka.resetHighScores();
    }
  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    if (keyEvent.getKeyChar() == 'r') {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
      gameFrame.getGameTitle().setBorder(gameFrame.getLineBorder());
    }
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }
  
}