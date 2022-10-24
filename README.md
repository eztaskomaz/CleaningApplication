# CleaningApplication
An application for assigning cleaning staffs to customers

### Description

Cleaning Application helps us to find an appointment each week from cleaning staffs. With cleaning application, customers can:
- Check the availability of cleaning staffs for the given date
- Check the availability of cleaning staffs for the given date, the time and the duration
- Book an available time slot with the available cleaning staffs
- Update his/her book to an available time slot with the available cleaning staffs
-------------------------------------

### How to run?
To be able to run this program quickliy, you need to follow the simple instructions below.

1. Jar file should be generated.
```
mvn clean install
```
2. Docker compose file should be build with;
```
docker-compose build
```
3. Then run with the following command.
```
docker-compose up
 ```

-------------------------------------

## **Technologies**

- Java 17

- SpringBoot

- PostgreSQL

- Junit5

- Maven
  
- Docker

-------------------------------------

## **APIs**

- ### **Availability Check With Booking Date**

This endpoint should return available cleaner professional list
and their available times for the given date.

```
(POST) localhost:8080/availability/findAvailableTimes
 ```
Example Request Body (Content-Type: application/json):
 ```
{
    "bookingDate": "2022-11-01",
    "page": 0,
    "size": 10
}
 ```
Example Response (Content-Type: application/json):
 ```
{
  "content": [
    {
      "availableTime": {
        "hour": 0,
        "minute": 0,
        "nano": 0,
        "second": 0
      },
      "staff": {
        "age": 0,
        "gender": "string",
        "name": "string",
        "surname": "string"
      }
    }
  ],
  "page": 0,
  "size": 0,
  "totalElements": 0,
  "totalPages": 0
}
 ```

- ### **Availability Check With Booking Date, Start Time and Duration**

This endpoint should return available cleaner professional list and their time slot if booking date, start time, and appointment duration is given.

```
(POST) localhost:8080/availability/checkTimeSlotAvailable
 ```
Example Request Body (Content-Type: application/json):
 ```
{
  "bookingDate": "2022-11-01",
  "duration": 2,
  "page": 0,
  "size": 10,
  "startTime": 10
}
  ```
Example Response (Content-Type: application/json):
 ```
{
  "content": [
    {
      "age": 0,
      "gender": "string",
      "name": "string",
      "surname": "string"
    }
  ],
  "page": 0,
  "size": 0,
  "totalElements": 0,
  "totalPages": 0
}
  ```
- ### **Add Booking**

This endpoint creates a booking for a customer with given staffs, booking date, start time, and appointment duration.

```
(POST) localhost:8080/booking/addBooking
 ```
Example Request Body (Content-Type: application/json):
 ```
 {
  "bookingDate": "2022-11-01",
  "customerId": 100001,
  "duration": 4,
  "staffIdList": [
    100001, 100002
  ],
  "startTime": 18
}
 ```
- ### **Update Booking**

This endpoint updates an existing booking for a customer with start time, and appointment duration.

```
(GET) localhost:8080/customer/add
 ```
Example Request Body (Content-Type: application/json):
 ```
 {
  "bookingDate": "2022-11-01",
  "customerId": 100001,
  "duration": 4,
  "staffIdList": [
    100001, 100002
  ],
  "startTime": 18
}
 ```
- ### **Lists Customer**

This endpoint should return all the customers.

```
(GET) localhost:8080/customer/list
 ```
Example Response (Content-Type: application/json):
 ```
 [
  {
    "address": "string",
    "email": "string",
    "id": 0,
    "name": "string",
    "surname": "string"
  }
]
 ```
- ### **Add Customer**

This endpoint creates a customer with given name, surname, mail and address.

```
(GET) localhost:8080/customer/add
 ```
Example Request Body (Content-Type: application/json):
 ```
 {
  "address": "address",
  "email": "email@gmail.com",
  "name": "name",
  "surname": "surname"
}
 ```
-------------------------------------

### Send Requests

APIs can be tested via requests.
These requests are prepared and placed in 'resources' folder:
```
cleaningapplication/src/main/resources/generated-requests.http
 ```
-------------------------------------

## **Database**

When the spring boot project is started, the schema changes will be applied immediately to
the configured database through Liquibase. This application uses a PostgreSQL database.
After the liquibase changes have run, six tables will exist in the fleetmanagement schema:

1. **databasechangelog** - A log of all the liquibase change sets run
2. **databasechangeloglock** - A table that is checked to get a lock to avoid multiple liquibase instances happening
   at the same time
3. **CUSTOMER** - Customer information are stored in this table.
   
    | Attributes | Value | |
    |----------------|-------|-------|
    | Id | Integer | PK |
    | Name | Varchar(32) | |
    | Surname | Varchar(32) | |
    | Email | Varchar(64) | UK |
    | Address | Varchar(256) | |
    | Created_Date | Timestamp | |
    | Last_Modified_Date | Timestamp | |

4. **VEHICLE** - Vehicles help to move cleaning staff. All vehicle information is stored in this table.

    | Attributes | Value | |
    |----------------|-------|-------|
    | Id | Integer | PK |
    | Driver_Name | Varchar(32) | |
    | Licence_Plate | Varchar(32) | UK |
    | Created_Date | Timestamp | |
    | Last_Modified_Date | Timestamp | |

5. **STAFF** - Customer information are stored in this table. Also, it stores the vehicle that assigns to the staff. 

   | Attributes | Value | |
   |----------------|-------|-------|
   | Id | Integer | PK |
   | Name | Varchar(32) | |
   | Surname | Varchar(32) | |
   | Age | Integer |  |
   | Gender | Varchar(8) |  |
   | Vehicle_Id | Integer | FK |
   | Created_Date | Timestamp | |
   | Last_Modified_Date | Timestamp | |

6. **BOOKING** - Booking information are stored in this table. Also, it stores the customer who makes this booking, and which staff is assigned to this customer.

   | Attributes | Value | |
   |----------------|-------|-------|
   | Id | Integer | PK |
   | Customer_Id | Integer | FK |
   | Staff_Id | Integer | FK |
   | Booking_Date | Date | |
   | Start_Time | Time | |
   | End_Time | Time | |
   | Created_Date | Timestamp | |
   | Last_Modified_Date | Timestamp | |

Liquibase creates these tables based on the SQL scripts stores on:

```
cleaningapplication/src/main/resources/db/changelog
 ```