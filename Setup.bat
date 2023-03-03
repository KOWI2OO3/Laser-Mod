@ECHO OFF
color 0a

TITLE Setup Workspace

:Menu
cls
SET /P M=Do you want to use Deamon? (true/false):
IF %M%==true goto :Deamon
IF %M%==false goto :Original

echo gradle.properties

:Deamon
echo # Sets default memory used for gradle commands. Can be overridden by user or command line properties. > gradle.properties
echo # This is required to provide enough memory for the Minecraft decompilation process. >> gradle.properties
echo org.gradle.jvmargs=-Xmx3G >> gradle.properties
echo org.gradle.daemon=true >> gradle.properties
goto :VersionMenu

:Original
echo # Sets default memory used for gradle commands. Can be overridden by user or command line properties. > gradle.properties
echo # This is required to provide enough memory for the Minecraft decompilation process. >> gradle.properties
echo org.gradle.jvmargs=-Xmx3G >> gradle.properties
goto :VersionMenu

:VersionMenu
cls
echo 1. 1.12.2 or lower
echo 2. 1.13 or higher
echo 3. compile
SET /P M=Wich Version do You Use?:
IF %M%== 1 goto :Main
IF %M%== 2 goto :Higher
IF %M%== 3 goto :compile

:Main
@ECHO ON
cls
gradlew setupDecompWorkspace && gradlew eclipse
pause

:Higher
@ECHO ON
cls
gradlew genEclipseRuns --refresh-dependencies && gradlew eclipse

:compile
@ECHO ON
cls
gradlew build
