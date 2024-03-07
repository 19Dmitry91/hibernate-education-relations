import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
class PurchaseListKey implements Serializable {

    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
@Entity
@Table(name = "purchaselist")
public class PurchaseList {

    @EmbeddedId
    private PurchaseListKey id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_name", referencedColumnName = "name", insertable = false, updatable = false)
    Student student;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_name", referencedColumnName = "name", insertable = false, updatable = false)
    Course course;
    private int price;
    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    public PurchaseListKey getId() {
        return id;
    }

    public void setId(PurchaseListKey id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
