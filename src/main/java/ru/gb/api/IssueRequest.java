package ru.gb.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IssueRequest {
    private Long readerId;
    private Long bookId;

}
