FROM gradle:7.5.1@sha256:7867aaca4ee9166e754f9b35bc5d2a87a6064aa2fa7b56c8279983e2c01d4e38 AS BUILDER

WORKDIR /app/

# Copy dependency-related files
COPY build.gradle.kts gradle.properties settings.gradle.kts /app/

# Only download dependencies
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

# Copy all files
COPY ./ /app/

# Build jar
RUN gradle clean shadowJar --no-daemon

FROM adoptopenjdk/openjdk16:alpine@sha256:7e5e2a48d07672a13ff4d11a62cdb2fbad4b9e0fc50a66ec42d043fa4554480f

COPY --from=BUILDER /app/build/libs/apollo-backend.jar .

ENTRYPOINT [ \
    "java", \
    "-server", \
    "-jar", \
    "apollo-backend.jar" \
]
