/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : sportapp

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 05/03/2026 17:12:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for run_record
-- ----------------------------
DROP TABLE IF EXISTS `run_record`;
CREATE TABLE `run_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '关联sys_user的id',
  `distance` decimal(10, 2) NOT NULL COMMENT '跑步里程(km)',
  `duration` int(11) NOT NULL COMMENT '跑步时长(秒)',
  `path_line` json NULL COMMENT '轨迹坐标点数组(JSON字符串)',
  `semester` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属学期(如:2025-Spring)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NULL DEFAULT 1 COMMENT '状态: 1有效, 0无效/作弊',
  `invalid_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '无效原因(如:速度过快)',
  `appeal_status` int(1) NULL DEFAULT 0 COMMENT '申诉状态: 0未申诉, 1申诉中, 2申诉通过, 3申诉驳回',
  `step_count` int(11) NULL DEFAULT 0 COMMENT '步数(辅助防作弊)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '跑步记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of run_record
-- ----------------------------
INSERT INTO `run_record` VALUES (1, 1, 0.01, 53, '[{\"latitude\": 23.02067, \"longitude\": 113.75179}]', NULL, '2026-02-03 14:00:08', 1, NULL, 0, 0);
INSERT INTO `run_record` VALUES (2, 1, 0.14, 18, '[{\"latitude\": 22.82009765625, \"longitude\": 113.67453993055555}, {\"latitude\": 22.82009765625, \"longitude\": 113.67453993055555}, {\"latitude\": 22.82094753689236, \"longitude\": 113.67428955078124}, {\"latitude\": 22.821027560763888, \"longitude\": 113.67428955078124}, {\"latitude\": 22.82102484809028, \"longitude\": 113.67436686197917}, {\"latitude\": 22.82094970703125, \"longitude\": 113.6743497721354}, {\"latitude\": 22.82094563802083, \"longitude\": 113.67434163411458}, {\"latitude\": 22.820959743923613, \"longitude\": 113.67434868706596}, {\"latitude\": 22.820970594618057, \"longitude\": 113.6743497721354}, {\"latitude\": 22.820975748697915, \"longitude\": 113.67434868706596}, {\"latitude\": 22.82099582248264, \"longitude\": 113.67435953776042}, {\"latitude\": 22.821004774305557, \"longitude\": 113.67435465494792}, {\"latitude\": 22.820999620225695, \"longitude\": 113.67435953776042}, {\"latitude\": 22.821002604166665, \"longitude\": 113.67436903211804}, {\"latitude\": 22.821000705295138, \"longitude\": 113.67436984592014}, {\"latitude\": 22.821019694010417, \"longitude\": 113.67436984592014}]', NULL, NULL, 1, NULL, NULL, 176);
INSERT INTO `run_record` VALUES (3, 1, 0.10, 17, '[{\"latitude\": 22.82151394314236, \"longitude\": 113.67377875434028}, {\"latitude\": 22.82151394314236, \"longitude\": 113.67377875434028}, {\"latitude\": 22.8214501953125, \"longitude\": 113.6739830186632}, {\"latitude\": 22.821435275607637, \"longitude\": 113.67399848090278}, {\"latitude\": 22.821394314236112, \"longitude\": 113.67406439887152}, {\"latitude\": 22.821387532552084, \"longitude\": 113.67409830729169}, {\"latitude\": 22.82134548611111, \"longitude\": 113.67417371961804}, {\"latitude\": 22.821344401041667, \"longitude\": 113.67417371961804}, {\"latitude\": 22.821339518229166, \"longitude\": 113.6741712782118}, {\"latitude\": 22.820982801649304, \"longitude\": 113.6743345811632}, {\"latitude\": 22.82099066840278, \"longitude\": 113.6743386501736}, {\"latitude\": 22.82097764756945, \"longitude\": 113.67436062282988}, {\"latitude\": 22.82097764756945, \"longitude\": 113.67438096788194}, {\"latitude\": 22.821005859375, \"longitude\": 113.674375}, {\"latitude\": 22.82102267795139, \"longitude\": 113.67435763888888}, {\"latitude\": 22.821047634548613, \"longitude\": 113.67434353298611}]', NULL, NULL, 1, NULL, NULL, 130);
INSERT INTO `run_record` VALUES (4, 1, 0.13, 14, '[{\"latitude\": 22.82151394314236, \"longitude\": 113.67377875434028}, {\"latitude\": 22.820914442274304, \"longitude\": 113.67425971137152}, {\"latitude\": 22.820935601128472, \"longitude\": 113.6743386501736}, {\"latitude\": 22.82094970703125, \"longitude\": 113.6743497721354}, {\"latitude\": 22.820955674913193, \"longitude\": 113.6743820529514}, {\"latitude\": 22.820959743923613, \"longitude\": 113.6744099934896}, {\"latitude\": 22.820962727864583, \"longitude\": 113.67440700954862}, {\"latitude\": 22.820952690972224, \"longitude\": 113.67439697265624}, {\"latitude\": 22.820952690972224, \"longitude\": 113.67438883463542}, {\"latitude\": 22.820967610677084, \"longitude\": 113.67436496310764}, {\"latitude\": 22.82111300998264, \"longitude\": 113.6744580078125}, {\"latitude\": 22.821114095052085, \"longitude\": 113.6744558376736}, {\"latitude\": 22.82114773220486, \"longitude\": 113.6744118923611}, {\"latitude\": 22.821143934461805, \"longitude\": 113.67442084418404}]', NULL, NULL, 1, NULL, NULL, 169);
INSERT INTO `run_record` VALUES (5, 1, 0.05, 19, '[{\"latitude\": 22.821280110677083, \"longitude\": 113.6740163845486}, {\"latitude\": 22.821119520399307, \"longitude\": 113.67425862630208}, {\"latitude\": 22.821115451388888, \"longitude\": 113.6742466905382}, {\"latitude\": 22.82111436631945, \"longitude\": 113.6742466905382}, {\"latitude\": 22.821107584635417, \"longitude\": 113.67426676432292}, {\"latitude\": 22.821124674479165, \"longitude\": 113.67430962456598}, {\"latitude\": 22.82112575954861, \"longitude\": 113.67431667751735}, {\"latitude\": 22.821124674479165, \"longitude\": 113.6743136935764}, {\"latitude\": 22.821124674479165, \"longitude\": 113.67431179470486}, {\"latitude\": 22.82112277560764, \"longitude\": 113.6743505859375}, {\"latitude\": 22.8211328125, \"longitude\": 113.67434163411458}, {\"latitude\": 22.821129828559027, \"longitude\": 113.67434054904514}, {\"latitude\": 22.821123860677083, \"longitude\": 113.67433078342016}, {\"latitude\": 22.821119791666668, \"longitude\": 113.67432562934027}, {\"latitude\": 22.821118706597225, \"longitude\": 113.67432671440972}, {\"latitude\": 22.821118706597225, \"longitude\": 113.6743226453993}, {\"latitude\": 22.821119791666668, \"longitude\": 113.67432074652778}, {\"latitude\": 22.821119791666668, \"longitude\": 113.6743226453993}]', NULL, NULL, 1, NULL, NULL, 63);
INSERT INTO `run_record` VALUES (6, 1, 1.20, 12, '[{\"latitude\": 22.821409776475697, \"longitude\": 113.6737939453125}, {\"latitude\": 22.820989854600693, \"longitude\": 113.67435356987848}, {\"latitude\": 22.820959743923613, \"longitude\": 113.67437201605902}, {\"latitude\": 22.821020779079863, \"longitude\": 113.67437201605902}, {\"latitude\": 22.82105278862847, \"longitude\": 113.67438883463542}, {\"latitude\": 22.821067708333334, \"longitude\": 113.67440402560764}, {\"latitude\": 22.821084798177083, \"longitude\": 113.674375}, {\"latitude\": 22.821094835069445, \"longitude\": 113.67437689887151}, {\"latitude\": 22.82109076605903, \"longitude\": 113.67436984592014}, {\"latitude\": 22.82120171440972, \"longitude\": 113.6743386501736}, {\"latitude\": 22.821194661458332, \"longitude\": 113.67433566623264}, {\"latitude\": 22.821194661458332, \"longitude\": 113.6743337673611}, {\"latitude\": 22.82119384765625, \"longitude\": 113.67433566623264}]', '2026-2027-1', NULL, 1, NULL, NULL, 143);
INSERT INTO `run_record` VALUES (7, 1, 0.00, 14000, '[{\"latitude\": 23.02067, \"longitude\": 113.75179}]', NULL, NULL, 0, '距离不足500米', NULL, 0);
INSERT INTO `run_record` VALUES (8, 1, 0.00, 3, '[{\"latitude\": 23.02067, \"longitude\": 113.75179}]', NULL, NULL, 0, '距离不足500米', 1, 0);
INSERT INTO `run_record` VALUES (9, 1, 0.01, 39, '[{\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.357958984375, \"longitude\": 116.69070122612848}, {\"latitude\": 24.357958984375, \"longitude\": 116.69070122612848}, {\"latitude\": 24.357958984375, \"longitude\": 116.69070122612848}, {\"latitude\": 24.357958984375, \"longitude\": 116.69070122612848}, {\"latitude\": 24.357935926649304, \"longitude\": 116.69076144748264}, {\"latitude\": 24.357935926649304, \"longitude\": 116.69076144748264}, {\"latitude\": 24.357935926649304, \"longitude\": 116.69076144748264}, {\"latitude\": 24.357935926649304, \"longitude\": 116.69076144748264}, {\"latitude\": 24.357935926649304, \"longitude\": 116.69076144748264}, {\"latitude\": 24.357935926649304, \"longitude\": 116.69076144748264}, {\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.35826877170139, \"longitude\": 116.68955647786458}, {\"latitude\": 24.357958984375, \"longitude\": 116.69070122612848}]', NULL, NULL, 0, '配速过慢，未达到跑步标准', 3, 8);
INSERT INTO `run_record` VALUES (10, 1, 0.00, 10, '[{\"latitude\": 23.129163, \"longitude\": 113.264435}]', NULL, '2026-02-19 10:51:27', 0, '距离不足500米', 3, 0);

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) NOT NULL COMMENT '发送者(老师ID)',
  `receiver_id` int(11) NOT NULL COMMENT '接收者(学生ID)',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型: remind(督促), like(点赞)',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `is_read` int(1) NULL DEFAULT 0 COMMENT '是否已读',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_message
