package course.courses;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import entity.CourseEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FinalValueUtil;
import util.HibernateUtil;
import util.MailUtil;
import util.MethodUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static java.lang.String.valueOf;

@SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
@WebServlet(urlPatterns = "/coursesDoc")
public class DocumentHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DocumentHandler.class);
    private ByteArrayOutputStream byteArrayOutputStreamPDF = new ByteArrayOutputStream();
    private static String timeNow = valueOf(new SimpleDateFormat(FinalValueUtil.PATTERN_TIME).format(new Date()));
    private ByteArrayOutputStream byteArrayOutputStreamExcel = new ByteArrayOutputStream();
    private static String subject = "Отчет по курсам";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");

            try {
                generateExcelDocAllCources(req, FinalValueUtil.EXCEL_EXTENSION_XLSX);
                resp.sendRedirect("/pages/admin/tables.jsp");
            } catch (Exception ex) {

            }
          /*  if(req.getParameter("type").equals("pdf")){
                generateDocHistoryBetPDF(req.getParameter("uuidAuth"),req,resp);
            }*/
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    private void generateExcelDocAllCources(HttpServletRequest req, String extension) {
        try {
            String url = String.valueOf( new URL(req.getRequestURL().toString())) ;
            List<Map<String, String>> dataList = new ArrayList<>();
            List<CourseEntity> coursesList = MethodUtil.getAllCourses();

            List<String> columnList = new ArrayList<>();
            columnList.add("Name");
            columnList.add("Status");
            columnList.add("Category");
            columnList.add("Section count");
            columnList.add("Description");
            columnList.add("Date create");

            assert coursesList != null;
            for (CourseEntity course : coursesList) {
                Map<String, String> map = new HashMap<>();
                map.put(columnList.get(0), course.getNameCourse());
                map.put(columnList.get(1), course.getStatus());
                map.put(columnList.get(2), Objects.requireNonNull(MethodUtil.getCourseCategoryByid(course.getCategory())).getName());
                map.put(columnList.get(4), course.getNameCourse());
                map.put(columnList.get(5), course.getDateCreate());
                dataList.add(map);
            }
            MailUtil mailUtil = new MailUtil();
            String fileName = "File_";
            mailUtil.addAttachment(MethodUtil.prepareFileForAttach(
                    MethodUtil.createExcelFile(dataList, columnList, "User's courses"),
                    fileName, extension));

            String body = "<br/>" + timeNow +
                    "<br/>Добрый день, найдите прикрепленные файлы в письме." +
                    "<br/>Адрес : <b>" + url +"</b>" +
                    "<br/><br/>С уважением";

            mailUtil.sendSimpleHtmlMail(FinalValueUtil.EMAIL_SUPPORT, body, subject );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void generateDocHistoryBetPDF(String uuidAuth, HttpServletRequest req, HttpServletResponse resp) {
        try {
            Document document = new Document(PageSize.A4);
            URL url = new URL(req.getRequestURL().toString());

            PdfPTable table = new PdfPTable(new float[]{20, 10, 10, 10});
            table.setTotalWidth(PageSize.A4.getWidth() - 10);
            table.setLockedWidth(true);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

            table.addCell("First/Last name");
            table.addCell("Bet");
            table.addCell("Date");
            table.addCell("Time");
            table.setHeaderRows(1);

            PdfPCell[] cells = table.getRow(0).getCells();
            for (PdfPCell cell : cells) {
                cell.setBackgroundColor(BaseColor.WHITE);
            }

            for (int i = 1; i < 1; i++) {
                table.addCell("");
            }

            PdfWriter pdfWriter = null;

            pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStreamPDF);

            document.open();

            document.addAuthor("Helper Service");
            document.addTitle("Bet history");

            document.add(new Paragraph("Helper Service"));
            document.add(new Paragraph("Bet history"));
            document.add(new Paragraph(valueOf(new SimpleDateFormat(FinalValueUtil.PATTERN_FULL_DATE_TIME).format(new Date()))));

            document.add(new Paragraph("\n---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("UUID Auth: " + uuidAuth));

            document.close();

            LOGGER.info("PDF document successfully generated");

        } catch (IOException | DocumentException e) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
    }


    public String getExcelEncode() {
        return Base64.getEncoder().encodeToString(byteArrayOutputStreamExcel.toByteArray());
    }
}
