package id.co.blogspot.fathan.util;

/**
 * Created by 4than.mustaqiim on 10/25/2016.
 */
public final class Precondition {

  private Precondition() {
  }

  public static void checkArgument(boolean condition, String errorMessage) throws Exception {
    if (!condition) {
      throw new Exception(errorMessage);
    }
  }

}
