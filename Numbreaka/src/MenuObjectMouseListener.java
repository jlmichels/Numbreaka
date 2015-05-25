import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;


public class MenuObjectMouseListener implements MouseListener {

  private final Color highlightColor;
  private final GameFrame gameFrame;
  private final Numbreaka numbreaka;
  
  // use highlight color through gameframe
  public MenuObjectMouseListener(Numbreaka numbreaka, GameFrame gameFrame, Color highlightColor) {
    this.numbreaka = numbreaka;
    this.gameFrame = gameFrame;
    this.highlightColor = highlightColor;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    if (menuObject.getText().equals("START")) {
      menuObject.setForeground(highlightColor);
    } else if (menuObject.getText().equals("QUIT")) {
      menuObject.setForeground(highlightColor);
    } else if (menuObject.getText().equals("HIGH SCORES")) {
      menuObject.setForeground(highlightColor);
    } else if (menuObject.getText().equals("RETRY")) {
      menuObject.setForeground(highlightColor);
    } else if (menuObject.getText().equals("Return to Main Menu")) {
      menuObject.setForeground(highlightColor);
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    if (menuObject.getText().equals("START")) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuObject.getText().equals("QUIT")) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuObject.getText().equals("HIGH SCORES")) {
      menuObject.setForeground(Color.DARK_GRAY);
    } else if (menuObject.getText().equals("RETRY")) {
      menuObject.setForeground(Color.BLACK);
    } else if (menuObject.getText().equals("Return to Main Menu")) {
      menuObject.setForeground(Color.BLACK);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    if (menuObject.getText().equals("START")) {
      gameFrame.displayGameWindow();
    } else if (menuObject.getText().equals("QUIT")) {
      System.exit(0);
    } else if (menuObject.getText().equals("HIGH SCORES")) {
      gameFrame.displayHighScoresScreen();
    } else if (menuObject.getText().equals("RETRY")) {
      gameFrame.getGameTitle().setForeground(highlightColor);
      numbreaka.resetGame();
      gameFrame.displayGameWindow();
    } else if (menuObject.getText().equals("NUMBREAKA")) {
      gameFrame.getGameTitle().setForeground(highlightColor);
      numbreaka.resetGame();
      gameFrame.displayMainMenu();
    } else if (menuObject.getText().equals("Return to Main Menu")) {
      gameFrame.displayMainMenu();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    JLabel menuObject = (JLabel) e.getSource();
    if (menuObject.getText().equals("RETRY")) {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    } else if (menuObject.getText().equals("NUMBREAKA")) {
      gameFrame.getGameTitle().setForeground(Color.BLACK);
    }
  }
  
  
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
