# Money Transfer Api


# play-java-payment application v2.6 + H2 DB + Jooq (Play JPA implementation)

This is a starter application that has base setup of Play 2.6 with H2 as database. This repository will constantly updated on better changes.

Please see the documentation at https://www.playframework.com/documentation/latest/Home for more details.

## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project then you'll find a prepackaged version of sbt in the project directory:

```
sbt clean run
```

And then go to http://localhost:9000 to see the running web application.

## Testing

By running below command. You can now check the test results in the terminal

```
sbt test
```

![TestResult](https://github.com/omjigupta/payment-api/blob/master/screenshots/test_cases_result.png)

### Requirements:
1) Java JDK 1.8
2) Scala SBT 1.2.8

## Endpoints:

### (GET) Get the Balance of Specific Account
![balance](https://github.com/omjigupta/payment-api/blob/master/screenshots/balance_api.png)
### (GET) Checking Balance if Account does not Exist
![wrongAccount](https://github.com/omjigupta/payment-api/blob/master/screenshots/balance_wrongAccount.png)
### (POST)Transfers money between two accounts
![transfer](https://github.com/omjigupta/payment-api/blob/master/screenshots/transfer_money_1.png)
