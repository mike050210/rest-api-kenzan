# rest-api-kenzan
REST api to manage employee resources
## How to run it
1. Clone the project
   `git clone https://github.com/mike050210/rest-api-kenzan.git`
2. Import the gradle project
3. Run EmployeeApiApplication class.

## How to test them
The application uses an embedded MongoDB which will be reset when the api stops. The application loads sample data when the application starts. Therefore, every time that you restart the application it will always have the same data loaded.

You have three subresources to test:
* Get all employees:
```
[GET] http://localhost:8080/api/employees
```

* Get an employee by ID:
```
[GET] http://localhost:8080/api/employees/{id}
```

* Create a new employee: 
```
[POST] http://localhost:8080/api/employees

{
    "firstName": "John", 
    "middleName": "", 
    "lastName": "Snow", 
    "dob": "1986-01-01", 
    "employmentDate": 
    "2018-01-01"
}

```

* Edit a new employee: 
```
[PUT] http://localhost:8080/api/employees/{id}

{
    "employeeID": {id},
    "firstName": "John", 
    "middleName": "R", 
    "lastName": "Travolta", 
    "dob": "1960-01-01", 
    "employmentDate": "2019-05-20"
}

```

* Delete an employee:
```
[DELETE] http://localhost:8080/api/employees/{id}
```
**Note:** Delete endpoint requires Authorization header (Basic Auth), use username=kenzan and password=live (Basic a2VuemFuOmxpdmU=).


### Front-end Application
You can also use the UI application in order to perform all this actions. For that, you need to clone and follow instructions to run the react project: https://github.com/mike050210/ui-api-kenzan
