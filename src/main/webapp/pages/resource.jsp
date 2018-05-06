<%@ page import="course.pojo.ResourceTO" %>
<%@ page import="course.pojo.SectionTO" %>
<%@ page import="course.resources.ResourceInformation" %>
<%@ page import="course.sections.SectionInformation" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MailUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Resources</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css" media="screen">
    <![endif]-->
    <link rel="stylesheet" media="all" href="/resources/css/style.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- Adding "maximum-scale=1" fixes the Mobile Safari auto-zoom bug: http://filamentgroup.com/examples/iosScaleBug/ -->
    <!-- JS -->
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css" media="screen">
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
    <script src="/resources/js/modal_win.js"></script>
    <!-- SKIN -->
    <link rel="stylesheet" media="all" href="/resources/css/skin.css"/>
    <link rel="stylesheet" media="all" href="/resources/css/modal.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" media="all" href="/resources/css/modal.css"/>

    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";
        ResourceTO resource = null;
        List<SectionTO> sections = null;
        String uuidCourse = null;
        String uuidSection = null;
        String uuidResource = null;
        SectionTO sectionInformations = null;
        if (cookieUtil.isFindCookie()) {
            uuidCourse = String.valueOf(request.getParameter("uuidCourse")).trim();
            uuidSection = String.valueOf(request.getParameter("uuidSection")).trim();
            uuidResource = String.valueOf(request.getParameter("uuidResource")).trim();
            sections = new SectionInformation().getCourseSection(uuidCourse);
/*
            sectionInformations = new SectionInformation().getSectionInformation(uuidCourse, uuidSection);
*/
            try {
                resource = new ResourceInformation().getResourceInformation(uuidCourse, uuidSection, uuidResource);
            } catch (Exception ex) {
                new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        } else {
            response.sendRedirect(urlRedirect);
        }
        assert resource != null;
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
            <%}%>
        </ul>
    </div>
</header>

<!-- MAIN -->
<div id="main">
    <div class="wrapper clearfix">
        <!-- masthead -->
        <div class="masthead clearfix">
            <h2>Подробная информация ресурса <%=resource.getName().toUpperCase()%>
            </h2>
        </div>

        <div class='mh-div'></div>
        <!-- ENDS masthead -->

        <!-- posts list -->
        <div id="posts-list" class="clearfix" style="min-height: 520px">
            <!-- Courses -->
            <article class="format-standard" style="padding-right: 80px">
                <div class="post-heading">
                    <h3>
                        <%=resource.getName()%>
                    </h3>
                </div>
                <div class="feature-image">
                    <a href="" data-rel="prettyPhoto">
                        <img src="/resources/img/slides/01.jpg" alt="<%=resource.getName()%>"/>
                    </a>
                </div>
                <div class="excerpt" style="width: 480px">
                    <p>Описание: <%=resource.getDescriptionResource()%>
                    </p>
                </div>

            </article>

        </div>
        <!-- ENDS posts list -->

        <!-- sidebar -->
        <aside id="sidebar" style="display: contents">
            <ul>
                <li class="block" style="padding: 0;margin: 0">
                    <h4>Подробно</h4>
                    <p>Автор: <%=resource.getAuthor()%>
                    </p>
                    <p>Ссылка: <a href="<%=resource.getLink()%>"
                                  style="text-decoration: #f1d76e"><%=resource.getLink()%>
                    </a></p>
                    <p>Категория: <%=MethodUtil.getNameResourceCategoryByid(resource.getCategory_link())%>
                    </p>
                </li>
            </ul>
            <div>
                <a href="#myModal2" id="btn2" class="btn-modal"
                   style="background-color: #aeb131;font-size: 12px;width: 100px;height: 30px;text-align: center; padding:11px;margin:10px;display: inline-block;text-decoration: none">Копировать</a>
                <a href="#myModal1" id="btn1" class="btn-modal"
                   style="background-color: #b1766a;font-size: 12px;width: 100px;height: 30px;text-align: center; padding: 11px; margin: 10px; display: inline-block; text-decoration: none">Удалить</a>
            </div>
            <em id="corner"></em>
        </aside>
        <!-- ENDS sidebar -->

        <!-- Fold image -->
        <div id="fold"></div>
    </div>
</div>
<!-- ENDS MAIN -->

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

<div id="myModal2" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Копирование ссылки</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/resourcehandler">
                    <input type="hidden" name="uuidCourse" value="<%=request.getParameter("uuidCourse")%>">
                    <div class="form-group">
                        <input type="text" class="form-control" id="name_resource" name="name_resource"
                               required
                               maxlength="50" placeholder="Название" value="<%=resource.getName()%>">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="link" required
                               maxlength="50"
                               value="<%=resource.getLink()%>">
                    </div>
                    <div class="form-group">
                        <h4 style="color: #3A3A3A">Из</h4> <input type="text" class="form-control"
                                                                  id="currentSection"
                                                                  name="currentSection"
                                                                  required
                                                                  maxlength="50"
                                                                  value="<%=new MethodUtil().getSectionNameByUuid(uuidCourse,resource.getUuidSection())%>"
                                                                  disabled>
                    </div>
                    <div class="form-group">
                        <h4 style="color: #3A3A3A">В</h4> <select class="form-control" id="uuidSection"
                                                                  name="uuidSection">
                        <option value="" disabled selected>Выберите раздел</option>
                        <%
                            assert sections != null;
                            for (int i = 0; i < sections.size(); i++) {
                                String uuid = sections.get(i).getUuidSection();
                                String name = sections.get(i).getName();
                                if (!uuid.equals(uuidSection)) {
                        %>
                        <option value="<%=uuid%>"><%=name%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    </div>
                    <input type="hidden" class="form-control" id="author" name="author"
                           value="<%=resource.getAuthor()%>">
                    <input type="hidden" id="id_category" name="id_category"
                           value="<%=resource.getCategory_link()%>">
                    <input class="form-control" type="hidden" name="desc" id="desc"
                           value="<%=resource.getDescriptionResource()%>">
                    <button type="submit" class="btn btn-lg sun-flower-button btn-block">
                        Добавить
                    </button>
                </form>
            </div>

        </div>
    </div>
</div>
<div id="myModal1" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Вы действительно хотите удалить?</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/resourceremoving">
                    <input type="hidden" name="uuidSectionDel" value="<%=uuidSection%>">
                    <input type="hidden" name="uuidCourseDel" value="<%=uuidCourse%>">
                    <input type="hidden" name="uuidResourceDel" value="<%=uuidResource%>">
                    <button type="submit" class="btn btn-lg sun-flower-button btn-block">
                        Удалить
                    </button>
                </form>
            </div>

        </div>
    </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="/resources/js/jQuery.headroom.min.js"></script>
<script>
    $(function () {
        $("#btn2").click(function () {
            $("#myModal2").modal('show');
        });
    });
    $(function () {
        $("#btn1").click(function () {
            $("#myModal1").modal('show');
        });
    });
</script>
</body>

</html>