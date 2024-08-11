compile:
	javac -d destDir src/example/hello/Hello.java src/example/hello/Server.java src/example/hello/Client.java

rmi:
	# Change to the destination directory and run all commands in the same subshell
	cd destDir
	pkill rmiregistry 
	rmiregistry & 

runServer:
	java -cp destDir example.hello.Server

runClient:
	java -cp destDir example.hello.Client		 
