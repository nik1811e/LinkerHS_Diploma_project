<%@ page import="course.courses.Searching" %>
<%@ page import="entity.CourseEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Catalog</title>
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

    <%

        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";
        List<CourseEntity> userCourseList;
        if (cookieUtil.isFindCookie() && !request.getParameter("name").isEmpty()) {
            userCourseList = new Searching().searchCourse(request.getParameter("name"),request.getParameter("type"),request.getParameter("uuid"));
        } else {
            response.sendRedirect(urlRedirect);
            return;
        }
        assert userCourseList != null;
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
                           style="text-decoration: none">Подписчики</a></li>
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
            <h1>Результат поиска</h1>
        </div>
        <div class='mh-div'></div>
        <!-- ENDS masthead -->

        <!-- posts list -->
        <div id="posts-list" class="clearfix" style="min-height: 600px">
            <%
                if (!userCourseList.isEmpty()) {
                    for (int i = 0; i < userCourseList.size(); i++) {
            %>
            <!-- Courses -->
            <article class="format-standard">
                <div class="entry-date">
                    <div class="number"><%=18%>
                    </div>
                    <div class="month"><%=03%></div>
                    <div class="year"><%=2018%></div>
                    <em></em></div>
                <div class="post-heading">
                    <h3>
                        <a href="course.jsp?uuidAuth=<%=request.getParameter("uuid")%>&&uuidCourse=<%=userCourseList.get(i).getUuid()%>"><%=userCourseList.get(i).getNameCourse()%>
                        </a></h3>
                    <div class="meta">
                        <span class="user"><%=userCourseList.get(i).getStatus().toUpperCase()%> | </span>
                        <span class="comments"><%=Objects.requireNonNull(MethodUtil.getCourseCategoryByid(userCourseList.get(i).getCategory())).getName().toUpperCase()%></span>
                    </div>
                </div>
                <div class="feature-image">
                    <a href="" data-rel="prettyPhoto">
                        <img src="/resources/img/course_def.jpg" alt="<%=userCourseList.get(i).getNameCourse()%>"/>
                    </a>
                </div>
                <div class="excerpt"
                     style="width: 480px"><%--<%=MethodUtil.getJsonCourseStructure(userCourseList.get(i).getUuid())%>--%>
                </div>
            </article>
            <%
                }
            %>
            <div class="page-navigation clearfix">
                <div class="nav-next"><a href="#">&#8592; Назад</a></div>
                <div class="nav-previous"><a href="#">Вперед &#8594;</a></div>
            </div>
            <%
            } else {
            %>
            <h2 class="text-center thin">Пусто</h2>
            <%}%>

        </div>
        <!-- ENDS posts list -->

        <!-- sidebar -->
        <aside id="sidebar">
            <ul>
                <li class="block" style="float: right">
                    <h4>Раздел с ресурсами</h4>
                    Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.
                    Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit
                    amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit
                    amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae,
                    ornare sit amet, wisi.
                </li>
            </ul>
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
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</body>
</html>