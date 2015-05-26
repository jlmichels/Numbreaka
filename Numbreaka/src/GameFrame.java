import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;


public class GameFrame extends JFrame {
  
  private static final Color HIGHLIGHT_COLOR = new Color(134, 39, 39);
  private static final Color GAME_BACKGROUND_COLOR = new Color(202, 221, 221);
  private static final LineBorder BLACK_LINE_BORDER = new LineBorder(Color.BLACK);
  private static final int GRID_SQUARE_WIDTH = 100;
  private static final int GRID_SQUARE_HEIGHT = 100;
  private static final long serialVersionUID = 1L; // necessary?
  private static final String MAIN_MENU_CARD = "Main Menu";
  private static final String GAME_WINDOW_CARD = "Game Window";
  private static final String HIGH_SCORES_CARD = "High Scores";
  private static final String FINAL_SCORE_CARD = "Final Score";
  private static final String WINDOW_TITLE = "Numbreaka";
  private static final String TITLE_AND_GRID_SQUARE_FONT_NAME = "Verdana";
  private static final String BUTTON_AND_LEFT_RIGHT_TITLE_BOXES_FONT_NAME = "Calibri";
  private final int gridX;
  private final int gridY;
  private final MenuObjectMouseListener menuObjectMouseListener;
  private final GridSquareMouseListener gridSquareMouseListener;
  private boolean isHighScoresScreenInitialized = false;
  private final CardLayout cardLayout = new CardLayout();
  private Font titleFont;
  private Font gridSquareFont;
  private GridSquare[][] gridSquares;
  private int firstScore;
  private int secondScore;
  private int thirdScore;
  private JLabel highScoresButton;
  private JLabel firstHighScore;
  private JLabel secondHighScore;
  private JLabel thirdHighScore;
  private JLabel newHighScore;
  private JLabel finalScoreDisplay;
  private JLabel currentNumberTitleBox;
  private JLabel powerupTitleBox;
  private JLabel gameTitle;
  private JPanel highScoresScreen = new JPanel();
  private JPanel cardHolder = new JPanel();
  private Numbreaka numbreaka;
    
  public GameFrame(Numbreaka numbreaka, int gridX, int gridY) {
    this.numbreaka = numbreaka;
    this.gridX = gridX;
    this.gridY = gridY;
    menuObjectMouseListener = new MenuObjectMouseListener(numbreaka, this);
    gridSquareMouseListener = new GridSquareMouseListener(numbreaka);
    Font buttonFont = new Font(BUTTON_AND_LEFT_RIGHT_TITLE_BOXES_FONT_NAME, Font.BOLD, 55);

    JPanel titleBar = setupTitleBar();
    JPanel mainMenu = setupMainMenu(buttonFont);
    JPanel gameGrid = setupGameGrid();
    JPanel finalScoreScreen = setupFinalScoreScreen(buttonFont);
    setupCardHolder(mainMenu, gameGrid, finalScoreScreen);
    setupGameFrame(titleBar);
  }
  
  private JPanel setupTitleBar() {
    JPanel titleBar = new JPanel();
    BoxLayout boxLayout = new BoxLayout(titleBar, BoxLayout.X_AXIS);

    titleFont = new Font(TITLE_AND_GRID_SQUARE_FONT_NAME, Font.BOLD, 36);
    gridSquareFont = new Font(TITLE_AND_GRID_SQUARE_FONT_NAME, Font.PLAIN, 66);

    // Creates left box to hold current number
    currentNumberTitleBox = getTitleBox("1");
    currentNumberTitleBox.setFont(gridSquareFont);

    // Creates right box to hold powerups
    powerupTitleBox = getTitleBox("POW");
    powerupTitleBox.setFont(new Font(BUTTON_AND_LEFT_RIGHT_TITLE_BOXES_FONT_NAME, Font.BOLD, 43));
    
    // Creates and designs top game title
    gameTitle = new JLabel(Menu.TITLE.toString());
    gameTitle.setBackground(GAME_BACKGROUND_COLOR);
    gameTitle.setForeground(Color.BLACK);
    gameTitle.setMaximumSize(new Dimension(300, 100));
    gameTitle.setPreferredSize(new Dimension(300, 100));
    gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gameTitle.setOpaque(true);
    gameTitle.setBorder(BLACK_LINE_BORDER);
    gameTitle.addMouseListener(menuObjectMouseListener);
    gameTitle.setFont(titleFont);
    
    // Modifies entire title bar
    titleBar.setSize(100,100);
    titleBar.setLayout(boxLayout);
    titleBar.add(Box.createRigidArea(new Dimension(0, 100)));
    titleBar.add(currentNumberTitleBox);
    titleBar.add(Box.createHorizontalGlue());
    titleBar.add(gameTitle);
    titleBar.add(Box.createHorizontalGlue());
    titleBar.add(powerupTitleBox);
    titleBar.setBorder(BLACK_LINE_BORDER);
    titleBar.setOpaque(true);
    titleBar.setVisible(true);
    
    return titleBar;
  }
  
