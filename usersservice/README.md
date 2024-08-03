# TO DO

# add comments

# add resilence4J

# add flyway migration tool

## General information

To run web server execute following command:</br>
linux:./gradlew bootRun</br>
windows:gradlew.bat bootRun</br>

To perform tests execute following command:</br>
linux:./gradlew test</br>
windows:gradlew.bat test</br>

## API usage

1. Start web server
2. Check server logs for security password, you should be able to see following lines in the logs:</br>
   Using generated security password: 7a9bf559-4f7d-47ea-9c39-93ce255a6e03
3. You can test api using curl command, replace password_here with one that is in your logs:</br>
   curl -u user:password_here -i -H 'Accept:application/json' http://localhost:8082/users/octocat</br>
   e.g in my case curl command will look like following:</br>
   curl -u user:7a9bf559-4f7d-47ea-9c39-93ce255a6e03 -i -H 'Accept:application/json' http://localhost:8082/users/octocat
