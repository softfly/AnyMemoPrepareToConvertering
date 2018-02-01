echo off
title AnyMemoPrepareToConvertering
set cp=%~dp0%AnyMemoPrepareToConvertering-0.0.1-SNAPSHOT-jar-with-dependencies.jar
java -jar %cp% %1
::pause