food-fighters
--This repo does not fight food, but you can use it log food.

# Food Logging Web App
This is a simple web application for logging your daily food intake. The app allows you to record what you eat for breakfast, lunch, dinner, and snacks.

## Features

- **Meal Sections:** Separate sections for breakfast, lunch, dinner, and snacks.
- **Add Items:** Easily add food items to each meal section.
- **Get Items:** Easily fetch all the entries made till now.

- **Frontend:** [React](https://reactjs.org/) - Dynamic and plenty of resources and frameworks available.
- **Backend:** Java with [Spring Boot](https://spring.io/projects/spring-boot) - Vast array of libraries available. It is a pretty robust and opinionated way to set up and configure backend services, reducing boilerplate code and speeding up development.
- **Database:** [MongoDB](https://www.mongodb.com/)  well suited for small-scale apps , can be scaled up to evolving data requirements. 


## Mongo DB schema
## MongoDB Schema



User Collection:
{
"_id": ObjectId,
"username": String,
"email": String,
"password": String (hashed),
"createdAt": Date,
"updatedAt": Date
}

Meal Collection:
{
"_id": ObjectId,
"userId": ObjectId (refers to User),
"type": String (e.g., "breakfast", "lunch", "dinner", "snacks"),
"date": Date,
"createdAt": Date,
"updatedAt": Date
}

FoodItem Collection:
{
"_id": ObjectId,
"mealId": ObjectId (refers to Meal),
"name": String,
"quantity": Number,
"calories": Number,
"createdAt": Date,
"updatedAt": Date
}