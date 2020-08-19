# Food Truck Command Line App

This is a simple command line app in Java which utilizes the San Francisco Socrata API for food trucks in the area. This app
grabs all the local food trucks available at the time and date user accesses the application. The results are sorted in ascending
order and given to the user 10 results at a time. Once the user reaches the end of the list the application will end.

## How to install Gradle
Please reference this [page](https://gradle.org/install/) (https://gradle.org/install/) of the Gradle documentation to
download and install Gradle correctly on MacOS & Linux.

## Build Steps

- Once you have unzipped the program onto your desktop, please go into the root folder inside your terminal (redfin_takehome_assignment).
- Inside the terminal run the following command:
```
gradle build
```
**Sometimes when unzipping a file it can create a duplicate folder with the same name as the zipped one. Please make sure you are
in the root folder where the src folder, build.gradle file, and README.md are stored.**
This will cause gradle to not build correctly.
- Once the build is successful, run this following command:
```
java -jar ./build/libs/food_truck_cli.jar
```


## Write Up
To start turning this proof-of-concept command line application into a fully featured web application, I would add a 
front-end UI with Google Maps integrated into it to provide visibility into food truck location and availability. I 
would capture the user’s location by either using the browser’s geolocation API or require user location input. The 
second feature I would add is the ability to save food truck favorites. This would allow the user to search the location
 of their favorite food trucks at any point in time. 

For the back end I would use a microservices architecture to help with scalability and use a blue/green deployment 
method to help with reliability or quick bug fixes. Since this application would have millions of concurrent users, I
 would cache common queries to help reduce repetitive back end calls and to assist with performance. To make sure my 
 data was always up to date, I would retrieve Socrata API data nightly. When adding the new data to the database I 
 would only add/update/delete rows that were different than the original dataset. Lastly, I would consider adding a 
 GraphQL to help resolve issues of “overfetching” or “underfetching” when using a REST API.

