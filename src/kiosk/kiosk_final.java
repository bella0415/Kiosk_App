import java.util.*;

/**
 * 메뉴 아이템을 나타내는 클래스
 */
class MenuItem {
    private String name; // 메뉴 이름
    private double price; // 가격
    private String description; // 메뉴 설명

    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }

    public void display() {
        System.out.printf("%-20s | W %.1f | %s\n", name, price, description);
    }
}

/**
 * 장바구니 클래스
 */
class Cart {
    private List<MenuItem> items = new ArrayList<>(); // 장바구니에 담길 메뉴 아이템 리스트

    public void addItem(MenuItem item) {
        items.add(item); // 아이템 추가
        System.out.println(item.getName() + " 이(가) 장바구니에 추가되었습니다.");
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }
        System.out.println("\n[ Orders ]");
        items.forEach(MenuItem::display); // 장바구니 아이템 출력
        System.out.printf("\n[ Total ]\nW %.1f\n", getTotalPrice()); // 총 금액 출력
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum(); // 가격 합산
    }

    public void clearCart() {
        items.clear();
        System.out.println("주문이 완료되었습니다.");
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // 외부 리스트 읽기
    public List<MenuItem> getItems() {
        return items;
    }
}

/**
 * 사용자 유형을 나타내는 Enum
 */
enum UserType {
    NATIONAL_HERO(0.9),
    SOLDIER(0.95),
    STUDENT(0.97),
    GENERAL(1.0);

    private final double discountRate;

    UserType(double discountRate) {
        this.discountRate = discountRate;
    }

    public double applyDiscount(double price) {
        return price * discountRate;
    }
}

/**
 * 키오스크 기능을 담당하는 클래스
 */
class Kiosk {
    private static final Map<Integer, List<MenuItem>> menuCategories = new HashMap<>(); // 메뉴 카테고리
    private Cart cart = new Cart(); // 장바구니 객체
    private Scanner scanner = new Scanner(System.in); // 사용자 입력을 위한 스캐너

    static {
        menuCategories.put(1, List.of(
                new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
                new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
                new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거")
        ));
        menuCategories.put(2, List.of(
                new MenuItem("Beef Hotdog", 8.9, "100% 비프 소시지가 들어간 클래식 핫도그"),
                new MenuItem("Cheese Hotdog", 10.4, "고소한 치즈가 추가된 핫도그"),
                new MenuItem("Bacon Hotdog", 11.4, "바삭한 베이컨이 더해진 핫도그"),
                new MenuItem("Bacon Cheese Hotdog", 12.9, "베이컨과 치즈가 조화를 이루는 핫도그")
        ));
        menuCategories.put(3, List.of(
                new MenuItem("Little Fries", 6.9, "바삭하고 담백한 작은 사이즈 감자튀김"),
                new MenuItem("Regular Fries", 8.9, "보통 사이즈 감자튀김"),
                new MenuItem("Large Fries", 10.9, "푸짐한 양의 감자튀김")
        ));
        menuCategories.put(4, List.of(
                new MenuItem("Veggie Sandwich", 9.9, "신선한 야채로 만든 건강한 샌드위치"),
                new MenuItem("Cheese Veggie Sandwich", 11.4, "치즈가 추가된 채식 샌드위치"),
                new MenuItem("Grilled Cheese Sandwich", 8.4, "고소한 치즈가 들어간 샌드위치"),
                new MenuItem("BLT Sandwich", 10.9, "베이컨, 양상추, 토마토가 들어간 샌드위치")
        ));
        menuCategories.put(5, List.of(
                new MenuItem("Soda", 3.9, "톡 쏘는 탄산음료"),
                new MenuItem("Bottled Water", 2.0, "깔끔한 생수"),
                new MenuItem("Budweiser", 6.0, "가볍고 깔끔한 아메리칸 라거 맥주"),
                new MenuItem("Stella Artois", 7.0, "부드러운 벨기에 정통 라거"),
                new MenuItem("Goose Island IPA", 9.0, "홉 향이 강한 시카고 대표 IPA 맥주")
        ));
    }

    public void displayCategoryMenu(int category) {
        List<MenuItem> menu = menuCategories.get(category);
        if (menu == null) {
            System.out.println("잘못된 입력입니다.");
            return;
        }
        System.out.println("\n[ CATEGORY MENU ]");
        menu.forEach(MenuItem::display); // 메뉴 항목 출력

        System.out.print("메뉴 번호를 선택하세요 (0 입력 시 메인 메뉴로 돌아감): ");
        int choice = getValidIntegerInput();

        if (choice >= 1 && choice <= menu.size()) {
            cart.addItem(menu.get(choice - 1));
        }
    }

