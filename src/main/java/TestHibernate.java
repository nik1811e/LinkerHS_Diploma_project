import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MethodUtil;

@SuppressWarnings("ALL")
public class TestHibernate {
    private static final Logger logger = Logger.getLogger(TestHibernate.class);

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

/*
        Registration doRegistration = new Registration();
        doRegistration.doRegistration("qwe", "qwe", "qwe@qwe.qwe");*/

     /*MailUtil mailUtil = new MailUtil();
       mailUtil.sendMailRegistration("service.helper.eng@gmail.com","test","123",);*/

/*        SectionHandler sectionHandler = new SectionHandler();
        sectionHandler.addSection(sectionHandler.prepareAddSection("qeqwe","133d96fe-8727-4b9f-a45a-ed13b7f3202d","fefqwdas"));*/

    /*    String s = "qdqwd";
        String uuidCourse ="84c89247-1ebf-47f3-a49d-9adba95785c1";
        System.out.println(session.createSQLQuery("select structure from course where uuid = ' "+ uuidCourse + "' "));
        */

       /* System.out.println("LOL" + ":" + session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " SET structure = :newBulk WHERE uuid = :uuid")
                .setParameter("newBulk", "kgkwejwl").setParameter("uuid", "1dfabb2d-5396-4580-a25f-d622f9267efa").executeUpdate() );*/

        /* new DocumentHandler().generateExcelDocAllCources(FinalValueUtil.EXCEL_EXTENSION_XLSX);*/

/*
        new UserRemoving().removeUserAndDepends(session, transaction, "f065837d-3b49-4301-9583-2acf988bd423");
*/
new MethodUtil().changePermission(session,transaction,"5b971bd2-a268-4e03-b987-853d98021106","admin");
    }

}
