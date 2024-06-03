--
-- Licensed to the Apache Software Foundation (ASF) under one or more
-- contributor license agreements.  See the NOTICE file distributed with
-- this work for additional information regarding copyright ownership.
-- The ASF licenses this file to You under the Apache License, Version 2.0
-- (the "License"); you may not use this file except in compliance with
-- the License.  You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

USE test_shop;

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
