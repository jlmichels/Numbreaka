import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


public class GameFrame extends JFrame {

  private static final String START_MENU_ITEM = "START";
  private static final String QUIT_MENU_ITEM = "QUIT";
  private static final String HIGH_SCORES_MENU_ITEM = "HIGH SCORES";
  private static final String RETRY_MENU_ITEM = "RETRY";
  private static final String RETURN_TO_MAIN_MENU_MENU_ITEM = "Return to Main Menu";
  private static final String TITLE_TEXT= "NUMBREAKA";
  private static final String MAIN_MENU_CARD = "Main Menu";
  private static final String GAME_WINDOW_CARD = "Game Window";
  private static final String HIGH_SCORES_CARD = "High Scores";
  private static final String FINAL_SCORE_CARD = "Final Score";
  private static final String WINDOW_TITLE = "Numbreaka";
  private static final String DEFAULT_FIRST_HIGH_SCORE = "1: AAA 000";
  private static final String DEFAULT_SECOND_HIGH_SCORE = "2: AAA 000";
  private static final String DEFAULT_THIRD_HIGH_SCORE = "3: AAA 000";
  private final GameOptions gameOptions;
  private final GameTextMouseAdapter menuObjectMouseListener;
  private final GridSquareMouseAdapter gridSquareMouseListener;
  private boolean isHighScoresScreenInitialized = false;
  private final CardLayout cardLayout = new CardLayout();
  private Font titleFont;
  private GridSquare[][] gridSquares;
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
    
