package org.nag.translator;

import org.nag.translator.source.SourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Scanner;

public class TranslatorController {

    public static void main(String[] args) throws IOException {
        //initialization
    	ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    	SourceLoader sourceLoader = context.getBean("sourceLoader", SourceLoader.class);
    	Translator translator = context.getBean("translator", Translator.class);

        Scanner scanner = new Scanner(System.in);
        String command = null;
        String source = null;
        String translation = null;
        do {
        	System.out.print("Enter the Path with text to translate (type 'exit' to exit): ");
            command = scanner.next();
            //the only way to stop the application is to do that manually or type "exit"
            if(!command.equals("exit")){
            	try {
            	    source = sourceLoader.loadSource(command);
            	    if(source.equals("Wrong path")) {
            	    	System.out.println("\nThe path you have entered is wrong. Please enter another path.\n");
            	    	continue;
            	    }
            	} catch (IOException e) {
            	    System.out.println(e.getMessage());
            	}
            	
                if(source != null ) {
            	    translation = translator.translate(source);
            	    System.out.println("Original: \n" + source + "\n");
            	    System.out.println("Translation: \n" + translation + "\n");
            	}
            	source = translation = null;
            }
        } while(!command.equals("exit"));
        scanner.close();
        System.out.println("Have a nice day!");
    }
}
