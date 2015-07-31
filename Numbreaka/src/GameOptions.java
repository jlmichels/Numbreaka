import java.awt.Color;
import java.awt.Font;

import javax.swing.border.LineBorder;

// TODO Make available in options menu or similar
public class GameOptions {
  private static final int GRID_X = 6;
  private static final int GRID_Y = 6;
  private static final int NUMBER_OF_GRID_SQUARES = GRID_X * GRID_Y;
  private static final Color HIGHLIGHT_COLOR = new Color(134, 39, 39);
  private static final Color GAME_BACKGROUND_COLOR = new Color(202, 221, 221);
  private static final LineBorder BLACK_LINE_BORDER = new LineBorder(Color.BLACK);
  private static final int GRID_SQUARE_WIDTH = 100;
  private static final int GRID_SQUARE_HEIGHT = 100;
  private static final String PRIMARY_FONT_NAME = "Verdana"; // change these names
  private static final String SECONDARY_FONT_NAME = "Calibri";
  
  public Font getGridSquareDefaultFont() {
    return getGridSquareFontOfSize(2);
  }
  
  public Font getGridSquareFontOfSize(int size) {
    switch (size) {
    case 2:   return makeGridSquareFont(66);
    case 3:   return makeGridSquareFont(49);
    case 4:   return makeGridSquareFont(37);
    case 5:   return makeGridSquareFont(30);
    case 6:   return makeGridSquareFont(25);
    default:  return makeGridSquareFont(66);
    }
  }
  
  private Font makeGridSquareFont(int size) {
    return new Font(PRIMARY_FONT_NAME, Font.PLAIN, size);
  }
    
  public String getPrimaryFontName() {
    return PRIMARY_FONT_NAME;
  }
  
  public String getSecondaryFontName() {
    return SECONDARY_FONT_NAME;
  }
  
  public int getGridX() {
    return GRID_X;
  }
  
  public int getGridY() {
    return GRID_Y;
  }
  
  public int getNumberOfGridSquares() {
    return NUMBER_OF_GRID_SQUARES;
  }
  
  public Color getHighlightColor() {
    return HIGHLIGHT_COLOR;
  }
  
  public Color getGameBackgroundColor() {
    return GAME_BACKGROUND_COLOR;
  }
  
  public LineBorder getBlackLineBorder() {
    return BLACK_LINE_BORDER;
  }
  
  public int getGridSquareWidth() {
    return GRID_SQUARE_WIDTH;
  }
  
  public int getGridSquareHeight() {
    return GRID_SQUARE_HEIGHT;
  }
}
