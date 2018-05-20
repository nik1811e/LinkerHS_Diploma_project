package course.sections;

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

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class SectionCreatingTest {
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
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();
        mockHttpServletRequest.setCharacterEncoding("UTF-8");

        when(mockHttpServletRequest.getParameter("uuidCourse")).thenReturn("b00d3c02-7c27-42e0-b4a1-b036de1bcf0c");
        when(mockHttpServletRequest.getParameter("name")).thenReturn("SectionNameTest");
        when(mockHttpServletRequest.getParameter("description")).thenReturn("Description_test");
        when(mockHttpServletRequest.getParameter("uuidAuth")).thenReturn("e61a37d7-c118-4ae1-abb7-2d61df870c9e");
    }

    @Test
    public void test() {
        new SectionHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
