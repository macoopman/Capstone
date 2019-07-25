## Login Flow

* Request: POST
* Header: Content-Type : application/json
* Endpoint /users/signin
* Body: Pass a username and password
* Response Success = 200
* Response Fail = 500 <-- Bad Request: invalid username or password
```java
// post request
{
	"username": "John_Bravo",
	"password": "password"
}

// return data
{
    "username": "John_Bravo",
    "userId": 100,
    "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huX0JyYXZvIiwicm9sZXMiOltdLCJpYXQiOjE1NjM0MTYxMTcsImV4cCI6MTU2MzQxNjcxN30.xa_JfuMnhzvpZdNyNUV5jr2w6w9qEP8SnQxV9lf1IKk",
    "userType": "students",
    "currentDepartmentId": 1,
    "currentSessionNumberId": 2,
    "currentClassNumberId": 1
}
```





---
## New User Creation Flow
** Note : Only for backend. 
### 1. Create User Object
* Request: POST
* Header: Content-Type : application/json
* Header: Authentication : Bearer {jwt}
* Endpoint /users/register
* UserType: professor = 0 ; student = 1
* Body: Pass a username and password
* Response Success = 200
* Response Fail = 500 <-- this will change for now just check that it was 200
```java
// POST request
{
	"username": "User1",
	"password": "Password",
	"userType": 0
}

// Responce 
{
{
    "id": 904,
    "username": "jwalker",
    "roles": [
        {
            "id": 2,
            "description": "User role",
            "roleName": "ROLE_USER",
            "authority": "ROLE_USER"
        }
    ],
    "userData": {
        "id": 904,
        "firstName": "dummy",
        "lastName": "dummy",
        "email": "dummy@gmail.com",
        "dateJoined": "2019-07-24T05:03:58.600+0000",
        "user": null,
        "currentSession": null,
        "rating": 1
    }
}
```

### 2. Create Student or Professor
* Request: PATCH
* Header: Content-Type : application/json
* Header: Authentication : Bearer {jwt}
* Endpoint /students{user_id}    or /professors{user_id} 
* Body: See examples
* Response Success = 200
* Response Fail = 500 <-- this will change for now just check that it was 200
```java
// Create Student (POST)
{
	"firstName" : "Randi",
	"lastName"	: "Reichert",
	"email"		: "RReichert@gmail.com",
	"major"		: "Computer Science"
}

// Student Return
{
    "firstName": "Randi",
    "lastName": "Reichert",
    "email": "RReichert@gmail.com",
    "dateJoined": "2019-07-22T04:48:47.000+0000",
    "gpa": 1,
    "major": "Computer Science",
    "_links": {
        "self": {
            "href": "http://104.248.0.248/students/902"
        },
        "student": {
            "href": "http://104.248.0.248/students/902"
        },
        "user": {
            "href": "http://104.248.0.248/students/902/user"
        },
        "currentSession": {
            "href": "http://104.248.0.248/students/902/currentSession"
        }
    }
}

// Create Professor
{
	"firstName": "UserP",
	"lastName" : "McUserP",
	"email" : "user@gmail.com",
	"rating" : 3.4
}

// Return 
{
    "firstName": "UserP",
    "lastName": "McUserP",
    "email": "user@gmail.com",
    "dateJoined": "2019-07-18T02:24:11.297+0000",
    "rating": 3.4,
    "_links": {
        "self": {
            "href": "http://localhost:8080/professors/3" <-- Note This
        },
        "professor": {
            "href": "http://localhost:8080/professors/3"
        },
        "currentDepartment": {
            "href": "http://localhost:8080/professors/3/currentDepartment"
        },
        "sessionsList": {
            "href": "http://localhost:8080/professors/3/sessionsList"
        },
        "currentClass": {
            "href": "http://localhost:8080/professors/3/currentClass"
        }
    }
}



```

## Other Endpoints

* all require jwt token -- when it is turned on
* only admin will be able to POST, PUT, PATCH
* everyone can GET
* Headers
    * Content-Type : application/json
    * Authentication : Bearer {jwt_token}
### Departments
GET  .../departments
GET  .../departments/{department_id}
POST .../departments     -- admin only


### Classes
GET  .../classes
GET  .../classes/{class_id}
POST .../classes     -- admin only

### Sessions
GET  .../sessions
GET  .../sessions/{session_id}
POST .../sessions     -- admin only


### Comments
GET  .../comments
GET  .../comments/{session_id}
POST .../comments     -- admin only























// Ignore this for now. Not needed any more
### Associating 
* Request: PUT
* Header: Content-Type : text/uri-list
* Header: Authentication : Bearer {jwt}
* Endpoint /users/{user_id}/userData
* Body: See examples
* Response Success = 204
* Response Fail = 500 <-- this will change for now just check that it was 200
```java
   http://localhost:8080/professors(or students)/{id}

```