FROM java:8
ADD ./target /opt/target/
WORKDIR /opt/target
RUN adduser admin
USER admin
CMD java -Xms16m -Xmx124m -XX:MaxMetaspaceSize=64m -XX:CompressedClassSpaceSize=8m -Xss256k -Xmn8m -XX:InitialCodeCacheSize=4m -XX:ReservedCodeCacheSize=8m -XX:MaxDirectMemorySize=16m -jar member-service-0.0.1-SNAPSHOT.jar