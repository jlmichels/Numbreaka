public enum Menu {
  START("START"), QUIT("QUIT"), HIGH_SCORES("HIGH SCORES"), RETRY("RETRY"), RETURN_TO_MAIN_MENU("Return to Main Menu"), TITLE("NUMBREAKA");
  
  private final String name;
  
  private Menu(String s) {
    name = s;
  }
  
  @Override
  public String toString() {
    return name;
  }
}