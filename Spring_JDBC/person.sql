/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : person

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2014-08-18 08:56:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('2', 'wang');
INSERT INTO `person` VALUES ('3', 'wang0');
INSERT INTO `person` VALUES ('4', 'wang1');
INSERT INTO `person` VALUES ('5', 'wang2');
INSERT INTO `person` VALUES ('6', 'wang3');
INSERT INTO `person` VALUES ('7', 'wang4');
INSERT INTO `person` VALUES ('8', 'wang0');
INSERT INTO `person` VALUES ('9', 'wang1');
INSERT INTO `person` VALUES ('10', 'wang2');
INSERT INTO `person` VALUES ('11', 'wang3');
INSERT INTO `person` VALUES ('12', 'wang4');
