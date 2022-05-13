package io.swagger.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")


public class User {
    @JsonProperty("id")
    private Integer id = null;

    /**
     * Gets or Sets type
     */
    public enum TypeEnum {
        CUSTOMER("customer"),

        EMPLOYEE("employee"),

        DISABLED("disabled");

        private String value;


        TypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TypeEnum fromValue(String text) {
            for (TypeEnum b : TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("type")
    private TypeEnum type = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("password")
    private String password = null;

    @JsonProperty("day_limit")
    private Integer dayLimit = null;

    @JsonProperty("transaction_limit")
    private Integer transactionLimit = null;

    public User id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @Schema(description = "")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @Schema(description = "")

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public User name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @Schema(description = "")

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     **/
    @Schema(description = "")

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     *
     * @return password
     **/
    @Schema(description = "")

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User dayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
        return this;
    }

    /**
     * Get dayLimit
     *
     * @return dayLimit
     **/
    @Schema(example = "500", description = "")

    public Integer getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
    }

    public User transactionLimit(Integer transactionLimit) {
        this.transactionLimit = transactionLimit;
        return this;
    }

    /**
     * Get transactionLimit
     *
     * @return transactionLimit
     **/
    @Schema(description = "")

    public Integer getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Integer transactionLimit) {
        this.transactionLimit = transactionLimit;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.type, user.type) &&
                Objects.equals(this.name, user.name) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.dayLimit, user.dayLimit) &&
                Objects.equals(this.transactionLimit, user.transactionLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, email, password, dayLimit, transactionLimit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
        sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
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
