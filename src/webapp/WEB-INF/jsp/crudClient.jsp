
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Client CRUD</title>
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
    <form action="/clientCrudResult" method="post">
        <label for="clientNameCreate">Client Name:</label>
        <input type="text" id="clientNameCreate" name="clientName">
        <label for="clientAddressCreate">Client Address:</label>
        <input type="text" id="clientAddressCreate" name="clientAddress">
        <input type="submit" value="Create">
    </form>
</div>

<div>
    <h2>READ</h2>
    <form action="/clientCrudResult" method="post">
        <label for="clientIdRead">Client ID:</label>
        <input type="text" id="clientIdRead" name="clientId">
        <input type="submit" value="Search">
    </form>
</div>

<div>
    <h2>UPDATE</h2>
    <form action="/clientCrudResult" method="post">
        <label for="clientIdUpdate">Client ID:</label>
        <input type="text" id="clientIdUpdate" name="clientId">
        <label for="clientNameUpdate">Client Name:</label>
        <input type="text" id="clientNameUpdate" name="clientName">
        <label for="clientAddressUpdate">Client Address:</label>
        <input type="text" id="clientAddressUpdate" name="clientAddress">
        <input type="submit" value="Update">
    </form>
</div>

<div>
    <h2>DELETE</h2>
    <form action="/clientCrudResult" method="post">
        <label for="clientIdDelete">Client ID:</label>
        <input type="text" id="clientIdDelete" name="clientId">
        <input type="submit" value="Delete">
    </form>
</div>

</body>
</html>