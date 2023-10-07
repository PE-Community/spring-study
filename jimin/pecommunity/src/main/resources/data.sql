INSERT INTO board (board_cd_pk, board_nm, sports) VALUES (1, '축구 게시판', 'SOCCER');
INSERT INTO board (board_cd_pk, board_nm, sports) VALUES (2, '배구 게시판', 'VOLLEYBALL');
INSERT INTO board (board_cd_pk, board_nm, sports) VALUES (3, '농구 게시판', 'BASKETBALL');
INSERT INTO board (board_cd_pk, board_nm, sports) VALUES (4, '배드민턴 게시판', 'BADMINTON');

INSERT INTO Member (member_pk, member_id, nickname, password, email, role, dormant_conversion_date)
VALUES (100, "test1", "testname1", "$2a$10$Je7.2OQpF.Mb2deXZ5.6uOTZA5BgBOk6sLamep5S20EfcJRHxCiqa", "test12@email", "MEMBER", null);
INSERT INTO Member (member_pk, member_id, nickname, password, email, role, dormant_conversion_date)
VALUES (101, "test2", "testname2", "$2a$10$Je7.2OQpF.Mb2deXZ5.6uOTZA5BgBOk6sLamep5S20EfcJRHxCiqa", "test12@email2", "MEMBER", null);

INSERT INTO Post (id, board_pk, member_pk, title, content, view_cnt, create_date, update_date) VALUES (100, 1, 100, "post 1", "post111", 0, null, null);
INSERT INTO Post (id, board_pk, member_pk, title, content, view_cnt, create_date, update_date) VALUES (101, 1, 101, "post 2", "post222", 0, null, null);
INSERT INTO Post (id, board_pk, member_pk, title, content, view_cnt, create_date, update_date) VALUES (102, 1, 100, "post 2", "post333", 0, null, null);

--
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (100, 100, 100, null, "LEVEL 0 - 1", 0, null, null, 0, 0);
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (101, 100, 100, 100, "LEVEL 1 - 1", 1, null, null, 1, 0);
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (102, 100, 100, 100, "LEVEL 1 - 2", 1, null, null, 1, 0);
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (103, 100, 100, 101, "LEVEL 2 - 1", 2, null, null, 1, 0);
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (104, 100, 100, 102, "LEVEL 2 - 1", 2, null, null, 1, 0);
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (105, 100, 100, 102, "LEVEL 2 - 2", 2, null, null, 1, 0);
--INSERT INTO Comment (id, member_pk, post_pk, parent_pk, content, level, create_date, update_date, is_removed, is_secret) VALUES (106, 100, 100, null, "LEVEL 0 - 2", 0, null, null, 0, 0);
--
