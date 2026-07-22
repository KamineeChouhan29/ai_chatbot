#!/bin/sh
echo "Searching for the Spring Boot JAR file..."
JAR_FILE=$(find . -name "*SNAPSHOT.jar" | head -n 1)

if [ -z "$JAR_FILE" ]; then
  echo "ERROR: NO JAR FILE FOUND! The build step did not generate a JAR."
  echo "Listing all files in current directory to debug:"
  ls -la
  exit 1
fi

echo "Found JAR file: $JAR_FILE"
echo "Starting Spring Boot..."
exec java -Xmx300m -jar "$JAR_FILE"
