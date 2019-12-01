# DWP Digital Tech Test - Kyle Bennett

## Brief

Using the language of your choice please build your own API which calls the API at 
https://bpdts-test-app.herokuapp.com/, and returns people who are listed as either 
living in London, or whose current coordinates are within 50 miles of London.

## Solution

The solution I have developed is a small java web app that runs on spring boot. It
offers 2 rest endpoints or a web form, built using thymeleaf, to perform the search. 

The endpoints return a JSON list of the relevant users with or the form displays them 
on screen.

### Building and Running

To build the solution run the following commands from the root folder:

mvn clean install

java -jar target/dwp-digital-tech-test-0.0.1-SNAPSHOT.jar

### How to use

The solution offers 2 methods of getting the specified users:

##### Api Call

The solution has 2 GET rest endpoints. One to return users within 50 miles of London 
and one that allows parameters to determine the distance and location to use in the 
search.

To return all users within 50 miles of London:

GET localhost:9090/api/users-within-fifty-miles-of-london

To search with different parameters:

GET localhost:9090/api/users-within-distance-of-location?distance=\<distance>&locationName=\<cityname>&locationLat=\<locationLatitude>&locationLong=\<locationLongitude> 

##### Web Form

Navigate to localhost:9090

Fill in the form with the city, distance and coordinates and click submit. 
For the purposes of this test the default form values will return users within
50 miles of London.

A list of users that meet the criteria will be displayed.

Click back to go back to the form.