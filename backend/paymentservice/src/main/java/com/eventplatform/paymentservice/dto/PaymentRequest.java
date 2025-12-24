package com.eventplatform.paymentservice.dto;

public class PaymentRequest {

    private Long reservationId;
    private Long userId;            // sécurité : propriétaire
    private String paymentMethod;   // CARD, CASH...
    private boolean simulateFail;   // optionnel

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isSimulateFail() {
        return simulateFail;
    }

    public void setSimulateFail(boolean simulateFail) {
        this.simulateFail = simulateFail;
    }
}
