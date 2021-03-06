import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class NumbreakaKeyAdapter extends KeyAdapter {
  
  private Numbreaka numbreaka;
  private GameFrame gameFrame;
  
  public NumbreakaKeyAdapter(Numbreaka numbreaka, GameFrame gameFrame) {
    this.numbreaka = numbreaka;
    this.gameFrame = gameFrame;
  }
  
  // Responds to keyboard input
  @Override
  public void keyPressed(KeyEvent keyEvent) {
    char key = keyEvent.getKeyChar();
    
    switch (key) {
      case '1': numbreaka.grantPowerup(1);
                break;
      case '2': numbreaka.grantPowerup(2);
                break;
      case '3': numbreaka.grantPowerup(3);
                break;
      case '4': numbreaka.grantPowerup(4);
                break;
      case '5': numbreaka.grantPowerup(5);
                break;
      case '6': numbreaka.grantPowerup(6);
                break;
      case '7': numbreaka.grantPowerup(7);
                break;
      case '8': numbreaka.grantPowerup(8);
                break;
      case 'c': numbreaka.incrementCurrentNumber();
                gameFrame.updateHelper();
                break;
      case 'q': numbreaka.quit();
                break;
      case 'r': gameFrame.highlightGameTitle();
                numbreaka.resetGame();
                break;
      case 'h': numbreaka.resetHighScores();
                break;
      default: ;
    }
  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    if (keyEvent.getKeyChar() == 'r') {
      gameFrame.returnColorToGameTitle();
    }
  }

}