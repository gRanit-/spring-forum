<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<c:forEach var="post" items="${posts}">
				<tr>
					<td>${post.author.email}</td>
					<td>${post.creationDate}</td>
					<td></td>
				</tr>
				<tr rowspan="2">
					<td colspan="3"
						style="white-space: -moz-pre-wrap !important; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; word-break: break-all; white-space: normal;">${post.text}</td>


				</tr>
</c:forEach>
