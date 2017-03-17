package org.mockapp.util;

import java.lang.reflect.Constructor;
import org.junit.Test;

/**
 * Created by 4than.mustaqiim on 3/16/2017.
 */
public class GeneratorTest {

  @Test
  public void constructorTest() throws Exception {
    Constructor<Generator> generatorConstructor = Generator.class.getDeclaredConstructor();
    generatorConstructor.setAccessible(true);
    generatorConstructor.newInstance();
  }

  @Test
  public void generateCodeTest() throws Exception {
    Generator.generateCode();
  }

}
