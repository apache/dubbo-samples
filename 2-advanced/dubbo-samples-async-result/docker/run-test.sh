#!/bin/bash
set -e

PROJECT_ROOT=$(cd "$(dirname "$0")/.." && pwd)

echo ">>> Building project..."
mvn clean package -pl 2-advanced/dubbo-samples-async-result -am -DskipTests

echo ">>> Starting Docker containers..."
cd "$PROJECT_ROOT/docker"
docker-compose up --build -d

echo ">>> Waiting for services to start..."
sleep 15

echo ">>> Running async result test..."
docker logs dubbo-samples-async-result-consumer | tee consumer.log

echo ">>> Checking for success keyword in logs..."
if grep -q "Async result received" consumer.log; then
  echo "✅ Async result sample ran successfully."
  exit 0
else
  echo "❌ Async result test failed."
  exit 1
fi

echo ">>> Cleaning up..."
docker-compose down
