package io.swagger.model.Request;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")


public class UserRequest {
    @JsonProperty("id")
    private Integer id = null;


    @JsonProperty("role")
    private String role = null;



    @JsonProperty("username")
    private String username = null;
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

    public UserRequest id(Integer id) {
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

    public UserRequest role(String role) {
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

    public UserRequest name(String name) {
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

    public UserRequest email(String email) {
        this.email = email;
        return this;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserRequest password(String password) {
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

    public UserRequest dayLimit(Long dayLimit) {
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

    public UserRequest transactionLimit(Long transactionLimit) {
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
        UserRequest userRequest = (UserRequest) o;
        return Objects.equals(this.id, userRequest.id) &&
                Objects.equals(this.role, userRequest.role) &&
                Objects.equals(this.name, userRequest.name) &&
                Objects.equals(this.email, userRequest.email) &&
                Objects.equals(this.password, userRequest.password) &&
                Objects.equals(this.dayLimit, userRequest.dayLimit) &&
                Objects.equals(this.transactionLimit, userRequest.transactionLimit);
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
