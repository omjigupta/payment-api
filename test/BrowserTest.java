import org.junit.Test;
import play.Application;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.WithBrowser;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class BrowserTest extends WithBrowser {

    protected Application provideApplication() {
        return fakeApplication(inMemoryDatabase());
    }

    protected TestBrowser provideBrowser(int port) {
        return Helpers.testBrowser(port);
    }

    @Test
    public void test() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort());
        assertTrue(browser.pageSource().contains(""));
    }
}
