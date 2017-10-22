<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container" style="width: 600px;">
<c:url var="login" value="/person/login"/>
<form action="${login}" method="post" class="form-horizontal" role="form">
    <fieldset>
        <div class="form-group">
        <c:if test="${param.status=='error'}">
            <label style="color:red">Invalid login or password!</label>
        </c:if>
        </div>
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label"><spring:message code="login"/></label>
            <div class="col-sm-10">
                <input id="login" name="login" type="text" path="login" class="form-control"/><br/>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label"><spring:message code="password"/></label>
            <div class="col-sm-10">
                <input id="password" name="password" type="password" maxlength="30" path="password" class="form-control"/><br/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit"><spring:message code="singUp"/></button>
            </div>
        </div>
    </fieldset>
</form>
</div>
