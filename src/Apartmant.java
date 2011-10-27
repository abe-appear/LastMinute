import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import org.apache.log4j.Logger;

import java.util.List;

public class Apartmant {
  private static final Logger LOG = Logger.getLogger(Apartmant.class);
  private int lghNr;
  private boolean lastMinute;
  private String address;
  private String region;
  private int numberOfRooms;
  private int livingArea;
  private int rent;
  private String date;
  private int numberOfApplicants;
  private int floor;

  public Apartmant(HtmlTableRow htmlTableRow) {
    List<HtmlTableCell> cells = htmlTableRow.getCells();
    lastMinute = checkForLastMinute((HtmlTable) cells.get(0).getChildElements().iterator().next());
    address = cells.get(1).getTextContent();
    region = cells.get(2).getTextContent();
    numberOfRooms = Integer.parseInt(cells.get(3).getTextContent());
    livingArea = Integer.parseInt(cells.get(4).getTextContent());
    rent = Integer.parseInt(cells.get(5).getTextContent());
    date = cells.get(6).getTextContent();
  }

  public Apartmant(int lghNr, String address, int numberOfRooms, int livingArea, int floor, int rent) {
    this.lghNr = lghNr;
    this.address = address;
    this.numberOfRooms = numberOfRooms;
    this.livingArea = livingArea;
    this.rent = rent;
    this.floor = floor;
  }

  public boolean isLastMinute() {
    return lastMinute;
  }

  public int getLghNr() {
    return lghNr;
  }

  @Override
  public String toString() {
    return "Apartmant{" +
           "lghNr=" + lghNr +
           ", lastMinute=" + lastMinute +
           ", address='" + address + '\'' +
           ", region='" + region + '\'' +
           ", numberOfRooms=" + numberOfRooms +
           ", livingArea=" + livingArea +
           ", rent=" + rent +
           ", date='" + date + '\'' +
           ", numberOfApplicants=" + numberOfApplicants +
           ", floor=" + floor +
           '}';
  }

  private boolean checkForLastMinute(HtmlTable detailsTable) {
    for(HtmlTableRow htmlTableRow : detailsTable.getRows()) {
      for(HtmlTableCell htmlTableCell : htmlTableRow.getCells()) {
        String id = htmlTableCell.getAttribute("id");
        if(id != null && id.toLowerCase().contains("directsearch")) {
          return true;
        }
      }
    }
    return false;
  }
}
