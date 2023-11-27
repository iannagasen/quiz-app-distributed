CURRENT_DIR=$(pwd)
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"
echo $(pwd)
cd ..
WORKING_DIR=$(pwd)/src/backend/
cd "$WORKING_DIR"

# # clean up on failure
# function cleanup() {
#   echo "has errors"
#   rm -rf "$WORKING_DIR/"
# }
# trap cleanup EXIT

# exit immediately if any command failed
set -e


spring init \
--boot-version=3.2.0 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api \
--package-name=dev.agasen.microsrv.api \
--groupId=dev.agasen.microsrv.api \
--dependencies=webflux,lombok \
--version=1.0.0-SNAPSHOT \
api

cd "$WORKING_DIR"

spring init \
--boot-version=3.2.0 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=util \
--package-name=dev.agasen.microsrv.util \
--groupId=dev.agasen.microsrv.util \
--dependencies=webflux,lombok \
--version=1.0.0-SNAPSHOT \
util