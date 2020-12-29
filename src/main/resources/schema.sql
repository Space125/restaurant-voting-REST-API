DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS votes CASCADE;
DROP TABLE IF EXISTS menus CASCADE;

DROP SEQUENCE IF EXISTS CUSTOM_SEQ;


CREATE SEQUENCE IF NOT EXISTS CUSTOM_SEQ start with 100000;

CREATE TABLE users
(
    id         BIGINT                  DEFAULT CUSTOM_SEQ.nextval PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id         INTEGER                 DEFAULT CUSTOM_SEQ.nextval PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurants (name);

CREATE TABLE votes
(
    id            INTEGER            DEFAULT CUSTOM_SEQ.nextval PRIMARY KEY,
    user_id       INTEGER            NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    date_vote     DATE DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_date_idx ON votes (user_id, date_vote);

CREATE TABLE menus
(
    id            INTEGER                     DEFAULT CUSTOM_SEQ.nextval PRIMARY KEY,
    restaurant_id INTEGER                     NOT NULL,
    name          VARCHAR(255)                NOT NULL,
    price         INTEGER                     NOT NULL,
    date_menu     DATE DEFAULT now()          NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    CONSTRAINT menu_unique_restaurant_name_date_idx UNIQUE (restaurant_id, name, date_menu)
);
CREATE INDEX menu_date_idx ON menus (date_menu);



