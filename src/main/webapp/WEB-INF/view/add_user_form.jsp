 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
 
 
 <form:form action="addUser" method="POST" modelAttribute="user">
 <table class="addUserTable">
        <tr>
            <td><label for="nick">nick</label></td>
            <td><form:input path="nick" id="nick" cssClass="form-control"/> </td>

        </tr>
        <tr>
            <td><label for="email">email</label></td>
            <td><form:input path="email" id="email" cssClass="form-control"/></td>
        </tr>

        <tr>
            <td><label for="password">password</label></td>
            <td><form:password path="password" id="password" class="form-control"/></td>
        </tr>
        <tr>
        <td></td>
        <td><button type="submit">submit</button></td>
        </tr>
    </table>
    
    </form:form>