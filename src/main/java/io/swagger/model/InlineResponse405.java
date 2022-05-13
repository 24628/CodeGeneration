package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.InlineResponse405Body;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse405
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-04-26T09:18:21.534Z[GMT]")


public class InlineResponse405 {
    @JsonProperty("code")
    private Integer code = null;

    @JsonProperty("body")
    @Valid
    private List<InlineResponse405Body> body = null;

    public InlineResponse405 code(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * Get code
     *
     * @return code
     **/
    @Schema(example = "405", description = "")

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public InlineResponse405 body(List<InlineResponse405Body> body) {
        this.body = body;
        return this;
    }

    public InlineResponse405 addBodyItem(InlineResponse405Body bodyItem) {
        if (this.body == null) {
            this.body = new ArrayList<InlineResponse405Body>();
        }
        this.body.add(bodyItem);
        return this;
    }

    /**
     * Get body
     *
     * @return body
     **/
    @Schema(description = "")
    @Valid
    public List<InlineResponse405Body> getBody() {
        return body;
    }

    public void setBody(List<InlineResponse405Body> body) {
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
        InlineResponse405 inlineResponse405 = (InlineResponse405) o;
        return Objects.equals(this.code, inlineResponse405.code) &&
                Objects.equals(this.body, inlineResponse405.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InlineResponse405 {\n");

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
