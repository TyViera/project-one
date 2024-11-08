
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Small Store - Product CRUD</title>
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
    <h1>Create Product</h1>
    <form id="createProductForm" method="post">
        <label for="productNameCreate">Product Name:</label>
        <input type="text" id="productNameCreate" name="productName">
        <label for="productCodeCreate">Product Code:</label>
        <input type="text" id="productCodeCreate" name="productCode">
        <input type="button" value="Create" onclick="submitCreateProductForm()">
    </form>
    <script>
        function submitCreateProductForm() {
            const productName = document.getElementById('productNameCreate').value;
            const productCode = document.getElementById('productCodeCreate').value;
            fetch('/product/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ productName, productCode })
            }).then(response => {
                if (response.ok) {
                    alert('Product created successfully');
                } else {
                    alert('Error creating product');
                }
            });
        }
    </script>
</div>

<div>
    <h2>Read Product</h2>
    <form id="readProductForm" method="get">
        <label for="productIdRead">Product ID:</label>
        <input type="text" id="productIdRead" name="productId">
        <input type="button" value="Search" onclick="submitReadProductForm()">
    </form>
    <div id="productInfo"></div>
    <script>
        function submitReadProductForm() {
            const productId = document.getElementById('productIdRead').value;
            fetch(`/product/get/${productId}`, {
                method: 'GET',
            }).then(response => response.json())
              .then(data => {
                  if (data) {
                      document.getElementById('productInfo').innerHTML = `
                          <p>Product Name: ${data.name}</p>
                          <p>Product Code: ${data.code}</p>
                      `;
                  } else {
                      document.getElementById('productInfo').innerHTML = '<p>Product not found</p>';
                  }
              }).catch(error => {
                  document.getElementById('productInfo').innerHTML = '<p>Error fetching product information</p>';
              });
        }
    </script>
</div>

<div>
    <h2>UPDATE</h2>
    <form id="updateProductForm" method="post">
        <label for="productIdUpdate">Product ID:</label>
        <input type="text" id="productIdUpdate" name="productId">
        <label for="productNameUpdate">Product Name:</label>
        <input type="text" id="productNameUpdate" name="productName">
        <label for="productCodeUpdate">Product Code:</label>
        <input type="text" id="productCodeUpdate" name="productCode">
        <input type="button" value="Update" onclick="submitUpdateProductForm()">
    </form>
    <script>
        function submitUpdateProductForm() {
            const productId = document.getElementById('productIdUpdate').value;
            const productName = document.getElementById('productNameUpdate').value;
            const productCode = document.getElementById('productCodeUpdate').value;
            fetch(`/product/update/${productId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ productName, productCode })
            }).then(response => {
                if (response.ok) {
                    alert('Product updated successfully');
                } else {
                    alert('Error updating product');
                }
            });
        }
    </script>
</div>

<div>
    <h2>DELETE</h2>
    <form id="deleteProductForm" method="post">
        <label for="productIdDelete">Product ID:</label>
        <input type="text" id="productIdDelete" name="productId">
        <input type="button" value="Delete" onclick="submitDeleteProductForm()">
    </form>
    <script>
        function submitDeleteProductForm() {
            const productId = document.getElementById('productIdDelete').value;
            fetch(`/product/delete/${productId}`, {
                method: 'PATCH',
            }).then(response => {
                if (response.ok) {
                    alert('Product deleted successfully');
                } else {
                    alert('Error deleting product');
                }
            });
        }
    </script>
</div>

</body>
</html>