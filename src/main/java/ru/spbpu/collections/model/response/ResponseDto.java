package ru.spbpu.collections.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spbpu.collections.model.response.enums.ResponseMessage;
import ru.spbpu.collections.model.response.enums.ResponseResult;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private ResponseResult result;
    private ResponseMessage message;
    private String details;
    private Long id;
}
