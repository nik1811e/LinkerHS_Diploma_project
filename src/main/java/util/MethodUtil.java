package util;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.isBlank;

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

    public static AuthInfEntity getAuthInfByUuid(String uuidAuth) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + FinalValueUtil.ENTITY_AUTH_INFO + " a WHERE uuid =:uuid", AuthInfEntity.class).
                    setParameter("uuid", uuidAuth).list().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public static int getIdCourseByUUID(Session session, String uuid) {
        int result = (int) session.createQuery("SELECT a.id FROM CourseEntity a WHERE uuid =:uuid").setParameter("uuid", uuid).list().get(0);
        LOGGER.debug(MethodUtil.class.getName() + " getIdCourseByUUID return: " + result);
        return result;
    }

    public static CategoryEntity getCourseCategoryByid(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_COURSE_CATEGORY + " c WHERE id =:id", CategoryEntity.class)
                    .setParameter("id", id).list().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public static ResourceCategoryEntity getResourceCategoryByid(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return (ResourceCategoryEntity) session.createQuery("SELECT c FROM " + FinalValueUtil.ENTITY_RESOURCE_CATEGORY + " c WHERE id =:id", ResourceCategoryEntity.class)
                    .setParameter("id", id).list().get(0);
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

    public static boolean getAccessInformation(String uuidCourse, String uuidAuth) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return !session.createQuery("SELECT id FROM " + FinalValueUtil.ENTITY_ACCEESS_INFO + " ac WHERE ac.idAuth =:idAuth and ac.idCourse =:idCourse")
                    .setParameter("idAuth", getIdAuthByUUID(session, uuidAuth)).setParameter("idCourse", getIdCourseByUUID(session, uuidCourse)).getResultList().isEmpty();
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean checkAccess(String status, AuthInfEntity owner, String uuidAuth, String uuidCourse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            if (owner.getId() == (getIdAuthByUUID(session, uuidAuth))) return true;
            if (status.trim().equals("Открыт")) return true;
            if (getAccessInformation(uuidCourse, uuidAuth)) return true;
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean updateJsonStructure(Session session, Transaction transaction, String uuidCourse, String jsonStructure) {
        try {
            if (!jsonStructure.isEmpty()) {
                session.createQuery("UPDATE " + FinalValueUtil.ENTITY_COURSE + " c SET c.structure = :newStructure WHERE c.uuid = :uuid")
                        .setParameter("newStructure", jsonStructure).setParameter("uuid", uuidCourse).executeUpdate();
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            return false;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    public static boolean isUniqueNameCourse(Session session, String name, AuthInfEntity idAuth, String uuidCourse) {
        try {
            List uuidQ = session.createQuery("SELECT uuid FROM " + FinalValueUtil.ENTITY_COURSE + "  WHERE nameCourse = :nameCourse and authById =:idAuth")
                    .setParameter("nameCourse", name).setParameter("idAuth", idAuth).list();
            assert uuidQ != null;
            if (!uuidQ.isEmpty()) {
                if (!uuidQ.get(0).equals(uuidCourse)) {
                    return false;
                }
                return true;
            }
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public static boolean isUniqueSectionName(String uuidCourse, String nameSection, String uuidSection) {
        List<SectionTO> sectionTOList = new SectionInformation().getCourseSection(uuidCourse);
        for (SectionTO sn : sectionTOList) {
            if (sn.getName().equals(nameSection) && !sn.getUuidSection().equals(uuidSection)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUniqueResource(String name, String link, String uuidCourse, String uuidSection, String uuidResource) {
        try {
            List<ResourceTO> resourceTOList = new ResourceInformation().getSectionResource(uuidCourse, uuidSection);
            assert resourceTOList != null;
            for (ResourceTO rc :
                    resourceTOList) {
                if ((rc.getName().trim().equals(name.trim()) || rc.getLink().trim().equals(link.trim())) && !rc.getUuidResource().equals(uuidResource)) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            return false;
        }
    }

    public static List<CourseEntity> getAllCourses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    public static boolean updateAuthInf(Session session, Transaction transaction, String login,String email, String fname, String lname, String bday, String uuid, String desc, String date,String image) {
        try {
            session.createQuery("UPDATE  " + FinalValueUtil.ENTITY_AUTH_INFO + " a SET " +
                    "a.login=:login, a.nameImage=:nameImage,a.email=:email," +
                    "a.FName =:fname,a.LName =:lname,a.BDay=:bday,a.about=:about," +
                    "a.dateReg=:date WHERE a.uuid =:uuid")
                    .setParameter("login", login).setParameter("email", email)
                    .setParameter("fname", fname).setParameter("lname", lname)
                    .setParameter("bday", bday).setParameter("uuid", uuid).setParameter("about", desc)
                    .setParameter("date", date).setParameter("nameImage",image).executeUpdate();
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

    private static String separateUploadFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename"))
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
        }
        return "";
    }

    public static String saveUploadFile(HttpServletRequest req) throws IOException, ServletException {
        String uploadFilePath = req.getServletContext().getRealPath("") + separator + FinalValueUtil.FOLDER_UPLOAD_IMAGES;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists())
            fileSaveDir.mkdirs();

        for (Part part : req.getParts()) {
            String fileName = separateUploadFileName(part);
            if (!isBlank(fileName)) {
                String path = uploadFilePath + separator + fileName;
                part.write(path);
                return Base64.getEncoder().encodeToString(readFileToByteArray(new File(path)));
            }
        }
        return "";
    }
}
