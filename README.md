# A simple atm service for MahiMarkets by Eduard Schlotter
A simple service that uses a brute force approach to figure out if there are enough notes to withdraw. 

# Start-up instructions
It can be ran in intellij or through the command line. You will be prompted to enter how many £50 and £20 notes you would like to add to the system

You need to send a put request to localhost:8080/cash/deposit or localhost:8080/cash/withdraw. Postman works great for this.

The body should be a cash object in JSON such as:
{"currency":"£", "amount":30}

# Pre-requisites
- Java 11
- Maven
- Lombok (IntelliJ/Eclipse plugin for annotation processing in your local environment)

# Explanation
I only set myself a few hours but tried to make it easy to add more currencies or denominations in the future. 
It also checks if the currency sign is a pound one and throws an error if it isn't. I should have made the error more specific and not used a HTTP error.
The tests show a few scenarios and the capabilities of the service. 


# Major faults
- DB is a hash map, I was planning on using and h2 database but I didn't have the time
- The methods got too big at some point so some refactoring would have been nice and it's something I would do in a real life scenario.
- Should have mocked things to test controller class aswell with Mockito or something similar but ran out of time. I usually only use tdd for unit tests and write integration tests later.