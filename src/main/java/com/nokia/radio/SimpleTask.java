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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo: simple task.
 * Random print a warn or error log 1mi later.
 * 
 *  @author <a href="laixiaoxuan@gmail.com">Adrian LAI</a>
 *
 */
public class SimpleTask implements Runnable
{
	/**
	 * slf4j logger interface
	 */
	protected static final transient Logger LOG = LoggerFactory.getLogger(SimpleTask.class);

    /*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
    @Override
	public void run() 
	{
		try 
		{
			//Sleeping 1mi.
			Thread.sleep(60*1000);
			
			//Random print a warn or error log.
			long time = new java.util.Date().getTime();
			if (time % 2 == 0) 
			{
				LOG.warn(String.valueOf(time));
			} else 
			{
				LOG.error(String.valueOf(time));
			}
		} catch (Exception e) 
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
