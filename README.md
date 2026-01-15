### Food Knowledge Hub

- this project is more about diving deep into 
Testing in general, Unit, Integration, Repository (JPA) testing, slice tests, end to end testing exc..


#### about project
* project gives knowladge about diffrent Food and whats inside them , all miniral, vitamins exc..,
* there are 2 types of user, admin user and normal user
  * Admin User
    * bulk insert
    * Can create, updtate info, and delete, (full CRUD access)
    * fetch unlimited rows from db
  * normal User
    * CRUD
    * fetch unlimited rows db
  * non-user
    * fetch only first 5 rows on db
    * read only

very simple project with the goal to dive deeper into code Testing


## Requirment
- docker installed
- jdk 21
- Spring boot 4+
- Maven


### Getting started
* git clone 
* `cd food-knowledge-hub`
*  ```./mvnw spring-boot:run -Dspring-boot.run.profiles=dev```


### Getting started with tests
* `mvn test`
* `mvn integration-test` or `mvn verify` to run integration testing


### Api testing json

 POST `http://localhost:8080/api/v0/food`
```json
{
  "name": "Banana",
  "imageUrl": "https://example.com/banana.png",
  "overview": " packed with nutrients like potassium, vitamin C, fiber, and vitamin B6, which support heart health, digestion, and nerve function. They can provide a quick energy boost,",
  "benefits": ["High in potassium"],
  "macronutrients": {
    "calories": 80,
    "proteinGrams": 1.1,
    "fatGrams": 0.3,
    "carbohydratesGrams": 23,
    "fiberGrams": 2.6,
    "sugarGrams": 12
  },
  "macrominerals": [
    {
      "macromineral": "POTASSIUM",
      "amountMilligrams": 358,
      "dailyValuePercent": 10
    }
  ],
  "microminerals": [
    {
      "micromineral": "MANGANESE",
      "amountMilligrams": 0.27,
      "dailyValuePercent": 13
    }
  ],
  "vitamins": [
    {
      "vitamin": "B6_PYRIDOXINE",
      "amountMilligrams": 0.4,
      "dailyValuePercent": 20
    }
  ]
}

```


### Future frontend, Here below is a test image, what it could look like
<img width="1102" height="775" alt="image" src="https://github.com/user-attachments/assets/ddef4ee0-b4ac-4bb4-9338-87a6685c454a" />
