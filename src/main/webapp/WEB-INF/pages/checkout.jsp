<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>

<tags:master pageTitle="Checkout">
    <p>
        Checkout
    </p>
    <table>
        <thead>
        <tr>
            <td>
                Image
            </td>
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
                        ${cartItem.quantity}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>

        </c:forEach>
        <tr>
            <td colspan="3" style="text-align: right">Total</td>
            <td>$${cart.totalPrice}</td>
        </tr>
    </table>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <p>
            <label for="name">Name:</label>
            <input id="name" name="name" value="${param.name}"/>
            <c:if test="${not empty nameError}">
                <span style="color: red">${nameError}</span>
            </c:if>
        </p>

        <p>
            <label for="address">Address:</label>
            <input id="address" name="address" value="${param.address}"/>
            <c:if test="${not empty addressError}">
                <span style="color: red">${addressError}</span>
            </c:if>
        </p>
        <p>
            Delivery mode:
            <select name="deliveryMode">
                <c:forEach items="${deliveryModes}" var="deliveryMode">
                    <option name="${deliveryMode}">${deliveryMode}($${deliveryMode.deliveryCost})</option>
                </c:forEach>
            </select>
            <c:if test="${not empty deliveryModeError}">
                <span style="color: red">${deliveryModeError}</span>
            </c:if>
        </p>
        <p>
            Pay method:
            <select name="paymentMethod">
                <c:forEach items="${paymentMethods}" var="paymentMethod">
                    <option name="${paymentMethod}">${paymentMethod}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            Enter date:
            <input type="date" name="deliveryDate" value="${param.deliveryDate}">
            <c:if test="${not empty deliveryDateError}">
            <span style="color: red">${deliveryDateError}</span>
            </c:if>
        <p>
            <button>Place Order</button>
        </p>

    </form>


</tags:master>