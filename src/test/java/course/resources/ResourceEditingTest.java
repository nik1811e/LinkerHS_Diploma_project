package course.resources;

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
public class ResourceEditingTest {
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
        when(mockHttpServletRequest.getParameter("uuidSection")).thenReturn("254a640d-db85-4120-8bd0-4432ad945014");
        when(mockHttpServletRequest.getParameter("uuidResource")).thenReturn("8d6f8073-761c-4910-a4a9-cf26af38384f");
        when(mockHttpServletRequest.getParameter("uuidAuth")).thenReturn("e61a37d7-c118-4ae1-abb7-2d61df870c9e");
        when(mockHttpServletRequest.getParameter("link")).thenReturn("http://localhost:8080/pages/course.jsp");
        when(mockHttpServletRequest.getParameter("name")).thenReturn("qaz");
        when(mockHttpServletRequest.getParameter("author")).thenReturn("test");
        when(mockHttpServletRequest.getParameter("desc")).thenReturn("desc "+UUID.randomUUID().toString());
        when(mockHttpServletRequest.getParameter("category")).thenReturn("1");
    }

    @Test
    public void testCreateRequest(){
        new ResourceEditing().doPost(mockHttpServletRequest,mockHttpServletResponse);
    }
}
