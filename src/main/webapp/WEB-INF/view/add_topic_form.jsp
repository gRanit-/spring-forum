 <%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
 
 
 <form:form action="createTopic" method="POST" modelAttribute="topic">
 <table class="addPostTable">
        <tr>
            <td><label for="title">Topic</label></td>
            <td><form:input path="title" id="title" cssClass="form-control"/> </td>
        </tr>
        <tr>
        <td></td>
        <td><button type="submit">submit</button></td>
        </tr>
    </table>
    
    </form:form>