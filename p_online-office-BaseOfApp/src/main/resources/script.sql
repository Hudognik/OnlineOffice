CREATE TABLE USR
(
    id          bigint auto_increment,
    username    varchar(256) not null,
    password    varchar(256) not null,
    PRIMARY KEY (id),
    UNIQUE KEY (id),
    UNIQUE KEY (username)
) DEFAULT CHARSET = utf8;

CREATE TABLE ROLE
(
    id   bigint NOT NULL AUTO_INCREMENT,
    role varchar(256) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (id),
    UNIQUE KEY (role)
) DEFAULT CHARSET = utf8;

CREATE TABLE user_role
(
    user_id bigint,
    role_id bigint,
    FOREIGN KEY (role_id) REFERENCES ROLE (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES USR (id) ON DELETE CASCADE ON UPDATE CASCADE
) DEFAULT CHARSET = utf8;

INSERT INTO USR (id, username, password)
VALUES (1,
        'admin',
        '$2a$10$VMIRLpPH68ATmRjB/If07uGaEy.YhICowNjpmFvoMkvHmzXYm8Ad2'
        );

INSERT INTO USR (id, username, password)
VALUES (2,
        'ivan',
        '$2a$10$VMIRLpPH68ATmRjB/If07uGaEy.YhICowNjpmFvoMkvHmzXYm8Ad2'
        );

INSERT INTO ROLE (id, role)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT INTO user_role (USER_ID, ROLE_ID)
VALUES (1, 1),
       (1, 2),
       (2, 2);

CREATE TABLE ROOM
(
    id          bigint auto_increment,
    name    varchar(256) not null,
    PRIMARY KEY (id),
    UNIQUE KEY (id),
    UNIQUE KEY (name)
) DEFAULT CHARSET = utf8;