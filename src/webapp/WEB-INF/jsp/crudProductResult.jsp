
<%@ page import="java.util.List" %>
<%@ page import="com.travelport.projectone.entities.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Product CRUD Result</title>
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
    <h1>Product CRUD Result</h1>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Product Code</th>
        </tr>
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            for (Product product : products) {
        %>
        <tr>
            <td><%= product.getId() %></td>
            <td><%= product.getName() %></td>
            <td><%= product.getCode() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="/crudProduct"><button>Back</button></a>
</div>

</body>
</html>