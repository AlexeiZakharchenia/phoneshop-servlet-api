<%@ attribute name="query" required="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="linkName" required="true" %>


<a href="${pageContext.servletContext.contextPath}/products?sort=${sort}&order=${order}&query=${query}">${linkName}</a>