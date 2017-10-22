<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<b><label style="color:red">${errorMessage}</label></b>
<br/>
<b><label style="color:green">${successMessage}</label></b>
<br/>

<b> Registration: </b><br/>

<div class="container" style="width: 600px;">
    <c:url var="registerUser" value="/admin/register"/>
    <s:form action="${registerUser}" modelAttribute="person" method="post" class="form-horizontal" role="form">
        <fieldset>
            <div class="form-group">
                <label for="login" class="col-sm-2 control-label"><spring:message code="login"/></label>
                <div class="col-sm-10">
                    <s:input id="login" value="${person.getLogin()}" type="text" path="login" class="form-control"/>
                    <s:errors path="login" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label"><spring:message code="password"/></label>
                <div class="col-sm-10">
                    <s:input id="password" value="${person.getPassword()}" type="password" maxlength="25" path="password" class="form-control"/>
                    <s:errors path="password" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label"><spring:message code="name"/></label>
                <div class="col-sm-10">
                    <s:input id="name" value="${person.getName()}" type="text" maxlength="255" path="name" class="form-control"/>
                    <s:errors path="name" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="surname" class="col-sm-2 control-label"><spring:message code="surname"/></label>
                <div class="col-sm-10">
                    <s:input id="surname" value="${person.getSurname()}" type="text" maxlength="255" path="surname" class="form-control"/>
                    <s:errors path="surname" cssStyle="color: red"/><br/>
                </div>
            </div>
            <div class="form-group">
                <label for="position1" class="col-sm-2 control-label"><spring:message code="position"/></label>
                <div class="col-sm-10">
                    <select id="position1" class="form-control" path="position" name="position" path="position">
                        <option value="JUNIOR">JUNIOR</option>
                        <option value="MIDDLE">MIDDLE</option>
                        <option value="SENIOR">SENIOR</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit"><spring:message code="save"/></button>
                </div>
            </div>
        </fieldset>
    </s:form>
</div>

<b> Search: </b><br/>

<div class="container" style="width: 600px;">
    <c:url var="multisearch" value="/admin/multilist"/>
    <form method="get" action="${multisearch}" class="form-horizontal">
        <div class="form-group">
            <label for="position2" class="col-sm-2 control-label">position:</label>
            <div class="col-sm-10">
                <select id="position2" class="form-control" name="position" path="position">
                    <option value="NONE" label="--- NONE ---" />
                                    <option value="JUNIOR">JUNIOR</option>
                                    <option value="MIDDLE">MIDDLE</option>
                                    <option value="SENIOR">SENIOR</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="surname" class="col-sm-2 control-label">surname:</label>
            <div class="col-sm-10">
                <input id="surname" type="text" maxlength="30" name="surname" path="surname" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10">
                <button class="btn btn-default" type="submit">Extended Search</button>
            </div>
        </div>
    </form>
    <c:url var="search" value="/admin/list"/>
    <form method="get" action="${search}" class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-10">
                <spring:message code="searchText"/>
            </div>
        </div>
        <div class="form-group">
            <label for="sorting" class="col-sm-2 control-label">sorting type:</label>
            <div class="col-sm-10">
                <select id="sorting" class="form-control" name="sorting" path="sorting">
                    <option value="NONE" label="--- NONE ---" />
                                    <option value="SURNAME">Surname Sorting</option>
                                    <option value="POSITION">Position Sorting</option>
                                    <option value="EXPERIENCE">Experience Sorting</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10">
                <button class="btn btn-default" type="submit">Search</button>
            </div>
        </div>
    </form>
</div>

<c:if test="${not empty position and position ne null and not empty persons and persons ne null and fn:length(persons) gt 0}">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Person Name</th>
            <th>Person Surname</th>
            <th>Person Login</th>
            <th>Person Position</th>
            <th>Person Experience (Days)</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${persons}" var="person">
            <tr>
                <td><c:out value="${person.name}"/></td>
                <td><c:out value="${person.surname}"/></td>
                <td><c:out value="${person.login}"/></td>
                <td><c:out value="${person.position}"/></td>
                <fmt:parseNumber var = "experience" integerOnly = "true"
                                 type = "number" value = "${(currentDate.getTime() - person.getExperience().getTime()) / parseNumber}" />
                <td><c:out value="${experience}"/></td>
                <td><a href="/admin/edit/${person.id}"><spring:message code="edit"/></a></td>
                <td><a href="/admin/delete/${person}"><spring:message code="delete"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:url var="first" value="/admin/multilist/${position};${surname}"/>
    <a href="${first}" class="btn btn-primary" role="button">First</a>

    <c:url var="next" value="/admin/multilist/${position};${surname}/${nextPage}"/>
    <a href="${next}" class="btn btn-primary" role="button">Next</a>
</c:if>

<c:if test="${empty position and position eq null and not empty persons and persons ne null and fn:length(persons) gt 0}">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Person Name</th>
            <th>Person Surname</th>
            <th>Person Login</th>
            <th>Person Position</th>
            <th>Person Experience (Days)</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${persons}" var="person">
            <tr>
                <td><c:out value="${person.name}"/></td>
                <td><c:out value="${person.surname}"/></td>
                <td><c:out value="${person.login}"/></td>
                <td><c:out value="${person.position}"/></td>
                <fmt:parseNumber var = "experience" integerOnly = "true"
                                 type = "number" value = "${(currentDate.getTime() - person.getExperience().getTime()) / parseNumber}" />
                <td><c:out value="${experience}"/></td>
                <td><a href="/admin/edit/${person.id}"><spring:message code="edit"/></a></td>
                <td><a href="/admin/delete/${person.id}"><spring:message code="delete"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:url var="first" value="/admin/list/${sorting}"/>
    <a href="${first}" class="btn btn-primary" role="button">First</a>

    <c:url var="next" value="/admin/list/${sorting}/${nextPage}"/>
    <a href="${next}" class="btn btn-primary" role="button">Next</a>
</c:if>
