<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="productComments" type="java.util.ArrayList" scope="request"/>
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
    <form method="post" action="${pageContext.servletContext}/products/${products.id}">
        <p>
            <input name="quantity" value="${not empty param.quantity? param.quantity : 1 }" style="text-align: right">
            <button>Add to cart</button>
            <c:if test="${not empty error}">
                <br><span style="color: red">${error}</span>
            </c:if>
        </p>
    </form>
    Leave feedback:
    <form method="post" action="${pageContext.servletContext.contextPath}/products/createProductComment/${products.id}>
        <p>
            <label for=" name
    ">Name:</label>
    <input id="name" name="name" value="${param.name}"/>
    <c:if test="${not empty nameError}">
        <span style="color: red">${nameError}</span>
    </c:if>
    </p>

    <p>
        <label for="rating">Rating(1-5):</label>
        <input id="rating" name="rating" value="${param.rating}"/>
        <c:if test="${not empty ratingError}">
            <span style="color: red">${ratingError}</span>
        </c:if>
    </p>
    <p>
        <label for="commentText">CommentText:</label>
        <input id="commentText" name="commentText" value="${param.commentText}"/>
        <c:if test="${not empty commentTextError}">
            <span style="color: red">${commentTextError}</span>
        </c:if>
    </p>
    <p>
        <button>POST</button>
    </p>

    </form>

    <h4>Comments</h4>
    <table>
        <c:forEach var="comment" items="${productComments}">
            <tr>
                <td>
                    Name:${comment.name} Rating:${comment.rating} Comment:${comment.commentText}
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
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                <br>
            </td>
            </th>
        </c:forEach>

        </thead>
    </table>
</tags:master>