# 도전과제

- 도전과제를 추가해 새로운 저만의 키오스크를 만들었습니다.

# 설계

- 카테고리
    - Burgers (메인메뉴)
        - Hamburger 13.4
        - Cheese Burger 14.9
        - Bacon Burger 15.9
        - Bacon Cheese Burger 17.4
    - Dogs
        - Beef Hotdog 8.9
        - Cheese Hotdog 10.4
        - Bacon Hotdog 11.4
        - Bacon Cheese Hotdog 12.9
    - Fries
        - Little 6.9
        - Regular 8.9
        - Large 10.9
    - Sandwiches
        - Veggie Sandwich 9.9
        - Cheese Veggie Sandwich 11.4
        - Grilled Cheese Sandwich 8.4
        - BLT (Bacon, Lettuce, Tomato) Sandwich 10.9
    - Drinks
        - Soda 3.9
        - Bottled Water 2.0
        - Budweiser * 6.0
        - Stella Artois * 7.0
        - Goose Island ipa * 9.0
    - fiveguys 메뉴판 참고 !
- 추가 하고 싶은 내용
    - Drinks 카테고리에 맥주 세 잔 중 하나라도 선택하면 성인 인증 문구가 뜨게 하기

---

# 클래스 다이어그램

## **클래스 다이어그램 설명**

1. **Kiosk**
    - `Cart` 객체를 포함하고 있으며, 메뉴를 표시하고 주문을 처리하는 역할
    - `displayMainMenu()`, `displayCategoryMenu()`, `handleOrder()` 등의 메서드 포함
2. **Cart**
    - `MenuItem` 객체 목록을 관리하고, 장바구니 기능을 제공
    - `addItem()`, `displayCart()`, `getTotalPrice()`, `clearCart()` 등의 메서드 포함
3. **MenuItem**
    - 단일 메뉴 항목을 나타내는 클래스
    - `name`, `price`, `description` 속성을 가지며, `display()` 메서드 포함
4. **UserType (Enum)**
    - 사용자 유형을 정의하는 열거형
    - 국가 유공자, 군인, 학생, 일반 고객 등의 할인율을 설정
