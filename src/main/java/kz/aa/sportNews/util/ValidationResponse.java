package kz.aa.sportNews.util;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResponse {

    private String status;
    private List<ErrorMessage> errorMessageList;
}