appengine-mail
==============
A simple App Engine Mail service interface.

A task to send your email will be pushed into App Engine's default queue.

##Creating an Email##
First, create an `Email` like this:
```java
Email email = EmailBuilder.to("batman@gotham.net").fromAddress("joker@gotham.net").fromPersonal("The Joker")
                  .subject("Why so serious?").body("Let's put a smile on that face!").build();

```

##Sending an Email##
appengine-mail gives you the flexibility of 3 different methods to send emails.

###Using EmailService###
`EmailService.send(email);`

###Using EmailSenderImpl###
```
EmailSender sender = EmailSenderImpl.buildDefault();

sender.send(email);
```

###Using Guice###
Install the provided module:
```java
public class YourModule extends AbstractModule {
    @Override
    protected void configure() {
        // ...
        install(new EmailModule());
    }
}
```
Inject and use:
```java
@Inject EmailSender emailSender;

emailSender.send(email);
```

##Maven Configuration
[Link to Maven Central](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.arcbees.appengine%22%20AND%20a%3A%22mail%22)

##Releases##
```
<dependency>
    <groupId>com.arcbees.appengine</groupId>
    <artifactId>mail</artifactId>
    <version>1.2</version>
</dependency>
```

##Snapshots##
```
<dependency>
    <groupId>com.arcbees.appengine</groupId>
    <artifactId>mail</artifactId>
    <version>1.3-SNAPSHOT</version>
</dependency>
```
