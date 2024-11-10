
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Client CRUD</title>
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
    <h1>Create Client</h1>
    <form id="createClientForm" method="post">
        <label for="clientNameCreate">Client Name:</label>
        <input type="text" id="clientNameCreate" name="clientName">
        <label for="clientNifCreate">Client Nif:</label>
        <input type="text" id="clientNifCreate" name="clientNif">
        <label for="clientAddressCreate">Client Address:</label>
        <input type="text" id="clientAddressCreate" name="clientAddress">
        <input type="button" value="Create" onclick="submitCreateClientForm()">
    </form>
    <script>
        function submitCreateClientForm() {
            const name = document.getElementById('clientNameCreate').value;
            const nif = document.getElementById('clientNifCreate').value;
            const address = document.getElementById('clientAddressCreate').value;
            fetch('client/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nif, address, name })
            }).then(response => {
                if (response.ok) {
                    alert('Client created successfully');
                } else {
                    alert('Error creating client');
                }
            });}
    </script>
</div>

<div>

    <h2>Read Client</h2>
    <form id="readClientForm" method="get">
        <label for="clientIdRead">Client ID:</label>
        <input type="text" id="clientIdRead" name="clientId">
        <input type="button" value="Search" onclick="submitReadClientForm()">
    </form>
    <div id="clientInfo"></div>
    <script>
        function submitReadClientForm() {
            const id = document.getElementById('clientIdRead').value;
            fetch(`/client/get/${id}`, {
                method: 'GET',
            }).then(response => response.json())
              .then(data => {
                  if (data) {
                      document.getElementById('clientInfo').innerHTML = `
                          <p>Client Nif: ${data.nif}</p>
                          <p>Client Name: ${data.name}</p>
                          <p>Client Address: ${data.address}</p>
                      `;
                  } else {
                      document.getElementById('clientInfo').innerHTML = '<p>Client not found</p>';
                  }
              }).catch(error => {
                  document.getElementById('clientInfo').innerHTML = '<p>Error fetching client information</p>';
              });
        }
    </script>
</div>

<div>
    <h2>UPDATE</h2>
    <form id="updateClientForm" method="post">
        <label for="clientIdUpdate">Client ID:</label>
        <input type="text" id="clientIdUpdate" name="clientId">
        <label for="clientNifUpdate">Client Nif:</label>
        <input type="text" id="clientNifUpdate" name="clientNif">
        <label for="clientNameUpdate">Client Name:</label>
        <input type="text" id="clientNameUpdate" name="clientName">
        <label for="clientAddressUpdate">Client Address:</label>
        <input type="text" id="clientAddressUpdate" name="clientAddress">
        <input type="button" value="Update" onclick="submitUpdateClientForm()">
    </form>
    <script>
        function submitUpdateClientForm() {
            const id = document.getElementById('clientIdUpdate').value;
            const nif = document.getElementById('clientNifUpdate').value;
            const name = document.getElementById('clientNameUpdate').value;
            const address = document.getElementById('clientAddressUpdate').value;
            fetch(`/client/update/${id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id, nif, name, address })
            }).then(response => {
                if (response.ok) {
                    alert('Client updated successfully');
                } else {
                    alert('Error updating client');
                }
            });
        }
    </script>
</div>

<div>
    <h2>DELETE</h2>
    <form id="deleteClientForm" method="post">
        <label for="clientIdDelete">Client ID:</label>
        <input type="text" id="clientIdDelete" name="clientId">
        <input type="button" value="Delete" onclick="submitDeleteClientForm()">
    </form>
    <script>
        function submitDeleteClientForm() {
            const id = Number(document.getElementById('clientIdDelete').value);
            fetch(`/client/delete/${id}`, {
                method: 'PATCH'
            }).then(response => {
                if (response.ok) {
                    alert('Client deleted successfully');
                } else {
                    alert('Error deleting client');
                }
            });
        }
    </script>
</div>

</body>
</html>