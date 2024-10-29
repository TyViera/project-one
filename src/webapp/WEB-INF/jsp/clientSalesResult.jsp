
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Client Sales Result</title>
</head>
<body>

<div>
    <a href="/index"><button>Home</button></a>
    <a href="/crudClient"><button>Client CRUD</button></a>
    <a href="/crudProduct"><button>Product CRUD</button></a>
    <a href="/sellProduct"><button>Sell</button></a>
    <a href="/clientSales"><button>Client Sales</button></a>
    <a href="/productSales"><button>Product Sales</button></a>
</div>

<div>
    <h1>Client Sales Result</h1>
    <table>
        <tr>
            <th>Client ID</th>
            <th>Client Name</th>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
        </tr>
        <%
            List<Object[]> sales = (List<Object[]>) request.getAttribute("sales");
            for (Object[] sale : sales) {
        %>
        <tr>
            <td><%= sale[0] %></td>
            <td><%= sale[1] %></td>
            <td><%= sale[2] %></td>
            <td><%= sale[3] %></td>
            <td><%= sale[4] %></td>
            <td><%= sale[5] %></td>
            <td><%= sale[6] %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>