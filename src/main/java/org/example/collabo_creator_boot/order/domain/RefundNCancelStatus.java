package org.example.collabo_creator_boot.order.domain;

public enum RefundNCancelStatus {

    REFUND("환불"),
    CANCEL("취소"),
    SHIPPED("발송 처리");


    private final String description;

    RefundNCancelStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static RefundNCancelStatus fromDbValue(String dbValue) {
        switch (dbValue) {
            case "1": return REFUND;
            case "2": return CANCEL;
            case "3": return SHIPPED;
            default: throw new IllegalArgumentException("Invalid order status value: " + dbValue);
        }
    }
}
