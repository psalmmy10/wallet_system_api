package com.wallet_system.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRes<T> implements Serializable {
    private String responseCode;
    private String responseMsg;
    private String responseDesc;
    private T data;

    public static <T> ApiRes<T> success(T data) {
        return new ApiRes<>("00","Success",null, data);
    }

    public static <T> ApiRes<T> successMessage(String responseDesc) {
        return new ApiRes<>("00", "Success", responseDesc, null);
    }
    
    public static <T> ApiRes<T> failed(T data) {
        return new ApiRes<>("99","Failed",null, data);
    }

    public static <T> ApiRes<T> error(String errorMsg) {
        return new ApiRes<>("55","Failed", errorMsg, null);
    }
}
