drop table if exists t_consumer;

/*==============================================================*/
/* Table: t_consumer                                            */
/*==============================================================*/
create table t_consumer
(
   id                   int not null auto_increment comment '主键id',
   nickName             varchar(255) not null comment '昵称',
   gender               int(2) comment '性别',
   birthday             datetime comment '出生日期',
   telephoneNum         int not null comment '手机号码',
   registerAddrCode     int(6) not null comment '注册地区域编码',
   mostLikeType1        int comment '第一喜欢商品类型id',
   mostLikeType2        int comment '第二喜欢商品类型id',
   primary key (id)
);

alter table t_consumer comment '用户表';