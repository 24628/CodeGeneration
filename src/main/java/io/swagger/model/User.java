package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")


public class User {
    @JsonProperty("id")
    private Integer id = null;


    @JsonProperty("role")
    private String role = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("password")
    private String password = null;

    @JsonProperty("dayLimit")
    private Long dayLimit = null;

    @JsonProperty("transactionLimit")
    private Long transactionLimit = null;

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

    public User role(String role) {
        this.role = role;
        return this;
    }

    /**
     * Get role
     *
     * @return role
     **/
    @Schema(description = "")

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public User dayLimit(Long dayLimit) {
        this.dayLimit = dayLimit;
        return this;
    }

    /**
     * Get dayLimit
     *
     * @return dayLimit
     **/
    @Schema(example = "500", description = "")

    public Long getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Long dayLimit) {
        this.dayLimit = dayLimit;
    }

    public User transactionLimit(Long transactionLimit) {
        this.transactionLimit = transactionLimit;
        return this;
    }

    /**
     * Get transactionLimit
     *
     * @return transactionLimit
     **/
    @Schema(description = "")

    public Long getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Long transactionLimit) {
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
                Objects.equals(this.role, user.role) &&
                Objects.equals(this.name, user.name) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.dayLimit, user.dayLimit) &&
                Objects.equals(this.transactionLimit, user.transactionLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, name, email, password, dayLimit, transactionLimit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
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
