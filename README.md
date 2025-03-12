# Task Management System

Проект написан на Java 23 с использованием Spring Boot 3.
Для работы требуется БД PostgreSQL.

## Использование
Для запуска приложения требуется установленный и запущенный Docker.
В корне проекта нужно создать файл .env и прописать следующие значения:
- POSTGRES_DB=<Название базы данных>
- POSTGRES_USER=<Ваш пользователь в postgres>
- POSTGRES_PASSWORD=<Пароль этого пользователя>

### Запуск
Для этого потребуется установленные Java 23, Maven, Docker.
- Запустить из модального окна IDEA [Run Anything](https://www.jetbrains.com/help/idea/running-anything.html) команду:

          mvn clean verify

- В терминале написать команду:

          docker-compose up --build

- Если нужно очистить базу данных при перезапуске, то напишите команду:

          docker-compose down -v

### API документация
После запуска приложения, документация будет доступна по адресу:
http://localhost:8081/swagger-ui/index.html#/

