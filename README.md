# bank-demo

**REST-сервис на Spring Boot для управления пользователями и их банковскими счетами**

---

## Стек и технологии

* **Язык**: Java 11+
* **Фреймворк**: Spring Boot 2.7
* **СУБД**: PostgreSQL
* **Миграции БД**: Flyway
* **Кэширование**: Spring Cache (Caffeine), Redis (по желанию)
* **Безопасность**: JWT (io.jsonwebtoken 0.11.5)
* **Сборка**: Maven
* **Логирование**: SLF4J + Logback
* **Тестирование**:

  * Unit-тесты: JUnit5, Mockito
  * Интеграционные: Spring MockMvc, Testcontainers (PostgreSQL)
* **Mapper**: MapStruct

---

## Требования

* Java 11+
* Maven 3.6+
* PostgreSQL (локально или в контейнере)
* (Опционально) Redis для кэша

---

## Быстрый старт

1. Склонировать репозиторий:

   ```bash
   git clone https://github.com/Akatofff/bank-demo.git
   cd bank-demo
   ```
2. Создать базу данных:

   ```sql
   CREATE DATABASE bank_demo;
   ```
3. В `src/main/resources/application.yml` указать параметры подключения и JWT-настройки:

   ````yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/bank_demo
       username: postgres
       password: password
   jwt:
     secret: MyJwtSecretKey
     expiration: 3600  # время жизни токена в секундах
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/bank_demo
       username: postgres
       password: password
   ````
4. Запустить сервис:

   ```bash
   mvn spring-boot:run
   ```

При первом старте Flyway применит миграции из `src/main/resources/db/migration`.

---

## Запросы к API

### Аутентификация

> Реализовано в `JwtTokenFilter`, по умолчанию нет эндпоинтов `/auth`, они могут быть добавлены отдельно.

### Пользователи (`/users`)

| Метод | Путь          | Тело запроса                     | Описание                    |
| :---- | :------------ | :------------------------------- | :-------------------------- |
| POST  | `/users`      | `{ "name":..., "password":... }` | Создать пользователя        |
| GET   | `/users`      | —                                | Получить всех (UserDto)     |
| GET   | `/users/{id}` | —                                | Получить пользователя по ID |

**DTO `UserDto`** содержит:

```yaml
id: Long
enails: Set<String>
phones: Set<String>
```

### Счета (`/accounts`)

| Метод | Путь                      | Параметры/Тело                           | Описание                                            |
| :---- | :------------------------ | :--------------------------------------- | :-------------------------------------------------- |
| POST  | `/accounts`               | `{ "balance":... , "user":{ "id":... }}` | Открыть новый счёт (устанавливает `initialBalance`) |
| GET   | `/accounts/user/{userId}` | —                                        | Получить все счета пользователя (`AccountDto`)      |
| POST  | `/accounts/transfer`      | `from`, `to`, `amount`                   | Перевод денег между счетами                         |

**DTO `AccountDto`** содержит:

```yaml
id: Long
balance: BigDecimal
```

---

## Архитектура проекта

```
src/
├── main/
│   ├── java/com/example/bankdemo/
│   │   ├── config/          # Security, Scheduler
│   │   ├── controller/      # REST-контроллеры
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA-сущности
│   │   ├── exception/       # Класс NotFoundException
│   │   ├── mapper/          # MapStruct мапперы
│   │   ├── repository/      # Spring Data JPA репозитории
│   │   ├── security/        # JWT Filter
│   │   ├── service/         # Интерфейсы сервисов
│   │   └── service/impl/    # Реализации бизнес-логики
│   └── resources/
│       ├── application.yml
│       └── db/migration/V1__init.sql
└── test/                  # Unit и интеграционные тесты
```

---

## Конфигурация кэша

По умолчанию используется Caffeine. Для подключения Redis
раскомментируйте секцию в `application.yml`:

```yaml
spring.cache.type: redis
spring.redis.host: localhost
spring.redis.port: 6379
```

---

## Тестирование

Запустить все тесты:

```bash
mvn clean test
```

---
