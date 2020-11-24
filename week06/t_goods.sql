drop table if exists t_goods;

/*==============================================================*/
/* Table: t_goods                                               */
/*==============================================================*/
create table t_goods
(
   id                   int not null auto_increment comment '商品主键id',
   goodsType            int comment '商品类型',
   goodsName            varchar(255) comment '商品名称',
   productAddr          int(6) comment '生产地区区域编码',
   weight               double comment '重量，单位：kg',
   volume               double comment '体积，立方米',
   primeCost            double comment '成本价，元',
   fixedPrice           double comment '定价，元',
   primary key (id)
);

alter table t_goods comment '商品';