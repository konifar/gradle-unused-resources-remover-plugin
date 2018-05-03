check:
	./gradlew clean check bintrayUpload

publish: check
	./gradlew releng
	./gradlew -PdryRun=false --info plugin:bintrayUpload