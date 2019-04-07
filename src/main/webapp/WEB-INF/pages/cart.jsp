<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <p>
        Cart
    </p>
    <c:if test="${not empty errors}">
        <span style="color: red">Error updating cart</span>
    </c:if>
    <c:if test="${not empty param.message}">
        <br><span style="color: green">${param.message}</span>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td>
                    Quantity
                </td>
                <td>
                    Price
                </td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                <c:set var="product" value="${cartItem.product}"/>
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td>
                        <a href="products/${product.id}"> ${product.description} </a>
                    </td>
                    <td>
                        <input name="quantity"
                               value="${not empty paramValues.quantity[status.index]? paramValues.quantity[status.index]: cartItem.quantity}"
                               style="text-align: right"
                               style="text-align: right"/>
                        <input type="hidden" name="productId" value="${product.id}"/>
                        <c:if test="${not empty errors[status.index]}">
                            <br><span style="color: red">${errors[status.index]}</span>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </td>
                    <td>
                        <button formaction="${pageContext.servletContext.contextPath}/cart/deleteItem/${product.id}">
                            Delete
                        </button>
                    </td>
                </tr>

            </c:forEach>
            <tr>
                <td colspan="3" style="text-align: right">Total</td>
                <td>$${cart.totalPrice}</td>
            </tr>
        </table>
        <br>
        <button>Update</button>
    </form>
    <h4>Recently Viewed:</h4>
    <table>
        <thead>
        <c:forEach var="product" items="${recentlyViewed}">
            <th>
            <td align="center">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                <br>
                <a href="${pageContext.servletContext.contextPath}/${product.id}"> ${product.description} </a> <br>
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
                <br>
            </td>
            </th>
        </c:forEach>
        </thead>
    </table>
</tags:master>