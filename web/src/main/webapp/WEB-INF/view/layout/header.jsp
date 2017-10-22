<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
  <nav class="navbar navbar-default">
    <div class="container" style="width: 700px;">
    <div class="container-fluid">
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">

          <sec:authorize access="hasAnyAuthority('USER','ADMIN')">
            <c:url var="edit" value="/person/afterLogin"/>
            <li><a href="${edit}"><spring:message code="main"/></a></li>
          </sec:authorize>

          <sec:authorize access="hasAnyAuthority('USER','ADMIN')">
            <c:url var="logout" value="/person/logout"/>
            <li><a href="${logout}"><spring:message code="logout"/></a></li>
          </sec:authorize>

          <%--<spring:message code="locale.change" var="changeLocale" htmlEscape="true"/>--%>
          <%--<c:url var="locale" value="?locale=${changeLocale}"/>--%>
          <%--<li><a href="${locale}"><spring:message code="locale.name"/></a></li>--%>
        </ul>
      </div>
    </div>
    </div>
  </nav>

</header>