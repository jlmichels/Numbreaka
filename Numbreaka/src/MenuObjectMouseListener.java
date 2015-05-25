import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;


public class MenuObjectMouseListener implements MouseListener {

  private final GameFrame gameFrame;
  private final Numbreaka numbreaka;
  
  public MenuObjectMouseListener(Numbreaka numbreaka, GameFrame gameFrame) {
    this.numbreaka = numbreaka;
    this.gameFrame = gameFrame;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals("START") || menuText.equals("QUIT") || menuText.equals("HIGH SCORES") || menuText.equals("RETRY") || menuText.equals("Return to Main Menu")) {
      gameFrame.highlight(menuObject);
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals("START")) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuText.equals("QUIT")) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuText.equals("HIGH SCORES")) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuText.equals("RETRY")) {
      menuObject.setForeground(Color.BLACK);
    } else if (menuText.equals("Return to Main Menu")) {
      menuObject.setForeground(Color.BLACK);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals("START")) {
      gameFrame.displayGameWindow();
    } else if (menuText.equals("QUIT")) {
      System.exit(0);
    } else if (menuText.equals("HIGH SCORES")) {
      gameFrame.displayHighScoresScreen();
    } else if (menuText.equals("RETRY")) {
      gameFrame.highlight(menuObject);
      numbreaka.resetGame();
      gameFrame.displayGameWindow();
    } else if (menuText.equals("NUMBREAKA")) {
      gameFrame.highlight(menuObject);
      numbreaka.resetGame();
      gameFrame.displayMainMenu();
    } else if (menuText.equals("Return to Main Menu")) {
      gameFrame.displayMainMenu();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals("RETRY")) {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    } else if (menuText.equals("NUMBREAKA")) {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    }
  }
  
  
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
