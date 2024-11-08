
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Product Sales Result</title>
</head>
<body>

<div>
    <a href="/project_one_war/"><button>Home</button></a>
    <a href="/project_one_war/crudClient"><button>Client CRUD</button></a>
    <a href="/project_one_war/crudProduct"><button>Product CRUD</button></a>
    <a href="/project_one_war/sellProduct"><button>Sell</button></a>
    <a href="/project_one_war/clientSales"><button>Client Sales</button></a>
    <a href="/project_one_war/productSales"><button>Product Sales</button></a>
</div>

<div>
    <h1>Product Sales</h1>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Product Price</th>
            <th>Product Quantity</th>
            <th>Total Sales</th>
        </tr>
        <%
            List<Object[]> productSales = (List<Object[]>) request.getAttribute("productSales");
            for (Object[] productSale : productSales) {
        %>
        <tr>
            <td><%= productSale[0] %></td>
            <td><%= productSale[1] %></td>
            <td><%= productSale[2] %></td>
            <td><%= productSale[3] %></td>
            <td><%= productSale[4] %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>