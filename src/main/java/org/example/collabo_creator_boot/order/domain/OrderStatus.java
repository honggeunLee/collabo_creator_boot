package org.example.collabo_creator_boot.order.domain;

public enum OrderStatus {
    WAITING_FOR_PAYMENT("입금대기"),      // 입금이 확인될 때까지 대기 상태
    PREPARING_FOR_SHIPMENT("배송 준비 처리"),  // 주문이 확인되고 배송 준비 중인 상태
    SHIPPED("발송 처리"),              // 주문이 발송된 상태
    REFUND_PROCESSING("환불 처리");    // 환불 요청이 처리 중인 상태

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus fromDbValue(String dbValue) {
        switch (dbValue) {
            case "1": return WAITING_FOR_PAYMENT;
            case "2": return PREPARING_FOR_SHIPMENT;
            case "3": return SHIPPED;
            case "4": return REFUND_PROCESSING;
            default: throw new IllegalArgumentException("Invalid order status value: " + dbValue);
        }
    }
}