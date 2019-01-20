
-- 用户收藏 添加字段
ALTER TABLE `t_user_collections`
ADD COLUMN `object_id`  int(11) NOT NULL DEFAULT 0 COMMENT '收藏内容id' AFTER `classify`;