    public void displayMainMenu() {
        System.out.println("\n[ MAIN MENU ]");
        System.out.println("1. \uD83C\uDF54[ Burgers ]");
        System.out.println("2. \uD83C\uDF2D[ Dogs ]");
        System.out.println("3. \uD83C\uDF5F[ Fries ]");
        System.out.println("4. \uD83C\uDF2F[ Sandwiches ]");
        System.out.println("5. \uD83C\uDF79[ Beverages ]");
        System.out.println("6. \uD83D\uDCB8[ Orders ] | 장바구니 확인 및 주문");
        System.out.println("7. ❌[ Cancel ] | 진행중인 주문 취소");
        System.out.println("0. 종료");
    }

    public void start() {
        while (true) {
            displayMainMenu(); // 메인 메뉴 출력
            System.out.print("메뉴 번호를 선택하세요: ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                case 1, 2, 3, 4, 5 -> displayCategoryMenu(choice);
                case 6 -> handleOrder(); // 장바구니 확인 및 주문
                case 7 -> cart.clearCart();
                default -> System.out.println("유효하지 않은 메뉴 번호입니다.");
            }
        }
    }

    private void handleOrder() {
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }
        cart.displayCart();

        System.out.println("1. 주문하기  2. 메뉴판으로 돌아가기 : ");
        int confirm = getValidIntegerInput();

        if (confirm == 1) {
            // 성인 인증이 필요한 항목이 있는지 확인
            if (containsAlcoholicItems()) {
                System.out.println("성인 인증이 필요합니다!");
                System.out.print("주민번호 앞자리를 입력해주세요 (예: 000415): ");
                String residentNumber = scanner.nextLine();

                if (!isAdult(residentNumber)) {
                    System.out.println("만 19세 이상만 주문 가능합니다 \uD83E\uDD16");
                    return;
                }
                System.out.println("성인 인증이 완료되었습니다.");
            }

            UserType userType = selectUserType();
            double finalPrice = userType.applyDiscount(cart.getTotalPrice()); // 최종 가격 계산
            System.out.printf("주문이 완료되었습니다. 최종 금액은 W %.1f 입니다.\n", finalPrice);
            cart.clearCart();
        }
    }

    // 장바구니에 알콜 음료가 포함되어 있는지 확인
    private boolean containsAlcoholicItems() {
        for (MenuItem item : cart.getItems()) {
            if (item.getName().equals("Budweiser") || item.getName().equals("Stella Artois") || item.getName().equals("Goose Island IPA")) {
                return true;
            }
        }
        return false;
    }

    // 주민번호 앞자리를 기준으로 만 19세 이상인지 확인
    private boolean isAdult(String residentNumber) {
        // 현재 년도 가져오기
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // 주민번호에서 생년월일 추출 (yyMMdd 형태로 가정)
        String birthDate = residentNumber.substring(0, 6);
        int birthYear = Integer.parseInt(birthDate.substring(0, 2));

        // 생년이 00-99로 표시되기 때문에 1900년대 혹은 2000년대 처리
        if (birthYear > 22) { // 2000년 이후 출생
            birthYear += 1900;
        } else { // 1900년대 출생
            birthYear += 2000;
        }

        int age = currentYear - birthYear;
        return age >= 19; // 나이가 19세 이상인지를 확인
    }

    // 유효한 사용자 유형 선택
    private UserType selectUserType() {
        System.out.println("\n회원 유형을 선택해주세요:");
        System.out.println("1. 국가 유공자");
        System.out.println("2. 군인");
        System.out.println("3. 학생");
        System.out.println("4. 일반 고객");

        int choice = getValidIntegerInput();
        switch (choice) {
            case 1: return UserType.NATIONAL_HERO;
            case 2: return UserType.SOLDIER;
            case 3: return UserType.STUDENT;
            default: return UserType.GENERAL;
        }
    }

    // 유효한 정수 입력을 받는 메서드
    private int getValidIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("잘못된 입력입니다. 다시 입력해주세요: ");
            }
        }
    }
}

/**
 * 키오스크 실행 클래스
 */
public class kiosk_final {
    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.start(); // 키오스크 시작
    }
}
