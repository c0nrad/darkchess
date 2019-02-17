run: build
	mvn exec:java -Dexec.mainClass="com.c0nrad.darkchess.App"

ui:
	cd client; npm start

release: build build-ui
	mvn package

build:
	mvn package

build-ui:
	cd client; npm run build; cd ..;

clean:
	rm -rf ./target

test: 
	mvn test -Dsurefire.useFile=false -X;