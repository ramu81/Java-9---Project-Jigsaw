# Java-9---Project-Jigsaw


jar --create --file=mlib/com.world@1.0.jar --module-version=1.0 -C mods/com.world .

jar --create --file=mlib/edu.kondapalli.jar --main-class=edu.greetings.Greetings -C mods/edu.kondapalli .

java -p mlib -m edu.kondapalli

jar --describe-module --file=mlib/com.world@1.0.jar

jar --describe-module --file=mlib/edu.kondapalli.jar


Services

Services allow for loose coupling between service consumers modules and service providers modules.

mkdir src\com.socket\com\socket

cd src\com.socket

start notepad module-info.java

module com.socket {
	exports com.socket;
	exports com.socket.spi;
	uses com.socket.spi.NetworkSocketProvider;
}

cd com\socket
	
start notepad NetworkSocket.java

package com.socket;

import java.io.Closeable;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.socket.spi.NetworkSocketProvider;

public abstract class NetworkSocket implements Closeable {
	protected NetworkSocket() { }

	public static NetworkSocket open() {
		ServiceLoader<NetworkSocketProvider> sl
			= ServiceLoader.load(NetworkSocketProvider.class);
		Iterator<NetworkSocketProvider> iter = sl.iterator();
		if (!iter.hasNext())
			throw new RuntimeException("No service providers found!");
		NetworkSocketProvider provider = iter.next();
		return provider.openNetworkSocket();
	}
}
	
mkdir src\com.socket\com\socket\spi

cd src\com.socket\com\socket\spi

start notepad NetworkSocketProvider.java

package com.socket.spi;

import com.socket.NetworkSocket;

public abstract class NetworkSocketProvider {
	protected NetworkSocketProvider() { }

	public abstract NetworkSocket openNetworkSocket();
}
	
mkdir src\org.fastsocket\org\fastsocket

cd src\org.fastsocket
	
start notepad module-info.java

module org.fastsocket {
	requires com.socket;
	provides com.socket.spi.NetworkSocketProvider
		with org.fastsocket.FastNetworkSocketProvider;
}

cd org\fastsocket

start notepad FastNetworkSocketProvider.java

package org.fastsocket;

import com.socket.NetworkSocket;
import com.socket.spi.NetworkSocketProvider;

public class FastNetworkSocketProvider extends NetworkSocketProvider {
	public FastNetworkSocketProvider() { }

	@Override
	public NetworkSocket openNetworkSocket() {
		return new FastNetworkSocket();
	}
}

start notepad FastNetworkSocket.java

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
