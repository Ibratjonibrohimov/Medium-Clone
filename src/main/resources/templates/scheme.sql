create table users (
                       id bigint generated always as IDENTITY primary key ,
                       email varchar,
                       username varchar(50) unique,
                       password varchar(50)
);
create table profile (
                       id bigint generated always as IDENTITY primary key ,
                       bio text default '',
                       image_path text default '',
                       following boolean default false,
                       user_id bigint,
                       foreign key (user_id) references users(id)
);

alter table users add constraint unique_email unique (email);
alter table users alter column password set DATA TYPE varchar;
alter table profile alter column image_path set default 'https://api.realworld.io/images/smiley-cyrus.jpeg' notnull ;

create table article (
    id bigint generated always as IDENTITY primary key ,
    title varchar,
    description varchar,
    body text,
    create_at DATE,
    update_at DATE,
    profile_id bigint,
    foreign key (profile_id) references profile(id)
);

create table tag(
    id bigint generated always as  IDENTITY primary key ,
    name varchar
);

alter table tag add constraint unique_tag_name unique (name);

create table article_tag(
    article_id bigint,
    tag_id bigint,
    constraint article_tag_cons
    foreign key (article_id) references article(id) on delete cascade ,
    foreign key (tag_id) references tag(id)
);


create table likes(
    article_id bigint,
    user_id bigint,
    constraint likes_cons
    foreign key  (article_id) references article(id) on delete cascade ,
    foreign key (user_id) references profile(id)
);

alter table article alter column create_at set DATA TYPE timestamp;
alter table article alter column update_at set DATA TYPE timestamp;

create table comment(
    id bigint generated always as IDENTITY primary key ,
    profile_id bigint,
    article_id bigint,
    body text,
    create_at timestamp,
    update_at timestamp,
    constraint comment_cons
    foreign key (profile_id) references profile(id) on DELETE cascade ,
    foreign key (article_id) references article(id)
);

alter table comment add constraint comment_article_cons foreign key (article_id) references article(id) on DELETE cascade ;

create table followers(
    user_id bigint,
    follower_id bigint,
    foreign key (user_id) references profile(id),
    foreign key (follower_id) references profile(id)
)