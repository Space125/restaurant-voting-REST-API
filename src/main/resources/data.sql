
-- ALTER SEQUENCE custom_seq RESTART WITH 100000 increment by 1;

INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Mikky', 'mikky@disnay.com', '{noop}disnay'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('ADMIN', 100002),
       ('USER', 100002);

INSERT INTO RESTAURANTS (name)
VALUES ('Corner Grill'),
       ('Bluefin'),
       ('McDonalds'),
       ('Caesars Palace');

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, DATE_VOTE)
VALUES (100000, 100003, '2020-12-23'),
       (100000, 100003, '2020-12-24'),
       (100000, 100005, '2020-12-25'),
       (100000, 100004, now()),
       (100001, 100004, '2020-12-23'),
       (100001, 100004, '2020-12-24'),
       (100001, 100006, '2020-12-25'),
       (100001, 100003, now()),
       (100002, 100004, '2020-12-23'),
       (100002, 100004, '2020-12-24'),
       (100002, 100006, '2020-12-25'),
       (100002, 100003, now());

INSERT INTO MENUS (RESTAURANT_ID, NAME, PRICE, DATE_MENU)
VALUES (100003, 'Caprese Burger', 500, '2020-12-23'),
       (100003, 'Borsch', 300, '2020-12-23'),
       (100003, 'New York Cheesecake', 350, '2020-12-23'),
       (100003, 'Bolognese Pasta', 250, '2020-12-24'),
       (100003, 'Chicken Noodles', 150, '2020-12-24'),
       (100003, 'Chiefs special', 500, '2020-12-24'),
       (100003, 'Corsican Burger', 500, '2020-12-25'),
       (100003, 'Tom Yum', 400, '2020-12-25'),
       (100003, 'Belgian Waffles', 375, '2020-12-25'),
       (100003, 'Combo box', 999, now()),
       (100003, 'California Roll', 1000, '2020-12-23'),
       (100004, 'Philly roll', 1200, '2020-12-23'),
       (100004, 'Salmon roll', 700, '2020-12-23'),
       (100004, 'California Roll', 1000, '2020-12-24'),
       (100004, 'Philly roll', 1200, '2020-12-24'),
       (100004, 'Salmon roll', 700, '2020-12-24'),
       (100004, 'California Roll', 1000, '2020-12-25'),
       (100004, 'Philly roll', 1200, '2020-12-25'),
       (100004, 'Salmon roll', 700, '2020-12-25'),
       (100004, 'California Roll', 1000, now()),
       (100004, 'Philly roll', 1200, now()),
       (100004, 'Salmon roll', 700, now()),
       (100005, 'MacCombo', 300, '2020-12-23'),
       (100005, 'MacCombo', 300, '2020-12-24'),
       (100005, 'MacCombo', 300, '2020-12-25'),
       (100005, 'MacCombo', 300, now()),
       (100006, 'Kobe Steak', 10000, '2020-12-23'),
       (100006, 'Fugu Fish', 50000, '2020-12-24'),
       (100006, 'Gold on a plate', 500000, '2020-12-25'),
       (100006, 'Something expensive', 100000, now());
