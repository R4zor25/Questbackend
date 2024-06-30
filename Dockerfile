# Használj egy alap képet, ami tartalmazza a szükséges JDK-t
FROM eclipse-temurin:21-jdk-jammy
# 2. Munkakönyvtár beállítása a konténerben

VOLUME /tmp
COPY build/libs/quest.backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]