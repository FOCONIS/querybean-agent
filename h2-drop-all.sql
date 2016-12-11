alter table o_address drop constraint if exists fk_o_address_country_code;
drop index if exists ix_o_address_country_code;

alter table o_order drop constraint if exists fk_o_order_shipping_address_id;
drop index if exists ix_o_order_shipping_address_id;

alter table o_order_detail drop constraint if exists fk_o_order_detail_order_id;
drop index if exists ix_o_order_detail_order_id;

alter table o_order_detail drop constraint if exists fk_o_order_detail_product_id;
drop index if exists ix_o_order_detail_product_id;

drop table if exists o_address;

drop table if exists o_country;

drop table if exists o_order;

drop table if exists o_order_detail;

drop table if exists o_product;

