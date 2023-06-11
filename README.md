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
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂data // 데이터 탐색 및 가공
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain // 상품 도메인
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Product.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service // 비즈니스 로직
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductSale.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Main.java
 ┃ ┗ 📂resources
 ┗ 📂test
 ┃ ┣ 📂java
 ┃ ┗ 📂resources
```

![feature](screenshots/cleanArchitecture.png)

### Components ###
클린 아키텍처는 꼭 원이 4개가 아니어도 되며 의존 규칙은 바깥에서 안으로 흘러야 한다.

Request 👉 Controller 👉 UseCase 👉 Entity(Repository)

- data
  - repository
    - products
      - 상품 주문에 사용할 데이터베이스 접근 객체 구성
- domain
  - products
    - dto
      - 상품 정보 관련 파일로 구성
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

### 🔎 Test ###
#### 테스트 화면 작업중...