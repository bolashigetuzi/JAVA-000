drop table if exists t_order;

/*==============================================================*/
/* Table: t_order                                               */
/*==============================================================*/
create table t_order
(
   id                   int not null auto_increment comment '订单id',
   consumerId           int not null comment '用户id',
   goodsId              int not null comment '商品id',
   payCost              double not null comment '付款金额',
   businessTime         datetime comment '交易时间',
   remark               varchar(255) comment '交易备注',
   primary key (id)
);

alter table t_order comment '订单';
