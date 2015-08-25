package prototype.query.assoc;

import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.TQPath;
import org.avaje.ebean.typequery.TypeQueryBean;

@TypeQueryBean
public class QAssocCountry<R> {

  public PString<R> code;
  public PString<R> name;

  public QAssocCountry(String name, R root, int depth) {
    this(name, root, null, depth);
  }
  public QAssocCountry(String name, R root, String prefix, int depth) {
    String path = TQPath.add(prefix, name);
    this.code = new PString<R>("code", root, path);
    this.name = new PString<R>("name", root, path);
  }
}
