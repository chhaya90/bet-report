windows console is not using UTF-8 by default,
inorder to get correctly visible euro symbol in report.
chcp 65001  : for displaying UTF-8 on Windows console.

To build and run docker Image :

1. cd {project-directory}
2. docker build -t bet-report .
3. docker run -p <port> bet-report

To build and run jar file :

1. cd {project-directory}
2. mvn clean install
3. java -jar cd/target/<bet-report-snapshot>.jar