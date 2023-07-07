## Steps to run the code:
- Go to the project root directory
- Build project using: `mvn clean install`
- Run the code by running the following command: `java -jar target/bitgo-transactions-jar-with-dependencies.jar`

## Note:
- I am committing the [transactions.json](src%2Fmain%2Fresources%2Ftransactions.json) file in the resources folder so once it's run, no API call is made to fetch the transaction's data. 
- If you need to check how data is fetched then remove the data of [transactions.json](src%2Fmain%2Fresources%2Ftransactions.json) file and fresh API calls would be made to get the data.
- I have hardcoded the hash of the **block number: 680000** in the HttpClient class, it needs to be updated if different block has to be checked.