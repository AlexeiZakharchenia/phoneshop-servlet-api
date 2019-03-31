<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Description">
    <p>
        Product Description.
    </p>
    <c:if test="${not empty param.message}">
        <br><span style="color: green">${param.message}</span>
    </c:if>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Image</td>
            <td>Description</td>
            <td>Stock</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>${products.id}</td>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ex..${products.imageUrl}">
            </td>
            <td>${products.description}</td>
            <td>${products.stock}</td>
            <td class="price">
                <fmt:formatNumber value="${products.price}" type="currency"
                                  currencySymbol="${products.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${products.id}">
        <p>
            <input name="quantity" value="${not empty param.quantity? param.quantity : 1 }" style="text-align: right">
            <button>Add to cart</button>
            <c:if test="${not empty error}">
                <br><span style="color: red">${error}</span>
            </c:if>
        </p>
    </form>
</tags:master>