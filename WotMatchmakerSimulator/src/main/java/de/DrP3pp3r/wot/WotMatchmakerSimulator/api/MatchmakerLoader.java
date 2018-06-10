package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.ServiceLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.IMatchmaker;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.ThreadedMatchmaker;

public class MatchmakerLoader
{
	public static IMatchmaker loadMatchmaker(String jarFilePath)
	{
		try
		{
			File file = new File(jarFilePath);

			if(file.exists())
			{
				System.out.format("File exists.\n");
			}
			else
			{
				System.out.format("File does not exists.\n");
			}

			URL url = file.toURI().toURL();
			URL[] urls = new URL[] { url };
			URLClassLoader loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

			for(URL u : loader.getURLs())
			{
				System.out.format("%s\n", u.toString());
			}
			
			System.out.format("Loading Matchmakers...\n");

			ServiceLoader<ThreadedMatchmaker> matchmakers = ServiceLoader.load(ThreadedMatchmaker.class, loader);

			System.out.format("Loaded Matchmakers.\n");

			for(IMatchmaker mm : matchmakers)
			{
				System.out.format("Matchmaker '%s' found!'\n", mm.getClass().toString());
				return(mm);
			}
		}
		catch(MalformedURLException ex)
		{
			System.out.format("Bad URL: %s\n", ex.getMessage());
		}

		return null;
	}
	
	public static IMatchmaker loadMatchmaker2(String jarFilePath)
	{
		IMatchmaker result = null;
		JarFile jarFile = null;
		try
		{
			File file = new File(jarFilePath);
			if(file.exists())
			{
				System.out.format("File exists.\n");
			}
			else
			{
				System.out.format("File does not exists.\n");
			}

			URL url = file.toURI().toURL();
			URL[] urls = new URL[] { url };
			URLClassLoader loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

			for(URL u : loader.getURLs())
			{
				System.out.format("%s\n", u.toString());
			}
	
			jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> e = jarFile.entries();
			while(e.hasMoreElements())
			{
				JarEntry je = e.nextElement();
				if(je.isDirectory() || !je.getName().endsWith(".class"))
				{
					continue;
				}
				
				// -6 because of .class
				String className = je.getName().substring(0, je.getName().length() - 6);
				className = className.replace('/', '.');
				Class<?> c = null;
				try
				{
					System.out.format("Trying to load class '%s'.\n", className);
					c = loader.loadClass(className);
				}
				catch(ClassNotFoundException e1)
				{
					System.out.format("Class '%s' not found.\n", className);
				}
				
				Boolean isMatchmaker = false;
				if(c != null)
				{
					if(Arrays.asList(c.getInterfaces()).contains(IMatchmaker.class))
					{
						isMatchmaker = true;
						System.out.format("Found IMatchmaker!\n");
						break;
					}
					
					Class<?> superClass = c.getSuperclass();
					while(superClass != null) {
						if(Arrays.asList(superClass.getInterfaces()).contains(IMatchmaker.class))
						{
							isMatchmaker = true;
							System.out.format("Found IMatchmaker!\n");
							break;
						}
						superClass = superClass.getSuperclass();
					}
				}
				
				if(isMatchmaker)
				{
					try
					{
						result = (IMatchmaker)c.newInstance();
						break;
					}
					catch(InstantiationException ex)
					{
					
					}
					catch(IllegalAccessException ex)
					{
					}
				}
				else
				{
					System.out.format("Class '%s' is no IMatchmaker.\n", className);
				}
			
			}
		}
		catch(MalformedURLException ex)
		{
			System.out.format("Bad URL: %s\n", ex.getMessage());
		}
		catch(IOException ex)
		{
			System.out.format("IO error: %s\n", ex.getMessage());
		}
		finally
		{
			try
			{
				if(jarFile != null)
				{
					jarFile.close();
				}
			}
			catch(IOException ex)
			{
				System.out.format("Error closing jar: %s\n", ex.getMessage());
			}
		}

		return result;
	}
}
