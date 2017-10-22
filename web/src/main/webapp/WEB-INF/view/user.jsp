<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<b><label style="color:red">${errorMessage}</label></b>
<br/>
<b><label style="color:green">${successMessage}</label></b>
<br/>

<div class="container" style="width: 600px;">
    <c:url var="editPage" value="/user/edit"/>
    <s:form action="${editPage}" modelAttribute="person" method="post" class="form-horizontal" role="form">
        <input type="hidden" name="personExperience" value="${person.getExperience().getTime()}" />
        <input type="hidden" name="personPosition" value="${person.getPosition()}" />
        <fieldset>
            <div class="form-group">
                <div class="col-sm-10">
                    <h3><spring:message code="editText"/></h3>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label"><spring:message code="name"/></label>
                <div class="col-sm-10">
                    <s:input id="name" type="text" value="${person.getName()}" maxlength="30" path="name" class="form-control"/>
                    <s:errors path="name" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="surname" class="col-sm-2 control-label"><spring:message code="surname"/></label>
                <div class="col-sm-10">
                    <s:input id="surname" type="text" value="${person.getSurname()}" maxlength="30" path="surname" class="form-control"/>
                    <s:errors path="surname" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="login" class="col-sm-2 control-label"><spring:message code="login"/></label>
                <div class="col-sm-10">
                    <s:input id="login" type="text" value="${person.getLogin()}" maxlength="30" path="login" class="form-control"/>
                    <s:errors path="login" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label"><spring:message code="password"/></label>
                <div class="col-sm-10">
                    <s:input id="password" type="password" value="${person.getPassword()}" maxlength="30" path="password" class="form-control"/>
                    <s:errors path="password" cssStyle="color: red"/><br/>
                </div>
            </div>

            <div class="form-group">
                <label for="experience" class="col-sm-2 control-label"><spring:message code="experience"/></label>
                <div class="col-sm-10">
                    <fmt:parseNumber var = "experience" integerOnly = "true"
                                     type = "number" value = "${(currentDate.getTime() - person.getExperience().getTime()) / parseNumber}" />
                    <s:label id="experience" maxlength="30" path="experience" class="form-control">${experience}</s:label>
                    <br/>
                </div>
            </div>
            <div class="form-group">
                <label for="position" class="col-sm-2 control-label"><spring:message code="position"/></label>
                <div class="col-sm-10">
                    <s:label id="position" maxlength="30" path="position" class="form-control">${person.getPosition()}</s:label>
                    <br/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit"><spring:message code="edit"/></button>
                </div>
            </div>
        </fieldset>
    </s:form>
</div>
