build:
	./gradlew build
clean:
	./gradlew clean 
jar:
	mkdir -p export
	./gradlew jar 
	mv ./engine/build/libs/engine-1.0.jar export/magma-engine.jar
	mv ./engine-desktop/build/libs/engine-desktop-1.0.jar export/magma-engine-desktop.jar

.PHONY: build clean jar
