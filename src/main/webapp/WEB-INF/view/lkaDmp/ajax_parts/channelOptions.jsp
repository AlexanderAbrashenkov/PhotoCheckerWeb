<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 26.05.2017
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="channelList" type="java.util.List<com.photochecker.model.common.Channel>" scope="request"></jsp:useBean>

<option disabled selected data-value="nothing"> -- выберете канал -- </option>
<c:forEach var="channel" items="${channelList}">
    <option data-value="${channel.id}">${channel.name}</option>
</c:forEach>