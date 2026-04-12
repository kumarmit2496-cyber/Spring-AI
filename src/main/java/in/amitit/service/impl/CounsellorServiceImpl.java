package in.amitit.service.impl;

import in.amitit.dto.DashboardResponseDto;
import in.amitit.entities.Counsellor;
import in.amitit.entities.Enquiry;
import in.amitit.repo.CounsellorRepo;
import in.amitit.repo.EnquiryRepo;
import in.amitit.service.CounsellorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CounsellorServiceImpl implements CounsellorService {


    @Autowired
    private CounsellorRepo counsellorRepo;

    @Autowired
    private EnquiryRepo enquiryRepo;


    @Override
    public boolean register(Counsellor counsellor) {
       Counsellor savedCounsellor=counsellorRepo.save(counsellor);
       if(savedCounsellor.getCounsellorId()!=null){
           return  true;
       }
        return false;
    }

    @Override
    public boolean isEmailUnique(String email) {
      Optional<Counsellor> opt= counsellorRepo.findByEmail(email);
       if(opt.isPresent()){
           return false;
       }
        return true;
    }

    @Override
    public Counsellor login(String email, String pwd) {
        Optional<Counsellor> byEmailAndPwd=counsellorRepo.findByEmailAndPwd(email,pwd);
        if(byEmailAndPwd.isPresent()){
            return byEmailAndPwd.get();
        }
        return null;
    }

    @Override
    public DashboardResponseDto getDashboardInfo(Integer counsellorId) {
    List<Enquiry>  enquiryList= enquiryRepo.findByCounsellorCounsellorId(counsellorId);

          int totalEnqs=enquiryList.size();

          Map<String,Long> statusWiseMap = enquiryList.stream().
                 collect(Collectors.groupingBy(Enquiry::getEnqStatus,Collectors.counting()));

          int openCnt=statusWiseMap.getOrDefault("OPEN", 0l).intValue();
          int enrolledCnt=statusWiseMap.getOrDefault("ENROLLED",0l).intValue();
          int lostCnt=statusWiseMap.getOrDefault("LOST",0l).intValue();

          DashboardResponseDto  dto= DashboardResponseDto.builder()
                  .totalEnqs(totalEnqs)
                  .enrolledEnq(enrolledCnt)
                  .openEnqs(openCnt)
                  .lostEnqs(lostCnt)
                  .build();


        return dto;
    }
}
