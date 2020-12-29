
-- ALTER SEQUENCE custom_seq RESTART WITH 100000 increment by 1;

INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO RESTAURANTS (name)
VALUES ('Corner Grill'),
       ('Bluefin'),
       ('McDonalds'),
       ('Caesars Palace');

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, DATE_VOTE)
VALUES (100000, 100002, '2020-12-23'),
       (100000, 100002, '2020-12-24'),
       (100000, 100004, '2020-12-25'),
       (100000, 100003, now()),
       (100001, 100003, '2020-12-23'),
       (100001, 100003, '2020-12-24'),
       (100001, 100005, '2020-12-25'),
       (100001, 100002, now());

INSERT INTO MENUS (RESTAURANT_ID, NAME, PRICE, DATE_MENU)
VALUES (100002, 'Caprese Burger', 500, '2020-12-23'),
       (100002, 'Borsch', 300, '2020-12-23'),
       (100002, 'New York Cheesecake', 350, '2020-12-23'),
       (100002, 'Bolognese Pasta', 250, '2020-12-24'),
       (100002, 'Chicken Noodles', 150, '2020-12-24'),
       (100002, 'Chiefs special', 500, '2020-12-24'),
       (100002, 'Corsican Burger', 500, '2020-12-25'),
       (100002, 'Tom Yum', 400, '2020-12-25'),
       (100002, 'Belgian Waffles', 375, '2020-12-25'),
       (100002, 'Combo box', 999, now()),
       (100003, 'California Roll', 1000, '2020-12-23'),
       (100003, 'Philly roll', 1200, '2020-12-23'),
       (100003, 'Salmon roll', 700, '2020-12-23'),
       (100003, 'California Roll', 1000, '2020-12-24'),
       (100003, 'Philly roll', 1200, '2020-12-24'),
       (100003, 'Salmon roll', 700, '2020-12-24'),
       (100003, 'California Roll', 1000, '2020-12-25'),
       (100003, 'Philly roll', 1200, '2020-12-25'),
       (100003, 'Salmon roll', 700, '2020-12-25'),
       (100003, 'California Roll', 1000, now()),
       (100003, 'Philly roll', 1200, now()),
       (100003, 'Salmon roll', 700, now()),
       (100004, 'MacCombo', 300, '2020-12-23'),
       (100004, 'MacCombo', 300, '2020-12-24'),
       (100004, 'MacCombo', 300, '2020-12-25'),
       (100004, 'MacCombo', 300, now()),
       (100005, 'Kobe Steak', 10000, '2020-12-23'),
       (100005, 'Fugu Fish', 50000, '2020-12-24'),
       (100005, 'Gold on a plate', 500000, '2020-12-25'),
       (100005, 'Something expensive', 100000, now());
