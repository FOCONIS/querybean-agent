package prototype.query;

import org.avaje.ebean.typequery.agent.BaseTest;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;


public class FinderTest extends BaseTest {

  @Test
  public void test() {

    Finder finder = new Finder();
    QAddress qAddress = finder.simpleFind();

    assertNotNull(qAddress.version);
    assertNotNull(qAddress.country);
    assertNotNull(qAddress.city);
  }


  @Test
  public void testAssocBeanDepth() {

    Finder finder = new Finder();
    QOrder qOrder = finder.orderQuery();

    assertNotNull(qOrder.id);
    assertNotNull(qOrder.details.product);
  }


  @Test
  public void testAliasProperties() throws NoSuchFieldException, IllegalAccessException {

    QProduct alias = QProduct.alias();

    Field id = QProduct.class.getField("id");
    Object idValue = id.get(alias);
    assertNotNull(idValue);

    Field name = QProduct.class.getField("name");
    Object nameValue = name.get(alias);
    assertNotNull(nameValue);

    assertNotNull(alias.id);
    assertEquals("id", alias.id.toString());
    assertNotNull(alias.name);
    assertEquals("name", alias.name.toString());
    assertNotNull(alias.sku);
  }
}