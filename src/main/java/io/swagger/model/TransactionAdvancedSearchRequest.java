package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class TransactionAdvancedSearchRequest {
    @JsonProperty("lessThenTransAmount:")
    private Long lessThanTransAmount = 0L;

    @JsonProperty("greaterThanTransAmount")
    private Long greaterThanTransAmount = 2000L;

    @JsonProperty("dateBefore")
    private LocalDateTime dateBefore = LocalDateTime.now().minusDays(1L);

    @JsonProperty("dateAfter")
    private LocalDateTime dateAfter = LocalDateTime.now().plusDays(1L);

    @JsonProperty("ibanTo")
    private String ibanTo = null;

    @JsonProperty("ibanFrom")
    private String ibanFrom = null;

    public Long getLessThanTransAmount() {
        return lessThanTransAmount;
    }

    public void setLessThanTransAmount(Long lessThanTransAmount) {
        this.lessThanTransAmount = lessThanTransAmount;
    }

    public Long getGreaterThanTransAmount() {
        return greaterThanTransAmount;
    }

    public void setGreaterThanTransAmount(Long greaterThanTransAmount) {
        this.greaterThanTransAmount = greaterThanTransAmount;
    }

    public LocalDateTime getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(LocalDateTime dateBefore) {
        this.dateBefore = dateBefore;
    }

    public LocalDateTime getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(LocalDateTime dateAfter) {
        this.dateAfter = dateAfter;
    }

    public String getIbanTo() {
        return ibanTo;
    }

    public void setIbanTo(String ibanTo) {
        this.ibanTo = ibanTo;
    }

    public String getIbanFrom() {
        return ibanFrom;
    }

    public void setIbanFrom(String ibanFrom) {
        this.ibanFrom = ibanFrom;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        System.out.println("in de equals account swagger");
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionAdvancedSearchRequest transactionAdvancedSearchRequest = (TransactionAdvancedSearchRequest) o;
        return Objects.equals(this.lessThanTransAmount, transactionAdvancedSearchRequest.lessThanTransAmount) &&
                Objects.equals(this.greaterThanTransAmount, transactionAdvancedSearchRequest.greaterThanTransAmount) &&
                Objects.equals(this.dateBefore, transactionAdvancedSearchRequest.dateBefore) &&
                Objects.equals(this.ibanTo, transactionAdvancedSearchRequest.ibanTo) &&
                Objects.equals(this.ibanFrom, transactionAdvancedSearchRequest.ibanFrom) &&
                Objects.equals(this.dateAfter, transactionAdvancedSearchRequest.dateAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessThanTransAmount, greaterThanTransAmount, dateBefore, ibanTo, ibanFrom, dateAfter);
    }

    @Override
    public String toString() {

        return "class accountAdvancedSearchRequest {\n" +
                "    lessThenTransAmount: " + toIndentedString(lessThanTransAmount) + "\n" +
                "    greaterThanTransAmount: " + toIndentedString(greaterThanTransAmount) + "\n" +
                "    dateBefore: " + toIndentedString(dateBefore) + "\n" +
                "    dateAfter: " + toIndentedString(dateAfter) + "\n" +
                "    ibanTo: " + toIndentedString(ibanTo) + "\n" +
                "    ibanFrom: " + toIndentedString(ibanFrom) + "\n" +
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
