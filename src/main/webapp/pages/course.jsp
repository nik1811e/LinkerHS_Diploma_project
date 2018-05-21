<%@ page import="com.google.gson.Gson" %>
<%@ page import="course.courses.CourseInformation" %>
<%@ page import="course.courses.RequestInformation" %>
<%@ page import="course.pojo.CourseStructureTO" %>
<%@ page import="course.pojo.RequestTO" %>
<%@ page import="course.pojo.SectionTO" %>
<%@ page import="course.sections.SectionInformation" %>
<%@ page import="entity.CategoryEntity" %>
<%@ page import="entity.CourseEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MailUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Courses</title>
    <!--[if lt IE 9]>
    <![endif]-->
    <link rel="stylesheet" media="all" href="/resources/css/style.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css" media="screen">


    <!-- Adding "maximum-scale=1" fixes the Mobile Safari auto-zoom bug: http://filamentgroup.com/examples/iosScaleBug/ -->
    <!-- JS -->
    <script src="/resources/js/jquery-1.6.4.min.js"></script>
    <script src="/resources/js/css3-mediaqueries.js"></script>
    <script src="/resources/js/custom.js"></script>
    <script src="/resources/js/tabs.js"></script>
    <!-- Tweet -->
    <link rel="stylesheet" href="/resources/css/jquery.tweet.css" media="all"/>
    <script src="/resources/js/tweet/jquery.tweet.js"></script>
    <!-- ENDS Tweet -->
    <!-- superfish -->
    <link rel="stylesheet" media="screen" href="/resources/css/superfish.css"/>
    <script src="/resources/js/superfish-1.4.8/js/hoverIntent.js"></script>
    <script src="/resources/js/superfish-1.4.8/js/superfish.js"></script>
    <script src="/resources/js/superfish-1.4.8/js/supersubs.js"></script>
    <!-- ENDS superfish -->
    <!-- prettyPhoto -->
    <script src="/resources/js/prettyPhoto/js/jquery.prettyPhoto.js"></script>
    <link rel="stylesheet" href="/resources/js/prettyPhoto/css/prettyPhoto.css" media="screen"/>
    <!-- ENDS prettyPhoto -->
    <!-- poshytip -->
    <link rel="stylesheet" href="/resources/js/poshytip-1.1/src/tip-twitter/tip-twitter.css"/>
    <link rel="stylesheet" href="/resources/js/poshytip-1.1/src/tip-yellowsimple/tip-yellowsimple.css"/>
    <script src="/resources/js/poshytip-1.1/src/jquery.poshytip.min.js"></script>
    <!-- ENDS poshytip -->
    <!-- GOOGLE FONTS -->
    <link href='http://fonts.googleapis.com/css?family=Arvo:400,700' rel='stylesheet' type='text/css'>
    <!-- Flex Slider -->
    <link rel="stylesheet" href="/resources/css/flexslider.css">
    <script src="/resources/js/jquery.flexslider-min.js"></script>
    <!-- ENDS Flex Slider -->
    <!-- Masonry -->
    <script src="/resources/js/masonry.min.js"></script>
    <script src="/resources/js/imagesloaded.js"></script>
    <!-- ENDS Masonry -->
    <!-- Less framework -->
    <link rel="stylesheet" media="all" href="/resources/css/lessframework.css"/>
    <!-- modernizr -->
    <script src="/resources/js/modernizr.js"></script>
    <!-- SKIN -->
    <link rel="stylesheet" media="all" href="/resources/css/skin.css"/>
    <link rel="stylesheet" media="all" href="/resources/css/modal.css"/>
    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";

        String uuidCourse = String.valueOf(request.getParameter("uuidCourse")).trim();
        List<CourseEntity> courseInformationList = null;
        CourseStructureTO courseInformationFromJson = null;
        List<SectionTO> sectionTOList = null;
        List<RequestTO> requests = new ArrayList<>();
        List<CategoryEntity> categoryEntityList = null;
        CategoryEntity currentCategory = null;
        boolean exist = false;
        if (cookieUtil.isFindCookie()) {
            try {
                requests = new RequestInformation().getRequest(request.getParameter("uuidAuth"));
                courseInformationList = new CourseInformation().getCourseInformation(uuidCourse);
                courseInformationFromJson = new CourseInformation().getCourseInformationFromJson(uuidCourse);
                sectionTOList = new SectionInformation().getCourseSection(uuidCourse);
                categoryEntityList = MethodUtil.getCourseCategory();
                currentCategory = MethodUtil.getCourseCategoryByid(courseInformationList.get(0).getCategory());
            } catch (Exception ex) {
                new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        } else {
            response.sendRedirect(urlRedirect);
        }

    %>
</head>

<body>
<header>
    <div class="wrapper clearfix">

        <div id="logo">
            <a href="index.jsp"><img src="/resources/img/l.png" alt="Helper Service"></a>
        </div>

        <!-- nav -->
        <ul id="nav" class="sf-menu">
            <li><a href="/pages/index.jsp" style="text-decoration: none">ГЛАВНАЯ</a></li>
            <li class="current-menu-item"><a href="#" style="text-decoration: none">КАТАЛОГ РЕСУРСОВ</a>
                <ul>
                    <li><a href="/pages/catalog.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                           style="text-decoration: none">Мои
                        ресурсы</a></li>
                    <li><a href="" style="text-decoration: none">Избранное</a></li>
                </ul>
            </li>
            <li><a href="#" style="text-decoration: none">ПОЛЬЗОВАТЕЛИ</a>
                <ul>
                    <li><a href="/pages/users.jsp" style="text-decoration: none">Список пользователей</a></li>
                    <li><a href="/pages/following.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                           style="text-decoration: none">Подписки</a></li>
                    <li><a href="/pages/follower.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                           style="text-decoration: none">Подпищики</a></li>
                </ul>
            </li>
            <%if (!cookieUtil.isFindCookie()) {%>
            <li><a href="/pages/signin.jsp" style="text-decoration: none">АВТОРИЗАЦИЯ</a></li>
            <%} else {%>
            <li><a href="/pages/profile.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                   style="text-decoration: none">ПРОФИЛЬ</a></li>
            <%
                }
                assert courseInformationFromJson != null;
            %>
        </ul>
    </div>
</header>

<div id="main">
    <div class="wrapper clearfix">
        <!-- home-block -->
        <div class="home-block clearfix">
            <div class="masthead clearfix" style="min-height: 450px">
                <div id="posts-list">
                    <article class="format-standard" style="padding-right: 80px">
                        <div class="post-heading">
                            <h3 style="display: inline-block">
                                <%=courseInformationList.get(0).getNameCourse()%><br>
                            </h3>
                            <%if(courseInformationFromJson.getUuidUser().trim().equals(cookieUtil.getUserUuidFromToken())){%>
                            <div style="float: right;display: inline-block">
                                <a href="#removeCourse" id="btnRemove" class="btn-modal"
                                   style="background-color: #b1766a;font-size: 12px;width: 100px;height: 30px;text-align: center; padding: 11px; margin: 10px; display: inline-block; text-decoration: none">Удалить
                                </a>
                                <a href="#editCourse" id="btnEdit" class="btn-modal"
                                   style="background-color: #bc8b23;font-size: 12px;width: 100px;height: 30px;text-align: center; padding: 11px; margin: 10px; display: inline-block; text-decoration: none">Редактировать
                                </a>
                            </div>
                            <%}%>
                            <br>
                            <div class="meta">
                                <span class="user"><%=courseInformationList.get(0).getStatus().toUpperCase()%> |</span>
                                <span class="comments"><%=Objects.requireNonNull(MethodUtil.getCourseCategoryByid(courseInformationList.get(0).getCategory())).getName().toUpperCase()%></span>
                            </div>
                        </div>
                        <div class="feature-image" style="width: 100%;height: 60%; ">
                            <a href="" data-rel="prettyPhoto">
                                <img src="/resources/img/slides/01.jpg"
                                     alt="<%=courseInformationList.get(0).getNameCourse()%>"/>
                            </a>
                        </div>
                        <div class="excerpt"><%=courseInformationFromJson.getDescriptionCourse()%>
                        </div>
                    </article>

                </div>
            </div>
            <div class='mh-div'></div>
            <!-- thumbs -->
            <div class="clearfix" style="min-height: 150px">
                <%
                    if (MethodUtil.checkAccess(
                            courseInformationList.get(0).getStatus(),
                            courseInformationList.get(0).getAuthById(),
                            cookieUtil.getUserUuidFromToken(),
                            uuidCourse)
                            ) { %>
                <h1 class="home-headline">Разделы</h1>
                <p><a href="#myModal2" id="btn2" class="btn-modal"
                      style="padding: 20px;font-size: 16px">Добавить раздел</a></p>
                <%
                    assert sectionTOList != null;
                    if (!sectionTOList.isEmpty()) {
                        for (SectionTO aSectionTOList : sectionTOList) {
                            String uuidSection = aSectionTOList.getUuidSection();
                            String name = aSectionTOList.getName().toUpperCase();
                %>
                <figure>
                    <a href="/pages/section.jsp?uuidAuth=<%=request.getParameter("uuidAuth")%>&&uuidCourse=<%=uuidCourse%>&&uuidSection=<%=uuidSection%>"
                       class="thumb"><img src="/resources/img/dummies/featured-1.jpg" alt="Alt text"/></a>
                    <figcaption>
                        <strong><%=name%>
                        </strong>
                        <span>
                            Ссылок: <%=aSectionTOList.getResource().size()%></span>
                    </figcaption>
                </figure>
                <%
                    }
                } else {
                %>
                <figure>
                    <h2 class="text-center thin">Пусто</h2>
                </figure>
                <%}%>
                <%} else {%>
                <h1 class="home-headline">Доступ закрыт</h1>
                <%
                    for (RequestTO reqst : requests) {
                        if (reqst.getUuidAuth().equals(cookieUtil.getUserUuidFromToken()) &&
                                reqst.getUuidCourse().equals(courseInformationList.get(0).getUuid())) {
                            exist = true;
                        }
                    }
                %>
                <%if (!exist) {%>
                <form id="contactForm" action="/courserequest" method="post" style="position: center">
                    <fieldset>
                        <input type="hidden" name="uuidCourseReq" value="<%=uuidCourse%>">
                        <input type="hidden" name="uuidAuthReq" value="<%=cookieUtil.getUserUuidFromToken()%>">
                        <input type="hidden" name="uuidAuthOwner" value="<%=request.getParameter("uuidAuth")%>">
                        <input type="submit" value="Запросить доступ к курсу" name="submit" id="submit"
                               class="btn-modal" style="padding: 15px"/>
                    </fieldset>
                </form>
                <%} else {%>
                <h3 class="text-center" style="text-align: center">Запрос доступа уже отправлен</h3>
                <%
                        }
                    }
                %>
            </div>
            <!-- ENDS thumbs -->
        </div>
        <!-- Fold image -->
        <div id="fold"></div>
    </div>

</div>

</div>
<div id="myModal2" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Добавление раздела</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/sectionhandler">
                    <input type="hidden" name="uuidAuth" value="<%=request.getParameter("uuidAuth")%>">

                    <input type="hidden" name="uuidCourse"
                           value="<%=request.getParameter("uuidCourse")%>">
                    <div class="form-group">
                        <input type="text" class="form-control" id="name" name="name" required
                               maxlength="50" placeholder="Название">
                    </div>
                    <div class="form-group">
                        <label for="description">Описание</label>
                        <textarea class="form-control" name="description"
                                  id="description"
                                  placeholder="Описание" maxlength="6000" rows="7"></textarea>
                    </div>
                    <button type="submit" class="btn-modal"
                            id="btnAddSection">
                        Добавить
                    </button>
                </form>
            </div>

        </div>
    </div>
</div>
<div id="removeCourse" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Вы действительно хотите удалить курс?</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/removecourse">
                    <input type="hidden" name="uuidCourse" value="<%=request.getParameter("uuidCourse")%>">
                    <input type="hidden" name="uuidAuth" value="<%=cookieUtil.getUserUuidFromToken()%>">
                    <button type="submit" class="btn-modal">
                        Удалить
                    </button>
                </form>
            </div>

        </div>
    </div>
</div>
<div id="editCourse" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Редиктирование</h4>
            </div>
            <div class="modal-body">
                <form action="/editcourse" role="form" method="post">
                    <input type="hidden" name="uuidAuth" value="<%=request.getParameter("uuidAuth")%>">
                    <input type="hidden" name="uuidCourseEdit" value="<%=request.getParameter("uuidCourse")%>">
                    <div class="form-group">
                        <input type="text" class="form-control" name="nameCourseEdit" required
                               maxlength="50" placeholder="Название"
                               value="<%=courseInformationList.get(0).getNameCourse()%>">
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="status" name="statusCourseEdit">
                            <%if (courseInformationList.get(0).getStatus().equals("Открыт")) {%>
                            <option selected>Открыт</option>
                            <option>Закрыт</option>
                            <%} else {%>
                            <option>Открыт</option>
                            <option selected>Закрыт</option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="id_category" name="courseCategoryEdit">
                            <%
                                assert categoryEntityList != null;
                                for (CategoryEntity aCategoryEntityList : categoryEntityList) {
                                    int id = aCategoryEntityList.getId();
                                    String name = aCategoryEntityList.getName();
                                    assert currentCategory != null;
                                    if (!currentCategory.getName().equals(name)) {
                            %>
                            <option value="<%=id%>"><%=name%>
                            </option>

                            <%} else {%>
                            <option selected value="<%=id%>"><%=name%>
                            </option>

                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                                    <textarea class="form-control" name="courseDescEdit" id="desc"
                                              placeholder="Описание курса" maxlength="200"
                                              rows="7"><%=courseInformationFromJson.getDescriptionCourse().trim()%></textarea>
                    </div>
                    <button type="submit" class="btn-modal" id="btnContactUs">
                        Внести изменения
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<footer>
    <div class="wrapper clearfix">

        <!-- bottom -->
        <div class="footer-bottom">
            <div class="left">Developed by <a href="https://vk.com/xxxnikgtxxx">@Nikita1811E</a></div>
            <div class="right">
                <ul id="social-bar">
                </ul>
            </div>
        </div>
        <!-- ENDS bottom -->
    </div>
</footer>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $("#btn2").click(function () {
            $("#myModal2").modal('show');
        });
    });
    $(function () {
        $("#btnRemove").click(function () {
            $("#removeCourse").modal('show');
        });
    });
    $(function () {
        $("#btnEdit").click(function () {
            $("#editCourse").modal('show');
        });
    });
</script>
</body>

</html>