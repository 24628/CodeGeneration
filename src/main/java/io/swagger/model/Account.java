package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")


public class Account {
    @JsonProperty("IBAN")
    private String IBAN = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("user_id")
    private String userId = null;

    @JsonProperty("absolute_limit")
    private Long absoluteLimit = null;

    /**
     * Get IBAN
     *
     * @return IBAN
     **/
    @Schema(example = "NL69INHO1234123412", required = false, description = "")

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Account type(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @Schema(required = false, description = "")

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account userId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get userId
     *
     * @return userId
     **/
    @Schema(required = false, description = "")

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Account absoluteLimit(Long absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
        return this;
    }

    /**
     * Get absoluteLimit
     *
     * @return absoluteLimit
     **/
    @Schema(description = "")

    public Long getAbsoluteLimit() {
        return absoluteLimit;
    }

    public void setAbsoluteLimit(Long absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
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
        Account account = (Account) o;
        return Objects.equals(this.IBAN, account.IBAN) &&
                Objects.equals(this.type, account.type) &&
                Objects.equals(this.userId, account.userId) &&
                Objects.equals(this.absoluteLimit, account.absoluteLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, type, userId, absoluteLimit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Account {\n");

        sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
        sb.append("}");
        return sb.toString();
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
