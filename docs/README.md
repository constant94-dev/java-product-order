> 상품주문 프로그램 핵심: 사용자가 원하는 상품을 문제없이 주문할 수 있어야 한다.

## 🛒 상품주문 프로그램 기능 목록

- [] 상품주문 프로그램 기능 출발지 - ProductController#run()
    - [] 프로그램 객체 준비 - ProductController#ready()
    - [] 프로그램 기능 시작 - ProductController#orderPlay()
- [] 상품 저장소
    - [x] 상품 데이터 가져오기 - ProductRepository#getCSVData()
    - [x] 콤마(,) 기준 분할 - ProductRepository#splitToComma()
    - [x] 상품 데이터 검증 - ProductRepository#validateTokens()
    - [x] 상품 데이터 제목 검증 - ProductRepository#validateTitle()
    - [x] 상품 데이터 추가 - ProductRepository#addProductMap()
    - [x] 상품 데이터 파일 읽기 종료 - ProductRepository#fileReaderClose()
- [] 상품 주문
    - [] 주문 또는 종료 입력 - InputView#orderORQuit()
    - [] 상품번호 입력 - InputView#orderOfNumber()
    - [] 수량 입력 - InputView#orderOfVolume()
- [] 주문 처리
    - [x] 상품번호 검증 - OrderService#validateOrderNumber()
        - [x] 
            - OrderService#hasMenuNumbers()
        - [x] 
            - OrderService#isOrderNumber()
    - [x] 상품수량 검증 - OrderService#validateOrderVolume()
        - [x] 상품 수량과 재고 수 확인 - Menu#volumeInStock()
- [] 주문 내역
    - [] 상품 주문 내역 출력 - OutputView#printToOrderResult()

## ♻️ 상품주문 프로그램 테스트 목록

- [] 상품 데이터 처리
    - [x] csv 파일에서 가져온 상품 데이터 가져오가 성공 - ProductRepositoryTest#getCSVDataSuccess()
- [] 상품 주문 처리
    - [x] 주문 상품 번호 존재하는 경우 성공 - OrderServiceTest#orderNumberToExist()
    - [x] 주문 상품 번호 존재하지 않는 경우 예외 발생 - OrderServiceTest#orderNumberToNotExist()
    - [x] 주문 상품 재고 부족 여부 확인 - OrderServiceTest#orderVolumeInStock()
    - [x] 주문 상품 재고 부족 예외 발생 - OrderServiceTest#orderVolumeNotInStock()