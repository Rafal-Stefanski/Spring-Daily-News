### Spring Daily News ( :newspaper: :smile: )
<img align="right" src="https://badges.pufler.dev/visits/Rafal-Stefanski/Spring-Daily-News">

- using: http client, MySQL, JDBC
- test: unit test with Junit5 and Mockito

### address endpoint: http://localhost:8080/news
***
### Description 
Application downloads list of news with photos, titles, descriptions and source link, from external API. Then adds them to database. For saved news GUI shows a view of the list, with source link button, to find out more on specific info and button for editing all fields in each news.
***
### Unit tests
![screen shot](https://github.com/Rafal-Stefanski/Spring-Daily-News/blob/master/src/main/resources/static/screenshot_test_01.png)

### Screenshots
![screen shot](https://github.com/Rafal-Stefanski/Spring-Daily-News/blob/master/src/main/resources/static/screenshot_01.png)

![screen shot](https://github.com/Rafal-Stefanski/Spring-Daily-News/blob/master/src/main/resources/static/screenshot_02.png)

#### 2024-06-12 update
App is now Dockerized.
1. in terminal `mvn wrapper:wrapper` to create .mvn folder in project directory.
2. in terminal `docker-compose up` or green arrow in docker-compose.yml gutter.
