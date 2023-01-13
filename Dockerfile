FROM sbtscala/scala-sbt:openjdk-17.0.2_1.8.0_3.2.1 as builder

WORKDIR /build

COPY project project

COPY build.sbt .

RUN sbt update
# Then build
COPY . .

RUN sbt assembly

FROM azul/zulu-openjdk:17-jre
WORKDIR /app

COPY --from=builder /build/target/scala-3.2.0/. .

CMD ["java", "-jar", "quoridor.jar"]
