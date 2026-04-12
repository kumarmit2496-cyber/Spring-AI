package in.amitit.repo;

import in.amitit.entities.Course;
import in.amitit.service.CourseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo  extends JpaRepository<Course,Integer> {
}
