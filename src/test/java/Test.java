import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        try(WebClient wc = new WebClient()) {
            wc.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page = wc.getPage("https://www.mstu.edu.ru/study/timetable/");
            System.out.println(page.getTitleText());
            String xpath = "/html/body/div[5]/div/div/div/div[5]/div/div[1]/form/p/select";
            List<HtmlSelect> selects = page.getByXPath(xpath);
            selects.get(1).setSelectedAttribute("4", true);
            selects.get(2).setSelectedAttribute("1", true);
            xpath = "/html/body/div[5]/div/div/div/div[5]/div/div[1]/form/p/button";
            HtmlButton bt = (HtmlButton) page.getByXPath(xpath).get(0);
            page = bt.click();
            xpath = "/html/body/div[5]/div/div/div/div[3]/div/div[1]/div/table/tbody/tr";
            List<HtmlTableRow> rows = page.getByXPath(xpath);
            for (HtmlTableRow row : rows) {
                HtmlTableDataCell dataCell = (HtmlTableDataCell) row.getCell(1);
                HtmlAnchor anchor = (HtmlAnchor) dataCell.getByXPath("a").get(0);
                System.out.println(anchor.getTextContent());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
