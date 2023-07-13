insert into users(email, username, password) VALUES
                                                 ('sherzod@gmail.com','sherzod_dev','12345');

insert into profile(bio, image_path, following, user_id) values
                                                             ('xiascknkjcnjaocjnoancascnosdncacnx xh xikxsjax',null,false,2);

insert into article(title, description, body, create_at, update_at, profile_id) values
('1-article','1-description','1-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1),
('2-article','2-description','2-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1),
('3-article','3-description','3-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1),
('4-article','4-description','4-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1),
('5-article','5-description','5-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1),
('6-article','6-description','6-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1),
('7-article','7-description','7-body','2023-06-16 12:10:56','2023-06-16 12:10:56',1);

insert into tag (name) values
                           ('tag1'),
                           ('tag2'),
                           ('tag3'),
                           ('tag4'),
                           ('tag5'),
                           ('tag6'),
                           ('tag7');
insert into article_tag (article_id, tag_id) values
                                                 (1,1),
                                                 (2,2),
                                                 (3,2),
                                                 (4,5),
                                                 (5,6),
                                                 (6,2),
                                                 (7,1);
insert into article_tag (article_id, tag_id) values
                                                 (7,2);

insert into comment(profile_id, article_id, body, create_at, update_at) values
(1,1,'comment1','2023-06-18 15:45:23','2023-06-18 15:45:23'),
(1,1,'comment2','2023-06-18 15:45:23','2023-06-18 15:45:23'),
(1,1,'comment3','2023-06-18 15:45:23','2023-06-18 15:45:23'),
(1,1,'comment4','2023-06-18 15:45:23','2023-06-18 15:45:23');

insert into likes(article_id, user_id) VALUES
                                           (1,2),
                                           (1,1),
                                           (2,2);
insert into article(title, description, body, create_at, update_at, profile_id) values
                                                                                    ('8-article','8-description','8-body','2023-06-16 12:10:56','2023-06-16 12:10:56',2);