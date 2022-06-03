package io.swagger.model.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class AtmRequest {

    @JsonProperty("iban")
    private String iban = null;

    @JsonProperty("pinCode")
    private Integer pinCode = null;

    @JsonProperty("amount")
    private Long amount = null;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AtmRequest atmRequest = (AtmRequest) o;
        return Objects.equals(this.iban, atmRequest.iban) &&
                Objects.equals(this.pinCode, atmRequest.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, pinCode);
    }

    @Override
    public String toString() {
        return "class Atm {\n" +
                "    iban: " + toIndentedString(iban) + "\n" +
                "    pinCode: " + toIndentedString(pinCode) + "\n" +
                "    amount: " + toIndentedString(amount) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
