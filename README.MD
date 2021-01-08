[![Codacy Badge](https://app.codacy.com/project/badge/Grade/18c707c3dff146ec9a7d1e20a383e212)](https://www.codacy.com/gh/Space125/restaurant-voting-rest-api/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Space125/restaurant-voting-rest-api&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Space125/restaurant-voting-rest-api.svg?branch=master)](https://travis-ci.org/Space125/restaurant-voting-rest-api)

# Restaurant voting REST API
Graduation Project

## Task

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

P.S.: Make sure everything works with the latest version that is on github :)

P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

## REST API

### Unauthorized visitors
#### Register visitor
```curl -X POST "http://localhost:8084/rest/profile/register" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"email\":\"newemail@ya.ru\",\"name\":\"newName\",\"password\":\"newPassword\"}"```

Produces JSON
```json
{
    "id": 100049,
    "name": "newName",
    "email": "newemail@ya.ru",
    "enabled": true,
    "registered": "2021-01-08T09:17:24.307+00:00",
    "roles": [
        "USER"
    ],
    "votes": null
}
```

#### Get actual list of restaurants with rating(list consists of transfer objects and is sorted by daily rating)
```curl -X GET "localhost:8084/rest/profile/restaurants" -H "accept: application/json"```

Produces JSON
```json
[
  {
    "id": 100004,
    "name": "Bluefin",
    "rating": 1
  },
  {
    "id": 100003,
    "name": "Corner Grill",
    "rating": 2
  },
  {
    "id": 100006,
    "name": "Caesars Palace",
    "rating": 0
  },
  {
    "id": 100005,
    "name": "McDonalds",
    "rating": 0
  }
]
```

### Authorized user
#### Get user profile

```curl -X GET "http://localhost:8084/rest/profile" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100001,
  "name": "Admin",
  "email": "admin@gmail.com",
  "enabled": true,
  "registered": "2020-12-29T13:37:57.222+00:00",
  "roles": [
    "ADMIN",
    "USER"
  ],
  "votes": null
}
```

#### Get user profile with votes
```curl -X GET "http://localhost:8084/rest/profile/with-votes" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100002,
  "name": "Admin",
  "email": "admin@gmail.com",
  "enabled": true,
  "registered": "2021-01-08T09:31:25.122+00:00",
  "roles": [
    "USER",
    "ADMIN"
  ],
  "votes": [
    {
      "id": 100018,
      "dateVote": "2021-01-08"
    },
    {
      "id": 100017,
      "dateVote": "2020-12-25"
    },
    {
      "id": 100016,
      "dateVote": "2020-12-24"
    },
    {
      "id": 100015,
      "dateVote": "2020-12-23"
    }
  ]
}
```

#### Get votes
```curl -X GET "http://localhost:8084/rest/profile/votes" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
[
  {
    "id": 100018,
    "dateVote": "2021-01-08"
  },
  {
    "id": 100017,
    "dateVote": "2020-12-25"
  },
  {
    "id": 100016,
    "dateVote": "2020-12-24"
  },
  {
    "id": 100015,
    "dateVote": "2020-12-23"
  }
]
```

#### Get today's vote
```curl -X GET "http://localhost:8084/rest/profile/votes/today" -H "accept: application/json" --user user@yandex.ru:password```

Produces JSON
```json
{
  "id": 100010,
  "dateVote": "2021-01-08"
}
```

#### Get vote by date
```curl -X GET "http://localhost:8084/rest/profile/votes/by?date=2020-12-23" -H "accept: application/json" --user user@yandex.ru:password```

Produces JSON
```json
{
  "id": 100007,
  "dateVote": "2020-12-23"
}
```

#### Update user(update himself)
```curl -X PUT "http://localhost:8084/rest/profile" -H "accept: */*" -H "Content-Type: application/json" -d "{\"email\":\"updateUser@yandex.ru\",\"name\":\"UpdateUser\",\"password\":\"password\"}" --user user@yandex.ru:password```

Produces - no content, status 204

#### Delete user (delete himself)
```curl -X DELETE "http://localhost:8084/rest/profile" -H "accept: */*" --user user@yandex.ru:password```

Produces - no content, status 204

#### Get list of restaurants by date
```curl -X GET "http://localhost:8084/rest/profile/restaurants/by?date=2020-12-25" -H "accept: application/json" --user user@yandex.ru:password```

Produces JSON
```json
[
  {
    "id": 100006,
    "name": "Caesars Palace",
    "rating": 2
  },
  {
    "id": 100005,
    "name": "McDonalds",
    "rating": 1
  },
  {
    "id": 100004,
    "name": "Bluefin",
    "rating": 0
  },
  {
    "id": 100003,
    "name": "Corner Grill",
    "rating": 0
  }
]
```

#### Get list of menus in restaurant by date
```curl -X GET "http://localhost:8084/rest/profile/restaurants/100003/menus/by?date=2020-12-25" -H "accept: application/json" --user user@yandex.ru:password```

Produces JSON
```json
[
  {
    "id": 100026,
    "name": "Tom Yum",
    "price": 400,
    "dateMenu": "2020-12-25"
  },
  {
    "id": 100025,
    "name": "Corsican Burger",
    "price": 500,
    "dateMenu": "2020-12-25"
  },
  {
    "id": 100027,
    "name": "Belgian Waffles",
    "price": 375,
    "dateMenu": "2020-12-25"
  }
]
```

#### Get a certain dish from menu
```curl -X GET "http://localhost:8084/rest/profile/restaurants/100003/menus/100021" -H "accept: application/json" --user user@yandex.ru:password```

Produces JSON
```json
{
  "id": 100021,
  "name": "New York Cheesecake",
  "price": 350,
  "dateMenu": "2020-12-23"
}
```

#### Vote for restaurant
```curl -X POST "http://localhost:8084/rest/profile/votes?restaurantId=100003" -H "accept: application/json" --user user@yandex.ru:password```

Produces JSON
```json
{
  "id": 100048,
  "dateVote": "2021-01-08"
}
```

### Authorized user with role admin
#### Get all user
```curl -X GET "http://localhost:8084/rest/admin/users" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
[
  {
    "id": 100002,
    "name": "Admin",
    "email": "admin@gmail.com",
    "enabled": true,
    "registered": "2021-01-08T11:33:17.899+00:00",
    "roles": [
      "USER",
      "ADMIN"
    ],
    "votes": null
  },
  {
    "id": 100001,
    "name": "Mikky",
    "email": "mikky@disnay.com",
    "enabled": true,
    "registered": "2021-01-08T11:33:17.899+00:00",
    "roles": [
      "USER"
    ],
    "votes": null
  },
  {
    "id": 100000,
    "name": "User",
    "email": "user@yandex.ru",
    "enabled": true,
    "registered": "2021-01-08T11:33:17.899+00:00",
    "roles": [
      "USER"
    ],
    "votes": null
  }
]
```

#### Get one user
```curl -X GET "http://localhost:8084/rest/admin/users/100000" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100000,
  "name": "User",
  "email": "user@yandex.ru",
  "enabled": true,
  "registered": "2021-01-08T11:33:17.899+00:00",
  "roles": [
    "USER"
  ],
  "votes": null
}
```

#### Get user with votes
```curl -X GET "http://localhost:8084/rest/admin/users/100000/with-votes" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100000,
  "name": "User",
  "email": "user@yandex.ru",
  "enabled": true,
  "registered": "2021-01-08T11:33:17.899+00:00",
  "roles": [
    "USER"
  ],
  "votes": [    
    {
      "id": 100009,
      "dateVote": "2020-12-25"
    },
    {
      "id": 100008,
      "dateVote": "2020-12-24"
    },
    {
      "id": 100007,
      "dateVote": "2020-12-23"
    }
  ]
}
```

#### Get user votes
```curl -X GET "http://localhost:8084/rest/admin/users/100001/votes" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
[
  {
    "id": 100013,
    "dateVote": "2021-01-08"
  },
  {
    "id": 100012,
    "dateVote": "2020-12-25"
  },
  {
    "id": 100011,
    "dateVote": "2020-12-24"
  },
  {
    "id": 100010,
    "dateVote": "2020-12-23"
  }
]
```

#### Get user's vote today
```curl -X GET "http://localhost:8084/rest/admin/users/100001/votes/today" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100013,
  "dateVote": "2021-01-08"
}
```

#### Get vote by date
```curl -X GET "http://localhost:8084/rest/admin/users/100001/votes/by?date=2020-12-23" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100010,
  "dateVote": "2020-12-23"
}
```

#### Get user by e-mail
```curl -X GET "http://localhost:8084/rest/admin/users/by?email=mikky@disnay.com" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100001,
  "name": "Mikky",
  "email": "mikky@disnay.com",
  "enabled": true,
  "registered": "2021-01-08T11:33:17.899+00:00",
  "roles": [
    "USER"
  ],
  "votes": null
}
```

#### Enable/disable user
```curl -X PATCH "http://localhost:8084/rest/admin/users/100001?enabled=false" -H "accept: */*" --user admin@gmail.com:admin```

Produces - no content, status 201

#### Create new user
```curl -X POST "http://localhost:8084/rest/admin/users" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"name\":\"newName\",\"email\":\"newemail@ya.ru\",\"password\":\"newPassword\"}" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100049,
  "name": "newName",
  "email": "newemail@ya.ru",
  "enabled": true,
  "registered": "2021-01-08T12:16:27.882+00:00",
  "roles": null,
  "votes": null
}
```

#### Update user
```curl -X PUT "http://localhost:8084/rest/admin/users/100001" -H "accept: */*" -H "Content-Type: application/json" -d "{\"name\":\"updateName\",\"email\":\"updateemail@ya.ru\",\"password\":\"updatePassword\"}" --user admin@gmail.com:admin```

Produces - no content, status 204

#### Delete user
```curl -X DELETE "http://localhost:8084/rest/admin/users/100001" -H "accept: */*" --user admin@gmail.com:admin```

Produces - no content, status 204

#### Get one restaurant
```curl -X GET "http://localhost:8084/rest/admin/restaurants/100004" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100004,
  "name": "Bluefin",
  "registered": "2021-01-08T11:33:17.920+00:00",
  "enabled": true,
  "votes": null,
  "menus": null
}
```

#### Get restaurant's one dish from menu
```curl -X GET "http://localhost:8084/rest/admin/restaurants/100003/menus/100019" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100019,
  "name": "Borsch",
  "price": 300,
  "dateMenu": "2020-12-23"
}
```

#### Get menu for restaurant
```curl -X GET "http://localhost:8084/rest/admin/restaurants/100004/menus" -H "accept: application/json" --user admin@gmail.com:admin```

Produces JSON
```json
[
  {
    "id": 100037,
    "name": "California Roll",
    "price": 1000,
    "dateMenu": "2021-01-08"
  },
  {
    "id": 100038,
    "name": "Philly roll",
    "price": 1200,
    "dateMenu": "2021-01-08"
  },
  {
    "id": 100039,
    "name": "Salmon roll",
    "price": 700,
    "dateMenu": "2021-01-08"
  },
  {
    "id": 100034,
    "name": "California Roll",
    "price": 1000,
    "dateMenu": "2020-12-25"
  },
  {
    "id": 100035,
    "name": "Philly roll",
    "price": 1200,
    "dateMenu": "2020-12-25"
  },
  {
    "id": 100036,
    "name": "Salmon roll",
    "price": 700,
    "dateMenu": "2020-12-25"
  },
  {
    "id": 100031,
    "name": "California Roll",
    "price": 1000,
    "dateMenu": "2020-12-24"
  },
  {
    "id": 100032,
    "name": "Philly roll",
    "price": 1200,
    "dateMenu": "2020-12-24"
  },
  {
    "id": 100033,
    "name": "Salmon roll",
    "price": 700,
    "dateMenu": "2020-12-24"
  },
  {
    "id": 100029,
    "name": "Philly roll",
    "price": 1200,
    "dateMenu": "2020-12-23"
  },
  {
    "id": 100030,
    "name": "Salmon roll",
    "price": 700,
    "dateMenu": "2020-12-23"
  }
]
```

#### Create new restaurant
```curl -X POST "http://localhost:8084/rest/admin/restaurants" -H "accept: application/json" -H "Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu" -H "Content-Type: application/json" -d "{\"name\":\"NewRestaurant\",\"enabled\":true,\"registered\":\"2020-10-26\"}" --user admin@gmail.com:admin```

Produces JSON
```json
{
  "id": 100048,
  "name": "NewRestaurant",
  "registered": "2020-10-26T00:00:00.000+00:00",
  "enabled": true,
  "votes": null,
  "menus": null
}
```

#### Update restaurant
```curl -X PUT "http://localhost:8084/rest/admin/restaurants/100003" -H "accept: */*" -H "Content-Type: application/json" -d "{\"id\":100003,\"name\":\"UpdatedName\",\"enabled\":true,\"registered\":\"2021-01-08\"}" --user admin@gmail.com:admin```

Produces - no content, status 204

#### Enable/disable restaurant
```curl -X PATCH "http://localhost:8084/rest/admin/restaurants/100003?enabled=false" -H "accept: */*" --user admin@gmail.com:admin```

Produces - no content, status 204

#### Delete restaurant
```curl -X DELETE "localhost:8084/rest/admin/restaurants/100003" -H "accept: */*" --user admin@gmail.com:admin```

Produces - no content, status 204

#### Create dish in menu
```curl -X POST "http://localhost:8084/rest/admin/restaurants/100003/menus" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"name\":\"New Year Pizza\",\"price\":1000,\"date\":\"2021-01-08\"}" --user admin@gmail.com:admin```


Produces JSON
```json
{
  "id": 100048,
  "name": "New Year Pizza",
  "price": 1000,
  "dateMenu": "2021-01-08"
}
```

#### Update dish in menu
```curl -X PUT "http://localhost:8084/rest/admin/restaurants/100003/menus/100019" -H "accept: */*" -H "Content-Type: application/json" -d "{\"id\":100019,\"name\":\"UpdatedName\",\"price\":50000,\"date\":\"2021-01-08\"}" --user admin@gmail.com:admin```

Produces - no content, status 204

#### Delete a dish from menu
```curl -X DELETE "http://kurilov-ie.corp.app-sol.ru:8084/rest/admin/restaurants/100003/menus/100048" -H "accept: */*" --user admin@gmail.com:admin```

Produces - no content, status 204