  private JLabel getTitleBox(String name) {
    JLabel titleBox = new JLabel(name);
    titleBox.setBorder(BLACK_LINE_BORDER);
    titleBox.setBackground(GAME_BACKGROUND_COLOR);
    titleBox.setForeground(Color.BLACK);
    titleBox.setMaximumSize(new Dimension(100, 100));
    titleBox.setPreferredSize(new Dimension(100, 100));
    titleBox.setHorizontalAlignment(SwingConstants.CENTER);
    titleBox.setOpaque(true);
    return titleBox;
  }
  
  private JPanel setupMainMenu(Font buttonFont) {
    JPanel mainMenu = new JPanel();
    BoxLayout boxLayout = new BoxLayout(mainMenu, BoxLayout.Y_AXIS);
    
    mainMenu.setLayout(boxLayout);
    mainMenu.setOpaque(true);
    mainMenu.setBackground(GAME_BACKGROUND_COLOR);
    mainMenu.setBorder(BLACK_LINE_BORDER);
    
    JLabel startButton = getMainMenuButton(Menu.START.toString(), buttonFont);
    highScoresButton = getMainMenuButton(Menu.HIGH_SCORES.toString(), buttonFont);
    JLabel quitButton = getMainMenuButton(Menu.QUIT.toString(), buttonFont);
    
    // Adds components to main menu
    mainMenu.add(Box.createRigidArea(new Dimension(0,130)));
    mainMenu.add(startButton, BorderLayout.CENTER);
    mainMenu.add(Box.createRigidArea(new Dimension(0,10)));
    mainMenu.add(highScoresButton, BorderLayout.CENTER);
    mainMenu.add(Box.createRigidArea(new Dimension(0,10)));
    mainMenu.add(quitButton, BorderLayout.CENTER);
    
    return mainMenu;
  }
  
  private JLabel getMainMenuButton(String name, Font buttonFont) {
    JLabel newButton = new JLabel(name);
    newButton.addMouseListener(menuObjectMouseListener);
    newButton.setHorizontalAlignment(SwingConstants.CENTER);
    newButton.setBackground(GAME_BACKGROUND_COLOR);
    newButton.setOpaque(true);
    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newButton.setFont(buttonFont);
    return newButton;
  }
  
  // Sets up game grid, creating all grid squares
  private JPanel setupGameGrid() {
    JPanel gameGrid = new JPanel();
    GridLayout gridLayout = new GridLayout(gridX, gridY);
    gameGrid.setLayout(gridLayout);
    gridLayout.setHgap(0);
    gridLayout.setVgap(0);
    gameGrid.setBorder(BLACK_LINE_BORDER);
    gridSquares = new GridSquare[gridX][gridY];
    fillGridWithSquares(gameGrid);
    assignSurroundingGridSquares();
    
    return gameGrid;
  }

