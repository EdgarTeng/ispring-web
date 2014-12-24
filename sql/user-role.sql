CREATE DATABASE  IF NOT EXISTS `ispringdb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `ispringdb`;

INSERT INTO `t_role` (`id`, `create_time`, `last_update`, `role_name`,`status`) VALUES (1, '2014-12-24 12:10:16', '2014-12-24 12:10:18', 'ADMIN', 1);
INSERT INTO `t_role` (`id`, `create_time`, `last_update`, `role_name`,`status`) VALUES (2, '2014-12-24 12:10:36', '2014-12-24 12:10:39', 'USER', 1);


INSERT INTO `t_menu` 
(`id`,`menu_name`,`parent_id`,`menu_code`,`menu_url`,`url_target`,`nav_menu`,`sort`,`remark`,`create_time`,`last_update`) 
VALUES 
(1,'Ӧ�û�������',0,NULL,NULL,NULL,1,0,NULL,'2013-03-28 00:00:00','2013-03-28 00:00:00'),
(2,'�û�������ҳ',1,'main','/back/main',NULL,1,0,NULL,'2013-03-28 00:00:00','2013-03-28 00:00:00'),
(3,'ϵͳ����',0,NULL,NULL,NULL,1,0,NULL,'2013-03-28 00:00:00','2013-03-28 00:00:00'),
(4,'�û�����',3,'users','/back/users',NULL,1,0,NULL,'2013-03-28 00:00:00','2013-03-28 00:00:00'),
(5,'��ɫ����',3,'roles','/back/roles',NULL,1,0,NULL,'2013-03-28 00:00:00','2013-03-28 00:00:00'),
(6,'��Դ����',3,'menus','/back/menus',NULL,1,0,NULL,'2013-03-28 00:00:00','2013-03-31 22:02:15');


INSERT INTO `t_role_menu` 
(`role_id`,`menu_id`)
VALUES 
(1,2),
(1,5),
(1,3),
(1,4),
(1,6),
(1,1),
(2,1),
(2,5),
(2,2),
(2,6);
