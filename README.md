## Product Search Service
- Built with Kotlin and Spring Boot with Spring Web

## Running the app
- Default port is 8080

```bash
## Running locally
$ ./gradlew bootRun
```

## Test
- Implemented unit tests with MOCKK, JUnit5 and AssertK

```bash
# unit tests
$ ./gradlew test

```


## Sample Curl with Mock Data

```
curl --location 'localhost:8080/products?searchTerm=Table'

Response:
 {
    "items": [
        {
            "id": 2,
            "name": "Modern Table",
            "options": [
                {
                    "id": 6,
                    "name": "Wood",
                    "price": 1223
                },
                {
                    "id": 7,
                    "name": "Metal",
                    "price": 2154
                }
            ],
            "priceRange": "US$1223.00-US$2154.00"
        }
    ],
    "meta": {
        "searchTerm": "Table",
        "count": 1
    }
}
```

## Modules
- com.productsearch.productsearch.app 
  - Containing Plumbing/Framework related code
- com.productsearch.productsearch.app 
  - Code containing core logic of the service, free of framework specific code
