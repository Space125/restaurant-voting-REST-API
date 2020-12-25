

INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name)
VALUES ('Corner Grill'),
       ('Bluefin'),
       ('McDonalds'),
       ('Caesars Palace');

INSERT INTO VOTE (USER_ID, RESTAURANT_ID, DATE_VOTE)
VALUES (1, 1, '2020-08-10'),
       (1, 1, '2020-08-11'),
       (1, 3, '2020-08-12'),
       (2, 2, '2020-08-10'),
       (2, 2, '2020-08-11'),
       (2, 4, '2020-08-12'),
       (2, 1, now());

INSERT INTO MENU (RESTAURANT_ID, NAME, PRICE, DATE_MENU)
VALUES (1, 'Caprese Burger', 500, '2020-08-10'),
       (1, 'Borsch', 300, '2020-08-10'),
       (1, 'New York Cheesecake', 350, '2020-08-10'),
       (1, 'Bolognese Pasta', 250, '2020-08-11'),
       (1, 'Chicken Noodles', 150, '2020-08-11'),
       (1, 'Chiefs special', 500, '2020-08-11'),
       (1, 'Corsican Burger', 500, '2020-08-12'),
       (1, 'Tom Yum', 400, '2020-08-12'),
       (1, 'Belgian Waffles', 375, '2020-08-12'),
       (1, 'Combo box', 999, now()),
       (2, 'California Roll', 1000, '2020-08-10'),
       (2, 'Philly roll', 1200, '2020-08-10'),
       (2, 'Salmon roll', 700, '2020-08-10'),
       (2, 'California Roll', 1000, '2020-08-11'),
       (2, 'Philly roll', 1200, '2020-08-11'),
       (2, 'Salmon roll', 700, '2020-08-11'),
       (2, 'California Roll', 1000, '2020-08-12'),
       (2, 'Philly roll', 1200, '2020-08-12'),
       (2, 'Salmon roll', 700, '2020-08-12'),
       (2, 'California Roll', 1000, now()),
       (2, 'Philly roll', 1200, now()),
       (2, 'Salmon roll', 700, now()),
       (3, 'MacCombo', 300, '2020-08-10'),
       (3, 'MacCombo', 300, '2020-08-11'),
       (3, 'MacCombo', 300, '2020-08-12'),
       (3, 'MacCombo', 300, now()),
       (4, 'Kobe Steak', 10000, '2020-08-10'),
       (4, 'Fugu Fish', 50000, '2020-08-11'),
       (4, 'Gold on a plate', 500000, '2020-08-12'),
       (4, 'Something expensive', 100000, now());
