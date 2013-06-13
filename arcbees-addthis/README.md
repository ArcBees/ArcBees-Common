GWT AddThis Widget
==============

Dependencies
------------
- Include this Maven Dependency (OSS sonatype)


- Inherits this way in your *.gwt.xml

```java
<inherits name="com.arcbees.addthis"/>
```

Install in your application
---------------------------

To put in your Gin Module :
```java
install(new AddThisModule());
bindConstant().annotatedWith(AddThisPubId.class).to("YOUR_ADDTHIS_PUBID");
```

Usage
-----
Create an object AddThisFactory
```java
AddThisFactory addThisFactory;
```

and then choose the url you want to share 
```java
addThisFactory.createShare("YOUR_URL");
```

Optionnaly, you can also set a title and a summary :
```java
addThisFactory.createShare(url, "TITLE", "SUMMARY")
```