-- ----------------------------
INSERT INTO `sys_message` VALUES (1, 2, 6, 'remind', '你的跑步里程尚未达标，老师喊你起来跑步啦！🏃‍♂️', 0, '2026-02-26 16:02:34');
INSERT INTO `sys_message` VALUES (2, 2, 1, 'remind', '你的跑步里程尚未达标，老师喊你起来跑步啦！🏃‍♂️', 1, '2026-02-26 16:04:11');
INSERT INTO `sys_message` VALUES (3, 2, 1, 'like', '真棒！你的跑步成绩已达标，老师为你点赞！👍', 1, '2026-02-27 17:58:33');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `publisher` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '管理员' COMMENT '发布人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `is_top` int(1) NULL DEFAULT 0 COMMENT '是否置顶: 1是 0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '关于本学期晨跑的通知', '请各位同学注意，本学期晨跑需达到100公里...', '管理员', '2026-02-27 14:22:20', 1);

-- ----------------------------
-- Table structure for sys_semester_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_semester_config`;
CREATE TABLE `sys_semester_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `semester` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学期，如 2025-2026-2',
  `target_distance` int(11) NOT NULL DEFAULT 100 COMMENT '目标里程(公里)',
  `is_current` int(1) NULL DEFAULT 0 COMMENT '是否当前学期: 1是 0否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_semester`(`semester`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学期目标配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_semester_config
