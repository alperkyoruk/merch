
# E-commerce Backend

This project is an E-commerce backend developed using Spring Boot, adhering to SOLID principles. It provides the foundational backend functionalities required to support an E-commerce website.



## Features
- Product Management: CRUD operations for managing products.

- User Management: Authentication and authorization functionalities for users.

- Order Management: Handling order creation, modification, and retrieval.
-  Cart Management: Management of user shopping carts.
- Category Management: Management of Categories and subcategories.
- Address Management: Management of user addresses.
- Payment Management: To simulate payment.
- Rating Management: Ratings for the products.
- Vendor Management: Management of Vendors
## Tech Stack

* Spring Boot: Framework for building robust Java applications.
* Spring Security: Authentication and authorization.
* Spring Data JPA: Simplifies database access.
* Hibernate: Object-relational mapping for JPA.
* PostgreSQL: Database management system.
* JUnit: Unit testing framework.

  
## Production

**Use the project on your own**

```bash
  git clone https://github.com/alperkyoruk/merch.git
```

### Configure Database
- Configure your database connection
- edit application properties for your settings

## Run it Up
```bash
cd merch
mvn clean install
java -jar target/merch.jar
```
  
## API Usage

#### Get All Products

```http
  GET /api/products/getProducts
```

Gets all product from database.


#### Get Product

```http
  GET /api/products/getProductsById/?productId{id}
```

| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | Product id |

Gets a single product with the given id.

#### Add Product

```http
  POST /api/products/addProduct
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | Product Name |
| `description` | `string` | Product Description |
| `price` | `float` | Product Price |
| `stock` | `int` | Product Stock |
| `image` | `string` | Product Image |
| `discounted` | `boolean` | Product discounted? |
| `discountRate` | `float` | Product Discount rate |
| `categoryId` | `int` | Product CategoryId |
| `subcategoryId` | `int` | Product SubcategoryId |
| `vendorId` | `int` | Product vendorId |
| `rating` | `int` | Product rating |

Adds a new product

  
## Feedback

Any Feedback will be appreciated. Please contact me if you have any suggestions.

  
