
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Sell</title>
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
    <form action="/sellProduct" method="post">
        <label for="clientId">Client ID:</label>
        <input type="text" id="clientId" name="clientId" required>
        <label for="productId">Product ID:</label>
        <input type="text" id="productId" name="productId" required>
        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity" required>
        <input type="submit" value="Sell">
    </form>
</div>

</body>
</html>