#! /bin/bash

CURRENT_DIR=$(pwd)
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"
echo $(pwd)
cd ..
mkdir "$(pwd)/src/backend/microservices"
WORKING_DIR=$(pwd)/src/backend/microservices
cd "$WORKING_DIR"

# # clean up on failure
# function cleanup() {
#   echo "has errors"
#   rm -rf "$WORKING_DIR/"
# }
# trap cleanup EXIT

# exit immediately if any command failed
set -e

#### CORE SERVICES ####
QUIZ_SERVICE=quiz-service
QUESTION_SERVICE=question-service
ANALYTICS_SERVICE=analytics-service

ROOT_PKG=dev.agasen.microsrv.core

#### DEPENDENCIES ####
MAPSTRUCT_GRADLE_DEP="
ext {
    mapstructVersion = \"1.5.3.Final\"
}

dependencies {
  implementation \"org.mapstruct:mapstruct:\${mapstructVersion}\"
  compileOnly \"org.mapstruct:mapstruct-processor:\${mapstructVersion}\"
  annotationProcessor \"org.mapstruct:mapstruct-processor:\${mapstructVersion}\"
  testAnnotationProcessor \"org.mapstruct:mapstruct-processor:\${mapstructVersion}\"
}"
TESTCONTAINER_MONGODB_GRADLE_DEP="
dependencies {
  implementation platform('org.testcontainers:testcontainers-bom:1.17.6')
  testImplementation 'org.testcontainers:testcontainers'
  testImplementation 'org.testcontainers:junit-jupiter'
  testImplementation 'org.testcontainers:mongodb'
}"
TESTCONTAINER_MYSQL_GRADLE_DEP="
dependencies {
  implementation platform('org.testcontainers:testcontainers-bom:1.17.6')
  testImplementation 'org.testcontainers:testcontainers'
  testImplementation 'org.testcontainers:junit-jupiter'
  testImplementation 'org.testcontainers:mysql'
}"
FALSE_JAR_GRADLE="
jar {
    enabled = false
}"

echo "STARTING...."


##### MICROSERVICES ################################### 
## QUIZ SERVICE ####################################

spring init \
--boot-version=3.2.0 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name="$QUIZ_SERVICE" \
--package-name="${ROOT_PKG}.quiz" \
--groupId="${ROOT_PKG}.quiz" \
--dependencies=actuator,webflux,mybatis,mysql,lombok \
--version=1.0.0-SNAPSHOT \
"${QUIZ_SERVICE}"

cd "$QUIZ_SERVICE"


echo "$MAPSTRUCT_GRADLE_DEP" >> build.gradle
echo "$TESTCONTAINER_MYSQL_GRADLE_DEP" >> build.gradle
echo "$FALSE_JAR_GRADLE" >> build.gradle

./gradlew build \
&& echo "$QUIZ_SERVICE build successfully" \
|| echo "$QUIZ_SERVICE build failed"

cd ../

## QUESTION SERVICE ###################################

spring init \
--boot-version=3.2.0 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name="$QUESTION_SERVICE" \
--package-name="${ROOT_PKG}.question" \
--groupId="${ROOT_PKG}.question" \
--dependencies=actuator,webflux,mybatis,mysql,lombok \
--version=1.0.0-SNAPSHOT \
"${QUESTION_SERVICE}"

cd "$QUESTION_SERVICE"

echo "$MAPSTRUCT_GRADLE_DEP" >> build.gradle
echo "$TESTCONTAINER_MYSQL_GRADLE_DEP" >> build.gradle
echo "$FALSE_JAR_GRADLE" >> build.gradle

./gradlew build \
&& echo "$QUESTION_SERVICE build successfully" \
|| echo "$QUESTION_SERVICE build failed"

cd ../

## ANALYTICS SERVICE ###################################

spring init \
--boot-version=3.2.0 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name="$ANALYTICS_SERVICE" \
--package-name="${ROOT_PKG}.analytics" \
--groupId="${ROOT_PKG}.analytics" \
--dependencies=actuator,webflux,data-mongodb,lombok \
--version=1.0.0-SNAPSHOT \
"${ANALYTICS_SERVICE}"

cd "$ANALYTICS_SERVICE"

echo "$MAPSTRUCT_GRADLE_DEP" >> build.gradle
echo "$TESTCONTAINER_MONGODB_GRADLE_DEP" >> build.gradle
echo "$FALSE_JAR_GRADLE" >> build.gradle

./gradlew build \
&& echo "$ANALYTICS_SERVICE build successfully" \
|| echo "$ANALYTICS_SERVICE build failed"

cd "$CURRENT_DIR"