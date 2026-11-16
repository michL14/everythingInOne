#!/bin/bash

echo "🧹 Cleaning Gradle build..."
./gradlew clean

echo "⚡ Generating SQLDelight code..."
./gradlew generateSqlDelightInterface

echo "Build Ressources"
./gradlew build

echo "🗑️ Deleting local DB files (desktop)..."
DB_FILE="app.db"
if [ -f "$DB_FILE" ]; then
  rm "$DB_FILE"
  echo "✅ Deleted: $DB_FILE"
else
  echo "ℹ️ No DB file found"
fi

echo "📱 Clearing Android app data..."
adb shell pm clear org.michorga.everythinginone || true

echo "🍏 Uninstalling iOS app..."
xcrun simctl uninstall booted org.michorga.everythinginone || true

echo "✅ Full reset complete!"
