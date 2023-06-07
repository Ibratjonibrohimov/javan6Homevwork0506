drop table if exists COMMENTS;
drop table if exists ARTICLES;
drop table if exists USERS;

create table USERS
(
    ID       INTEGER      not null AUTO_INCREMENT,
    USERNAME varchar(100) not null,
    PASSWORD varchar(100) not null,
    EMAIL    varchar(100) not null,
    PRIMARY KEY (ID)
);

CREATE TABLE ARTICLES
(
    ID           INTEGER      NOT NULL AUTO_INCREMENT,
    TITLE        VARCHAR(128) NOT NULL,
    BODY         VARCHAR(128) NOT NULL,
    USER_ID      int,
    PUBLISH_DATE date default CURRENT_DATE,
    UPDATED_AT   date default CURRENT_DATE,
    FOREIGN KEY (user_id) references USERS (ID),
    PRIMARY KEY (ID)
);

create table COMMENTS
(
    ID         INTEGER      not null AUTO_INCREMENT,
    BODY       varchar(128) not null,
    USER_ID    INTEGER,
    ARTICLE_ID INTEGER,
    PUBLISH_DATE date default CURRENT_DATE,
    UPDATED_AT   date default CURRENT_DATE,
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) references USERS (ID),
    FOREIGN KEY (ARTICLE_ID) references ARTICLES (ID)
)