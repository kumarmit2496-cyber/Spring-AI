package in.amitit.dto;


import lombok.Builder;
import lombok.Data;
import org.hibernate.boot.beanvalidation.IntegrationException;

@Data
@Builder
public class DashboardResponseDto {


    private Integer totalEnqs;
    private Integer openEnqs;
    private Integer enrolledEnq;
    private Integer lostEnqs;

}
