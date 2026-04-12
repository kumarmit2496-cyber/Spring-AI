package in.amitit.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "enquiry")
@Setter
@Getter
public class Enquiry {

  @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer enqId;

  private String stuName;
  private String stuPhno;
  private String classMode;
  private String enqStatus;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "counselloar_id")
  private Counsellor counsellor;


  @CreationTimestamp
  private LocalDateTime createAt;

  @UpdateTimestamp
  private LocalDateTime updateAt;

}
