
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Product CRUD</title>
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
    <h2>CREATE</h2>
    <form action="/productCrudResult" method="post">
        <label for="productNameCreate">Product Name:</label>
        <input type="text" id="productNameCreate" name="productName">
        <label for="productCodeCreate">Product Code:</label>
        <input type="text" id="productCodeCreate" name="productCode">
        <input type="submit" value="Create">
    </form>
</div>

<div>
    <h2>READ</h2>
    <form action="/productCrudResult" method="post">
        <label for="productIdRead">Product ID:</label>
        <input type="text" id="productIdRead" name="productId">
        <input type="submit" value="Search">
    </form>
</div>

<div>
    <h2>UPDATE</h2>
    <form action="/productCrudResult" method="post">
        <label for="productIdUpdate">Product ID:</label>
        <input type="text" id="productIdUpdate" name="productId">
        <label for="productNameUpdate">Product Name:</label>
        <input type="text" id="productNameUpdate" name="productName">
        <label for="productCodeUpdate">Product Code:</label>
        <input type="text" id="productCodeUpdate" name="productCode">
        <input type="submit" value="Update">
    </form>
</div>

<div>
    <h2>DELETE</h2>
    <form action="/productCrudResult" method="post">
        <label for="productIdDelete">Product ID:</label>
        <input type="text" id="productIdDelete" name="productId">
        <input type="submit" value="Delete">
    </form>
</div>

</body>
</html>