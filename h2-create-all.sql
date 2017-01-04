create table o_address (
  id                            bigint auto_increment not null,
  when_created                  timestamp,
  when_updated                  timestamp,
  line1                         varchar(255),
  line2                         varchar(255),
  city                          varchar(255),
  country_code                  varchar(255),
  version                       bigint not null,
  constraint pk_o_address primary key (id)
);

create table o_country (
  code                          varchar(255) not null,
  name                          varchar(255),
  constraint pk_o_country primary key (code)
);

create table o_order (
  id                            bigint auto_increment not null,
  when_created                  timestamp,
  when_updated                  timestamp,
  status                        integer,
  order_date                    date,
  ship_date                     date,
  shipping_address_id           bigint,
  version                       bigint not null,
  constraint ck_o_order_status check ( status in (0,1,2,3)),
  constraint pk_o_order primary key (id)
);

create table o_order_detail (
  id                            bigint auto_increment not null,
  when_created                  timestamp,
  when_updated                  timestamp,
  order_id                      bigint,
  order_qty                     integer,
  ship_qty                      integer,
  unit_price                    double,
  product_id                    bigint,
  version                       bigint not null,
  constraint pk_o_order_detail primary key (id)
);

create table o_product (
  id                            bigint auto_increment not null,
  when_created                  timestamp,
  when_updated                  timestamp,
  sku                           varchar(255),
  name                          varchar(255),
  version                       bigint not null,
  constraint pk_o_product primary key (id)
);

alter table o_address add constraint fk_o_address_country_code foreign key (country_code) references o_country (code) on delete restrict on update restrict;
create index ix_o_address_country_code on o_address (country_code);

alter table o_order add constraint fk_o_order_shipping_address_id foreign key (shipping_address_id) references o_address (id) on delete restrict on update restrict;
create index ix_o_order_shipping_address_id on o_order (shipping_address_id);

alter table o_order_detail add constraint fk_o_order_detail_order_id foreign key (order_id) references o_order (id) on delete restrict on update restrict;
create index ix_o_order_detail_order_id on o_order_detail (order_id);

alter table o_order_detail add constraint fk_o_order_detail_product_id foreign key (product_id) references o_product (id) on delete restrict on update restrict;
create index ix_o_order_detail_product_id on o_order_detail (product_id);

