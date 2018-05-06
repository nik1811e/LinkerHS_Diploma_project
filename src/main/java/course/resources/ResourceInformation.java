package course.resources;

import com.google.gson.Gson;
import course.pojo.ResourceTO;
import course.pojo.SectionTO;
import course.sections.SectionInformation;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;

@WebServlet(urlPatterns = "/resourceinformation")
public class ResourceInformation extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ResourceInformation.class);
    private Gson gson = new Gson();

    public List<ResourceTO> getSectionResource(String uuidSection, String uuidCourse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            List<SectionTO> sectionsTOList = new SectionInformation().getCourseSection(uuidCourse);
            List<ResourceTO> resourceTOList;
            for (SectionTO sn : sectionsTOList) {
                if (sn.getUuidSection().equals(uuidSection)) {
                    resourceTOList = sn.getResource();
                    return resourceTOList;
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public ResourceTO getResourceInformation(String uuidCourse, String uuidSection, String uuidResource) {
        logger.info("getResourceInformation");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            List<ResourceTO> resourceTOList = getSectionResource(uuidSection, uuidCourse);
            for (ResourceTO rt : resourceTOList) {
                if (rt.getUuidResource().equals(uuidResource)) {
                    return rt;
                }
            }
        } catch (Exception ex) {
            return null;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
