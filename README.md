# CRUD-REST-API
I have created this API to implement complete login and signup functionality with user authentication by using an access token and saving the encrypted password in the database. To avoid malicious email registration, I have implemented email verification by sending emails via Amazon Simple Email Service. I have deployed this web service on an Amazon EC2 instance which uses Amazon Linux 2023 as an OS.
_I have deployed this web service on Amazon EC2 Instance. You can see in the following image_
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Screenshot%20(43).png)

_Note:- Here in the demonstration I have deployed the code on local only_

## Demonstration: How API works?
### 1. Registration/Create User 
- URL to create user http://localhost:8080/mobile-app-ws/api/users/create
- Http Request with the following JSON payload
```json
{
    "firstName" : "Sarfaraz",
    "lastName" : "Hussain",
    "email" : "call2me786@gmail.com",
    "password" : "password"
}
```   
- Http Response and we are creating userId unique as you can see in the response
```Json
{
    "email": "call2me786@gmail.com",
    "firstName": "Sarfaraz",
    "lastName": "Hussain",
    "userId": "XAmzmIfnvrTlfudz3LqigwjD0Yu5w3"
}
```
- After getting the response an email has been sent to the registered email id we need to verify this otherwise our verification will not change in the database. 
_You can see the emailVerificationStatus in the following DB SS._
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/db1.png)
- Go to registred email id inbox and verify the mail then emailVerificationStatus will change to 1.
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/email.png)
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Screenshot%20(37).png)
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Capture.PNG)

### 2. Login with user credentials that we have just created 
- First we need access token to login. URL to get access token is http://localhost:8080/mobile-app-ws/api/authentication . Following is the username and password in the form of JSON payload.This request will save half access token in the database and half will return as a response to the user and will store it in ios keychain if we are using this web service in ios application. 
```json
{
    "userName" : "call2me786@gmail.com",
    "userPassword" : "password"
}
```
- Https response with access token 
```json
{
    "id": "XAmzmIfnvrTlfudz3LqigwjD0Yu5w3",
    "token": "9UFKFy5Wiw0W8nMB2cLuA="
}
```
- Now we can login by using created id and access token. We will pass the created id as path parameter and token as the Bearer Token in postman header. 
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Screenshot%20(38).png)


### 3. Update user details
- We need to pass userId of user in PathParameter. URL to update the user is http://localhost:8080/mobile-app-ws/api/users/update/XAmzmIfnvrTlfudz3LqigwjD0Yu5w3 and then we need to pass access token in the postman header section. And we need to pass JSON payload updated user details as you can see in the following pictures.

- Passing access token as bearer token.
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Screenshot%20(40).png)

- JSON payload with updated user details.
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Screenshot%20(39).png)

### Get list of users by Query Parameter
- How many record we want to fetch from the database, we need to pass in the query parameter. URL For this request http://localhost:8080/mobile-app-ws/api/users/list/?start=0&limit=2

- Response 
```json
[
    {
        "email": "call2me786@gmail.com",
        "firstName": "Sultan",
        "href": "/users/user/XAmzmIfnvrTlfudz3LqigwjD0Yu5w3",
        "lastName": "Singh",
        "userId": "XAmzmIfnvrTlfudz3LqigwjD0Yu5w3"
    },
    {
        "email": "shiitian786@gmail.com",
        "firstName": "Sharukh",
        "href": "/users/user/5CWRDZtst0dshgnz1AvV12gqx72KBl",
        "lastName": "Khan",
        "userId": "5CWRDZtst0dshgnz1AvV12gqx72KBl"
    }
]
```
![img](https://github.com/Sarfaraz-Hussain/CRUD-REST-API/blob/master/images/Screenshot%20(42).png)
