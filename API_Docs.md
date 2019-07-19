## Login Flow

* Request: POST
* Header: Content-Type : application/json
* Endpoint /users/signin
* Body: Pass a username and password
* Response Success = 200
* Response Fail = 500 <-- this will change for now just check that it was 200
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
### 1. Create User Object
* Request: POST
* Header: Content-Type : application/json
* Header: Authentication : Bearer {jwt}
* Endpoint /users/register
* Body: Pass a username and password
* Response Success = 200
* Response Fail = 500 <-- this will change for now just check that it was 200
```java
// POST request
{
	"username": "User1",
	"password": "Password"
}

// Responce 
{
    "id": 901,                      <== note this
    "username": "User1",
    "roles": [
        {
            "id": 2,
            "description": "User role",
            "roleName": "ROLE_USER",
            "authority": "ROLE_USER"
        }
    ],
    "userData": null
}
```

### 2. Create Student or Professor
* Request: POST
* Header: Content-Type : application/json
* Header: Authentication : Bearer {jwt}
* Endpoint /students    or /professors
* Body: See examples
* Response Success = 200
* Response Fail = 500 <-- this will change for now just check that it was 200
```java
// Create Student (POST)
{
	"firstName": "User",
	"lastName" : "McUser",
	"email" : "user@gmail.com",
	"gpa" : 3.4,
	"major" : "Computer Science"
	
}

// Student Return
{
    "firstName": "User",
    "lastName": "McUser",
    "email": "user@gmail.com",
    "dateJoined": "2019-07-18T02:21:58.385+0000",
    "gpa": 3.4,
    "major": "Computer Science",
    "_links": {
        "self": {
            "href": "http://localhost:8080/students/1" <-- Note This
        },
        "student": {
            "href": "http://localhost:8080/students/1"
        },
        "sessionsList": {
            "href": "http://localhost:8080/students/1/sessionsList"
        },
        "currentDepartment": {
            "href": "http://localhost:8080/students/1/currentDepartment"
        },
        "currentClass": {
            "href": "http://localhost:8080/students/1/currentClass"
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