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


public class GameFrame extends JFrame {
  
  private static final Color HIGHLIGHT_COLOR = new Color(134, 39, 39);
  private static final Color GAME_BACKGROUND_COLOR = new Color(202, 221, 221);
  private boolean isHighScoresScreenInitialized = false;
  private static final long serialVersionUID = 1L; // necessary?
  private CardLayout cardLayout;
  private Font buttonFont;
  private Font titleFont;
  private Font gridSquareFont;
  private GridSquare[][] gridSquares;
  private int firstScore;
  private int secondScore;
  private int thirdScore;
  private int gridX;
  private int gridY;
  private int gridSquareWidth = 100;
  private int gridSquareHeight = 100;
  private JLabel highScoresButton;
  private JLabel firstHighScore;
  private JLabel secondHighScore;
  private JLabel thirdHighScore;
  private JLabel newHighScore;
  private JLabel finalScoreDisplay;
  private JLabel leftTitleBox;
  private JLabel rightTitleBox;
  private JLabel gameTitle;
  private JPanel mainMenu;
  private JPanel gameWindow;
  private JPanel highScoresScreen;
  private JPanel cardHolder;
  private JPanel titleBar;
  private JPanel finalScoreScreen;
  private Numbreaka numbreaka;
  private String firstInitials;
  private String secondInitials;
  private String thirdInitials;
  private final MenuObjectMouseListener menuObjectMouseListener;
  private final GridSquareMouseListener gridSquareMouseListener;
    
  public GameFrame(Numbreaka numbreaka, int gridX, int gridY) {
    this.numbreaka = numbreaka;
    this.gridX = gridX;
    this.gridY = gridY;
    menuObjectMouseListener = new MenuObjectMouseListener(numbreaka, this, HIGHLIGHT_COLOR); // change to method
    gridSquareMouseListener = new GridSquareMouseListener(numbreaka, this);
    firstInitials = "";
    secondInitials = "";
    thirdInitials = "";
    
    titleBar = new JPanel();
    cardHolder = new JPanel();
    mainMenu = new JPanel();
    gameWindow = new JPanel();
    highScoresScreen = new JPanel();
    finalScoreScreen = new JPanel();
    cardLayout = new CardLayout();
    
    setupTitleBar();
    setupMainMenu();
    setupGameWindowGrid();
    setupFinalScoreScreen();
    setupCardHolder();
    setupGameFrame();
  }
  
  // Sets up title bar
  private void setupTitleBar() {
    BoxLayout boxLayout = new BoxLayout(titleBar, BoxLayout.X_AXIS);
    leftTitleBox = new JLabel("1");
    rightTitleBox = new JLabel("POW");
    titleFont = new Font("Verdana", Font.BOLD, 36);
    gridSquareFont = new Font("Verdana", Font.PLAIN, 66);

    // Creates left box to hold current number
    leftTitleBox.setBorder(numbreaka.getLineBorder());
    leftTitleBox.setBackground(GAME_BACKGROUND_COLOR);
    leftTitleBox.setForeground(Color.BLACK);
    leftTitleBox.setMaximumSize(new Dimension(100, 100));
    leftTitleBox.setPreferredSize(new Dimension(100, 100));
    leftTitleBox.setHorizontalAlignment(SwingConstants.CENTER);
    leftTitleBox.setFont(gridSquareFont);
    leftTitleBox.setOpaque(true);

    // Creates right box to hold powerups
    rightTitleBox.setBorder(numbreaka.getLineBorder());
    rightTitleBox.setBackground(GAME_BACKGROUND_COLOR);
    rightTitleBox.setForeground(HIGHLIGHT_COLOR);
    rightTitleBox.setMaximumSize(new Dimension(100, 100));
    rightTitleBox.setPreferredSize(new Dimension(100, 100));
    rightTitleBox.setHorizontalAlignment(SwingConstants.CENTER);
    rightTitleBox.setFont(new Font("Calibri", Font.BOLD, 43));
    rightTitleBox.setOpaque(true);
    
    // Creates and designs main menu title
    gameTitle = new JLabel("NUMBREAKA");
    gameTitle.setBackground(GAME_BACKGROUND_COLOR);
    gameTitle.setForeground(Color.BLACK);
    gameTitle.setMaximumSize(new Dimension(300, 100));
    gameTitle.setPreferredSize(new Dimension(300, 100));
    gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gameTitle.setOpaque(true);
    gameTitle.setBorder(numbreaka.getLineBorder());
    gameTitle.addMouseListener(menuObjectMouseListener);
    gameTitle.setFont(titleFont);
    
    titleBar.setSize(100,100);
    titleBar.setLayout(boxLayout);
    titleBar.add(Box.createRigidArea(new Dimension(0, 100)));
    titleBar.add(leftTitleBox);
    titleBar.add(Box.createHorizontalGlue());
    titleBar.add(gameTitle);
    titleBar.add(Box.createHorizontalGlue());
    titleBar.add(rightTitleBox);
    titleBar.setBorder(numbreaka.getLineBorder());
    titleBar.setOpaque(true);
    titleBar.setBackground(Color.RED);
    titleBar.setVisible(true);
  }
  
