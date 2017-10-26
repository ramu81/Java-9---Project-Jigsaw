# Java-9---Project-Jigsaw


package org.fastsocket;

import com.socket.NetworkSocket;

class FastNetworkSocket extends NetworkSocket {
	FastNetworkSocket() { }
	public void close() { }
}

dir /s /B *.java > sources.txt

javac -d mods --module-source-path src @sources.txt

cd src\edu.kondapalli

start notepad module-info.java

module edu.kondapalli {
	requires com.socket;
}

cd edu\greetings 
start notepad Greetings.java

package edu.greetings;
import com.socket.NetworkSocket;
public class Greetings {
	public static void main(String[] args) {
		NetworkSocket s = NetworkSocket.open();
        System.out.println(s.getClass());
	}
}

dir /s /B *.java > sources.txt

javac -d mods --module-source-path src @sources.txt

java -p mods -m edu.kondapalli/edu.greetings.Greetings