  private void fillGridWithSquares(JPanel gameGrid) {
    // Variable used to assign an ID number to each grid square
    int id = 0;
    
    // Creates gridSquares and fills grid, assigning id numbers
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        gridSquares[i][j] = new GridSquare(id);
        gridSquares[i][j].setBorder(BLACK_LINE_BORDER);
        gridSquares[i][j].addMouseListener(gridSquareMouseListener);
        gridSquares[i][j].setOpaque(true);
        gridSquares[i][j].setBackground(GAME_BACKGROUND_COLOR);
        gridSquares[i][j].setForeground(Color.BLACK);
        gridSquares[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        gridSquares[i][j].setFont(gridSquareFont);
        gameGrid.add(gridSquares[i][j]);
        
        // Increments ID number for next grid square
        id++;
      }
    }
  }
  
  private void assignSurroundingGridSquares() {
    // Assigns surrounding grid squares (4 directions) if they exist
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        if (i > 0) {
          gridSquares[i][j].setGridSquareUp(gridSquares[i-1][j]);
        }
        if (j > 0) {
          gridSquares[i][j].setGridSquareLeft(gridSquares[i][j-1]);
        }
        if (i < gridY-1) {
          gridSquares[i][j].setGridSquareDown(gridSquares[i+1][j]);
        }
        if (j < gridX-1) {
          gridSquares[i][j].setGridSquareRight(gridSquares[i][j+1]);
        }
      }
    }
  }
  
  private void setupHighScoresScreen() {
    BoxLayout boxLayout = new BoxLayout(highScoresScreen, BoxLayout.Y_AXIS);
    Font buttonFont = new Font(BUTTON_AND_LEFT_RIGHT_TITLE_BOXES_FONT_NAME, Font.BOLD, 55); // TODO get rid of dependency on this; setup high scores screen right away
    
    highScoresScreen.setLayout(boxLayout);
    highScoresScreen.setOpaque(true);
    highScoresScreen.setBackground(GAME_BACKGROUND_COLOR);
    highScoresScreen.setBorder(BLACK_LINE_BORDER);
    
    // Creates and designs High Scores screen title
    JLabel highScoresTitle = new JLabel(Menu.HIGH_SCORES.toString());
    highScoresTitle.setHorizontalAlignment(SwingConstants.CENTER);
    highScoresTitle.setBackground(GAME_BACKGROUND_COLOR);
    highScoresTitle.setForeground(Color.BLACK);
    highScoresTitle.setOpaque(true);
    highScoresTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    highScoresTitle.setFont(titleFont);
     
    // Creates and designs first high score text
    firstHighScore = new JLabel("");
    firstHighScore.setHorizontalAlignment(SwingConstants.LEFT);
    firstHighScore.setBackground(GAME_BACKGROUND_COLOR);
    firstHighScore.setForeground(HIGHLIGHT_COLOR);
    firstHighScore.setOpaque(true);
    firstHighScore.setAlignmentX(Component.CENTER_ALIGNMENT);
    firstHighScore.setFont(buttonFont);
    
    // Creates and designs second high score text
    secondHighScore = new JLabel("");
    secondHighScore.setHorizontalAlignment(SwingConstants.LEFT);
    secondHighScore.setBackground(GAME_BACKGROUND_COLOR);
    secondHighScore.setForeground(HIGHLIGHT_COLOR);
    secondHighScore.setOpaque(true);
    secondHighScore.setAlignmentX(Component.CENTER_ALIGNMENT);
    secondHighScore.setFont(buttonFont);
    
    // Creates and designs third high score text
    thirdHighScore = new JLabel("");
    thirdHighScore.setHorizontalAlignment(SwingConstants.LEFT);
    thirdHighScore.setBackground(GAME_BACKGROUND_COLOR);
    thirdHighScore.setForeground(HIGHLIGHT_COLOR);
    thirdHighScore.setOpaque(true);
    thirdHighScore.setAlignmentX(Component.CENTER_ALIGNMENT);
    thirdHighScore.setFont(buttonFont);
    
    // Creates and designs return to main menu button
    JLabel returnToMainMenu = new JLabel(Menu.RETURN_TO_MAIN_MENU.toString());
    returnToMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
    returnToMainMenu.setBackground(GAME_BACKGROUND_COLOR);
    returnToMainMenu.setForeground(Color.BLACK);
    returnToMainMenu.setOpaque(true);
    returnToMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    returnToMainMenu.addMouseListener(menuObjectMouseListener);
    returnToMainMenu.setFont(new Font(BUTTON_AND_LEFT_RIGHT_TITLE_BOXES_FONT_NAME, Font.BOLD, 45));
    
    // Adds components to high scores screen
    highScoresScreen.add(Box.createRigidArea(new Dimension(0,40)));
    highScoresScreen.add(highScoresTitle, BorderLayout.CENTER);
    highScoresScreen.add(Box.createRigidArea(new Dimension(0,60)));
    highScoresScreen.add(firstHighScore);
    highScoresScreen.add(secondHighScore);
    highScoresScreen.add(thirdHighScore);
    highScoresScreen.add(Box.createRigidArea(new Dimension(0,40)));
    highScoresScreen.add(returnToMainMenu, BorderLayout.CENTER);
  }

  // Sets up final score screen on game over
  private JPanel setupFinalScoreScreen(Font buttonFont) {
    JPanel finalScoreScreen = new JPanel();
    BoxLayout boxLayout = new BoxLayout(finalScoreScreen, BoxLayout.Y_AXIS);

    finalScoreScreen.setLayout(boxLayout);
    finalScoreScreen.setOpaque(true);
    finalScoreScreen.setBackground(GAME_BACKGROUND_COLOR);
    finalScoreScreen.setBorder(BLACK_LINE_BORDER);
    
    // Creates and designs High Scores screen title
    JLabel finalScoreTitle = new JLabel("FINAL SCORE");
    finalScoreTitle.setHorizontalAlignment(SwingConstants.CENTER);
    finalScoreTitle.setBackground(GAME_BACKGROUND_COLOR);
    finalScoreTitle.setForeground(Color.BLACK);
    finalScoreTitle.setOpaque(true);
    finalScoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    finalScoreTitle.setFont(titleFont);
    
    // Creates and designs new high score text
    newHighScore = new JLabel("NEW HIGH SCORE!");
    newHighScore.setHorizontalAlignment(SwingConstants.CENTER);
    newHighScore.setBackground(GAME_BACKGROUND_COLOR);
    newHighScore.setForeground(HIGHLIGHT_COLOR);
    newHighScore.setOpaque(true);
    newHighScore.setVisible(false);
    newHighScore.setAlignmentX(Component.CENTER_ALIGNMENT);
    newHighScore.setFont(buttonFont);
    
    // Creates and designs current high score display text
    finalScoreDisplay = new JLabel("");
    finalScoreDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    finalScoreDisplay.setBackground(GAME_BACKGROUND_COLOR);
    finalScoreDisplay.setForeground(HIGHLIGHT_COLOR);
    finalScoreDisplay.setOpaque(true);
    finalScoreDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
    finalScoreDisplay.setFont(buttonFont);
    
    // Creates and designs RETRY button
    JLabel retry = new JLabel(Menu.RETRY.toString());
    retry.setHorizontalAlignment(SwingConstants.CENTER);
    retry.setBackground(GAME_BACKGROUND_COLOR);
    retry.setForeground(Color.BLACK);
    retry.setOpaque(true);
    retry.setAlignmentX(Component.CENTER_ALIGNMENT);
    retry.addMouseListener(menuObjectMouseListener);
    retry.setFont(buttonFont);
       
    // Adds components to high scores screen
    finalScoreScreen.add(Box.createRigidArea(new Dimension(0,40)));
    finalScoreScreen.add(finalScoreTitle, BorderLayout.CENTER);
    finalScoreScreen.add(newHighScore, BorderLayout.CENTER);
    finalScoreScreen.add(Box.createRigidArea(new Dimension(0,60)));
    finalScoreScreen.add(finalScoreDisplay);
    finalScoreScreen.add(Box.createRigidArea(new Dimension(0,40)));
    finalScoreScreen.add(retry);
    
    return finalScoreScreen;
  }
  
  private void setupCardHolder(JPanel mainMenu, JPanel gameGrid, JPanel finalScoreScreen) {
    cardHolder.setLayout(cardLayout);
    cardHolder.add(mainMenu, MAIN_MENU_CARD);
    cardHolder.add(gameGrid, GAME_WINDOW_CARD);
    cardHolder.add(highScoresScreen, HIGH_SCORES_CARD);
    cardHolder.add(finalScoreScreen, FINAL_SCORE_CARD);
    cardHolder.setVisible(true);
  }
  
  private void setupGameFrame(JPanel titleBar) {
    BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
    
    this.getContentPane().setLayout(boxLayout);
    this.getContentPane().add(titleBar);
    this.getContentPane().setBackground(GAME_BACKGROUND_COLOR);
    this.getContentPane().add(cardHolder);
    this.setTitle(WINDOW_TITLE);
//    this.addMouseListener(menuObjectMouseListener);
    this.addKeyListener(new NumbreakaKeyListener(numbreaka, this));
    this.setBounds(400, 300, (gridX*GRID_SQUARE_WIDTH+8), (gridY*GRID_SQUARE_HEIGHT)+gridY+128);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setAlwaysOnTop(true);
    this.setResizable(false);
    this.setVisible(true);
  }
