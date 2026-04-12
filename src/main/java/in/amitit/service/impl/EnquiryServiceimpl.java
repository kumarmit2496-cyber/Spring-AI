package in.amitit.service.impl;

import in.amitit.dto.EnqFilterRequestDto;
import in.amitit.dto.EnquiryDto;
import in.amitit.entities.Counsellor;
import in.amitit.entities.Course;
import in.amitit.entities.Enquiry;
import in.amitit.repo.CounsellorRepo;
import in.amitit.repo.CourseRepo;
import in.amitit.repo.EnquiryRepo;
import in.amitit.service.EnquiryService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class EnquiryServiceimpl implements EnquiryService {


    @Autowired
    private CounsellorRepo counsellorRepo;

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Autowired
    private CourseRepo courseRepo;


    @Override
    public boolean addEnquiry(EnquiryDto enquiryDto, Integer counsellorId) {

       Counsellor counsellor= counsellorRepo.findById(counsellorId).orElseThrow();
        Course course=courseRepo.findById(enquiryDto.getCourseId()).orElseThrow();

        Enquiry entity=new Enquiry();
        BeanUtils.copyProperties(enquiryDto,entity);
        entity.setCourse(course);
        entity.setCounsellor(counsellor);
        Enquiry savedenq=enquiryRepo.save(entity);
        return savedenq.getEnqId()!=null;
    }

    @Override
    public List<Enquiry> getAllEnquiries(Integer counsellorId) {
        return enquiryRepo.findByCounsellorCounsellorId(counsellorId);
    }

    @Override
    public List<Enquiry> getEnquiriesWithFilter(EnqFilterRequestDto filterRequestDto, Integer counsellorId) {
            Counsellor counsellor=counsellorRepo.findById(counsellorId).orElseThrow();
            Enquiry enquiry=new Enquiry();
            enquiry.setCounsellor(counsellor);

            /*if(!StringUtils.isEmpty(filterRequestDto.getClassMode())){
                enquiry.setClassMode(filterRequestDto.getClassMode());
            }
            if(!StringUtils.isEmpty(filterRequestDto.getEnqStatus())){
                enquiry.setClassMode(filterRequestDto.getEnqStatus());
            }

            if(filterRequestDto.getCourseId()!=null && filterRequestDto.getCourseId()>0){
               Course  course=courseRepo.findById(filterRequestDto.getCourseId()).orElseThrow();
               enquiry.setCourse(course);
            }
*/

                if(filterRequestDto.getClassMode()!=null && !"".equals(filterRequestDto.getClassMode())){
                    enquiry.setClassMode(filterRequestDto.getClassMode());
                }

                if(filterRequestDto.getCourseId()!=null && filterRequestDto.getCourseId()>0){
                    Course course=courseRepo.findById(filterRequestDto.getCourseId()).orElseThrow();
                    enquiry.setCourse(course);
                }

                if(filterRequestDto.getEnqStatus()!=null && !"".equals(filterRequestDto.getEnqStatus())) {
                    enquiry.setEnqStatus(filterRequestDto.getEnqStatus());
                }

        return enquiryRepo.findAll(Example.of(enquiry));
    }

    @Override
    public Enquiry getEnquiryById(Integer enqId) {
        return enquiryRepo.findById(enqId).orElseThrow();
    }

    @Override
    public boolean updateEnquiry(EnquiryDto enquiryDto) {
        Optional<Enquiry> byId=enquiryRepo.findById(enquiryDto.getEnqId());
        if(byId.isPresent()){
            Enquiry enquiry=byId.get();
            enquiry.setStuName(enquiryDto.getStuName());
            enquiry.setStuPhno(enquiryDto.getStuPhno());
            enquiry.setEnqStatus(enquiryDto.getEnqStatus());
            enquiryRepo.save(enquiry);
            return true;
        }

        return false;
    }
}
