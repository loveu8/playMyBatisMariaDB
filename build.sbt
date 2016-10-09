name := """myFirstApp"""

scriptClasspath := Seq("*")

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  // 增加mybatis , google Inject 與 mariadb 需要的jar檔
  "org.mybatis" % "mybatis" % "3.4.1",		
  "org.mybatis" % "mybatis-guice" % "3.8",
  "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.4.6",
  // Java寄信功能
  "javax.mail" % "javax.mail-api" % "1.5.6",
  "javax.mail" % "mail" % "1.4.7",
  // Spring Aop
  "org.springframework" % "spring-aop" % "4.2.4.RELEASE"
)

// Compile the project before generating Eclipse files, so that .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)           

// Java project. Don't expect Scala IDE       
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java    

// Use .class files instead of generated .scala files for views and routes        
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources) 

// if you want to running compile , this "fork in run" must set false
fork in run := false

// 以下這兩段必須要增加到sbt檔案，目的是要把我們mybaits的xml，編譯到classpath
// 讓myBaits知道要執行那個編譯過後的xml檔案
// Add app folder as resource directory so that mapper xml files are in the classpath
unmanagedResourceDirectories in Compile <+= baseDirectory( _ / "app" )
  
// but filter out java and html files that would then also be copied to the classpath
excludeFilter in Compile in unmanagedResources := "*.java" || "*.html"

routesGenerator := InjectedRoutesGenerator
