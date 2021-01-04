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

