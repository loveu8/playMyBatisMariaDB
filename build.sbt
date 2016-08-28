name := """myFirstApp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mybatis" % "mybatis" % "3.4.1",
  "org.mybatis" % "mybatis-guice" % "3.8",
  "com.google.inject.extensions" % "guice-multibindings" % "4.0",
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.4.6"
)

// Compile the project before generating Eclipse files, so that .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)           

// Java project. Don't expect Scala IDE       
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java    

// Use .class files instead of generated .scala files for views and routes        
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources) 

// if you want to running compile , this "fork in run" must set false
fork in run := false

// Add app folder as resource directory so that mapper xml files are in the classpath
unmanagedResourceDirectories in Compile <+= baseDirectory( _ / "app" )
  
// but filter out java and html files that would then also be copied to the classpath
excludeFilter in Compile in unmanagedResources := "*.java" || "*.html"