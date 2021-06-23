import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class GuestHeaderFooterPageEvent extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) 
    {
        // This is the main header of the PDF File
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, new BaseColor(/*RED*/ 0, /*GREEN*/ 0, /*BLUE*/ 0));
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(Methods.getHEADER(), font),
            (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
        
        // This is the main footer of the PDF File
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(Methods.getFOOTER()),
            (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 5, 0);
    }

}
