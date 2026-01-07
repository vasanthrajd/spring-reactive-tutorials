#### Functional End Points

In Spring WebFlux, functional endpoints provide an alternative to the traditional annotation-based approach for defining routes and handling requests. Functional endpoints allow you to define routes and their corresponding handlers using a more functional programming style.
Here's a simple example of how to create functional endpoints in a Spring WebFlux application:

In Web flux, 
An HttpRequest is handled with a HandlerFunction.
    it takes a ServerRequest and returns a Mono<ServerResponse>.
HandlerFunction is equivalent of @RequestMapping annotation, but with the major difference that router functions provide not just data, but also behavior.

**Why Functional Endpoints?**
- Flexibility: Functional endpoints allow for more flexible routing and handling of requests.
- Composability: You can easily compose multiple handler functions and routes together.
- Testability: Functional endpoints can be easier to test in isolation.
- Lightweight: They can lead to more concise and readable code for simple use cases.
- Minimal Annotation Overhead: They avoid the "magic" of reflection-based annotations. This can lead to slightly faster application startup times 
and reduced memory footprint by bypassing some of the heavy Spring infrastructure required to scan and process @RequestMapping.

**ServerRequest and ServerResponse:**
- ServerRequest: Represents the incoming HTTP request. It provides methods to access request data such as headers, query parameters, and the request body.
- ServerResponse: Represents the outgoing HTTP response. It provides methods to set response status, headers, and body.
- Immutable: ServerRequest and ServerResponse are immutable, meaning that once they are created, their state cannot be changed. 
This immutability helps ensure thread safety and makes it easier to reason about the flow of data in your application.

**Extracting body from ServerRequest:**
To extract the body from a ServerRequest, you can use the bodyToMono or bodyToFlux methods, depending on whether you expect a single object or a stream of objects.

```declarative
// For single object
Mono<String> bodyMono = request.bodyToMono(String.class);
Mono<String> string = request.body(BodyExtractors.toMono(String.class));

// For multiple objects
Flux<MyClass> bodyFlux = request.bodyToFlux(MyClass.class);
Flux<Person> people = request.body(BodyExtractors.toFlux(Person.class));

// For form data
Mono<MultiValueMap<String, String>> map = request.formData();

// For Mutlipart data
Mono<MultiValueMap<String, Part>> map = request.multipartData();

```

**Building ServerResponse:**
To build a ServerResponse, you can use the static methods provided by the ServerResponse class.
```declarative
// Example of building a simple OK response with a body
ServerResponse response = ServerResponse.ok()
    .contentType(MediaType.APPLICATION_JSON)
    .body(BodyInserters.fromValue("Hello, World!"));

ServerResponse response = ServerResponse.created()
    .build();

```

**Handler Function:**
```declarative
HandlerFunction<ServerResponse> helloWorld =
  request -> ServerResponse.ok().bodyValue("Hello World");
```

**Validation:**

To perform validation in functional endpoints, you can use the Validator interface provided by Spring. 
You can create a custom validator and use it to validate the request body before processing it.

```declarative
    Mono<MyClass> myClass = request.bodyToMono(MyClass.class)
        .doOnNext(obj -> {
            Errors errors = new BeanPropertyBindingResult(obj, "myClass");
            validator.validate(obj, errors);
            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            }
        })
        .flatMap(validObj -> {
            // Process the valid object
        });
    
```

**Predicates:**
Predicates are used to define conditions for routing requests to specific handler functions. 
You can use built-in predicates or create custom ones.

You can compose multiple request predicates together by using:

RequestPredicate.and(RequestPredicate) -> both must match.

RequestPredicate.or(RequestPredicate) -> either can match.

Many of the predicates from RequestPredicates are composed. For example, RequestPredicates.GET(String) is 
composed from RequestPredicates.method(HttpMethod) and RequestPredicates.path(String). 

**Routes:** 
Router functions are evaluated in order: if the first route does not match, the second is evaluated, and so on. 
Therefore, it makes sense to declare more specific routes before general ones.

**Serving Resources:**
It is possible to redirect requests matching a specified predicate to a resource. This can be useful, for example, for handling redirects in Single Page Applications.


**Filter:**
Filters can be applied to router functions to modify the request or response.
Filters can be used for cross-cutting concerns such as logging, authentication, and error handling.
```declarative
    RouterFunction<ServerResponse> routerFunction = RouterFunctions.route()
        .GET("/hello", helloWorldHandler)
        .filter((request, next) -> {
            // Pre-processing logic
            Mono<ServerResponse> responseMono = next.handle(request);
            return responseMono.doOnNext(response -> {
                // Post-processing logic
            });
        })
        .build();
```

MongoDB Reactive Repositories can also be used with functional endpoints in Spring WebFlux.
```declarative
docker pull mongo:latest
docker run -d -p 27017:27017 --name=mongo-example mongo:latest
```