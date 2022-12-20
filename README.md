# Cycloid EPG

## General description
This project was developed as a RESTful API in Java, using Springboot as framework
to provide EPG information.

Using the endpoints the user will be able to create, delete or update some value stored
in the h2 database.

The hateoas was used to improve the user experience, everything was developed with the objective
to facilitate frontend integration and navigation between/across the endpoints.

## Models

![models_cycloid_epg.png](/static/img_1.png)

- The relationship between Channel and Program is **one to many**, a Channel can have many Programs, and Many Programs
can be associated with only one Channel.

## Usage

### Back-end - Java Spring Boot
On the main folder (cycloid-epg):
```bash
mvn clean install
mvn spring-boot:run
```

- It is possible just run directly on bash, or you can import the project as existing sources based on maven.

## Endpoints

- There is a file called [cycloid-epg.postman_collection.json](/static/cycloid-epg.postman_collection.json) inside the 'static' folder, 
it is a collection created to help you test each endpoint.
  - To create this collection I used postman.


- These are some examples of requests using curl.

  - Create Channel
    ```curl
    curl --location --request POST 'localhost:8085/api/channels/create' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name":"Cycloid Epg",
        "position": 1, 
        "category": "Education"
    }'
    ```
  - List All Channels
    ```curl
        curl --location --request GET 'localhost:8085/api/channels'
      ```

  - Get Channel By Id
      ```curl
         curl --location --request GET 'http://localhost:8085/api/channels/1'
      ```    

  - Create Program
    ```curl
    curl --location --request POST 'localhost:8085/api/programs/create' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "channelId": 1,
        "imageUrl": "https://www.bureauveritas.pt/sites/g/files/zypfnx751/files/2021-10/Cycloid_Tagline_RGB_Positive_reduz.jpg", 
        "title": "Cycloid Custom Program",
        "description":"Consulting, Architecture, Design and Development. Maintenance and evolution at your own pace.",
        "startTime": "15-12-2022 11:00:00", 
        "endTime": "15-12-2022 12:00:00"
    }'
    ```
  - Get Program By Id
      ```curl
         curl --location --request GET 'localhost:8085/api/programs/1'
      ```    
  - Update Program
    ```curl
    curl --location --request PUT 'localhost:8085/api/programs/1' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "channelId": 1,
        "imageUrl": "https://www.bureauveritas.pt/sites/g/files/zypfnx751/files/2021-10/Cycloid_Tagline_RGB_Positive_reduz.jpg", 
        "title": "Cycloid Custom Program - Testing",
        "description":"REMOVED",
        "startTime": "15-12-2022 11:00:00", 
        "endTime": "15-12-2022 12:00:00"
    }'
    ```
  - Delete Program
      ```curl
        curl --location --request DELETE 'localhost:8085/api/programs/1'
      ```    
  - Get All Programs in Channel
      ```curl
        curl --location --request GET 'localhost:8085/api/programs/channel/1'
      ```

## Unit tests
On the main folder (cycloid-epg):
```bash
mvn test
```
