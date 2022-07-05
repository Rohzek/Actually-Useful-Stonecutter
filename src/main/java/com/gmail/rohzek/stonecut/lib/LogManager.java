package com.gmail.rohzek.stonecut.lib;

import org.apache.logging.log4j.Level;

import com.gmail.rohzek.stonecut.ActuallyUsefulStonecutter;

public class LogManager 
{
	public static void Log(String message) 
	{
		ActuallyUsefulStonecutter.LOGGER.log(Level.ALL, message);
	}
}
