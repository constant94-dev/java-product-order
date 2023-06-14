## [29CM 2023 SS 개발자 공개채용 과제]

<p>2023년 29CM 개발자 공개채용 백엔드 과제에 참여할 기회를 주신 것에 감사드립니다.</p>

## 🔧 프로젝트 환경 ##
<ul>
  <li>Java version: jdk-11.0.18</li>
  <li>Gradle version: 8.0</li>
  <li>lombok version: 1.18.28</li>
</ul>

## 📢 개발 요구사항 ##
- 상품은 고유의 상품번호와 상품명, 판매가격, 재고수량 정보를 가지고 있습니다.
- 한 번에 여러개의 상품을 같이 주문할 수 있어야 합니다.
- 상품번호, 주문수량은 반복적으로 입력 받을 수 있습니다.
- 주문은 상품번호, 수량을 입력받습니다.
  - empty 입력 (space + ENTER) 이 되었을 경우 해당 건에 대한 주문이 완료되고, 결제하는 것으로 판단합니다.
  - 결제 시 재고 확인을 하여야 하며 재고가 부족할 경우 결제를 시도하면 SoldOutException 이 발생되어야 합니다.
- 주문 금액이 5만원 미만인 경우 배송료 2,500 원이 추가되어야 합니다.
- 주문이 완료되었을 경우 주문 내역과 주문 금액, 결제 금액(배송비 포함)을 화면에 display 합니다.
- 'q' 또는 'quit' 을 입력하면 프로그램이 종료되어야 합니다.
- Test 에서는 반드시 multi thread 요청으로 SoldOutException 이 정상 동작하는지 확인하는 단위테스트가 작성되어야 합니다.
- 상품의 데이터는 과제로 주어지는 데이터를 사용해주세요
  - 데이터를 불러오는 방식은 자유입니다.
  - 코드에 포함되어도 좋고, 파일을 불러도 되고, in memory db를 사용하셔도 됩니다.
  - 하지만 상품에 대한 상품번호, 상품명, 판매가격, 재고수량 데이터는 그대로 사용하셔야 합니다.
  - 상품 데이터 csv 파일을 같이 제공합니다.

## 📂 Project Structure ##

```bash
📦src
 ┣ 📂main
 ┃ ┣ 📂generated
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂kr
 ┃ ┃ ┃ ┗ 📂co
 ┃ ┃ ┃ ┃ ┗ 📂_29cm
 ┃ ┃ ┃ ┃ ┃ ┗ 📂homework
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂data
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂products
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂products
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Product.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂products
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductSale.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Main.java
 ┃ ┗ 📂resources
 ┗ 📂test
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂kr
 ┃ ┃ ┃ ┗ 📂co
 ┃ ┃ ┃ ┃ ┗ 📂_29cm
 ┃ ┃ ┃ ┃ ┃ ┗ 📂homework
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MultiThreadRequestTest.java
 ┃ ┗ 📂resources
```

![feature](screenshots/cleanArchitecture.png)

### Components ###
클린 아키텍처는 꼭 원이 4개가 아니어도 되며 의존 규칙은 바깥에서 안으로 흘러야 한다.

Request 👉 Controller 👉 UseCase 👉 Entity(Repository)

현재 프로젝트에 요구사항을 보았을 때 높은 수준의 추상화는 구조가 더 복잡해질 것이라 판단<br/>
또한, RDBMS 를 사용하지 않고 CSV 파일에 데이터를 가져와 개발하여 만들어질 구조는 아래와 같다.

Request 👉 Service 👉 Entity(Repository)

이후에 확장성을 위한 directory 구조

- data
  - repository
    - products
      - 상품 주문에 사용할 데이터베이스 요청 객체 구성
- domain
  - products
    - 상품 정보 도메인 객체
      - dto
        - 계층 간 데이터 전송을 위한 객체
- service
  - products
      - 상품 주문 비즈니스 로직
- controller (이 프로젝트에서는 main() 함수로 대체)
  - 요청을 받아 방향을 정하는 첫 관문

## 📌 Stack ##
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"> <img src="https://img.shields.io/badge/openjdk-6CD74A?style=for-the-badge&logo=openjdk&logoColor=white">

## 💻 실행 화면 ##

### 초기 상품 메뉴 ###
![feature](screenshots/order_menu.png)

### 2개 상품 주문 ###
![feature](screenshots/order_example1.png)

### 상품 결제 후 메뉴 ###
![feature](screenshots/order_menu2.png)

### 1개 상품 주문 ###
![feature](screenshots/order_example2.png)

### 상품 결제 후 메뉴 ###
![feature](screenshots/order_menu3.png)

### 상품 재고 부족 ###
![feature](screenshots/order_example3.png)

### 상품 주문 종료 ###
![feature](screenshots/order_example4.png)

## 🔎 Test ##

### 테스트 실행 ###
![feature](screenshots/unitTestMethod.png)

![feature](screenshots/unitTestStart.png)

### 초기 사용자 및 멀티 쓰레드 세팅 ###
![feature](screenshots/initUser.png)

### 현재 실행되고 있는 쓰레드 ###
![feature](screenshots/activeThreads.png)

### 사용자 A 상품 주문 ###
![feature](screenshots/resultUserA.png)

### 사용자 B 상품 주문 ###
![feature](screenshots/resultUserB.png)

### 쓰레드 작업 완료 ###
![feature](screenshots/completedTasks.png)

### 사용자 A 와 사용자 B 멀티 쓰레드 요청 처리 테스트 성공화면 ###
![feature](screenshots/testWithConcurrencyResult.png)

## 테스트 쓰레드 WorkFlow
![feature](screenshots/orderWorkFlow.png)

## ✍ 쓰레드 처리의 Point
<p>
  <h4>WorkFlow 에서 보여지는 흐름과 같이 사용자가 주문에 대한 결제를 진행할 때 다른 쓰레드에서는 주문 결제를 기다리고 있어야한다.
  그렇지 않으면 상품번호:213341 의 재고가 부족함에도 주문 결제가 진행되어 사용자는 없는 상품을 주문을 하게된다.</h4>
</p>
<p>
  <h4>여기서 고민해야되는 Point 가 DB Transaction Level 과 method Level 2가지이다.
  해결방법은 결제가 발생할 때 Lock 을 걸어주는 것이다.
  </h4>
  <h4>이 프로젝트에서는 데이터베이스를 사용하지 않았기 때문에 method Level 에서 Lock 을 걸어주었다. 
  </h4>
</p>

![feature](screenshots/synchronized.png)

<p>
  <h4>쓰레드가 getOrderComplete() 작업을 수행할 때 다른 쓰레드가 접근 하지 못하게 하고 작업이 마무리 된 후에 다른 쓰레드가 접근할 수 있다. 
  </h4>
</p>

## ⏱ 시간 복잡도
시간 복잡도 작성 중...
