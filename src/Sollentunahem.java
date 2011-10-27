import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Sollentunahem extends Thread {
    private static final Logger LOG = Logger.getLogger(Sollentunahem.class.getName());
    private String baseUrl;
    private List<Apartmant> registeredApartmants = new ArrayList<Apartmant>();
    private WebClient webClient;
    private String persunnummer;
    private String password;
    private boolean onlyFavorites;
    private int numberOfAvailableApartmants = 0;

    public Sollentunahem(boolean onlyFavorites) throws Exception {
        this.onlyFavorites = onlyFavorites;
        baseUrl = "https://www.sollentunahem.se/";

    }

    private void InitWebClient() {
        webClient = new WebClient(BrowserVersion.FIREFOX_3);
        webClient.setThrowExceptionOnScriptError(false);
        webClient.setRefreshHandler(new RefreshHandler() {
            public void handleRefresh(Page page, URL url, int arg) throws IOException {
                System.out.println("handleRefresh");
            }
        });
    }

    public Sollentunahem() throws Exception {
        this(true);
    }

    public HtmlPage login(String username, String password) throws IOException {
        InitWebClient();
        HtmlPage page = (HtmlPage) webClient.getPage(baseUrl);
        HtmlForm form = page.getFormByName("Default");
        form.getInputByName("ucTop:txtUserID").setValueAttribute(username);
        form.getInputByName("ucTop:txtPassword").setValueAttribute(password);
        return form.getInputByName("ucTop:btnLogin").click();
    }

    public HtmlPage gotoAvailable(HtmlPage page) throws IOException {
        HtmlForm form;
        form = page.getFormByName("frmMain");
        HtmlAnchor availableListAnchor = onlyFavorites ?
                (HtmlAnchor) form.getFirstByXPath("//*[@id=\"hlListMatchingApartments\"]") :
                (HtmlAnchor) form.getFirstByXPath("//*[@id=\"hlListApartments\"]");
        page = availableListAnchor.click();
        return page;
    }

    private void register(HtmlTableRow htmlTableRow, Apartmant apartmant) throws IOException {
        List<HtmlTableCell> cells = htmlTableRow.getCells();
        HtmlTable detailsTable = (HtmlTable) cells.get(0).getChildElements().iterator().next();
        HtmlAnchor linkAnchor = (HtmlAnchor) detailsTable.getRows().get(0).getCells().get(1).getChildElements().iterator().next();
        HtmlPage page = linkAnchor.click();
        HtmlForm form = page.getFormByName("form1");
        HtmlInput registrationButton = null;
        try {
            registrationButton = form.getInputByName("btnRegister");
        } catch (ElementNotFoundException e) {
            LOG.info("We have already regisrtered for this apartmant.");
            return;
        }
        page = registrationButton.click();
        LOG.info("Successfully registred for an apartmant: " + apartmant);
        registeredApartmants.add(apartmant);

    }

    private void readRegisteredApartmants(HtmlTable table) {
        registeredApartmants.clear();
        List<HtmlTableRow> tableRows = table.getRows();
        for (int i = 1; i < tableRows.size(); i++) {
            HtmlTableRow row = tableRows.get(i);
            registeredApartmants.add(new Apartmant(
                    Integer.parseInt(row.getCell(0).getTextContent()),
                    row.getCell(1).getTextContent(),
                    Integer.parseInt(row.getCell(2).getTextContent()),
                    Integer.parseInt(row.getCell(3).getTextContent()),
                    Integer.parseInt(row.getCell(4).getTextContent()),
                    Integer.parseInt(row.getCell(5).getTextContent())));
        }
    }

    public void findAndRegisterForLastMinutes(HtmlPage availablePage) throws IOException {
        HtmlForm form = availablePage.getFormByName("form1");
        HtmlTable table = form.getFirstByXPath("//*[@id=\"dgList\"]");
        List<HtmlTableRow> htmlTableRows = table.getRows();
        numberOfAvailableApartmants += htmlTableRows.size() -1 ;
        for (int i = 1; i < htmlTableRows.size(); i++) {
            HtmlTableRow htmlTableRow = htmlTableRows.get(i);
            Apartmant apartmant = new Apartmant(htmlTableRow);
            LOG.info("Adding apartmant: " + apartmant);
            if (apartmant.isLastMinute()) {
                LOG.info("A lastminute apartmant was found.");
                register(htmlTableRow, apartmant);
            }
        }
    }

    public List<Apartmant> getRegisteredApartmants() {
        return registeredApartmants;
    }

    public void run() {
        LOG.debug("Starting...");
        try {
            HtmlPage myPage = login(persunnummer, password);
            HtmlPage availablePage = gotoAvailable(myPage);
            while (availablePage != null) {
                findAndRegisterForLastMinutes(availablePage );
                availablePage = gotoNextPage(availablePage);
                sleep(500);
                for (Apartmant apartmant : getRegisteredApartmants()) {
                    LOG.info("Registered apartmant: " + apartmant.getLghNr());
                }
                LOG.info("Number of registered apartmants: " + getRegisteredApartmants().size());
            }
        } catch (Exception e) {
            LOG.error("Failed while finding last minute apartmanats", e);
        }
    }

    private HtmlPage gotoNextPage(HtmlPage availablePage) throws IOException {
        HtmlForm form = availablePage.getFormByName("form1");
        HtmlInput nextButton = form.getFirstByXPath("//*[@id=\"ucNavigationBarSimple_btnNext\"]");
        if (!nextButton.getDisabledAttribute().equals("disabled")) {
            return nextButton.click();
        }
        HtmlInput firstButton = form.getFirstByXPath("//*[@id=\"ucNavigationBarSimple_btnFirst\"]");
        if (!firstButton.getDisabledAttribute().equals("disabled")) {
            LOG.debug("Available apartmants:" + numberOfAvailableApartmants);
            numberOfAvailableApartmants = 0;
            return firstButton.click();
        }
        LOG.debug("Available apartmants:" + numberOfAvailableApartmants);
        HtmlPage myPage = login(persunnummer, password);
        return  gotoAvailable(myPage);
    }

    public void start(String persunnummer, String password) {
        this.persunnummer = persunnummer;
        this.password = password;
        start();
    }
}