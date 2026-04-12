package in.amitit.dto;

import lombok.Data;

@Data
public class EnqFilterRequestDto {

    private String classMode;
    private Integer courseId;
    private String enqStatus;
}