  // Sets up the main menu using BoxLayout
  private void setupMainMenu() {
    BoxLayout boxLayout = new BoxLayout(mainMenu, BoxLayout.Y_AXIS);
    buttonFont = new Font("Calibri", Font.BOLD, 55);
    
    mainMenu.setLayout(boxLayout);
    mainMenu.setOpaque(true);
    mainMenu.setBackground(GAME_BACKGROUND_COLOR);
    mainMenu.setBorder(numbreaka.getLineBorder());
    
    // Creates and designs START button
    JLabel startButton = new JLabel("START");
    startButton.addMouseListener(menuObjectMouseListener);
    startButton.setHorizontalAlignment(SwingConstants.CENTER);
    startButton.setBackground(GAME_BACKGROUND_COLOR);
    startButton.setOpaque(true);
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.setFont(buttonFont);
   
    // Designs highScoresButton
    highScoresButton = new JLabel("HIGH SCORES");
    highScoresButton.addMouseListener(menuObjectMouseListener);
    highScoresButton.setHorizontalAlignment(SwingConstants.CENTER);
    highScoresButton.setBackground(GAME_BACKGROUND_COLOR);
    highScoresButton.setOpaque(true);
    highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    highScoresButton.setFont(buttonFont);
    
    // Creates and designs QUIT button
    JLabel quitButton = new JLabel("QUIT");
    quitButton.addMouseListener(menuObjectMouseListener);
    quitButton.setHorizontalAlignment(SwingConstants.CENTER);
    quitButton.setBackground(GAME_BACKGROUND_COLOR);
    quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    quitButton.setOpaque(true);
    quitButton.setFont(buttonFont);
    
    // Adds components to main menu
    mainMenu.add(Box.createRigidArea(new Dimension(0,130)));
    mainMenu.add(startButton, BorderLayout.CENTER);
    mainMenu.add(Box.createRigidArea(new Dimension(0,10)));
    mainMenu.add(highScoresButton, BorderLayout.CENTER);
    mainMenu.add(Box.createRigidArea(new Dimension(0,10)));
    mainMenu.add(quitButton, BorderLayout.CENTER);
  }
  
  // Sets up game grid, creating all grid squares
  private void setupGameWindowGrid() {
    GridLayout gridLayout = new GridLayout(gridX, gridY);

    gameWindow.setLayout(gridLayout);
    gridLayout.setHgap(0);
    gridLayout.setVgap(0);
    gameWindow.setBorder(numbreaka.getLineBorder());
    gridSquares = new GridSquare[gridX][gridY];

    // Fills grid with new grid squares
    fillGridWithSquares();
    
    // Assigns surrounding grid square (4 directions) if they exist
    assignSurroundingGridSquares();
    
  }

