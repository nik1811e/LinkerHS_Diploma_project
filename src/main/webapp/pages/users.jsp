<%@ page import="entity.AuthInfEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MailUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Users</title>

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" media="all" href="/resources/css/style.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
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
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css" media="screen">
    <link rel="stylesheet" media="screen" href="/resources/css/superfish.css"/>
    <script src="/resources/js/superfish-1.4.8/js/hoverIntent.js"></script>
    <script src="/resources/js/superfish-1.4.8/js/superfish.js"></script>
    <script src="/resources/js/superfish-1.4.8/js/supersubs.js"></script>
    <!-- ENDS superfish -->
    <!-- prettyPhoto -->
    <script src="/resources/js/prettyPhoto/js/jquery.prettyPhoto.js"></script>
    <link rel="stylesheet" href="js/prettyPhoto/css/prettyPhoto.css" media="screen"/>
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
    <link rel="stylesheet" media="all" href="/resources/css/paper.css"/>
    <%
        CookieUtil cookieUtil = new CookieUtil(request);
        String urlRedirect = "/pages/signin.jsp";
        List<AuthInfEntity> authInfEntityList = new ArrayList<>();
        if (cookieUtil.isFindCookie()) {
            try {
                authInfEntityList = MethodUtil.getUsersFromDb();
            } catch (Exception ex) {
                new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        } else {
            response.sendRedirect(urlRedirect);
        }
        assert authInfEntityList != null;
    %>
</head>

<body>

<header>
    <div class="wrapper clearfix">
        <div id="logo">
            <a href="/pages/index.jsp"><img src="/resources/img/l.png" alt="Helper Service"></a>
        </div>

        <!-- nav -->
        <ul id="nav" class="sf-menu sf-js-enabled sf-shadow">
            <li class="current-menu-item"><a href="#" style="text-decoration: none">ГЛАВНАЯ</a></li>
            <li><a href="#" style="text-decoration: none">КАТАЛОГ РЕСУРСОВ</a>
                <ul>
                    <li><a href="/pages/catalog.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>"
                           style="text-decoration: none">Мои
                        ресурсы</a></li>
                    <li><a href="" style="text-decoration: none">Избранное</a></li>
                </ul>
            </li>
            <li  class="current-menu-item"><a href="#" style="text-decoration: none">ПОЛЬЗОВАТЕЛИ</a>
                <ul>
                    <li><a href="/pages/users.jsp" style="text-decoration: none">Список пользователей</a></li>
                    <li><a href="/pages/following.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>" style="text-decoration: none">Подписки</a></li>
                    <li><a href="/pages/follower.jsp?uuidAuth=<%=cookieUtil.getUserUuidFromToken()%>" style="text-decoration: none">Подпищики</a></li>
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
            <h1>Ваши запросы</h1><span class="subheading">In Photography, Recent work</span>
        </div>
        <!-- posts list -->
        <div class="home-add clearfix" style="padding-bottom: 50px;padding-top: 75px">
            <div class="left-home-block home-posts" style="width: 100%; margin: 0">
                <%
                    for (AuthInfEntity auth : authInfEntityList) {
                %>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3"
                     style="  display: flex; flex-flow: row wrap; justify-content: center; text-align: center">
                    <div class="card card-user"
                         style="height: 100px;width: 100px; margin-bottom: 70px">
                        <div class="author" style="margin: 0;padding: 0;">
                            <img class="avatar border-white" src="/resources/img/avatar.png"
                                 alt="..." style="width: 100px;height: 100px"/>
                            <h5 class="title"><%=auth.getFName()%> <%=auth.getLName()%>
                                <br/>
                                <a href="/pages/profile.jsp?uuidAuth=<%=auth.getUuid()%>">
                                    <small style="color: #d3b34e">@<%=auth.getLogin()%>
                                    </small>
                                </a>
                            </h5>
                        </div>
                    </div>
                </div>
                <%}%>
            </div>
            <div id="fold"></div>
        </div>
    </div>
</div>
<!-- ENDS MAIN -->


<footer>

    <div class="wrapper clearfix">

        <!-- widgets -->
        <ul class="widget-cols clearfix">
            <li class="first-col">

                <div class="widget-block">
                    <h4>RECENT POSTS</h4>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post"/></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post"/></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                    <div class="recent-post clearfix">
                        <a href="#" class="thumb"><img src="img/dummies/54x54.gif" alt="Post"/></a>
                        <div class="post-head">
                            <a href="#">Pellentesque habitant morbi senectus</a><span> March 12, 2011</span>
                        </div>
                    </div>
                </div>
            </li>

            <li class="second-col">

                <div class="widget-block">
                    <h4>FREE TEMPLATES &amp; THEMES</h4>
                    <p>Visit <a href="http://templatecreme.com/">Template Creme</a> and browse the selection of
                        well-made FREE Templates and WordPress Themes.</p>
                </div>

            </li>

            <li class="third-col">

                <div class="widget-block">
                    <div id="tweets" class="footer-col tweet">
                        <h4>TWITTER WIDGET</h4>
                    </div>
                </div>

            </li>

            <li class="fourth-col">

                <div class="widget-block">
                    <h4>FREE TEMPLATES &amp; THEMES</h4>
                    <p>Visit <a href="http://templatecreme.com/">Template Creme</a> and browse the selection of
                        well-made FREE Templates and WordPress Themes.</p>
                </div>

            </li>
        </ul>
        <!-- ENDS widgets -->


        <!-- bottom -->
        <div class="footer-bottom">
            <div class="left">Simpler Template by <a href="http://www.luiszuno.com">luiszuno.com</a></div>
            <div class="right">
                <ul id="social-bar">
                    <li><a href="http://www.facebook.com" title="Become a fan" class="poshytip"><img
                            src="img/social/facebook.png" alt="Facebook"/></a></li>
                    <li><a href="http://www.twitter.com" title="Follow my tweets" class="poshytip"><img
                            src="img/social/twitter.png" alt="twitter"/></a></li>
                    <li><a href="http://www.google.com" title="Add to the circle" class="poshytip"><img
                            src="img/social/plus.png" alt="Google plus"/></a></li>
                </ul>
            </div>
        </div>
        <!-- ENDS bottom -->

    </div>
</footer>


</body>
</html>