<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>
<tags:master pageTitle="Order Overview">
    <p>
        Order Owerview
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
        <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
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
            <td>$${order.orderTotal}</td>
        </tr>
    </table>
    <p>
        Name:
        <br>
            ${order.name}
    </p>

    <p>
        Address:
        <br>
            ${order.address}
    </p>
    <p>
        Delivery mode:
        <br>
            ${order.deliveryMode}
    </p>


</tags:master>