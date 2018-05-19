package course.courses;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class CourseEditingTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Configuration configuration;

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() {
        mockHttpServletRequest.setCharacterEncoding("UTF-8");
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();
        when(mockHttpServletRequest.getParameter("uuidCourseEdit")).thenReturn("b00d3c02-7c27-42e0-b4a1-b036de1bcf0c");
        when(mockHttpServletRequest.getParameter("nameCourseEdit")).thenReturn("Java");
        when(mockHttpServletRequest.getParameter("statusCourseEdit")).thenReturn("Закрыт");
        when(mockHttpServletRequest.getParameter("uuidAuth")).thenReturn("e61a37d7-c118-4ae1-abb7-2d61df870c9e");
        when(mockHttpServletRequest.getParameter("courseCategoryEdit")).thenReturn("2");
        when(mockHttpServletRequest.getParameter("courseDescEdit")).thenReturn("Description test: "+ UUID.randomUUID().toString());
    }

    @Test
    public void test() {
        new CourseEditing().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
