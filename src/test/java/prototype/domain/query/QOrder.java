package prototype.domain.query;

import org.avaje.ebean.typequery.PEnum;
import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PSqlDate;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQRootBean;
import org.avaje.ebean.typequery.TypeQueryBean;
import prototype.domain.Order;
import prototype.domain.query.assoc.QAssocAddress;
import prototype.domain.query.assoc.QAssocOrderDetail;


@TypeQueryBean
public class QOrder extends TQRootBean<Order,QOrder> {

  public PLong<QOrder> id;
  public PLong<QOrder> version;
  public PTimestamp<QOrder> whenCreated;
  public PTimestamp<QOrder> whenUpdated;
  public PEnum<QOrder,Order.Status> status;
  public PSqlDate<QOrder> orderDate;
  public PSqlDate<QOrder> shipDate;

  public QAssocAddress<QOrder> shippingAddress;
  public QAssocOrderDetail<QOrder> details;

  public QOrder() {
    this(3);
  }
  public QOrder(int maxDepth) {
    super(Order.class);
    setRoot(this);
//    this.id = new PLong<QOrder>("id", this);
//    this.version = new PLong<>("version", this);
//    this.whenCreated = new PTimestamp<>("whenCreated", this);
//    this.whenUpdated = new PTimestamp<>("whenUpdated", this);
//    this.status = new PEnum<>("status", this);
//    this.orderDate = new PSqlDate<>("orderDate", this);
//    this.shipDate = new PSqlDate<>("shipDate", this);
//    this.customer = new QAssocCustomer<>("customer", this, maxDepth);
//    this.shippingAddress = new QAssocAddress<>("shippingAddress", this, maxDepth);
//    this.details = new QAssocOrderDetail<>("details", this, maxDepth);
  }

  /**
   * Construct for alias.
   */
  private QOrder(boolean alias) {
    super(alias);
    setRoot(this);
    this.id = new PLong<QOrder>("id", this);
    this.version = new PLong<QOrder>("version", this);
    this.whenCreated = new PTimestamp<QOrder>("whenCreated", this);
    this.whenUpdated = new PTimestamp<QOrder>("whenUpdated", this);
    this.status = new PEnum<QOrder,Order.Status>("status", this);
    this.orderDate = new PSqlDate<QOrder>("orderDate", this);
    this.shipDate = new PSqlDate<QOrder>("shipDate", this);
    this.shippingAddress = new QAssocAddress<QOrder>("shippingAddress", this);
    this.details = new QAssocOrderDetail<QOrder>("details", this);
  }
}
