package org.nag.translator.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

/**
 * Implementation for loading content from local file system.
 * This implementation supports absolute paths to local file system without specifying file:// protocol.
 * Examples c:/1.txt or d:/pathToFile/file.txt
 */
@Component
public class FileSourceProvider implements SourceProvider {

    public boolean isAllowed(String pathToSource) {

    	try (FileReader fr = new FileReader(pathToSource)){
    	    int ch = fr.read();
    	    if (ch != -1) return true;
    	} catch (IOException e) {
    		return false;
    	}
        return false;
    }

    public String load(String pathToSource) throws IOException {
    	try (BufferedReader br = new BufferedReader(new FileReader(pathToSource)) ) {
    	    StringBuffer text = new StringBuffer();
            int nextChar;

            while ((nextChar = br.read()) != -1) {
                text = text.append((char) nextChar);
            }

            return text.toString();

        } catch (IOException e) {
            System.out.println("Cannot read from file: " + e.getMessage());
        }
        return null;
    }
}
