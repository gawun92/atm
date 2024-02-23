package backend.atm.pojo;

import backend.atm.enumerate.AuthCardStatus;

public class AuthCardResponse {
    private String message;
    private AuthCardStatus authCardStatus = AuthCardStatus.FAILED;

    private Long cardPk;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthCardStatus getAuthCardStatus() {
        return authCardStatus;
    }

    public void setAuthCardStatus(AuthCardStatus authCardStatus) {
        this.authCardStatus = authCardStatus;
    }

    public Long getCardPk() {
        return cardPk;
    }

    public void setCardPk(Long cardPk) {
        this.cardPk = cardPk;
    }
}
