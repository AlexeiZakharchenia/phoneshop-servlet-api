<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink query="${param.query}" sort="description" order="asc" linkName="asc"/>
                <tags:sortLink query="${param.query}" sort="description" order="desc" linkName="desc"/>
            </td>
            <td>
                Price
                <tags:sortLink query="${param.query}" sort="price" order="asc" linkName="asc"/>
                <tags:sortLink query="${param.query}" sort="price" order="desc" linkName="desc"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td><a href="products/${product.id}"> ${product.description} </a></td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>

        </c:forEach>
    </table>
    <h4>Recently Viewed:</h4>
    <table>
        <thead>
        <c:forEach var="product" items="${recentlyViewed}">
            <th>
            <td align="center">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                <br>
                <a href="${pageContext.servletContext.contextPath}/products/${product.id}"> ${product.description} </a>
                <br>
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
                <br>
            </td>
            </th>
        </c:forEach>
        </thead>
</tags:master>