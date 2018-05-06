<%--suppress ALL --%>
<%@ page import="course.pojo.ResourceTO" %>
<%@ page import="course.pojo.SectionTO" %>
<%@ page import="course.resources.ResourceInformation" %>
<%@ page import="course.sections.SectionInformation" %>
<%@ page import="entity.ResourceCategoryEntity" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="util.MethodUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8"/>
    <title>Helper Service | Sections</title>
    <!--[if lt IE 9]><![endif]-->
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

        List<ResourceTO> resourceTOList = null;
        List<ResourceCategoryEntity> resourceCategoryList = null;
        String uuidCourse = String.valueOf(request.getParameter("uuidCourse").trim());
        String uuidSection = String.valueOf(request.getParameter("uuidSection")).trim();
        SectionTO sectionInformation = new SectionInformation().getSectionInformation(uuidCourse, uuidSection);
        if (cookieUtil.isFindCookie()) {
            resourceTOList = new ResourceInformation().getSectionResource(String.valueOf(request.getParameter("uuidSection").trim()),
                    uuidCourse);
            resourceCategoryList = MethodUtil.getResourceCategory();
        } else {
            response.sendRedirect(urlRedirect);
        }
        assert resourceTOList != null;
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

<div id="main">
    <div class="wrapper clearfix">

        <!-- home-block -->
        <div class="home-block clearfix">
            <div class="masthead clearfix">
                <div id="posts-list">
                    <article class="format-standard" style="padding-right: 80px;">
                        <div class="post-heading">
                            <h3>
                                <%=sectionInformation.getName()%><br>
                            </h3>
                            <a href="#removeSection" id="btnSectionRemove" class="btn-modal"
                                     style="background-color: #b1766a;font-size: 12px;width: 100px;height: 30px;text-align: center; padding: 11px; margin: 10px; display: inline-block; text-decoration: none">Удалить раздел</a>
                            <br>
                            <div class="meta">
                                <span class="user"><%=Objects.requireNonNull(MethodUtil.getCourseNameByUuid(sectionInformation.getUuidCourse())).toUpperCase()%> |</span>
                                <span class="comments"><%=sectionInformation.getDateLastUpdate().toUpperCase()%></span>
                            </div>
                        </div>
                        <div class="feature-image" style="width: 100%;height: 60%; ">
                            <a href="" data-rel="prettyPhoto">
                                <img src="/resources/img/slides/01.jpg"
                                     alt="<%=sectionInformation.getName()%>"/>
                            </a>
                        </div>
                        <div class="excerpt">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam malesuada
                            neque non mi maximus
                            mattis. Etiam nulla turpis, placerat sed turpis a, malesuada interdum nibh. Vivamus
                            tristique,
                            diam vitae tincidunt scelerisque, est ligula dictum mauris, in suscipit orci nunc ut massa.
                            Cras
                            sagittis nisl blandit leo facilisis accumsan. Maecenas egestas, ante ac finibus mollis,
                            velit
                            nulla faucibus odio, quis egestas massa dolor in arcu. Lorem ipsum dolor sit amet,
                            consectetur
                            adipiscing elit. Cras tincidunt vel dui et pharetra. Maecenas odio risus, ultrices
                            sollicitudin
                            tempor sed, pulvinar id mi. Suspendisse at magna sit amet velit lobortis tempor. Suspendisse
                            potenti.
                        </div>
                    </article>
                </div>
            </div>
            <div class='mh-div'></div>
            <h1 class="home-headline">Ресурсы</h1>
            <!-- thumbs -->
            <div class="clearfix">
                <p><a href="#myModal2" id="btn2" class="btn-modal" style="padding: 20px;font-size: 16px">Добавить
                    ресурс</a></p>

                <%
                    if (!resourceTOList.isEmpty()) {
                        assert resourceTOList != null;
                        for (ResourceTO aResourceTOList : resourceTOList) {
                            String uuidResource = aResourceTOList.getUuidResource();
                %>
                <figure>
                    <a href="/pages/resource.jsp?uuidAuth=<%=request.getParameter("uuidAuth")%>&&uuidCourse=<%=uuidCourse%>&&uuidSection=<%=uuidSection%>&&uuidResource=<%=uuidResource%>"
                       class="thumb"><img src="/resources/img/dummies/featured-1.jpg" alt="Alt text"/></a>
                    <figcaption>
                        <strong><%=aResourceTOList.getName()%>
                        </strong>
                        <span><%=aResourceTOList.getAuthor()%></span>

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
            </div>
            <!-- ENDS thumbs -->
        </div>
        <!-- Fold image -->
        <div id="fold"></div>
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
<div id="myModal2" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Добавление ссылки</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/resourcehandler">
                    <input type="hidden" name="uuidAuth" value="<%=request.getParameter("uuidAuth")%>">

                    <input type="hidden" name="uuidCourse" value="<%=request.getParameter("uuidCourse")%>">
                    <input type="hidden" name="uuidSection"
                           value="<%=request.getParameter("uuidSection")%>">
                    <div class="form-group">
                        <input type="text" class="form-control" id="name_resource" name="name_resource"
                               required
                               maxlength="50" placeholder="Название">
                    </div>
                    <div class="form-group">
                        <input type="url"
                               pattern="_(^|[\s.:;?\-\]<\(])(https?://[-\w;/?:@&=+$\|\_.!~*\|'()\[\]%#,☺]+[\w/#](\(\))?)(?=$|[\s',\|\(\).:;?\-\[\]>\)])_i"
                               class="form-control" id="link" name="link" required maxlength="70"
                               placeholder="Ссылка на ресурс">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="author" name="author" required
                               maxlength="70" placeholder="Автор ресурса">
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="id_category" name="id_category">
                            <option value="" disabled selected>Выберите категорию</option>
                            <%
                                assert resourceCategoryList != null;
                                for (int i = 0; i < resourceCategoryList.size(); i++) {
                                    int id = resourceCategoryList.get(i).getId();
                                    String name = resourceCategoryList.get(i).getName();

                            %>
                            <option value="<%=id%>"><%=name%>
                            </option>
                            <%}%>
                        </select>
                    </div>
                    <div class="form-group">
                                    <textarea class="form-control" type="textarea" name="desc" id="desc"
                                              placeholder="Описание" maxlength="6000" rows="7"></textarea>
                    </div>
                    <button type="submit" class="btn btn-lg sun-flower-button btn-block" id="btnContactUs">
                        Добавить
                    </button>
                </form>
            </div>

        </div>
    </div>
</div>
<div id="removeSection" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" style="color: #3A3A3A">Вы действительно хотите удалить раздел?</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post" action="/sectionremoving">
                    <input type="hidden" name="uuidCourse" value="<%=request.getParameter("uuidCourse")%>">
                    <input type="hidden" name="uuidAuth" value="<%=cookieUtil.getUserUuidFromToken()%>">
                    <input type="hidden" name="uuidSection" value="<%=request.getParameter("uuidSection")%>">
                    <button type="submit" class="btn-modal">
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
        $("#btnSectionRemove").click(function () {
            $("#removeSection").modal('show');
        });
    });
</script>
</body>

</html>