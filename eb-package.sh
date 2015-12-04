rm -rf package
rm package.zip
mkdir package
mvn package
cp target/status.jar package
cp etc/Procfile package
cp etc/Buildfile package
cp *.yml package
cd package
zip package *
mv package.zip ..
cd ..