//
//  // Turns all grid square numbers RED
//  // Not used anymore?
//  public void highlightNumbers() {
//    for (int i = 0; i < gridY; i++) {
//      for (int j = 0; j < gridX; j++) {
//        gridSquares[i][j].setForeground(HIGHLIGHT_COLOR);
//      }
//    }
//  }  
  
  // Resets game to initial conditions
  public void reset() {
    highScoresButton.setForeground(Color.DARK_GRAY);
    newHighScore.setVisible(false);
    for (GridSquare[] gridSquareArray : gridSquares) {
      for (GridSquare gs : gridSquareArray) {
        gs.reset(GAME_BACKGROUND_COLOR);
      }
    }
  }
  
  public void displayMainMenu() {
    cardLayout.show(cardHolder, MAIN_MENU_CARD);
    this.repaint();
  }
  
  public void displayGameWindow() {
    cardLayout.show(cardHolder, GAME_WINDOW_CARD);
    this.repaint();
  }

  public void displayHighScoresScreen() {
    if (!isHighScoresScreenInitialized) {
      setupHighScoresScreen();
      isHighScoresScreenInitialized = true;
    }
    
    String firstInitials = "";
    String secondInitials = "";
    String thirdInitials = "";
    
    getHighScores();
//    setHighScores();
    
    if (firstScore <= 0) {
      firstHighScore.setText("1: AAA 000");
    } else {
      firstHighScore.setText("1: " + firstInitials + " " + firstScore);
    }
    if (secondScore <= 0) {
      secondHighScore.setText("2: AAA 000");
    } else {
      secondHighScore.setText("2: " + secondInitials + " " + secondScore);
    }
    if (thirdScore <= 0) {
      thirdHighScore.setText("3: AAA 000");
    } else {
      thirdHighScore.setText("3: " + thirdInitials + " " + thirdScore);
    }
    cardLayout.show(cardHolder, HIGH_SCORES_CARD);
    this.repaint();
  }
  
  private void getHighScores() {
//    // Gets high scores and sets to temp values for readability
//    TreeMap<Integer, String> tempHighScores = numbreaka.getHighScores();
//    
//    if (tempHighScores.lastEntry() != null) {
//      firstInitials = tempHighScores.lastEntry().getValue();
//      firstScore = tempHighScores.lastKey();
//    }
//    if (tempHighScores.lowerEntry(firstScore) != null) {
//      secondInitials = tempHighScores.lowerEntry(firstScore).getValue();
//      secondScore = tempHighScores.lowerKey(firstScore);
//    }
//    if (tempHighScores.lowerEntry(secondScore) != null) {
//      thirdInitials = tempHighScores.lowerEntry(secondScore).getValue();
//      thirdScore = tempHighScores.lowerKey(secondScore);
//    }
  }
  
