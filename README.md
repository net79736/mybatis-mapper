# MyBatis Mapper 프로젝트

Spring Boot와 MyBatis를 활용한 주문(Order) 및 제품(Product) 관리 시스템입니다.

## 📋 프로젝트 개요

이 프로젝트는 Spring Boot 3.2.5와 MyBatis를 사용하여 주문과 제품 정보를 관리하는 웹 애플리케이션입니다. 
1:N 관계(주문:제품)를 MyBatis의 `collection` 매핑을 통해 처리하며, 동적 SQL을 활용한 조건부 검색 기능을 제공합니다.

## 🛠 기술 스택

- **Java**: 22
- **Spring Boot**: 3.2.5
- **MyBatis**: 3.0.3
- **Database**: MySQL 5.1.45
- **템플릿 엔진**: Thymeleaf
- **커넥션 풀**: HikariCP
- **빌드 도구**: Maven
- **패키징**: WAR
- **기타**: Lombok, log4jdbc-log4j2

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── org/wrapper/mybatismapper/
│   │       ├── configuration/
│   │       │   └── DatabaseConfiguration.java    # MyBatis 및 데이터소스 설정
│   │       ├── MybatisMapperApplication.java      # 메인 애플리케이션 클래스
│   │       ├── order/
│   │       │   ├── mapper/
│   │       │   │   └── OrderMapper.java          # MyBatis 매퍼 인터페이스
│   │       │   ├── service/
│   │       │   │   └── OrderService.java         # 비즈니스 로직 서비스
│   │       │   └── vo/
│   │       │       ├── OrderVO.java              # 주문 VO
│   │       │       ├── ProductVO.java            # 제품 VO
│   │       │       └── SearchVO.java             # 검색 조건 VO
│   │       └── ServletInitializer.java           # WAR 배포용 초기화 클래스
│   └── resources/
│       ├── application.properties                 # 애플리케이션 설정
│       ├── mybatis/
│       │   ├── mapper/
│       │   │   └── OrderMap.xml                  # MyBatis 매퍼 XML
│       │   └── SQL(script)/
│       │       ├── common.sql                    # 테이블 생성 스크립트
│       │       ├── createUser.sql                # 데이터베이스/사용자 생성 스크립트
│       │       └── insert.sql                    # 샘플 데이터 삽입 스크립트
│       └── templates/
│           └── index.html                        # 기본 페이지
└── test/
    └── java/
        └── org/wrapper/mybatismapper/
            └── order/
                └── service/
                    └── OrderServiceTest.java     # 서비스 테스트 코드
```

## 🗄 데이터베이스 구조

### tb_order (주문 테이블)
- `seq`: 주문 일련번호 (PK, AUTO_INCREMENT)
- `userId`: 회원 아이디
- `name`: 회원 이름
- `email`: 회원 이메일
- `phone`: 회원 전화번호
- `address`: 주소
- `regDate`: 생성일시
- `editDate`: 수정일시

### tb_product (제품 테이블)
- `seq`: 제품 일련번호 (PK)
- `order_seq`: 주문 일련번호 (FK)
- `name`: 제품 이름
- `price`: 가격
- `regDate`: 생성일시
- `editDate`: 수정일시

**관계**: 하나의 주문(tb_order)은 여러 개의 제품(tb_product)을 가질 수 있습니다 (1:N 관계).

## 🚀 시작하기

### 사전 요구사항

- Java 22 이상
- Maven 3.6 이상
- MySQL 5.7 이상

### 설치 및 실행

1. **저장소 클론**
   ```bash
   git clone <repository-url>
   cd mybatis-mapper
   ```

2. **데이터베이스 설정**
   
   MySQL에 접속하여 다음 스크립트를 실행합니다:
   ```sql
   -- createUser.sql 실행
   CREATE DATABASE orders DEFAULT CHARACTER SET UTF8;
   CREATE USER 'TP'@'localhost' IDENTIFIED BY '1234';
   GRANT ALL PRIVILEGES ON orders.* TO 'TP'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **테이블 생성 및 샘플 데이터 삽입**
   
   `orders` 데이터베이스를 사용한 후:
   ```sql
   -- common.sql 실행 (테이블 생성)
   -- insert.sql 실행 (샘플 데이터 삽입)
   ```

4. **데이터소스 설정**
   
   `application.properties` 파일에 데이터베이스 연결 정보를 설정합니다:
   ```properties
   spring.datasource.hikari.jdbc-url=jdbc:mysql://localhost:3306/orders
   spring.datasource.hikari.username=TP
   spring.datasource.hikari.password=1234
   spring.datasource.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
   ```

