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
    
    if (menuText.equals(Menu.START.toString()) || menuText.equals(Menu.QUIT.toString()) || menuText.equals(Menu.HIGH_SCORES.toString())
        || menuText.equals(Menu.RETRY.toString()) || menuText.equals(Menu.RETURN_TO_MAIN_MENU.toString())) {
      gameFrame.highlight(menuObject);
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals(Menu.START.toString()) || menuText.equals(Menu.QUIT.toString()) || menuText.equals(Menu.HIGH_SCORES.toString())) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuText.equals(Menu.RETRY.toString()) || menuText.equals(Menu.RETURN_TO_MAIN_MENU.toString())) {
      menuObject.setForeground(Color.BLACK);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals(Menu.START.toString())) {
      gameFrame.displayGameWindow();
    } else if (menuText.equals(Menu.QUIT.toString())) {
      System.exit(0);
    } else if (menuText.equals(Menu.HIGH_SCORES.toString())) {
      gameFrame.displayHighScoresScreen();
    } else if (menuText.equals(Menu.RETRY.toString())) {
      gameFrame.highlight(menuObject);
      numbreaka.resetGame();
      gameFrame.displayGameWindow();
    } else if (menuText.equals(Menu.TITLE.toString())) {
      gameFrame.highlight(menuObject);
      numbreaka.resetGame();
      gameFrame.displayMainMenu();
    } else if (menuText.equals(Menu.RETURN_TO_MAIN_MENU.toString())) {
      gameFrame.displayMainMenu();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    String menuText = menuObject.getText();
    
    if (menuText.equals(Menu.RETRY.toString())) {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    } else if (menuText.equals(Menu.TITLE.toString())) {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    }
  }
  
  
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
