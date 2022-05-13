package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse403
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")


public class InlineResponse403 {
    @JsonProperty("code")
    private Integer code = null;

    @JsonProperty("body")
    private String body = null;

    public InlineResponse403 code(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * Get code
     *
     * @return code
     **/
    @Schema(example = "403", description = "")

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public InlineResponse403 body(String body) {
        this.body = body;
        return this;
    }

    /**
     * Get body
     *
     * @return body
     **/
    @Schema(example = "You dont have permissions to take this action", description = "")

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineResponse403 inlineResponse403 = (InlineResponse403) o;
        return Objects.equals(this.code, inlineResponse403.code) &&
                Objects.equals(this.body, inlineResponse403.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InlineResponse403 {\n");

        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    body: ").append(toIndentedString(body)).append("\n");
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
