
<%@ page import="java.util.List" %>
<%@ page import="com.travelport.projectone.entities.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Client CRUD Result</title>
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
    <table>
        <tr>
            <th>Client ID</th>
            <th>Client Name</th>
            <th>Client Address</th>
        </tr>
        <%
            List<Client> clients = (List<Client>) request.getAttribute("clients");
            for (Client client : clients) {
        %>
        <tr>
            <td><%= client.getId() %></td>
            <td><%= client.getName() %></td>
            <td><%= client.getAddress() %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>