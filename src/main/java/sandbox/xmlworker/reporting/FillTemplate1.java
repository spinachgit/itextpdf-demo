package sandbox.xmlworker.reporting;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.html.Tags;
import sandbox.WrapToTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@WrapToTest
public class FillTemplate1 {

    public static final String DEST = "results/xmlworker/report1.pdf";
    public static final String PDF =  "resources/pdfs/stationery.pdf";
    public static final String HTML = "resources/xml/movies.html";
    public static final String CSS = "resources/xml/style1.css";
    
    public void createPdf(String result) throws IOException, DocumentException {
        FillTemplateHelper template = new FillTemplateHelper(PDF);
        template.setSender("Bruno Lowagie\nAdolf Baeyensstraat 121\n9040 Sint-Amandsberg\nBELGIUM");
        template.setReceiver("iText Software Corp.\nCambridge Innovation Center\n1 Broadway, 14th Floor\nCambridge, MA 02142 USA");
        // step 1
        Document document = new Document(template.getPageSize(),
            template.getmLeft(), template.getmRight(), template.getmTop(), template.getmBottom());
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(result));
        writer.setPageEvent(template);
        // step 3
        document.open();
        // step 4
        ElementList elements = FillTemplateHelper.parseHtml(HTML, CSS, Tags.getHtmlTagProcessorFactory());
        for (Element e : elements) {
            document.add(e);
        }
        // step 5
        document.close();
    }
    
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillTemplate1().createPdf(DEST);
    }
}
