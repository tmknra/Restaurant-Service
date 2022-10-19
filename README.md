# Restaurant-Service

Cервис обзора ресторанов. Пользователь сервиса может получить основную информацию о ресторане, отзывы и рейтинг по ресторану, оставить свой отзыв.
Также, пользователь может зарегистрироваться в системе, оставить свои данные.
   
Проект является дипломной работой в рамках прохождения курса "Java-разработчик" от https://online.top-academy.ru/. 
Использованные технологии выбраны с целью ознакомления и обучения.

Технологии и библиотеки, использованные в проекте:
-	Java 17
-	Spring Framework
-	PostgreSQL
-	Gradle
-	Liquibase
-	Lombok
-	Mapstract
-	RabbitMQ
-	Docker
-	Junit5
-	Mockito

Проект выполнен по микросервисной архитектуре. Имеются два модуля, которые сообщаются между собой посредством брокера сообщений.
В качестве UI использован Swagger. 
Проект запускается локально, экземпляра приложения на удаленном сервере нет. 
Требования для корректного запуска: Java 17, PostgreSQL, RabbitMQ, Docker.

1) [Restaurant Service](http://localhost:8080/swagger-ui/index.html)  
   Основной модуль проекта. Выполнен в двух контроллерах:

   RestaurantController:
    - Создание ресторана.*
    - Получение ресторана.
    - Получение списка ресторанов (два запроса - полный и кастомизированный).
    - Обновление* и удаление ресторана.
    - Получение отзывов и рейтинга по ресторану.

   FeedbackController:
    - Создание нового отзыва.
    - Получение отзыва.
    - Обновление отзыва.
    - Удаление отзыва.

   *Операция подразумевает взаимодействие с сервисом пользователей. При добавлении/обновлении ресторана сервис проверяет
    корректность вводимых данных о пользователе обращаясь к UserService при помощи FeignClient. При отсутствии проверяе-
    мого пользователя на выходе получаем ошибку, данные не добавляются.


2) [User Service](http://localhost:8081/swagger-ui/index.html)  
   Сервис управления пользователями. Реализация сервиса подразумевает дальнейшее добавления модуля Security.
   Реализованные операции:
    - Создание пользователя.
    - Обновление пользователя.
    - Получение пользователя и списка пользователей.
    - Удаление пользователя.**
    - Обновление владельца ресторана.**

   **Операция подразумевают взаимодействие с сервисом ресторанов: при успешном выполнении запроса на стороне
   UserService сервис отправляет сообщение в RestaurantService посредством RabbitMQ для обновления данных в
   базе данных ресторанов.


