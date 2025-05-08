# Spring Boot 동적 설정 리프레시 애플리케이션

이 프로젝트는 Spring Boot 애플리케이션으로, 실행 중 애플리케이션 속성 및 커스텀 YAML 설정을 동적으로 리프레시하는 기능을 제공합니다.

## 개요

애플리케이션은 두 가지 주요 리프레시 기능을 제공합니다.

1.  **애플리케이션 속성 리프레시**: `application.yml` (또는 `application.properties`) 파일에 정의된 `@ConfigurationProperties` 또는 `@Value` 어노테이션이 붙은 빈의 속성을 리프레시합니다.
2.  **커스텀 설정 파일 리프레시**: `src/main/resources/custom-config.yml` 파일의 내용을 HTTP 요청을 통해 동적으로 업데이트하고, 관련된 빈에 변경 사항을 반영합니다.

관리자 엔드포인트는 API 키를 사용하여 보호됩니다.

## 사전 요구 사항

*   Java 11 이상
*   Maven 3.2 이상

## 설정

1.  **API 키 설정**:
    `src/main/resources/application.yml` (또는 `.properties`) 파일에 관리자 API 키를 설정해야 합니다.
    ```yaml
    admin:
      security:
        api-key: "YOUR_SECURE_API_KEY" # 이 값을 안전한 키로 변경하세요.
    ```

2.  **커스텀 설정 파일**:
    `src/main/resources/custom-config.yml` 파일은 애플리케이션에서 사용하는 커스텀 설정을 정의합니다. 예시:
    ```yaml
    app:
      feature:
        x:
          enabled: true
          name: "Initial Feature Name"
      message: "Hello from custom-config.yml"
    ```

## 애플리케이션 실행

Maven을 사용하여 애플리케이션을 빌드하고 실행합니다.

```bash
mvn spring-boot:run