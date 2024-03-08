import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        Course course = session.get(Course.class, 1);
        System.out.println(course.getName() + ": количество студентов - " + course.getStudentsCount());

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> query = builder.createQuery(PurchaseList.class);
        Root<PurchaseList> root = query.from(PurchaseList.class);
        query.select(root);
        List<PurchaseList> purchaseList = session.createQuery(query).getResultList();

        Transaction transaction = session.beginTransaction();
        for (PurchaseList purchase : purchaseList) {
            LinkedPurchaseList linkedPurchase = new LinkedPurchaseList();
            int studentId = purchase.getStudent().getId();
            linkedPurchase.setStudentId(studentId);
            int courseId = purchase.getCourse().getId();
            linkedPurchase.setCourseId(courseId);
            linkedPurchase.setId(new LinkedPurchaseListKey(studentId, courseId));
            session.save(linkedPurchase);
        }
        transaction.commit();
        sessionFactory.close();
    }
}
