# Using JCEF (Java Chromium Embedded Framework)

To use the Java Chromium Embedded Framework (JCEF) in your project, follow these steps:

## Step 1: Update Java version

Make sure you are using Java 11 or higher, as JCEF is supported from this version onwards.

## Step 2: Add Maven dependency

Add the following Maven dependency to your project:
https://github.com/jcefmaven/jcefmaven

```xml
<dependency>
  <groupId>me.friwi</groupId>
  <artifactId>jcefmaven</artifactId>
  <version>110.0.25.1</version>
</dependency>
```

## Step 3: Add VM options

Add the following VM options to your project to work around a known bug in JOGL:
https://github.com/jzy3d/jogl/issues/12

```
--add-exports java.base/java.lang=ALL-UNNAMED --add-exports java.desktop/sun.awt=ALL-UNNAMED --add-exports java.desktop/sun.java2d=ALL-UNNAMED
```

## Step 4: JCEF configuration

Create an instance of the CefAppBuilder:


```Java
CefAppBuilder builder = new CefAppBuilder();
```
Configure the Builder instance according to your requirements.
The builder downloads the necessary binaries, you can also provide them yourself if you wish.

```Java
builder.setInstallDir(new File("jcef-bundle")); // Standardwert
builder.setProgressHandler(new ConsoleProgressHandler()); // Standardwert
builder.addJcefArgs("--disable-gpu"); // Nur ein Beispiel
builder.getCefSettings().windowless_rendering_enabled = true; // Default value - select OSR mode
```
Set an app handler. Do not use `CefApp.addAppHandler(...)` as it may cause problems on MacOSX:

```Java
builder.setAppHandler(new MavenCefAppHandlerAdapter(){
});
```
## Step 5: Add JCEF components

Add a JCEF component to your Swing window:

```Java
JFrame frame = new JFrame();
CefApp app = builder.build();
CefClient client = app.createClient();
CefBrowser browser = client.createBrowser("https://google.com", true, true);
frame.add(browser.getUIComponent());
frame.setVisible(true);
```
