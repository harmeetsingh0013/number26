# Number 26 Java Code Challenge

Application contains RESTFul web services that stores some transactions(in memory) and returns information about those transactions.

The transactions to be stored have a type and an amount. The service should support returning all
transactions of a type. Also, transactions can be linked to each other (using a "parent_id") and we
need to know the total amount involved for all transactions linked to a particular transaction. 


### In detail the api spec looks like the following:
##### PUT /transactionservice/transaction/$transaction_id
**BODY:**
```sh
{ 
    "amount":double,
    "type":string,
    "parent_id":long 
}
```
**Where:**
  - **transaction_id** is a long specifying a new transaction
  - **amount** is a double specifying the amount
  - **type** is a string specifying a type of the transaction.
  - **parent_id** is an optional long that may specify the parent transaction of this transaction.

##### GET /transactionservice/transaction/$transaction_id
**Returns:**
```sh
{ 
    "amount":double,
    "type":string,
    "parent_id":long 
}
```
##### GET /transactionservice/types/$type
**Returns:**
```sh
[ long, long, .... ]
```
A json list of all transaction ids that share the same type $type.

##### GET /transactionservice/sum/$transaction_id
**Returns:**
```sh
{ "sum", double }
```
A sum of all transactions that are transitively linked by their parent_id to $transaction_id.

### Response errors code and messages as following:

* 1001 - Transaction not found.
* 1002 - Parent Transaction not found.
* 1003 - Transactions not found.    
* 1004 - Transaction id already exists.

### Technologies
##### Following technologies are used:
* Java 8
* Spring MVC Rest Controller
* Maven
* JUnit
* Apache Tomcat 8.x