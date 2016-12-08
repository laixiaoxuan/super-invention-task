/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.nokia.radio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.Files;
import java.util.Arrays;

/**
 * Task starter.
 * Control starting or stopping the process. 
 * 
 *  @author <a href="laixiaoxuan@gmail.com">Adrian LAI</a>
 *
 */
public class Starter
 {
    /**
	 * slf4j logger interface
	 */
	protected static final transient Logger LOG = LoggerFactory.getLogger(Starter.class);
	
	/**
	 * Start process.
	 */
	private static void start() throws Exception
	{
		String hostName =  InetAddress.getLocalHost().getHostName();
		if (new File("../bin/" +hostName+ ".pid").exists()) 
		{
			throw new Exception("Pid file already exist: "+ Files.readFirstLine(new File("../bin/" + hostName + ".pid"),Charset.defaultCharset()));
		}
		Files.write(Tools.getPid(), new File("../bin/" + hostName+ ".pid"),Charset.defaultCharset());
		LOG.info("###############TASK \"DEMO TASK\" START####################");
		new Thread(new SimpleTask()).start();
		while(Thread.activeCount()>1)
		{
			//Waiting simple task thread finish.
			Thread.sleep(200);	
		}
		LOG.info("###############TASK \"DEMO TASK\" END####################");
		new File("../bin/" + hostName + ".pid").deleteOnExit();
	}

	/**
	 * Stop process.
	 */
	private static void stop() throws Exception 
	{
		String hostName =  InetAddress.getLocalHost().getHostName();
		String os = System.getProperties().getProperty("os.name");
		LOG.info("os.name-->" + os);
		String pid = Files.readFirstLine(new File("../bin/" + hostName + ".pid"),Charset.defaultCharset());
		ProcessBuilder processBuilder=null;
		if (os.indexOf("indows") > -1) 
		{
			//windows
			LOG.info("tskill " + pid + " /A /V");
			processBuilder = new ProcessBuilder("tskill", pid, "/A", "/V");
		} else 
		{
			//linux
			LOG.info("kill -9 " + pid);
			processBuilder = new ProcessBuilder("kill", "-9", pid);
		}
		Process process = processBuilder.start();
		String line;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));) 
		{
			while ((line = in.readLine()) != null) 
			{
				LOG.info(line);
			}
		}
		process.waitFor();
		new File("../bin/" + hostName + ".pid").deleteOnExit();
	}

    /**
	 * Main.
	 */
	public static void main(String[] args) throws Exception
	{
		switch (args[0]) 
		{
		case "start":start();break;
		case "stop":stop();break;
		default: throw new Exception(Arrays.toString(args));
		}
	}

}
