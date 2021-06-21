# Makefile for api-supplychain-storebackoffice-ticketprepa-mongo
# ----------------------------------------------------------------

help: ## print this message
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-10s\033[0m %s\n", $$1, $$2}'


envs:=export LOCAL=1 && export MAVEN_OPTS='-Xms1g -Xmx3g'

start-dependencies: ## start docker-compose up (warn : not used by integration-tests)
	docker-compose up -d

stop-dependencies: ## stop docker-compose
	docker-compose stop

generate-license-headers: ## add the license to the source files if not exits
	mvn com.mycila:license-maven-plugin:format -Dlicense.skipExistingHeaders=true

run: ## run project (need of dependencies)
	$(envs) && mvn compile && mvn -Dspring-boot.run.profiles=local spring-boot:run

run-bench: ## run bench up to 5 users for 2 minutes
	$(envs) && mvn -Dusers1=5 gatling:test -Pbench

install:  ## build Maven project
	$(envs) && mvn clean install

install-skip-tests: ## build Maven project and skip tests
	$(envs) && mvn clean install -DskipTests

integration-tests: ## run integration tests with embedded dependencies
	$(envs) && mvn clean test

integration-tests-with-docker: ## run integration tests with docker-compose dependencies
	$(envs) && export START_TEST_CONTAINERS=true && mvn clean test



load-mongo-seeds: ## push indexes and load seeds
	$(envs) && mvn clean install -DskipTests && unzip -qo target/*jar -d target/tmp && java -cp "target/tmp/BOOT-INF/lib/*" com.boulanger.plato.mongo.MongoSeedLoader localhost 27017 "src/test/resources/seeds/mongo"
