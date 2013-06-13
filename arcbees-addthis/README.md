GWT AddThis Widget
==============

Dependencies
------------
- Include this Maven Dependency (OSS sonatype)
    ```xml
    <dependency>
          <groupId>com.arcbees</groupId>
          <artifactId>addthis</artifactId>
          <version>1.0-SNAPSHOT</version>
    </dependency>
    ```
Inherits this way in your *.gwt.xml
    `<inherits name="com.google.gwt.inject.Inject"/>`

Install in your application
---------------------------

To put in your Gin Module :

`install(new AddThisModule()); `
`bindConstant().annotatedWith(AddThisPubId.class).to("YOUR_ADDTHIS_PUBID");`

Usage
-----
Create an object AddThisFactory
    `AddThisFactory addThisFactory;`
and then choose the url you want to share :
   `addThisFactory.createShare(url)`

Optionnaly, you can also set a title and a summary :
   `addThisFactory.createShare(url, "TITLE", "SUMMARY") `