5. **애플리케이션 실행**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   또는 Maven이 설치되어 있다면:
   ```bash
   mvn spring-boot:run
   ```

6. **애플리케이션 접속**
   
   브라우저에서 `http://localhost:8080` 접속

## 🔧 주요 기능

### 1. 주문 목록 조회 (조건부 검색)

`OrderMapper.selectOrderList(SearchVO searchVO)` 메서드를 통해 다양한 조건으로 주문 목록을 조회할 수 있습니다.

**지원하는 검색 조건:**
- `userId`: 회원 아이디 (LIKE 검색)
- `name`: 회원 이름 (LIKE 검색)
- `email`: 회원 이메일 (정확히 일치)
- `address`: 주소 (정확히 일치)
- `productName`: 제품 이름 (정확히 일치)
- `price`: 가격 이하 (이하 검색)

**예시:**
```java
SearchVO searchVO = new SearchVO();
searchVO.setName("John");
searchVO.setPrice(90.0);
List<OrderVO> orders = orderService.selectOrderList(searchVO);
```

### 2. 주문 일련번호 리스트로 조회

`OrderMapper.selectOrderListByOrderSeq(List<String> orderSeqList)` 메서드를 통해 여러 주문 일련번호로 주문 목록을 조회할 수 있습니다.

**예시:**
```java
List<String> orderSeqList = Arrays.asList("1", "2", "3");
List<OrderVO> orders = orderService.selectOrderListByOrderSeq(orderSeqList);
```

### 3. 1:N 관계 매핑

MyBatis의 `<collection>` 태그를 사용하여 주문(OrderVO)과 제품(ProductVO)의 1:N 관계를 자동으로 매핑합니다.

```xml
<resultMap id="OrderListMap" type="org.wrapper.mybatismapper.order.vo.OrderVO">
    <id column="seq" property="seq" />
    <result column="user_id" property="userId" />
    <!-- ... -->
    <collection property="productObjList" ofType="org.wrapper.mybatismapper.order.vo.ProductVO">
        <id column="seq" property="seq" />
        <result column="order_seq" property="orderSeq" />
        <!-- ... -->
    </collection>
</resultMap>
```

## 🧪 테스트

테스트 코드는 `OrderServiceTest` 클래스에 포함되어 있습니다.

**테스트 실행:**
```bash
./mvnw test
```

**주요 테스트 메서드:**
- `selectOrderListWhereClause()`: 조건부 검색 테스트 (Deprecated)
- `selectOrderListByOrderSeq()`: 주문 일련번호 리스트로 조회 테스트

## ⚙️ 설정 설명

### DatabaseConfiguration

- **@MapperScan**: `org.wrapper.mybatismapper.**.mapper` 패키지의 매퍼 인터페이스를 자동 스캔
- **@EnableTransactionManagement**: 트랜잭션 관리 활성화
- **HikariCP**: 고성능 커넥션 풀 사용
- **SqlSessionFactory**: MyBatis 세션 팩토리 설정
- **Mapper XML 위치**: `classpath:/mybatis/mapper/*.xml`

### MybatisMapperApplication

- **@PostConstruct**: 애플리케이션 시작 시 TimeZone을 UTC로 설정
- WAR 패키징을 위한 `ServletInitializer` 클래스 포함

## 📝 주요 클래스 설명

### OrderVO
주문 정보를 담는 Value Object입니다. `productObjList` 필드를 통해 해당 주문에 속한 제품 목록을 포함합니다.

### ProductVO
제품 정보를 담는 Value Object입니다. `orderSeq` 필드로 주문과의 관계를 나타냅니다.

### SearchVO
검색 조건을 담는 Value Object입니다. 모든 필드는 선택적(Optional)이며, 설정된 조건만 WHERE 절에 추가됩니다.

### OrderService
주문 관련 비즈니스 로직을 처리하는 서비스 클래스입니다. `OrderMapper`를 주입받아 사용합니다.

## 🔍 동적 SQL 활용

MyBatis의 동적 SQL 기능을 활용하여 조건부 WHERE 절을 생성합니다:

```xml
<sql id="selectListOrderWhereClause">
    <if test="userId != null and userId != ''">
        AND tb_o.user_id LIKE CONCAT(#{userId}, '%')
    </if>
    <!-- ... 기타 조건들 ... -->
</sql>
```

## 🕐 타임존 테스트 기능

이 프로젝트에는 DB와 서버의 시간 불일치 문제를 확인하고 테스트할 수 있는 기능이 포함되어 있습니다.

### 개요

