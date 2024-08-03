# TO DO
# add comments
# add resilence4J
# add flyway migration tool

## 
To run web server execute following command:
linux:./gradlew bootRun
windows:gradlew.bat bootRun

To perform tests execute following command:
linux:./gradlew test
windows:gradlew.bat test

## API usage
1. Start web server
2. Check server logs for security password, you should be able to see following lines in the logs:
Using generated security password: 7a9bf559-4f7d-47ea-9c39-93ce255a6e03
3. You can test api using curl command, replace password_here  with one that is in your logs
curl -u user:password_here -i -H 'Accept:application/json' http://localhost:8082/users/octocat
e.g in my case curl command will look like following:
curl -u user:7a9bf559-4f7d-47ea-9c39-93ce255a6e03 -i -H 'Accept:application/json' http://localhost:8082/users/octocat
