# CRUD-REST-API
I have created this API to implement complete login and signup functionality with user authentication by using an access token and saving the encrypted password in the database, to avoid malicious email registration. I have implemented email verification by sending emails via Amazon Simple Email Service. I have deployed this web service on an Amazon EC2 instance which uses Amazon Linux 2023 as an OS.

_Note:- Here in the demonstration I have deployed the code on local only_

## Demonstration: How API works?
### 1. Create User 
- URL to create user http://localhost:8080/mobile-app-ws/api/users/create
- Http Request
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
