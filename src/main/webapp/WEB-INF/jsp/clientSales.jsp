
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Client Sales</title>
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
    <form action="/clientSalesResult" method="post">
        <label for="clientId">Client ID:</label>
        <input type="text" id="clientId" name="clientId">
        <input type="submit" value="Search">
    </form>
</div>
</body>
</html>