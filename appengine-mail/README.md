appengine-mail
==============
A simple App Engine Mail service interface.

A task to send your email will be pushed into App Engine's default queue.

##Creating an Email##
First, create an `Email` like this:
```java
Email email = EmailBuilder.to("batman@gotham.net").fromAddress("joker@gotham.net")
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
[Link to Maven Central](http://search.maven.org/#artifactdetails%7Ccom.arcbees.appengine%7Cmail%7C1.0%7Cjar)

##Releases##
```
<dependency>
    <groupId>com.arcbees.appengine</groupId>
    <artifactId>mail</artifactId>
    <version>1.0</version>
</dependency>
```

##Snapshots##
```
<dependency>
    <groupId>com.arcbees.appengine</groupId>
    <artifactId>mail</artifactId>
    <version>1.1-SNAPSHOT</version>
</dependency>
```

[![githalytics.com alpha](https://cruel-carlota.gopagoda.com/90af60e86e56006cb47853038b538f4c "githalytics.com")](http://githalytics.com/ArcBees/appengine-mail)

