/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3307
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3307
 Source Schema         : jimmer-code-gen-test

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 07/08/2023 14:46:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for single_test_table
-- ----------------------------
DROP TABLE IF EXISTS `single_test`;
CREATE TABLE `single_test`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'John Doe' COMMENT '姓名',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `salary` decimal(10, 2) NULL DEFAULT 1000.00 COMMENT '薪水',
  `is_employed` tinyint(1) NULL DEFAULT 1 COMMENT '是否就业',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'example@example.com' COMMENT '电子邮件',
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '地址',
  `birthdate` date NULL DEFAULT '2000-01-01' COMMENT '出生日期',
  `join_date` datetime(0) NULL DEFAULT '2020-01-01 00:00:00' COMMENT '加入日期',
  `rating` float NULL DEFAULT 3.5 COMMENT '评级',
  `is_active` tinyint(1) NULL DEFAULT 0 COMMENT '是否活跃',
  `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '+1234567890' COMMENT '电话号码',
  `department_id` smallint(0) NULL DEFAULT 1 COMMENT '部门ID',
  `years_of_experience` double NULL DEFAULT 2.5 COMMENT '工作经验',
  `is_manager` bit(1) NULL DEFAULT b'0' COMMENT '是否经理',
  `hire_date` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '雇佣日期',
  `start_time` time(0) NULL DEFAULT '09:00:00' COMMENT '开始时间',
  `end_time` time(0) NULL DEFAULT '17:00:00' COMMENT '结束时间',
  `is_remote` enum('Yes','No') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'No' COMMENT '是否远程',
  `favorite_color` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Blue' COMMENT '喜欢的颜色',
  `website_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://example.com' COMMENT '网站URL',
  `is_verified` int(0) NULL DEFAULT 1 COMMENT '是否已验证',
  `weight` decimal(5, 2) NULL DEFAULT 60.00 COMMENT '体重',
  `gender` enum('Male','Female','Other') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Other' COMMENT '性别',
  `is_married` int(0) NULL DEFAULT 0 COMMENT '是否已婚',
  `bio` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '个人简介',
  `hire_type` enum('Full-time','Part-time','Contractor') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Full-time' COMMENT '雇佣类型',
  `zipcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '12345' COMMENT '邮政编码',
  `last_login` datetime(0) NULL DEFAULT '2022-01-01 12:00:00' COMMENT '最后登录时间',
  `is_admin` tinyint(1) NULL DEFAULT 0 COMMENT '是否管理员',
  `country_code` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'US' COMMENT '国家代码',
  `vacation_days` smallint(0) NULL DEFAULT 20 COMMENT '年假天数',
  `is_active_user` enum('Active','Inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Active' COMMENT '是否活跃用户',
  `profile_image` blob NULL COMMENT '个人资料图片',
  `registration_date` date NULL DEFAULT '2022-01-01' COMMENT '注册日期',
  `hourly_rate` decimal(10, 2) NULL DEFAULT 50.00 COMMENT '时薪',
  `is_retired` tinyint(1) NULL DEFAULT 0 COMMENT '是否退休',
  `membership_level` enum('Basic','Premium','VIP') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Basic' COMMENT '会员等级',
  `language_code` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'ENG' COMMENT '语言代码',
  `is_approved` tinyint(1) NULL DEFAULT 1 COMMENT '是否已批准',
  `skill_set` json NULL COMMENT '技能集合',
  `education_level` enum('High School','Bachelor','Master','PhD') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Bachelor' COMMENT '教育水平',
  `notes` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of single_test_table
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
