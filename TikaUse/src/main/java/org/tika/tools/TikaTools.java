package org.tika.tools;

import jdk.internal.org.xml.sax.SAXException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

public class TikaTools {


    /**
     * Tika AutoDetectParser类来识别和抽取内容
     * @throws TikaException
     * @throws SAXException
     * @throws IOException
     */
    public static void getTextFronPDF(){
        String content = "";
        try {
            InputStream  input = new FileInputStream(new File("data/test1.pdf"));
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            parser.parse(input, handler, metadata);
            content = handler.toString();
            input.close();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);

    }


    public static void main(String[] args) throws IOException, TikaException, org.xml.sax.SAXException {
        getTextFronPDF();
    }

}
