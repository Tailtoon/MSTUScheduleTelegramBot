package Schedule;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;

public class ScheduleParser {


    public Schedule parse(String institute, Integer course, String group) {
        Schedule sc = null; // In case of error null returned
        try(WebClient wc = new WebClient()) {
            wc.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page = wc.getPage("https://www.mstu.edu.ru/study/timetable/");
            String xpath = "/html/body/div[5]/div/div/div/div[5]/div/div[1]/form/p/select";
            List<HtmlSelect> selects = page.getByXPath(xpath); // Get selects to set institute and course
            HtmlSelect instSelect = selects.get(1);
            HtmlSelect courseSelect = selects.get(2);
            instSelect.setSelectedAttribute(instSelect.getOptionByText(institute), true);
            courseSelect.setSelectedAttribute(courseSelect.getOptionByValue(course.toString()), true);
            xpath = "/html/body/div[5]/div/div/div/div[5]/div/div[1]/form/p/button";
            HtmlButton bt = (HtmlButton) page.getByXPath(xpath).get(0);
            page = bt.click();

            xpath = "/html/body/div[5]/div/div/div/div[3]/div/div[1]/div/table/tbody/tr";
            List<HtmlTableRow> rows = page.getByXPath(xpath);
            //To print out all groups for ИАТ 1 course
//            for (HtmlTableRow row : rows) {
//                HtmlTableDataCell dataCell = (HtmlTableDataCell) row.getCell(0);
//                HtmlAnchor anchor = (HtmlAnchor) dataCell.getByXPath("b/a").get(0);
//                System.out.print(anchor.getTextContent() + " ");
//                dataCell = (HtmlTableDataCell) row.getCell(1);
//                anchor = (HtmlAnchor) dataCell.getByXPath("a").get(0);
//                System.out.println(anchor.getTextContent());
//            }
            for (HtmlTableRow row : rows) {
                HtmlTableDataCell dataCell = (HtmlTableDataCell) row.getCell(0);
                HtmlAnchor anchor = (HtmlAnchor) dataCell.getByXPath("b/a").get(0);
                if (anchor.getTextContent().equals(group)) {
                    page = anchor.click();
                    xpath = "/html/body/div[5]/div/div/div/div[4]/div/table";
                    List<HtmlTable> tables = page.getByXPath(xpath);
                    if (tables.size() == 7) {
                        tables.remove(6); // Remove last element, because page contains one empty unneeded table
                        List<Day> days = new ArrayList<Day>();
                        for (HtmlTable table : tables) {
                            List<DoublePeriod> dps = new ArrayList<DoublePeriod>();
                            for (int i = 1; i < 8; i++) {
                                int num = Integer.parseInt(table.getCellAt(i, 0).getTextContent());
                                String name = table.getCellAt(i, 1).getTextContent();
                                String teacher = table.getCellAt(i, 2).getTextContent();
                                String cab = table.getCellAt(i, 3).getTextContent();
                                dps.add(new DoublePeriod(num, name, teacher, cab));
                            }
                            days.add(new Day(dps));
                        }
                        sc = new Schedule(days);
                        sc.debugPrintSchedule();
                        return sc;
                    }
                    else {
                        throw new Exception("ScheduleParseError: Amount of tables doesn't match 7");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return sc;
        }
        return sc;
    }
}
