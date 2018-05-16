package util;

import com.google.gson.Gson;
import course.pojo.CourseStructureTO;
import course.pojo.ResourceTO;
import course.pojo.SectionTO;
import course.resources.ResourceInformation;
import course.sections.SectionInformation;
import entity.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class MethodUtil {
    private static final Logger LOGGER = Logger.getLogger(MethodUtil.class);

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(FinalValueUtil.REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        LOGGER.debug(MethodUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static String getUuudAuthById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String result = session.createQuery("SELECT a.uuid FROM " + FinalValueUtil.ENTITY_AUTH_INFO
                    + " a WHERE  a.id =:id").setParameter("id", id).list().get(0).toString();
            LOGGER.debug(MethodUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public static int getIdAuthByUUID(Session session, String uuid) {
        int result = (int) session.createQuery("SELECT a.id FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE" +
                " uuid = :uuid").setParameter("uuid", uuid).list().get(0);
        LOGGER.debug(MethodUtil.class.getName() + " getIdUserByLoginEmail return: " + result);
        return result;
    }

    public static List<AuthInfEntity> getAuthInfByUuid(String uuidAuth) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE uuid =:uuid").
                    setParameter("uuid", uuidAuth).getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public static int getIdCourseByUUID(Session session, String uuid) {
        int result = (int) session.createQuery("SELECT a.id FROM CourseEntity a WHERE uuid =:uuid").setParameter("uuid", uuid).list().get(0);
        LOGGER.debug(MethodUtil.class.getName() + " getIdCourseByUUID return: " + result);
        return result;
    }

    public static String getNameCourseCategoryByid(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return String.valueOf(session.createQuery("SELECT name FROM " + FinalValueUtil.ENTITY_COURSE_CATEGORY + " WHERE id =:id")
                    .setParameter("id", id).list().get(0));
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getNameResourceCategoryByid(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return String.valueOf(session.createQuery("SELECT name FROM " + FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " WHERE id =:id")
                    .setParameter("id", id).list().get(0));
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getJsonCourseStructure(Session session, String uuidCourse) {
        try {
            return String.valueOf(session.createQuery("SELECT s.structure FROM " + FinalValueUtil.ENTITY_COURSE + " s WHERE uuid = :uuid")
                    .setParameter("uuid", uuidCourse).list().get(0));
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static CourseStructureTO getCourseInfFromJson(String uuidCourse) {
        Gson gson = new Gson();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return gson.fromJson(MethodUtil.getJsonCourseStructure(session, uuidCourse), CourseStructureTO.class);
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static Integer[] getDateCourse(String dataCreate) throws ParseException {
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

    public static String getJsonRequest(Session session, String uuidAuth) {
        try {
            return String.valueOf(session.createSQLQuery("select course_request from auth_inf where uuid='" + uuidAuth + "'").list().get(0));
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static List<CategoryEntity> getCourseCategory() {
        LOGGER.info("getCourseCategory");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE_CATEGORY + " c ").getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public static List<ResourceCategoryEntity> getResourceCategory() {
        LOGGER.info("getResourceCategory");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT rc FROM " + FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " rc ").getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static List<AuthInfEntity> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a ORDER BY a.dateReg").getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT id FROM " + FinalValueUtil.ENTITY_ACCEESS_INFO + " ac WHERE ac.idAuth =:idAuth and ac.idCourse =:idCourse")
                    .setParameter("idAuth", getIdAuthByUUID(session, uuidAuth)).setParameter("idCourse", getIdCourseByUUID(session, uuidCourse)).getResultList().isEmpty();
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean checkAccess(String status, AuthInfEntity idCourseOwner, String uuidAuth, String uuidCourse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            boolean accessEntity = getAccessInformation(uuidCourse, uuidAuth);
            if (status.equals("Открыт")) return true;
            if (!accessEntity) return true;
            return idCourseOwner.getId() == (getIdAuthByUUID(session, uuidAuth));
        } catch (Exception ex) {
            return false;
        }
    }

    public static String getCourseNameByUuid(String uuidCourse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction();
            return String.valueOf(session.createQuery("SELECT nameCourse FROM " + FinalValueUtil.ENTITY_COURSE + "  WHERE uuid =:uuid")
                    .setParameter("uuid", uuidCourse).list().get(0));
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getSectionNameByUuid(String uuidCourse, String uuidSection) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction();
            return new SectionInformation().getSectionInformation(uuidCourse, uuidSection).getName();
        } catch (Exception ex) {
            return null;
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
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public static List<AuthInfEntity> getUsersFromDb() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a").getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean isExistFollowing(String uuiddFollower, String uuididFollowing) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return !(session.createQuery("SELECT f FROM " + FinalValueUtil.ENTITY_FOLLOWING + " f where f.idAuth =:idFollower and f.idFollowing =:idFollowing")
                    .setParameter("idFollower", getIdAuthByUUID(session, uuiddFollower)).setParameter("idFollowing", getIdAuthByUUID(session, uuididFollowing)).list().isEmpty());
        } catch (Exception ex) {
            return false;
        }
    }

    public static List<FollowingEntity> getUserFollowings(String uuidFollower) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT f FROM " + FinalValueUtil.ENTITY_FOLLOWING + " f where f.idAuth =:idFollower")
                    .setParameter("idFollower", getIdAuthByUUID(session, uuidFollower)).list();
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<FollowingEntity> getUserFollowers(String uuidFollowing) {
        try( Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT f FROM " + FinalValueUtil.ENTITY_FOLLOWING + " f where f.idFollowing =:uuidFollowing")
                    .setParameter("uuidFollowing", getIdAuthByUUID(session, uuidFollowing)).list();
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean updateRequest(Session session, Transaction transaction, String request, String uuidAuth) {
        try {
            session.createQuery("UPDATE " + FinalValueUtil.ENTITY_AUTH_INFO + " c SET c.request =:newRequest WHERE c.uuid = :uuid")
                    .setParameter("newRequest", request).setParameter("uuid", uuidAuth).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public static boolean isUniqueNameCourse(Session session, String name, AuthInfEntity idAuth) {
        try {
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE c.nameCourse = :nameCourse and authById =:idAuth")
                    .setParameter("nameCourse", name).setParameter("idAuth", idAuth).list().isEmpty();
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public static List<CourseEntity> getCourseByUuid(Session session, String uuidCourse) {
        try {
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c WHERE uuid =:uuidCourse")
                    .setParameter("uuidCourse", uuidCourse).list();
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static boolean isUniqueSectionName(String uuidCourse, String nameSection) {
        List<SectionTO> sectionTOList = new SectionInformation().getCourseSection(uuidCourse);
        for (SectionTO sn :
                sectionTOList) {
            if (sn.getName().equals(nameSection)) {
                return false;
            }
        }
        return true;
    }

    public static List<CourseEntity> getAllCourses() {
        try ( Session session  = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE + " c").getResultList();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static File prepareExcelFileForAttach(Workbook workbook, String fileName, String extension) {
        try {
            File tempFile = File.createTempFile(fileName, extension);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();
            return tempFile;
        } catch (IOException ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static Workbook createExcelFile(List<Map<String, String>> dataList, List<String> columnList, String sheetName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        Row rowHeader = sheet.createRow(0);

        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(String.valueOf(columnList.get(i)));
        }

        int rowNumber = 1;
        for (Map<String, String> stringMap : dataList) {
            Row row = sheet.createRow(rowNumber++);
            int columnNumber = 0;
            for (String column : columnList) {
                Cell cell = row.createCell(columnNumber++);
                cell.setCellValue(stringMap.get(column));
            }
        }
        return workbook;
    }

    public static File prepareFileForAttach(Object o, String fileName, String extension) {
        try {
            File tempFile = File.createTempFile(fileName, extension);

            LOGGER.info("file name: " + tempFile.getName());
            LOGGER.info("file path: " + tempFile.getAbsolutePath());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (extension.equals(FinalValueUtil.EXCEL_EXTENSION_XLSX) || extension.equals(FinalValueUtil.EXCEL_EXTENSION_XLS)) {
                LOGGER.info("prepareFileForAttach\textension: " + extension);
                Workbook workbook = (Workbook) o;
                workbook.write(byteArrayOutputStream);
            }

            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();

            return tempFile;
        } catch (IOException ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static boolean updateAuthInf(Session session, Transaction transaction, String login, String password, String email, String fname, String lname, String bday, String uuid, String desc, String date, String status) {
        try {
            session.createQuery("UPDATE  " + FinalValueUtil.ENTITY_AUTH_INFO + " a SET " +
                    "a.login=:login, a.password=:password,a.email=:email," +
                    "a.FName =:fname,a.LName =:lname,a.BDay=:bday,a.about=:about," +
                    "a.role=:status,a.dateReg=:date WHERE a.uuid =:uuid")
                    .setParameter("login", login).setParameter("password", password).setParameter("email", email)
                    .setParameter("fname", fname).setParameter("lname", lname)
                    .setParameter("bday", bday).setParameter("uuid", uuid).setParameter("about", desc)
                    .setParameter("date", date).setParameter("status", status).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public static void setSectionResources(String uuidSection, List<SectionTO> sections, List<SectionTO> tempSectionList, List<ResourceTO> res) {
        for (SectionTO sect : sections) {
            if (sect.getUuidSection().equals(uuidSection)) {
                sect.setResource(res);
                tempSectionList.add(sect);
            } else {
                tempSectionList.add(sect);
            }
        }
    }
}
