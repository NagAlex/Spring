package org.nag.translator.source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SourceLoader should contains all implementations of SourceProviders to be able to load different sources.
 */
@Component
public class SourceLoader {
	
    private List<SourceProvider> sourceProviders = new ArrayList<>();
    
    public List<SourceProvider> getSourceProviders() {
		return sourceProviders;
	}

    @Autowired
    public void setSourceProviders(List<SourceProvider> sourceProviders) {
		this.sourceProviders = sourceProviders;
	}

    public String loadSource(String pathToSource) throws IOException {

    	String text = "Wrong path";
    	for(SourceProvider sp: sourceProviders)
            if(sp.isAllowed(pathToSource)) text = sp.load(pathToSource);
        
        return text;
    }
}
