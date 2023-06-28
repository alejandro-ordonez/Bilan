FROM openjdk:11 as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw -B package --update-snapshots --no-transfer-progress --file pom.xml -Dmaven.test.skip=true -Pstage
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/BOOT-INF/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/BOOT-INF/classes
COPY --from=build ${DEPENDENCY}/org /app/org

WORKDIR /app

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]