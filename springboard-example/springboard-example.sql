/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : springboard

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 12/05/2021 11:26:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
                            `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `status` tinyint NOT NULL,
                            `user_id` bigint UNSIGNED NOT NULL,
                            `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `created_time` datetime NULL DEFAULT NULL,
                            `updated_time` datetime NULL DEFAULT NULL,
                            `last_logged_in_time` datetime NULL DEFAULT NULL,
                            `last_logged_in_addr` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                            PRIMARY KEY (`username`) USING BTREE,
                            INDEX `idx_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('ching', 0, 3, '{bcrypt}$2a$10$QgT74QL/xnG0LHgnIQhkfOhZgjcWayi0xlzU.ppf6/QI99Ed2pTR6', '2021-05-12 11:25:38', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for identities_permissions
-- ----------------------------
DROP TABLE IF EXISTS `identities_permissions`;
CREATE TABLE `identities_permissions`  (
                                           `identity_id` bigint NOT NULL,
                                           `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           PRIMARY KEY (`identity_id`, `permission`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of identities_permissions
-- ----------------------------
INSERT INTO `identities_permissions` VALUES (2, 'troopers:create');
INSERT INTO `identities_permissions` VALUES (2, 'troopers:delete');
INSERT INTO `identities_permissions` VALUES (2, 'troopers:read');
INSERT INTO `identities_permissions` VALUES (2, 'troopers:update');
INSERT INTO `identities_permissions` VALUES (3, 'be-handsome');

-- ----------------------------
-- Table structure for identity
-- ----------------------------
DROP TABLE IF EXISTS `identity`;
CREATE TABLE `identity`  (
                             `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                             `type` tinyint UNSIGNED NOT NULL,
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `created_time` datetime NULL DEFAULT NULL,
                             `updated_time` datetime NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of identity
-- ----------------------------
INSERT INTO `identity` VALUES (1, 0, '<admin>', '2021-05-12 11:25:38', NULL);
INSERT INTO `identity` VALUES (2, 0, '<troppers_op>', '2021-05-12 11:25:38', NULL);
INSERT INTO `identity` VALUES (3, 1, 'Ching the nameless', NULL, NULL);

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
                                `user_id` bigint NOT NULL,
                                `role_id` bigint NOT NULL,
                                PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;
