package org.example.collabo_creator_boot.order.domain;

public enum OrderStatus {
    PENDING("주문 생성됨"),    // 주문 생성됨
    COMPLETED("결제 완료"),  // 결제 완료
    CANCELED("주문 취소");  // 주문 취소

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus fromDbValue(String dbValue) {
        switch (dbValue) {
            case "PENDING": return PENDING;
            case "COMPLETED": return COMPLETED;
            case "CANCELED": return CANCELED;
            default: throw new IllegalArgumentException("유효하지 않은 주문 상태: " + dbValue);
        }
    }
}