-- ----------------------------
INSERT INTO `sys_semester_config` VALUES (1, '2024-2025-1', 80, 0);
INSERT INTO `sys_semester_config` VALUES (2, '2025-2026-2', 18, 0);
INSERT INTO `sys_semester_config` VALUES (3, '2026-2027-1', 1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号(学号/工号)',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色标识：student 或 teacher',
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班级(老师可为空)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE COMMENT '账号必须唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2022001', '123456', '张三同学', 'student', '信息工程学院', '电子信息工程1班', '2026-01-10 17:13:30');
INSERT INTO `sys_user` VALUES (2, 'T001', '123456', '李老师', 'teacher', '信息工程学院', '电子信息工程1班,计算机2班', '2026-01-10 17:13:30');
INSERT INTO `sys_user` VALUES (3, '2026001', '654321', '李四', 'student', '信息工程学院', '通信工程3班', '2026-01-13 18:00:43');
INSERT INTO `sys_user` VALUES (4, '273555', '111111', '张琳', 'teacher', '', '电子信息工程1班', '2026-01-19 17:42:54');
INSERT INTO `sys_user` VALUES (5, 'admin', '123456', '系统管理员', 'admin', '教务处', NULL, '2026-02-21 20:09:44');
INSERT INTO `sys_user` VALUES (6, '123123', '123456', 'lisa', 'student', '计算机学院', '计算机2班', '2026-02-23 14:43:09');
INSERT INTO `sys_user` VALUES (7, '111111', '111', 'hui', 'student', '计算机学院', '计算机1班', '2026-02-26 20:08:19');
INSERT INTO `sys_user` VALUES (8, '2022002', '123456', '王五同学', 'student', '信息工程学院', '电子信息工程1班', '2026-02-27 20:11:04');
INSERT INTO `sys_user` VALUES (9, '2022003', '123456', '赵六同学', 'student', '计算机学院', '计算机1班', '2026-02-27 20:11:04');
INSERT INTO `sys_user` VALUES (10, '2022004', '123456', '孙七同学', 'student', '计算机学院', '计算机2班', '2026-02-27 20:11:04');
INSERT INTO `sys_user` VALUES (11, '2022005', '123456', '周八同学', 'student', '信息工程学院', '通信工程3班', '2026-02-27 20:11:04');
INSERT INTO `sys_user` VALUES (12, 'T002', '123456', '王老师', 'teacher', '信息工程学院', '电子信息工程1班', '2026-02-27 20:11:04');
INSERT INTO `sys_user` VALUES (13, 'T003', '123456', '李老师', 'teacher', '计算机学院', '计算机1班', '2026-02-27 20:11:04');
INSERT INTO `sys_user` VALUES (14, 'T004', '123456', '张老师', 'teacher', '计算机学院', '计算机2班', '2026-02-27 20:11:04');

SET FOREIGN_KEY_CHECKS = 1;
