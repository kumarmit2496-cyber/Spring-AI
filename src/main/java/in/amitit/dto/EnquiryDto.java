package in.amitit.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnquiryDto {

    private Integer enqId;
    private String stuName;
    private String stuPhno;
    private String classMode;
    private String enqStatus;
    private Integer courseId;


}
