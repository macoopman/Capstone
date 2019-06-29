# Capstone



### Creating New User/Student
* Create POST to */users to persist new user
    * Content-Type:json
    * Send username, password, etc
    * 201 - responce should hold the url to the new data 
```java
{
    "username": "macoopman",
    "password": "password1",
    "_links": {
        "self": {
            "href": "http://localhost:8080/users/1"  <--- HERE
        },
        "user": {
            "href": "http://localhost:8080/users/1"
        },
        "person": {
            "href": "http://localhost:8080/users/1/person"
        }
    }
}
```
    
* Create POST to */Student to persist new student
    * "Content-Type:json"
    * send name, age, etc
    * 201 - returns
```java
{
    "firstName": "Mike",
    "lastName": "Coopman",
    "dateJoined": "2019-06-29T03:17:21.988+0000",
    "gpa": 3.4,
    "major": "Computer Science",
    "_links": {
        "self": {
            "href": "http://localhost:8080/students/2" <-- Unique url
        },
        "student": {
            "href": "http://localhost:8080/students/2"
        },
        "user": {
            "href": "http://localhost:8080/students/2/user"
        }
    }
}
```
    
* Establish the relationship 
    * PUT request
    * Content-Type:text/uri-list
 ```bash
 curl -i -X PUT -d "http://localhost:8080/student/2" -H "Content-Type:text/uri-list" http://localhost:8080/users/1/person
 
 #Returns 
 HTTP/1.1 204 
 Date: Sat, 29 Jun 2019 03:26:12 GMT

 ```
 
 
 
