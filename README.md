# leanix-todo application
## JSON structure
```json lines
{
  id: [mandatory],
  name: [mandatory],
  description: "",
  tasks: [{
      name: [mandatory],
      description: ""
  }]
}
```
## APIs
* GET </host/>:8080/application/todos → Returns a list of all Todos
* POST </host/>:8080/application/todos → Expects a Todo (without id) and returns a Todo with id
* GET </host/>:8080/application/todos/{id} → Returns a Todo
* PUT </host/>:8080/application/todos/{id} → Overwrites an existing Todo
* DELETE </host/>:8080/application/todos/{id} → Deletes a Todo

## Prerequisites
* Maven
* Docker

## Running the application
* ```cd </root project directory/>```
* ```mvn clean package```
* ```docker run --name leanix-postgres -p 5432:5432 -e POSTGRES_PASSWORD=pg-password -e POSTGRES_USER=pg-user -e POSTGRES_DB=todo -d postgres```
* ```java -jar target/ToDo-1.0-SNAPSHOT.jar server to-do.yml```

## Accessing the API's
* Get all

```curl --location --request GET 'localhost:8080/application/todos'```
* Get by Id

```curl --location --request GET 'localhost:8080/application/todos/{id}'```
* Create task
```
curl --location --request POST 'localhost:8080/application/todos' \
--header 'Content-Type: application/json' \
--data-raw '{<<task_object>>}'
```
* Delete task

```curl --location --request DELETE 'localhost:8080/application/todos/{id}'```
* Update an existing task
```
curl --location --request PUT 'localhost:8080/application/todos/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{<<task_object>>}' 
```

## Future Improvements
* Security
* Caching