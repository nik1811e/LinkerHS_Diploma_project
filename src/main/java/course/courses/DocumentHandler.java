package course.courses;

import entity.CourseEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;

@WebServlet(urlPatterns = "/coursestoexcel")
public class DocumentHandler extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DocumentHandler.class);

    private ByteArrayOutputStream byteArrayOutputStreamExcel = new ByteArrayOutputStream();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    private void generateExcelDocAllCources(String extension) {
        List<Map<String, String>> dataList = new ArrayList<>();
        List<CourseEntity> coursesList = MethodUtil.getAllCourses();

        List<String> columnList = new ArrayList<>();
        columnList.add("Name");
        columnList.add("Status");
        columnList.add("Category");
        columnList.add("Section count");
        columnList.add("Description");

        assert coursesList != null;
        for (CourseEntity course : coursesList) {
            Map<String, String> map = new HashMap<>();
            map.put(columnList.get(0), course.getNameCourse());
            map.put(columnList.get(1), course.getStatus());
            map.put(columnList.get(2), MethodUtil.getNameCourseCategoryByid(course.getCategory()));
            map.put(columnList.get(4), course.getNameCourse());
            dataList.add(map);
        }
        MailUtil mailUtil = new MailUtil();
        String fileName = "File_";
        mailUtil.addAttachment(MethodUtil.prepareFileForAttach(
                MethodUtil.createExcelFile(dataList, columnList, "Bet history"),
                fileName, extension));
        mailUtil.sendErrorMail("");
    }

    public String getExcelEncode() {
        return Base64.getEncoder().encodeToString(byteArrayOutputStreamExcel.toByteArray());
    }
}
