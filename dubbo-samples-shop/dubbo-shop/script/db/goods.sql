create table goods
(
    id      int            not null
        primary key,
    picture varchar(255)   null,
    name    varchar(255)   null,
    price   decimal(10, 2) null,
    `desc`  varchar(255)   null
);
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (1, '/assets/images/products/mug.jpg', '马克杯', 15.00, NULL);
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (2, '/assets/images/products/bamboo-glass-jar.jpg', '杯子', 16.00, NULL);
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (3, '/assets/images/products/candle-holder.jpg', '蜡烛架子', 26.00, NULL);
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (4, '/assets/images/products/hairdryer.jpg', '吹风机', 58.00, NULL);
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (5, '/assets/images/products/loafers.jpg', '平底便鞋', 56.00, NULL);
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (6, '/assets/images/products/salt-and-pepper-shakers.jpg', '摇壶', 30.00, '好货啊');
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (7, '/assets/images/products/sunglasses.jpg', '太阳镜', 23.00, '遮阳专用');
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (8, '/assets/images/products/tank-top.jpg', '吊带衫', 95.00, '方便易穿');
INSERT INTO `goods`(`id`, `picture`, `name`, `price`, `desc`) VALUES (9, '/assets/images/products/watch.jpg', '手表', 106.00, '彰显时尚');