  public GameFrame(Numbreaka numbreaka, GameOptions gameOptions) {
    this.numbreaka = numbreaka;
    this.gameOptions = gameOptions;
    menuObjectMouseListener = new GameTextMouseAdapter(this);
    gridSquareMouseListener = new GridSquareMouseAdapter(numbreaka);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        numbreaka.quit();
      }
    });
    Font buttonFont = new Font(gameOptions.getSecondaryFontName(), Font.BOLD, 55);
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

    titleFont = new Font(gameOptions.getPrimaryFontName(), Font.BOLD, 36);

    // Creates left box to hold current number
    currentNumberTitleBox = getTitleBox("1");
    currentNumberTitleBox.setFont(gameOptions.getGridSquareDefaultFont());
    currentNumberTitleBox.setBorder(gameOptions.getBlackLineBorder());

    // Creates right box to hold powerups
    powerupTitleBox = getTitleBox("");
    powerupTitleBox.setFont(new Font(gameOptions.getPrimaryFontName(), Font.BOLD, 37));
    powerupTitleBox.setBorder(gameOptions.getBlackLineBorder());
    powerupTitleBox.setForeground(gameOptions.getHighlightColor());
    
    // Creates and designs top game title
    gameTitle = new JLabel(TITLE_TEXT);
    gameTitle.setBackground(gameOptions.getGameBackgroundColor());
    gameTitle.setForeground(Color.BLACK);
    int gameTitleWidth = (gameOptions.getGridX() * 100) - 200;
    gameTitle.setMaximumSize(new Dimension(gameTitleWidth, 100));
    gameTitle.setPreferredSize(new Dimension(gameTitleWidth, 100));
    gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gameTitle.setOpaque(true);
    gameTitle.setBorder(gameOptions.getBlackLineBorder());
    gameTitle.addMouseListener(menuObjectMouseListener);
    gameTitle.setFont(titleFont);
    
    // Modifies entire title bar
    titleBar.setBackground(gameOptions.getGameBackgroundColor());
    titleBar.setSize(100,100);
    titleBar.setLayout(boxLayout);
    titleBar.add(Box.createRigidArea(new Dimension(0, 100)));
    titleBar.add(currentNumberTitleBox);
    titleBar.add(Box.createHorizontalGlue());
    titleBar.add(gameTitle);
    titleBar.add(Box.createHorizontalGlue());
    titleBar.add(powerupTitleBox);
    titleBar.setBorder(gameOptions.getBlackLineBorder());
    titleBar.setOpaque(true);
    titleBar.setVisible(true);
    
    return titleBar;
  }
  
  private JLabel getTitleBox(String name) {
    JLabel titleBox = new JLabel(name);
    titleBox.setBorder(gameOptions.getBlackLineBorder());
    titleBox.setBackground(gameOptions.getGameBackgroundColor());
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
    mainMenu.setBackground(gameOptions.getGameBackgroundColor());
    mainMenu.setBorder(gameOptions.getBlackLineBorder());
    
    JLabel startButton = getMainMenuButton(START_MENU_ITEM, buttonFont);
    highScoresButton = getMainMenuButton(HIGH_SCORES_MENU_ITEM, buttonFont);
    JLabel quitButton = getMainMenuButton(QUIT_MENU_ITEM, buttonFont);
    
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
    newButton.setBackground(gameOptions.getGameBackgroundColor());
    newButton.setOpaque(true);
    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newButton.setFont(buttonFont);
    return newButton;
  }
  
  // Sets up game grid, creating all grid squares
  private JPanel setupGameGrid() {
    JPanel gameGrid = new JPanel();
    GridLayout gridLayout = new GridLayout(gameOptions.getGridX(), gameOptions.getGridY());
    gameGrid.setLayout(gridLayout);
    gridLayout.setHgap(0);
    gridLayout.setVgap(0);
    gameGrid.setBackground(Color.BLACK);
    gameGrid.setBorder(gameOptions.getBlackLineBorder());
    gridSquares = new GridSquare[gameOptions.getGridX()][gameOptions.getGridY()];
    fillGridWithSquares(gameGrid);
    assignSurroundingGridSquares();
    return gameGrid;
  }

  private void fillGridWithSquares(JPanel gameGrid) {
    int id = 0;
    
    // Creates gridSquares and fills grid, assigning id numbers
    for (int i = 0; i < gameOptions.getGridY(); i++) {
      for (int j = 0; j < gameOptions.getGridX(); j++) {
        gridSquares[i][j] = new GridSquare(id, gameOptions);
        gridSquares[i][j].setBorder(gameOptions.getBlackLineBorder());
        gridSquares[i][j].addMouseListener(gridSquareMouseListener);
        gridSquares[i][j].setOpaque(true);
        gridSquares[i][j].setBackground(gameOptions.getGameBackgroundColor());
        gridSquares[i][j].setForeground(Color.BLACK);
        gridSquares[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        gridSquares[i][j].setFont(gameOptions.getGridSquareDefaultFont());
        gameGrid.add(gridSquares[i][j]);
        id++;
      }
    }
  }
  
  private void assignSurroundingGridSquares() {
    // Assigns surrounding grid squares (all 4 directions) if they exist
    for (int i = 0; i < gameOptions.getGridY(); i++) {
      for (int j = 0; j < gameOptions.getGridX(); j++) {
        if (i > 0) {
          gridSquares[i][j].setGridSquareUp(gridSquares[i-1][j]);
        }
        if (j > 0) {
          gridSquares[i][j].setGridSquareLeft(gridSquares[i][j-1]);
        }
        if (i < gameOptions.getGridY()-1) {
          gridSquares[i][j].setGridSquareDown(gridSquares[i+1][j]);
        }
        if (j < gameOptions.getGridX()-1) {
          gridSquares[i][j].setGridSquareRight(gridSquares[i][j+1]);
        }
      }
    }
  }
  
  private void setupHighScoresScreen() {
    BoxLayout boxLayout = new BoxLayout(highScoresScreen, BoxLayout.Y_AXIS);
    Font buttonFont = new Font(gameOptions.getSecondaryFontName(), Font.BOLD, 55); // TODO get rid of dependency on this; setup high scores screen right away
    
    highScoresScreen.setLayout(boxLayout);
    highScoresScreen.setOpaque(true);
    highScoresScreen.setBackground(gameOptions.getGameBackgroundColor());
    highScoresScreen.setBorder(gameOptions.getBlackLineBorder());
    
    // Creates and designs High Scores screen title
    JLabel highScoresTitle = new JLabel(HIGH_SCORES_MENU_ITEM);
    highScoresTitle.setHorizontalAlignment(SwingConstants.CENTER);
    highScoresTitle.setBackground(gameOptions.getGameBackgroundColor());
    highScoresTitle.setForeground(Color.BLACK);
    highScoresTitle.setOpaque(true);
    highScoresTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    highScoresTitle.setFont(titleFont);
     
    firstHighScore = makeHighScoreLabel(DEFAULT_FIRST_HIGH_SCORE, buttonFont);
    secondHighScore = makeHighScoreLabel(DEFAULT_SECOND_HIGH_SCORE, buttonFont);
    thirdHighScore = makeHighScoreLabel(DEFAULT_THIRD_HIGH_SCORE, buttonFont);
    JLabel returnToMainMenu = makeReturnToMainMenuButton(RETURN_TO_MAIN_MENU_MENU_ITEM);
    
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
  
  private JLabel makeReturnToMainMenuButton(String name) {
    JLabel returnToMainMenuButton = new JLabel(name);
    returnToMainMenuButton.setHorizontalAlignment(SwingConstants.CENTER);
    returnToMainMenuButton.setBackground(gameOptions.getGameBackgroundColor());
    returnToMainMenuButton.setForeground(Color.BLACK);
    returnToMainMenuButton.setOpaque(true);
    returnToMainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    returnToMainMenuButton.addMouseListener(menuObjectMouseListener);
    returnToMainMenuButton.setFont(new Font(gameOptions.getSecondaryFontName(), Font.BOLD, 45));
    return returnToMainMenuButton;
  }
  
  private JLabel makeHighScoreLabel(String name, Font buttonFont) {
    JLabel highScoreLabel = new JLabel(name);
    highScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
    highScoreLabel.setBackground(gameOptions.getGameBackgroundColor());
    highScoreLabel.setForeground(gameOptions.getHighlightColor());
    highScoreLabel.setOpaque(true);
    highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    highScoreLabel.setFont(buttonFont);
    return highScoreLabel;
  }

  // Sets up final score screen on game over
  private JPanel setupFinalScoreScreen(Font buttonFont) {
    JPanel finalScoreScreen = new JPanel();
    BoxLayout boxLayout = new BoxLayout(finalScoreScreen, BoxLayout.Y_AXIS);

    finalScoreScreen.setLayout(boxLayout);
    finalScoreScreen.setOpaque(true);
    finalScoreScreen.setBackground(gameOptions.getGameBackgroundColor());
    finalScoreScreen.setBorder(gameOptions.getBlackLineBorder());
    
    // Creates and designs High Scores screen title
    JLabel finalScoreTitle = new JLabel("FINAL SCORE");
    finalScoreTitle.setHorizontalAlignment(SwingConstants.CENTER);
    finalScoreTitle.setBackground(gameOptions.getGameBackgroundColor());
    finalScoreTitle.setForeground(Color.BLACK);
    finalScoreTitle.setOpaque(true);
    finalScoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    finalScoreTitle.setFont(titleFont);
    
    // Creates and designs new high score text
    newHighScore = new JLabel("NEW HIGH SCORE!");
    newHighScore.setHorizontalAlignment(SwingConstants.CENTER);
    newHighScore.setBackground(gameOptions.getGameBackgroundColor());
    newHighScore.setForeground(gameOptions.getHighlightColor());
    newHighScore.setOpaque(true);
    newHighScore.setVisible(false);
    newHighScore.setAlignmentX(Component.CENTER_ALIGNMENT);
    newHighScore.setFont(buttonFont);
    
    // Creates and designs current high score display text
    finalScoreDisplay = new JLabel("");
    finalScoreDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    finalScoreDisplay.setBackground(gameOptions.getGameBackgroundColor());
    finalScoreDisplay.setForeground(gameOptions.getHighlightColor());
    finalScoreDisplay.setOpaque(true);
    finalScoreDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
    finalScoreDisplay.setFont(buttonFont);
    
    // Creates and designs RETRY button
    JLabel retry = new JLabel(RETRY_MENU_ITEM);
    retry.setHorizontalAlignment(SwingConstants.CENTER);
    retry.setBackground(gameOptions.getGameBackgroundColor());
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
    
    setLayout(boxLayout);
    add(titleBar);
    setBackground(gameOptions.getGameBackgroundColor());
    add(cardHolder);
    this.setTitle(WINDOW_TITLE);
    this.addKeyListener(new NumbreakaKeyAdapter(numbreaka, this));
    int width = gameOptions.getGridX()*gameOptions.getGridSquareWidth()+8;
    int height = gameOptions.getGridY()*gameOptions.getGridSquareHeight()+gameOptions.getGridY()+128;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int monitorWidth = (int) screenSize.getWidth();
    int monitorHeight = (int) screenSize.getHeight();
    int startX = (monitorWidth - width)/2;
    int startY = (monitorHeight - height)/2;
    this.setBounds(startX, startY, width, height);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setAlwaysOnTop(true);
    this.setResizable(false);
    this.setVisible(true);
  }
  
  public void resetGame() {
    newHighScore.setVisible(false);
    powerupTitleBox.setText("");
    currentNumberTitleBox.setText(Integer.toString(numbreaka.getCurrentNumber()));
    currentNumberTitleBox.setFont(gameOptions.getGridSquareDefaultFont());
    displayMainMenu();
    for (GridSquare[] gridSquareArray : gridSquares) {
      for (GridSquare gs : gridSquareArray) {
        gs.reset();
      }
    }
  }
  
  public void displayMainMenu() {
    cardLayout.show(cardHolder, MAIN_MENU_CARD);
  }
  
  public void displayGameWindow() {
    cardLayout.show(cardHolder, GAME_WINDOW_CARD);
  }

  public void displayHighScoresScreen() {
    if (!isHighScoresScreenInitialized) {
      setupHighScoresScreen();
      isHighScoresScreenInitialized = true;
    }

    String first = numbreaka.getHighScore(1);
    String second = numbreaka.getHighScore(2);
    String third = numbreaka.getHighScore(3);
    if (!first.equals("")) {
      firstHighScore.setText("1: " + numbreaka.getHighScore(1));  
    } else {
      firstHighScore.setText(DEFAULT_FIRST_HIGH_SCORE);
    }
    if (!second.equals("")) {
      secondHighScore.setText("2: " + numbreaka.getHighScore(2));  
    } else {
      secondHighScore.setText(DEFAULT_SECOND_HIGH_SCORE);
    }
    if (!third.equals("")) {
      thirdHighScore.setText("3: " + numbreaka.getHighScore(3));  
    } else {
      thirdHighScore.setText(DEFAULT_THIRD_HIGH_SCORE);
    }
    cardLayout.show(cardHolder, HIGH_SCORES_CARD);
  }
  
  public void displayFinalScorePopup(int score, boolean isNewHighScore) {
    finalScoreDisplay.setText(Integer.toString(score));
    if (isNewHighScore) {
      // clear this on reset
      newHighScore.setVisible(true);
    }
    cardLayout.show(cardHolder, FINAL_SCORE_CARD);
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
    String currentNumberString = Integer.toString(numbreaka.getCurrentNumber());
    int currentNumberLength = currentNumberString.length();
    updateHelperFontIfNecessary(gameOptions.getGridSquareFontOfSize(currentNumberLength));
    currentNumberTitleBox.setText(currentNumberString);
  }
  
  private void updateHelperFontIfNecessary(Font newFont) {
    if (currentNumberTitleBox.getFont() != newFont) {
      currentNumberTitleBox.setFont(newFont);
    }
  }
  
  public void updatePowerup(Numbreaka.Powerup powerup) {
    switch (powerup) {
    case NOTHING:
      powerupTitleBox.setText("");
      break;
    case MULTIPLY:
      powerupTitleBox.setText("x2");
      break;
    case VERTICAL:
      powerupTitleBox.setText("U-D");
      break;
    case HORIZONTAL:
      powerupTitleBox.setText("L-R");
      break;
    case RIGHT_ROTATION:
      powerupTitleBox.setText("-->");
      break;
    case LEFT_ROTATION:
      powerupTitleBox.setText("<--");
      break;
    case REVERSE:
      powerupTitleBox.setText("<=>");
      break;
    case CONSOLIDATE:
      powerupTitleBox.setText("CON");
      break;
    case RESURRECT:
      powerupTitleBox.setText("REZ");
      break;
    default:
      throw new IllegalArgumentException();
    }
  }
  
  public void highlightAsNecessary(JLabel gameTextLabel) {
    String gameText = gameTextLabel.getText();
    if (gameText.equals(START_MENU_ITEM) || gameText.equals(QUIT_MENU_ITEM) || gameText.equals(HIGH_SCORES_MENU_ITEM)
        || gameText.equals(RETRY_MENU_ITEM) || gameText.equals(RETURN_TO_MAIN_MENU_MENU_ITEM)) {
        highlight(gameTextLabel);
    }
  }
  
  public void highlight(JLabel gameTextLabel) {
    gameTextLabel.setForeground(gameOptions.getHighlightColor());
  }
  
  public void returnColorAsNecessary(JLabel gameTextLabel) {
    String gameText = gameTextLabel.getText();
    if (gameText.equals(START_MENU_ITEM) || gameText.equals(QUIT_MENU_ITEM) || gameText.equals(HIGH_SCORES_MENU_ITEM)) {
      gameTextLabel.setForeground(Color.DARK_GRAY);
    } else if (gameText.equals(RETRY_MENU_ITEM) || gameText.equals(RETURN_TO_MAIN_MENU_MENU_ITEM) || gameText.equals(TITLE_TEXT)) {
      gameTextLabel.setForeground(Color.BLACK);
    }
  }
  
  public void performActionAsNecessary(JLabel gameTextLabel) {
    String gameText = gameTextLabel.getText();
    switch (gameText) {
    case START_MENU_ITEM:
      displayGameWindow();
      break;
    case QUIT_MENU_ITEM:
      numbreaka.quit();
      break;
    case HIGH_SCORES_MENU_ITEM:
      displayHighScoresScreen();
      break;
    case RETRY_MENU_ITEM:
      highlight(gameTextLabel);
      numbreaka.resetGame();
      displayGameWindow();
      break;
    case TITLE_TEXT:
      highlight(gameTextLabel);
      numbreaka.resetGame();
      displayMainMenu();
      break;
    case RETURN_TO_MAIN_MENU_MENU_ITEM:
      displayMainMenu();
      break;
    default:
      // Do nothing
    }
  }
  
  public void highlightGameTitle() {
    gameTitle.setForeground(gameOptions.getHighlightColor());
  }
  
  public void returnColorToGameTitle() {
    gameTitle.setForeground(Color.BLACK);
  }
}