포스팅에서 언급한 내용을 실제로 확인할 수 있는 테스트 코드와 API를 제공합니다:
- DATETIME vs TIMESTAMP의 타임존 처리 차이
- LocalDateTime vs ZonedDateTime의 동작 차이
- JDBC URL의 serverTimezone 설정 영향
- JVM 타임존 설정 영향

### 테이블 생성

타임존 테스트를 위한 테이블을 생성합니다:

```sql
-- timezone_test.sql 실행
source src/main/resources/mybatis/SQL(script)/timezone_test.sql
```

### API 엔드포인트

#### 1. 모든 테스트 데이터 조회
```
GET /api/timezone/test-data
```

응답 예시:
```json
{
  "jvmTimeZone": "Asia/Seoul",
  "dbTimeZoneInfo": {
    "global": "SYSTEM",
    "session": "SYSTEM",
    "system": "KST"
  },
  "data": [
    {
      "id": 1,
      "zoneDateTime": "2024-09-09T15:25:51+09:00[Asia/Seoul]",
      "localDateTime": "2024-09-09T15:25:51",
      "zoneTimestamp": "2024-09-09T15:25:51+09:00[Asia/Seoul]",
      "localTimestamp": "2024-09-09T15:25:51"
    }
  ]
}
```

#### 2. 특정 ID 데이터 상세 분석
```
GET /api/timezone/test-data/{id}
```

타임존 변환 분석 결과를 포함하여 반환합니다.

#### 3. DB 타임존 정보 조회
```
GET /api/timezone/db-timezone
```

#### 4. 테스트 데이터 생성
```
POST /api/timezone/test-data/kst  # KST 환경에서 생성
POST /api/timezone/test-data/utc  # UTC 환경에서 생성
```

### 테스트 코드 실행

포스팅에서 언급한 여러 시나리오를 테스트할 수 있습니다:

```bash
./mvnw test -Dtest=ZoneServiceTest
```

**주요 테스트 시나리오:**

1. **테스트 1**: 기본 환경(KST)에서 타임존 동작 확인
   - 서버 JVM 타임존: KST
   - JDBC URL 타임존 설정: 없음
   - DB 타임존: KST

2. **테스트 2**: JDBC URL UTC 설정 시 타임존 동작 확인
   - 서버 JVM 타임존: KST
   - JDBC URL 타임존 설정: UTC
   - DB 타임존: KST

3. **상세 분석**: 특정 ID의 데이터를 상세 분석하여 타임존 변환 문제 확인

4. **타임존 변환 중복 문제 재현**: 포스팅에서 언급한 문제점 재현

### 테스트 방법

1. **application.yml 설정 확인**
   ```yaml
   spring:
     datasource:
       hikari:
         jdbc-url: jdbc:log4jdbc:mysql://localhost:3307/orders?serverTimezone=UTC&characterEncoding=UTF-8
   ```
   - `serverTimezone=UTC` 설정 여부에 따라 테스트 결과가 달라집니다.

2. **테스트 실행**
   - 테스트 코드를 실행하여 로그를 확인합니다.
   - API를 호출하여 결과를 확인합니다.

3. **문제 확인**
   - 포스팅에서 언급한 문제점들이 실제로 발생하는지 확인합니다.
   - ZonedDateTime을 UTC로 변환할 때 예상과 다른 결과가 나오는지 확인합니다.

### 관련 파일

- `src/main/java/org/wrapper/mybatismapper/timezone/vo/ZoneEntity.java`
- `src/main/java/org/wrapper/mybatismapper/timezone/mapper/ZoneMapper.java`
- `src/main/resources/mybatis/mapper/ZoneMap.xml`
- `src/main/java/org/wrapper/mybatismapper/timezone/service/ZoneService.java`
- `src/main/java/org/wrapper/mybatismapper/timezone/controller/ZoneController.java`
- `src/test/java/org/wrapper/mybatismapper/timezone/service/ZoneServiceTest.java`
- `src/main/resources/mybatis/SQL(script)/timezone_test.sql`

## 📌 참고사항

- 이 프로젝트는 WAR 파일로 패키징되어 외부 톰캣 서버에 배포할 수 있습니다.
- SQL 로깅을 위해 `log4jdbc-log4j2`를 사용합니다.
- TimeZone은 UTC로 설정되어 있습니다.
- Lombok을 사용하여 보일러플레이트 코드를 줄였습니다.
- 타임존 테스트 기능을 통해 DB와 서버의 시간 불일치 문제를 확인할 수 있습니다.

## 📄 라이선스

이 프로젝트는 개인 학습 및 교육 목적으로 작성되었습니다.

