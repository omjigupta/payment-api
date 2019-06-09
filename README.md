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

1) Download the payment-api-0.1.tgz file available in the root of the project. If you clone this repo you will get inside the payment-api main directory.
2) Extract the tar file in a folder of your preference.

```
tar -xvzf /path/to/payment-api-0.1.tgz
```

3) You can either navigate to the extracted folder or execute the app from the folder you are.

```
payment-api-0.1/bin/payment-api -Dplay.http.secret.key=omjigupta
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

## Endpoints:

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

### (POST)Transfers money between two accounts
```
http://localhost:9000/v1/transactions
```
* HTTP Request:
```json
POST /v1/transactions HTTP/1.1
Accept: application/json
Host: localhost:9000

{
"senderAccountId":424800,
"receiverAccountId":124800,
"amount":1,
"currency":"USD"
}
```
* HTTP Response:

![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/transfer_money_1.png)
