# Money Transfer Api

## play-java-payment application v2.6 + H2 DB + Jooq (Play JPA implementation)

This is a starter application that has base setup of Play 2.6 with H2 as database. This repository will constantly updated on better changes.

Please see the documentation at https://www.playframework.com/documentation/latest/Home for more details.

## Running

### Running server in console

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project then you'll find a prepackaged version of sbt in the project directory:

```
sbt clean run
```

And then go to http://localhost:9000 to see the running web application.

### Running as Standalone server

#### Using tar payment-api-1.0.tgz
1) Download the payment-api-1.0.tgz file available in the root of the project. If you clone this repo you will get inside the payment-api main directory.
2) Extract the tar file in a folder of your preference.

```
tar -xvzf /path/to/payment-api-1.0.tgz
```

3) You can either navigate to the extracted folder or execute the app from the folder you are.

```
payment-api-1.0/bin/payment-api -Dplay.http.secret.key=omjigupta
```
#### Using zip payment-api-1.0.zip
1) Download the payment-api-1.0.zip file available in the root of the project.
2) Extract the zip file in a folder of your preference.
```
unzip payment-api-1.0.zip 
```
3) Navigate to the extracted folder or execute below command to run it
```
sh bin/payment-api
```

## Testing in console

By running below command. You can check the test results in the terminal

```
sbt test
```

![TestResult](https://github.com/omjigupta/payment-api/blob/master/screenshots/test_cases_result.png)

### Requirements:
1) Java JDK 1.8
2) Scala SBT 1.2.8

### Currency Support:
1) Currently, Application is supporting only INR, USD and EUR.
2) Currency conversion values are hardcoded for now. Conversion rates added from [exchange](https://www.xe.com/currencyconverter/).

## Endpoints:

### (GET) Ping - "About me" and application start request
```
http://localhost:9000/about
```
By Hitting above request you will be redirected to my LinkedIn profile

```
http://localhost:9000/
```
By Hitting this request you can check application is running or not


### (GET) Get the Balance of Specific Account
```
http://localhost:9000/v1/accounts/424800/balance
```
![balance](https://github.com/omjigupta/payment-api/blob/master/screenshots/balance_api.png)

### (GET) Checking Balance if Account does not Exist
```
http://localhost:9000/v1/accounts/1234/balance
```
![wrongAccount](https://github.com/omjigupta/payment-api/blob/master/screenshots/balance_wrongAccount.png)

### (POST) Transfers money between same currency accounts
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":324800,
"receiverAccountId":533000,
"amount":10,
"currency":"INR"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/transfer_between_same_currency.png)

### (POST) Transfers money between cross currency accounts
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":124800,
"receiverAccountId":533000,
"amount":1,
"currency":"EUR"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/transfer_money_between_different_currency.png)

### (POST) Transfers money between cross currency accounts when given amount in different currency
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":124800,
"receiverAccountId":533000,
"amount":1,
"currency":"USD"
}
```
* HTTP Response:
You can see selected part of image.

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/transferamount_currency_is_different_from_accounts.png)

### (POST) Transfers money between cross currency accounts if Sender doesn't have enough fund
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":124800,
"receiverAccountId":533000,
"amount":15,
"currency":"EUR"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/notEnoughBalance.png)

### (POST) Transfers Negative money between cross currency accounts
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":124800,
"receiverAccountId":533000,
"amount":-1,
"currency":"EUR"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/NegativeBalanceTest.png)

### (POST) Transfers Negative money between cross currency accounts
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":324800,
"receiverAccountId":533000,
"amount":10,
"currency":"YEN"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/wrongCurrency.png)

### (POST) Transfers money when account doesn't exist
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":3200,
"receiverAccountId":533000,
"amount":10,
"currency":"USD"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/wrongaccount.png)

