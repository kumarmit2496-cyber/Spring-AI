package in.amitit.service.impl;

import in.amitit.entities.Course;
import in.amitit.repo.CourseRepo;
import in.amitit.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceimpl implements CourseService {

      @Autowired
      private CourseRepo courseRepo;

    @Override
    public List<Course> getCourse() {
        return courseRepo.findAll();
    }
}
