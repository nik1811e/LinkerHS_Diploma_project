package course.resources;

import course.pojo.ResourceTO;
import course.sections.SectionInformation;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/resourceinformation")
public class ResourceInformation extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ResourceInformation.class);

    public List<ResourceTO> getSectionResource(String uuidSection, String uuidCourse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
           return new ArrayList<>(new SectionInformation().getSectionInformation(uuidCourse,uuidSection).getResource());
        } catch (Exception ex) {
            return null;
        }
    }

    public ResourceTO getResourceInformation(String uuidCourse, String uuidSection, String uuidResource) {
        LOGGER.info("getResourceInformation");
        try {
            List<ResourceTO> resourceTOList = getSectionResource(uuidSection, uuidCourse);
            for (ResourceTO rt : resourceTOList) {
                if (rt.getUuidResource().equals(uuidResource)) {
                    return rt;
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }
}
