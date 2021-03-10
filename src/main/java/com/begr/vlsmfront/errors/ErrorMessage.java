package com.begr.vlsmfront.errors;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessage{

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}