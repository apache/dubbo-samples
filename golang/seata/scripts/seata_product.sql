/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : seata_product

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 07/06/2020 23:08:54
*/

CREATE database if NOT EXISTS `seata_product` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `seata_product`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for branch_transaction
-- ----------------------------
DROP TABLE IF EXISTS `branch_transaction`;
CREATE TABLE `branch_transaction` (
  `sysno` bigint(20) NOT NULL AUTO_INCREMENT,
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `args_json` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL COMMENT '1，初始化；2，已提交；3，已回滚',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sysno`) USING BTREE,
  UNIQUE KEY `xid` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='事务记录表';

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `sysno` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_sysno` bigint(20) unsigned NOT NULL,
  `account_qty` int(11) DEFAULT NULL COMMENT '财务库存',
  `available_qty` int(11) DEFAULT NULL COMMENT '可用库存',
  `allocated_qty` int(11) DEFAULT NULL COMMENT '分配库存',
  `adjust_locked_qty` int(11) DEFAULT NULL COMMENT '调整锁定库存',
  PRIMARY KEY (`sysno`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品库存';

-- ----------------------------
-- Records of inventory
-- ----------------------------
BEGIN;
INSERT INTO `inventory` VALUES (1, 1, 1000000, 1000000, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `sysno` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品名',
  `product_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_desc` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '描述',
  `product_desc_long` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '描述',
  `default_image_src` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `c3_sysno` bigint(20) DEFAULT NULL,
  `barcode` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `merchant_sysno` bigint(20) DEFAULT NULL,
  `merchant_productid` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1，待上架；2，上架；3，下架；4，售罄下架；5，违规下架',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `modify_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`sysno`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SKU';

-- ----------------------------
-- Records of product
-- ----------------------------
BEGIN;
INSERT INTO `product` VALUES (1, '刺力王', '从小喝到大的刺力王', '好喝好喝好好喝', '', 'https://img10.360buyimg.com/mobilecms/s500x500_jfs/t1/61921/34/1166/131384/5cf60a94E411eee07/1ee010f4142236c3.jpg', 0, '', 15, 5, 5, 5, 1, '', 1, '2019-05-28 03:36:17', '', '', '2019-06-06 01:37:36');
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_unionkey` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