//  private void setHighScores() {
//    numbreaka.setHighScores(firstInitials, firstScore, secondInitials, secondScore, thirdInitials, thirdScore);
//  }
  
  public void displayFinalScorePopup(int score, boolean isHighScore) {
    finalScoreDisplay.setText(Integer.toString(score));
    if (isHighScore) {
      // clear this on reset
      newHighScore.setVisible(true);
    }
    getHighScores();
//    setHighScores();
    cardLayout.show(cardHolder, FINAL_SCORE_CARD);
    this.repaint();
  }
   
  public GridSquare[][] getGridSquares() {
    return gridSquares;
  }
  
  public JLabel getLeftTitleBox() {
    return currentNumberTitleBox;
  }
  
  public JLabel getRightTitleBox() {
    return powerupTitleBox;
  }
  
  public JLabel getGameTitle() {
    return gameTitle;
  }
  
  public void updateHelper() {
    getLeftTitleBox().setText(Integer.toString(numbreaka.getCurrentNumber()));
  }
  
  public Color getHighlightColor() {
    return HIGHLIGHT_COLOR;
  }
  
  public LineBorder getLineBorder() {
    return BLACK_LINE_BORDER;
  }
  
  public void highlight(JLabel jLabel) {
    jLabel.setForeground(HIGHLIGHT_COLOR);
  }
}
