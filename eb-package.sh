rm -rf package
mkdir package
mvn package
cp target/status.jar package
cp etc/Procfile package
cp etc/Buildfile package
zip package
