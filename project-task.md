# Animal Shelter - JavaEE Workshop Project
You have been tasked with developing an application for an animal shelter.
The frontend will be written by a small Austrian indie-company called AFGA, so you just need to provide a backend for it.

# Step 1 - Entities
In this stage, you will provide the basic infrastructure for a standard dashboard application.
Adding, retrieving and altering entries will be covered here.

This includes creating all relevant model classes, repositories and endpoints.

### a) Adding Animals
As an animal shelter admin, I want to add new animals to our database. I at least want to store the following information:

- name (string, e.g. "Rupert")
- type (enum, e.g. WALRUS)
- comment (string, e.g. "Where the hell did he even come from?")
- available (boolean, e.g. true)

### b) Listing Animals
As an anonymous internet user, I want to list all available animal name and what type they are.
Do not show unnecessary information (e.g. comment).

### c) Animal detail view
As a particularly interested internet user, I want to retrieve all available information to a specific animal.

### d) Adopting animals
As a particularly interested internet user, I want to adopt a specific, available animal.


### General requirements
- You should be able to query your endpoints using your browser for GET requests and Postman or Insomnia for POST.
- Feel free to implement the requirements any way you want to.
- You better add some ester egg for Rupert the walrus.
- You may add more equally important infos if required, like IDs or whether the animals have been vaccinated or not.
- Try to keep your classes clean. Try to stick to the single responsibility principle in classes.

### Bonus 1) Test data
By default, Quarkus will execute your `import.sql`.

Provide some test data in there, including Rupert's less loved family.

### Bonus 2) Improved persistence
Instead of the provided embedded H2 database, try to use and configure any other quarkus endorsed database.

You will need to add dependencies to your maven config, but if done correctly, you should not need to edit much in your `application.properties`.

### Bonus 3) Panache
If you're done with manually writing entity manager queries, try out the new way of storing and querying using Quarkus Panache entities.


# Step 2 - Validation

Have you tried what happens when you add an animal with a wrong type?
Have you tried leaving the name `undefined`?

### a) Basic validation
"Garbage in, garbage out" is a well known phrase, so let's keep the garbage from entering our app in the first place.

- Using hibernate validators, enforce that newly added animals must have a non-empty name (= at least three characters of text).
- When adopting an animal, check if it is available and throw an error otherwise.

### b) Meaningful Errors
In case of errors, provide useful information to the user.

- For every endpoint, try to avoid returning responses with statuscode >=500 and throw meaningful 400 errors instead.
- When the user enters something wrong, return human-readable error messages.

### c) 404
When the user tries to access individual animals, which don't actually exist, provide them with meaningful 404 error messages.

Try to re-use this logic for all endpoints with that behaviour.

### General requirements
- Error messages should be visible just like any other response.
- The network tab in your browser or the response code in postman should reflect the error state clearly.

### Bonus 1) Documentation
To prevent user-errors in the first place, documentation is key.

- Document your endpoints with auto-generated OpenAPI specifications using `quarkus-smallrye-openapi`.
- View your documentation in the browser. By default, the url is `/q/swagger-ui`, but try to change it to `/documentation`.

### Bonus 2) Examples
In addition to your readable documentation, try to add meaningful example values to your endpoints.

You have succeeded if the swagger-ui from **Bonus 1)** has all the necessary data to add a new animal without the user having to provide data themselves.


# Step 3 (Bonus) - Networking

So far, your application runs perfectly on its own. Either way, let's change that and add networking with external services to the equation :)

### a) Additional Info

- Integrate API Ninja's animal API https://api-ninjas.com/api/animals.
- Create a free API token. Don't spam them or misuse it just for fun.
- The same detail view as in **Step 1 c)** should contain an additional field with all the infos you can get for its type. Don't store this in the database, but query it, when the detail view is requested.
- Send requests using a Quarkus rest client from `quarkus-rest-client-jsonb` or `quarkus-rest-client-reactive-jsonb`. (Try to find out the difference :) )

### b) Cache
Calling external APIs is expensive, even if it is another team's service running on the same physical machine.
Cache responses wherever possible to improve user experience, save time, computation cost, networking and money.

### c) Redirects
Add (e.g. Wikipedia-) links to animals. You can choose whether to make it configurable on a per-animal basis or hard-coded on a per-type basis.

Either way, provide a route for a specific animal where a user will be redirected to the external page.