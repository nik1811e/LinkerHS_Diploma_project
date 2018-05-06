package util;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.ResourceTO;
import course.pojo.SectionTO;
import course.resources.ResourceInformation;
import course.sections.SectionInformation;
import entity.AuthInfEntity;
import entity.CategoryEntity;
import entity.FollowingEntity;
import entity.ResourceCategoryEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class MethodUtil {
    private static final Logger logger = Logger.getLogger(MethodUtil.class);

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(FinalValueUtil.REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        logger.debug(MethodUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static String getUuudAuthById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            String result = session.createQuery("SELECT a.uuid FROM " + FinalValueUtil.ENTITY_AUTH_INFO
                    + " a WHERE  a.id =:id").setParameter("id", id).list().get(0).toString();
            logger.debug(MethodUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
            return result;
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static int getIdAuthByUUID(Session session, String uuid) {
        int result = (int) session.createQuery("SELECT a.id FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE" +
                " uuid = :uuid").setParameter("uuid", uuid).list().get(0);
        logger.debug(MethodUtil.class.getName() + " getIdUserByLoginEmail return: " + result);
        return result;
    }

    public static List<AuthInfEntity> getAuthInfByUuid(String uuidAuth) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE uuid =:uuid").
                    setParameter("uuid", uuidAuth).getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static int getIdCourseByUUID(Session session, String uuid) {
        int result = (int) session.createQuery("SELECT a.id FROM CourseEntity a WHERE uuid =:uuid").setParameter("uuid", uuid).list().get(0);
        logger.debug(MethodUtil.class.getName() + " getIdCourseByUUID return: " + result);
        return result;
    }

    public static String getNameCourseCategoryByid(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return String.valueOf(session.createQuery("SELECT name FROM " + FinalValueUtil.ENTITY_COURSE_CATEGORY + " WHERE id =:id")
                    .setParameter("id", id).list().get(0));
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static String getNameResourceCategoryByid(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return String.valueOf(session.createQuery("SELECT name FROM " + FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " WHERE id =:id")
                    .setParameter("id", id).list().get(0));
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static String getJsonCourseStructure(Session session, String uuidCourse) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            return String.valueOf(session.createQuery("SELECT s.structure FROM " + FinalValueUtil.ENTITY_COURSE + " s WHERE uuid = :uuid")
                    .setParameter("uuid", uuidCourse).list().get(0));

        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin("\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static CourseStructureTO getCourseInfFromJson(String uuidCourse){
        Session session = null;
        Gson gson = new Gson();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

           return gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);

        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin("\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static Integer[] getDateCourse (String dataCreate) throws ParseException {
        int day;
        int month;
        int year;
        Integer[] dateArr = null;

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd/MM/yyyy");
        Date date = format.parse(dataCreate);
        day = date.getDay();
        month = date.getMonth();
        year = date.getYear();

        dateArr[0] = day;
        dateArr[1] = month;
        dateArr[2] = year;
        return dateArr;
    }

    public static String getJsonRequest(Session session,String uuidAuth) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            /*return String.valueOf(session.createQuery("SELECT s.request FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " s WHERE uuid = :uuid")
                    .setParameter("uuid", uuidAuth).list().get(0));*/
            return String.valueOf(session.createSQLQuery("select course_request from auth_inf where uuid='"+uuidAuth+"'").list().get(0));
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin("\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static List<CategoryEntity> getCourseCategory() {
        logger.info("getCourseCategory");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE_CATEGORY + " c ").getResultList();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public static List<ResourceCategoryEntity> getResourceCategory() {
        logger.info("getResourceCategory");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT rc FROM " + FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " rc ").getResultList();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }

    }

    public static List<AuthInfEntity> getUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a ORDER BY a.dateReg").getResultList();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public static void showMessage(HttpServletResponse resp, boolean success, String errorMessage, String successMessage) throws IOException {
        if (success) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(successMessage);
        } else {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(errorMessage);
        }
    }

    public static boolean getAccessInformation(String uuidCourse, String uuidAuth) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            int idAuth = getIdAuthByUUID(session, uuidAuth);
            int idCourse = getIdCourseByUUID(session, uuidCourse);
            return session.createQuery("SELECT id FROM " + FinalValueUtil.ENTITY_ACCEESS_INFO + " ac WHERE ac.idAuth =:idAuth and ac.idCourse =:idCourse")
                    .setParameter("idAuth", idAuth).setParameter("idCourse", idCourse).getResultList().isEmpty();
        } catch (Exception ex) {
            return false;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static boolean checkAccess(String status, AuthInfEntity idCourseOwner, String uuidAuth, String uuidCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            boolean accessEntity = getAccessInformation(uuidCourse, uuidAuth);
            if (status.equals("Открыт")) return true;
            if (!accessEntity) return true;
            return idCourseOwner.getId() == (getIdAuthByUUID(session, uuidAuth));
        } catch (Exception ex) {
            return false;
        } finally {
            if (session.isOpen())
                session.close();
        }

    }

    public static String getCourseNameByUuid(String uuidCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction();
        try {
            return String.valueOf(session.createQuery("SELECT nameCourse FROM " + FinalValueUtil.ENTITY_COURSE + "  WHERE uuid =:uuid")
                    .setParameter("uuid", uuidCourse).list().get(0));
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static String getSectionNameByUuid(String uuidCourse, String uuidSection) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction();
        try {
            return new SectionInformation().getSectionInformation(uuidCourse, uuidSection).getName();
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static boolean isUniqueResource(String name, String link, String uuidCourse, String uuidSection) {

        List<ResourceTO> resourceTOList = new ResourceInformation().getSectionResource(uuidSection, uuidCourse);
        for (ResourceTO rc :
                resourceTOList) {
            if (rc.getName().trim().equals(name.trim()) || rc.getLink().trim().equals(link.trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSectionExist(List<SectionTO> section, String uuid) {
        for (SectionTO sect : section) {
            if (sect.getUuidSection().equals(uuid))
                return true;
        }
        return false;
    }

    public static boolean updateJsonStructure(Session session, Transaction transaction, String uuidCourse, String jsonStructure) {
        try {
            session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " c SET c.structure = :newStructure WHERE c.uuid = :uuid")
                    .setParameter("newStructure", jsonStructure).setParameter("uuid", uuidCourse).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            logger.error(ex.getStackTrace());
            return false;
        }
    }

    public static List<AuthInfEntity> getUsersFromDb() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a").getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public static boolean isExistFollowing(String uuiddFollower, String uuididFollowing) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            boolean isExist = !(session.createQuery("SELECT f FROM " + FinalValueUtil.ENTITY_FOLLOWING + " f where f.idAuth =:idFollower and f.idFollowing =:idFollowing")
                    .setParameter("idFollower", getIdAuthByUUID(session, uuiddFollower)).setParameter("idFollowing", getIdAuthByUUID(session, uuididFollowing)).list().isEmpty());
            return isExist;
        } catch (Exception ex) {

        } finally {
            if (session.isOpen())
                session.close();
        }
        return false;
    }

    public static List<FollowingEntity> getUserFollowings(String uuidFollower) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT f FROM " + FinalValueUtil.ENTITY_FOLLOWING + " f where f.idAuth =:idFollower")
                    .setParameter("idFollower", getIdAuthByUUID(session, uuidFollower)).list();
        } catch (Exception ex) {

        } finally {
            if (session.isOpen())
                session.close();
        }
        return null;
    }

    public static List<FollowingEntity> getUserFollowers(String uuidFollowing) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT f FROM " + FinalValueUtil.ENTITY_FOLLOWING + " f where f.idFollowing =:uuidFollowing")
                    .setParameter("uuidFollowing", getIdAuthByUUID(session, uuidFollowing)).list();
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }

    }

    public static boolean updateRequest(Session session, Transaction transaction, String request, String uuidAuth) {
        try {
            session.createQuery("UPDATE " + FinalValueUtil.ENTITY_AUTH_INFO + " c SET c.request =:newRequest WHERE c.uuid = :uuid")
                    .setParameter("newRequest", request).setParameter("uuid", uuidAuth).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            logger.error(ex.getStackTrace());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
