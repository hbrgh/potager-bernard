package potager.btalva.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class VariousUtils {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VariousUtils.class);
	
	public static String MESS_ERR_FATALE_NB_ARGS_INCORRECT = "Incorrect number of launch args!";
	

	
	public static void harakiri(Exception e, String nameOfMainClass) {
		LOGGER.error(e.toString());
		LOGGER.error("{} : ZE TRAGIC END!", nameOfMainClass);
		throw new RuntimeException(e);
	}
	
	public static void harakiri(Exception e) {
		harakiri(e, getMainClassName() );
	}

	
	public static void harakiri(String errFatale, String nameOfMainClass) {
		LOGGER.error(errFatale);
		LOGGER.error("{} : ZE TRAGIC END!", nameOfMainClass);
		throw new RuntimeException(errFatale);
	}
	
	public static void harakiri(String errFatale) {
		harakiri(errFatale, getMainClassName() );
		
	}
	
	
	
	public static void happyBeginning(String nameOfMainClass) {
		LOGGER.info("{} : ZE HAPPY BEGINNING!", nameOfMainClass);
		
	}
	
	public static void happyEnd(String nameOfMainClass) {
		LOGGER.info("{} : ZE HAPPY END!", nameOfMainClass);
		
	}
	
	public static void writeLaunchedJVMdetails() {
		
		LOGGER.info(System.getProperty("java.vm.name"));
		LOGGER.info(System.getProperty("java.vm.version"));
		LOGGER.info(System.getProperty("java.version"));
		LOGGER.info(System.getProperty("java.runtime.version"));
		LOGGER.info(System.getProperty("java.specification.vendor"));
		// get a RuntimeMXBean reference
		RuntimeMXBean lRuntimeMXBean = ManagementFactory.getRuntimeMXBean();
		LOGGER.info("JVM NAME: {}",lRuntimeMXBean.getName());
		// get the jvm's input arguments as a list of strings
		List<String> listOfArguments = lRuntimeMXBean.getInputArguments();

		// print the arguments using my logger
		for (String a : listOfArguments) {
			LOGGER.info("JVM ARG: {}",a);
		}
		


		
	
	}
	
	public static void writeLaunchedApplicationArgs() {
		

		LOGGER.info("COMMAND LINE ARGUMENTS: {}", System.getProperty("sun.java.command"));

		
		
	}
	
	
	public static String getMainClassName() {
		
		StackTraceElement[] stack = Thread.currentThread ().getStackTrace();
		StackTraceElement main = stack[stack.length - 1];
		String mainClass = main.getClassName();
		return(mainClass);
	}


}
