package in.amitit.service;

import in.amitit.dto.DashboardResponseDto;
import in.amitit.entities.Counsellor;

public interface CounsellorService {

    public boolean register(Counsellor counsellor);
    public boolean isEmailUnique(String email);
    public Counsellor login(String email,String pwd);
    public DashboardResponseDto getDashboardInfo(Integer counsellorId);


}
