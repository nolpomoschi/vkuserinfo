# VK User Info Service

## Описание проекта

RESTful сервис для получения информации о пользователях VK (ФИО) и проверки их членства в группах. Сервис использует официальное API ВКонтакте.

## Функциональность

- Получение ФИО пользователя по его ID
- Проверка членства пользователя в указанной группе
- Кэширование результатов запросов
- Документация API через Swagger UI

## Технологии

- Java 17
- Spring Boot 3.1.5
- Apache Camel
- Spring Cache
- Lombok
- Swagger/OpenAPI 3.0
- JUnit 5 + Mockito

## Требования

- JDK 17+
- Maven 3.8+
- Сервисный ключ приложения VK

## Установка и запуск

1. Клонировать репозиторий:
```bash
git clone https://github.com/nolpomoschi/vkuserinfo.git
cd vkuserinfo
```

2. Собрать проект:
```bash
mvn clean package
```

3. Запустить приложение:
```bash
java -jar target/vkuserinfo-1.0.0.jar
```

Или запустить через IDE (класс `VkUserInfoApplication`)

## Конфигурация

Основные настройки в `application.yml`:

```yaml
server:
  port: 8080
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=5m
```

## Использование API

### Получение информации о пользователе

**Запрос:**
```http
POST /api/v1/user-info
Headers:
  vk_service_token: YOUR_VK_SERVICE_TOKEN
Content-Type: application/json

{
  "user_id": 12345,
  "group_id": 67890
}
```

**Ответ:**
```json
{
  "last_name": "Иванов",
  "first_name": "Иван",
  "middle_name": "Иванович",
  "member": true
}
```

### Документация API

После запуска сервиса документация доступна по адресу:  
http://localhost:8080/swagger-ui.htm

### Тестирование

Запуск тестов:
```bash
mvn test
```