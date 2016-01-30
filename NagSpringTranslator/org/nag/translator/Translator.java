package org.nag.translator;

import org.nag.translator.source.URLSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Provides utilities for translating texts to russian language.<br/>
 * Uses Yandex Translate API, more information at <a href="http://api.yandex.ru/translate/">http://api.yandex.ru/translate/</a><br/>
 * Depends on {@link URLSourceProvider} for accessing Yandex Translator API service
 */
@Component
public class Translator {
    @Autowired
    private URLSourceProvider urlSourceProvider;
	/**
     * Yandex Translate API key could be obtained at <a href="http://api.yandex.ru/key/form.xml?service=trnsl">http://api.yandex.ru/key/form.xml?service=trnsl</a>
     * to do that you have to be authorized.
     */
    private static final String YANDEX_API_KEY = "Your Yandex Translate API key should be here";
    private static final String TRANSLATION_DIRECTION = "uk"; // es - Espanol

    public URLSourceProvider getUrlSourceProvider() {
		return urlSourceProvider;
	}

	public void setUrlSourceProvider(URLSourceProvider urlSourceProvider) {
		this.urlSourceProvider = urlSourceProvider;
	}
    
    /**
     * Translates text to ukrainian language
     * @param original text to translate
     * @return translated text
     * @throws IOException
     */
    public String translate(String original) throws IOException {
        String transl = urlSourceProvider.load( prepareURL(original) );
        String clearTransl = parseContent(transl);
        return clearTransl;
    }

    /**
     * Prepares URL to invoke Yandex Translate API service for specified text
     * @param text to translate
     * @return url for translation specified text
     */
    private String prepareURL(String text) {
        return "https://translate.yandex.net/api/v1.5/tr/translate?key=" + YANDEX_API_KEY + "&text=" + encodeText(text) + "&lang=" + TRANSLATION_DIRECTION + "&options=1";
    }

    /**
     * Parses content returned by Yandex Translate API service. Removes all tags and system texts. Keeps only translated text.
     * @param content that was received from Yandex Translate API by invoking prepared URL
     * @return translated text
     */
    private String parseContent(String content) {
        StringBuffer strBuf = new StringBuffer(content);
        int firstOccur = strBuf.indexOf("<text>");
        int lastOccur = strBuf.lastIndexOf("</text>");

        return strBuf.substring(firstOccur + 6, lastOccur);
    }

    /**
     * Encodes text that need to be translated to put it as URL parameter
     * @param text to be translated
     * @return encoded text
     */
    private String encodeText(String text) {
        try {
            String encText = URLEncoder.encode(text, "UTF-8");
            return encText;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Can't translate text into UTF-8 format: " + e.getMessage());
        }
        return "";
    }
}
