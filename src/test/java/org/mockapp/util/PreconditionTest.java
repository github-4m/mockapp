package org.mockapp.util;

import java.lang.reflect.Constructor;
import org.junit.Test;

/**
 * Created by fathan.mustaqiim on 11/25/2016.
 */
public class PreconditionTest {

  @Test
  public void constructorTest() throws Exception {
    Constructor<Precondition> preconditionConstructor = Precondition.class.getDeclaredConstructor();
    preconditionConstructor.setAccessible(true);
    preconditionConstructor.newInstance();
  }
}
