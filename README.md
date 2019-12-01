# money-transfer-app-cf
money-transfer-app with Java Collection Framework as In memory storage


Framework: Quarkus
Database: Java Collection Framework as In Memory Database


How to run:
./mvnw clean install
java -jar target/money-transfer-app-cf-1.0-runner.jar 


<b>Wallet Resources</b>:

Base URL:<br />
http://localhost:8080/v1/wallets

GET "<i>/</i>"
-
Find all wallets
Success Response code: 200
Sample Success Response:
`
[
    {
        "phoneNumber": "9832799830",
        "balance": 3000
    },
    {
        "phoneNumber": "9832799831",
        "balance": 3000
    }
]
`

POST "<i>/wallet</i>"
-
Create a new Wallet
Sample Request:
`{
	"phoneNumber": "9832799828",
	"balance": 3000
}`
Response code: 200
Sample Success Response:
`{
    "status": "Created",
    "description": "Account registered successfully"
}`

Response code:400
Sample Request:
`{
	"phoneNumber": "9832799828",
	"balance": 0
}`
Response code: 200
Sample Success Response:
`{
    "errorMessage": "Amount Should be Positive",
    "statusCode": 400
}`


PUT "<i>/wallet/addmoney</i>"
-
Add some additional money to a specific wallet.


<b>Money Transfer Resources</b>:

Base URL:<br />
http://localhost:8080/v1/transactions

GET "<i>/</i>"
-
Find all money transfer transactions

GET "<i>/transaction/{id}/id</i>"
-
Find money transfer transaction by transactionID

GET "<i>/transaction/{sender}/sender</i>"
-
Find all money transfer transactions by a specific sender. Here {sender} is valid sender.

POST "<i>/transaction</i>"
-
A new transaction.