  private void fillGridWithSquares() {
    // Variable used to assign an ID number to each grid square
    int id = 0;
    
    // Creates gridSquares and fills grid, assigning id numbers
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        gridSquares[i][j] = new GridSquare(id);
        gridSquares[i][j].setBorder(numbreaka.getLineBorder());
        gridSquares[i][j].addMouseListener(gridSquareMouseListener);
        gridSquares[i][j].setOpaque(true);
        gridSquares[i][j].setBackground(GAME_BACKGROUND_COLOR);
        gridSquares[i][j].setForeground(Color.BLACK);
        gridSquares[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        gridSquares[i][j].setFont(gridSquareFont);
        gameWindow.add(gridSquares[i][j]);
        
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

    highScoresScreen.setLayout(boxLayout);
    highScoresScreen.setOpaque(true);
    highScoresScreen.setBackground(GAME_BACKGROUND_COLOR);
    highScoresScreen.setBorder(numbreaka.getLineBorder());
    
    // Creates and designs High Scores screen title
    JLabel highScoresTitle = new JLabel("HIGH SCORES");
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
    JLabel returnToMainMenu = new JLabel("Return to Main Menu");
    returnToMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
    returnToMainMenu.setBackground(GAME_BACKGROUND_COLOR);
    returnToMainMenu.setForeground(Color.BLACK);
    returnToMainMenu.setOpaque(true);
    returnToMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    returnToMainMenu.addMouseListener(menuObjectMouseListener);
    returnToMainMenu.setFont(new Font("Calibri", Font.BOLD, 45));
    
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
  private void setupFinalScoreScreen() {
    BoxLayout boxLayout = new BoxLayout(finalScoreScreen, BoxLayout.Y_AXIS);

    finalScoreScreen.setLayout(boxLayout);
    finalScoreScreen.setOpaque(true);
    finalScoreScreen.setBackground(GAME_BACKGROUND_COLOR);
    finalScoreScreen.setBorder(numbreaka.getLineBorder());
    
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
    JLabel retry = new JLabel("RETRY");
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
  }
  
  private void setupCardHolder() {
    cardHolder.setLayout(cardLayout);
    cardHolder.add(mainMenu, "Main Menu");
    cardHolder.add(gameWindow, "Game Window");
    cardHolder.add(highScoresScreen, "High Scores");
    cardHolder.add(finalScoreScreen, "Final Score");
    cardHolder.setVisible(true);
  }
  
  private void setupGameFrame() {
    BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
    
    this.getContentPane().setLayout(boxLayout);
    this.getContentPane().add(titleBar);
    this.getContentPane().setBackground(GAME_BACKGROUND_COLOR);
    this.getContentPane().add(cardHolder);
    this.setTitle("Numbreaka v0.1");
//    this.addMouseListener(menuObjectMouseListener);
    this.addKeyListener(new NumbreakaKeyListener(numbreaka, this));
    this.setBounds(400, 300, (gridX*gridSquareWidth+8), (gridY*gridSquareHeight)+gridY+128);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setAlwaysOnTop(true);
    this.setResizable(false);
    this.setVisible(true);
  }

  // Turns all grid square numbers RED
  // Not used anymore?
  public void highlightNumbers() {
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        gridSquares[i][j].setForeground(HIGHLIGHT_COLOR);
      }
    }
  }  
  
  // Resets game to initial conditions
  public void reset() {
    highScoresButton.setForeground(Color.DARK_GRAY);
    newHighScore.setVisible(false);
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        gridSquares[i][j].reset(GAME_BACKGROUND_COLOR);
      }
    }
  }
  
  public void displayMainMenu() {
    cardLayout.show(cardHolder, "Main Menu");
    this.repaint();
  }
  
  public void displayGameWindow() {
    cardLayout.show(cardHolder, "Game Window");
    this.repaint();
  }

  public void displayHighScoresScreen() {
    if (!isHighScoresScreenInitialized) {
      setupHighScoresScreen();
      isHighScoresScreenInitialized = true;
    }
    
    getHighScores();
    setHighScores();
    
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
    cardLayout.show(cardHolder, "High Scores");
    this.repaint();
  }
  
  private void getHighScores() {
    // Gets high scores and sets to temp values for readability
    TreeMap<Integer, String> tempHighScores = numbreaka.getHighScores();
    
    if (tempHighScores.lastEntry() != null) {
      firstInitials = tempHighScores.lastEntry().getValue();
      firstScore = tempHighScores.lastKey();
    }
    if (tempHighScores.lowerEntry(firstScore) != null) {
      secondInitials = tempHighScores.lowerEntry(firstScore).getValue();
      secondScore = tempHighScores.lowerKey(firstScore);
    }
    if (tempHighScores.lowerEntry(secondScore) != null) {
      thirdInitials = tempHighScores.lowerEntry(secondScore).getValue();
      thirdScore = tempHighScores.lowerKey(secondScore);
    }
  }
  
  private void setHighScores() {
    numbreaka.setHighScores(firstInitials, firstScore, secondInitials, secondScore, thirdInitials, thirdScore);
  }
  
  public void displayFinalScorePopup(int score, boolean isHighScore) {
    finalScoreDisplay.setText(Integer.toString(score));
    if (isHighScore) {
      // clear this on reset
      newHighScore.setVisible(true);
    }
    getHighScores();
    setHighScores();
    cardLayout.show(cardHolder, "Final Score");
    this.repaint();
  }
   
  public GridSquare[][] getGridSquares() {
    return gridSquares;
  }
  
  public JLabel getLeftTitleBox() {
    return leftTitleBox;
  }
  
  public JLabel getRightTitleBox() {
    return rightTitleBox;
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
}
