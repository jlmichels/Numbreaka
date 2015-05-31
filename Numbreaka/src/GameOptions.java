import java.awt.Color;
import java.awt.Font;

import javax.swing.border.LineBorder;

// TODO Make available in options menu or similar
public class GameOptions {
  private static final int GRID_X = 7;
  private static final int GRID_Y = 7;
  private static final int NUMBER_OF_GRID_SQUARES = GRID_X * GRID_Y;
  private static final Color HIGHLIGHT_COLOR = new Color(134, 39, 39);
  private static final Color GAME_BACKGROUND_COLOR = new Color(202, 221, 221);
  private static final LineBorder BLACK_LINE_BORDER = new LineBorder(Color.BLACK);
  private static final int GRID_SQUARE_WIDTH = 100;
  private static final int GRID_SQUARE_HEIGHT = 100;
  private static final String PRIMARY_FONT_NAME = "Verdana"; // change these names
  private static final String SECONDARY_FONT_NAME = "Calibri";
  private static final Font GRID_SQUARE_TWO_DIGIT_FONT = new Font(PRIMARY_FONT_NAME, Font.PLAIN, 66);
  private static final Font GRID_SQUARE_THREE_DIGIT_FONT = new Font(PRIMARY_FONT_NAME, Font.PLAIN, 49);
  private static final Font GRID_SQUARE_FOUR_DIGIT_FONT = new Font(PRIMARY_FONT_NAME, Font.PLAIN, 37);
  private static final Font GRID_SQUARE_FIVE_DIGIT_FONT = new Font(PRIMARY_FONT_NAME, Font.PLAIN, 30);
  private static final Font GRID_SQUARE_SIX_DIGIT_FONT = new Font(PRIMARY_FONT_NAME, Font.PLAIN, 25);
  
  public Font getSixDigitFont() {
    return GRID_SQUARE_SIX_DIGIT_FONT;
  }
  
  public Font getFiveDigitFont() {
    return GRID_SQUARE_FIVE_DIGIT_FONT;
  }
  
  public Font getFourDigitFont() {
    return GRID_SQUARE_FOUR_DIGIT_FONT;
  }
  
  public Font getThreeDigitFont() {
    return GRID_SQUARE_THREE_DIGIT_FONT;
  }
  
  public Font getTwoDigitFont() {
    return GRID_SQUARE_TWO_DIGIT_FONT;
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
