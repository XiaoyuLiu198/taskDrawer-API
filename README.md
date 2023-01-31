# taskDrawer-API
An API service connecting with MySQL based on Java Spring

## Functionality
Supports CRUD operations and matching operation of tasks. Each task record contains task id, task name, task description and time needed for completing the task.
Task id is the primary key in the data model.

## JPA 
Spring JPA is appiled in this project to simplify ORM and data access layers. The implemetation of data access layers includes utilizing DAO to simplify boilerplate code, and IoC to simpilify dependency control.
The complete diagram is 
<img width="614" alt="Screen Shot 2023-01-31 at 12 15 57 AM" src="https://user-images.githubusercontent.com/65391883/215681428-ddaeb9a1-4153-455a-bc0b-52ea89f9351a.png">

## MyBatis
To implement the matching functionality(for example to fetch tasks which time to complete is below x days), MyBatis is appied to enable creating handlers filing custom SQL queries. 

## Load Balancer
To ensure the scalability of this service, load balancer is applied to route the API requests to different servers. Used spring cloud LoadBalancer and webflux to implement client-side load balancing.

└── client

└── server

When someone hits the endpoint in client side, WebClient builder creates a WebClient instance, which makes an HTTP GET request to the serve side URL and gives us the result.
