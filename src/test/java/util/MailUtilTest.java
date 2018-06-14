package util;

import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class MailUtilTest {
    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Before
    public void init() {
        initMocks(this);
        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/"));
    }

    @Test
    public void test() {
        new MailUtil().sendErrorMail("");
        new MailUtil().sendSimpleHtmlMail("", "", "");
    }
}
