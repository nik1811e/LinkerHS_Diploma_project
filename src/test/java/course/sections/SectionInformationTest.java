package course.sections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class SectionInformationTest {
/*    @Mock
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
       }*/

    @Test
    public void testGetCourseSection() {
        new SectionInformation().getCourseSection("b00d3c02-7c27-42e0-b4a1-b036de1bcf0c");
    }
    @Test
    public void testGetSectionInformation() {
        new SectionInformation().getSectionInformation("b00d3c02-7c27-42e0-b4a1-b036de1bcf0c","254a640d-db85-4120-8bd0-4432ad945014");
    }
}
